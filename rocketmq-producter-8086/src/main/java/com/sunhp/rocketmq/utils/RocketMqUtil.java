package com.sunhp.rocketmq.utils;

import com.alibaba.fastjson.JSONObject;
import com.sunhp.rocketmq.config.RocketMqConfig;
import com.sunhp.rocketmq.entity.Sms;
import com.sunhp.rocketmq.enums.ResponseCodeEnum;
import com.sunhp.rocketmq.exception.MyException;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author sunhp
 * 使用官方Client时使用此配置
 * @Description
 * @Date 2021/1/13 13:35
 **/
@Service("rocketMqUtil")
public class RocketMqUtil {
    private static final Logger logger = LoggerFactory.getLogger(RocketMqUtil.class);
    //SendResult [sendStatus=SEND_OK, msgId=13135C45521058644D4641089A200000, offsetMsgId=C0A8123D00002A9F00000000000000A8,
    // messageQueue=MessageQueue [topic=smsTopic, brokerName=broker-b, queueId=0], queueOffset=0]

    @Resource
    private RocketMqConfig config;

    /**
     * Producer端发送同步消息
     * 这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知
     * @param message
     * @param tag
     */
    public SendResult SyncProducer(String message, String keys, String tag, String topic) throws Exception {
        DefaultMQProducer producer = getProducer();
        producer.setRetryTimesWhenSendFailed(2);

        //最佳实践：每个消息在业务层面的唯一标识码要设置到keys字段，方便将来定位消息丢失问题。
        // 服务器会为每个消息创建索引（哈希索引），应用可以通过topic、key来查询这条消息内容，以及消息被谁消费。
        Message msg = new Message(topic, tag, keys, message.getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult result = producer.send(msg);
        result.getSendStatus();

        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
        return result;
    }

    /**
     * 发送异步消息
     * 异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。
     * @param message
     * @param tag
     * @throws Exception
     */
    public void AsyncProducer(String message, String tag, String topic) throws Exception {
        DefaultMQProducer producer = getProducer();
        producer.setRetryTimesWhenSendAsyncFailed(config.getAsyncRetryTimes());

        int messageCount = 1;
        // 根据消息数量实例化倒计时计算器(这里不控制具体条数，放在message中，注意rocket默认消息大小上限为4M)
        final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);
        // 创建消息，并指定Topic，Tag和消息体
        Message msg = new Message(topic,tag, message.getBytes(RemotingHelper.DEFAULT_CHARSET));

        // SendCallback接收异步返回结果的回调
        producer.send(msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                SendStatus sendStatus = sendResult.getSendStatus();
                switch (sendStatus){
                    case SEND_OK:
                        logger.info("RocketMQ asynchronous message sent successfully,{}", JSONObject.toJSONString(sendResult));
                        break;
                    default:
                        logger.error("RocketMQ asynchronous message sent fail,{}",JSONObject.toJSONString(sendResult));
                        break;
                }
            }
            @Override
            public void onException(Throwable e) {
                logger.error("RocketMQ failed to send a message asynchronously,{}",e);
            }
        });
        // 等待5s
        countDownLatch.await(5, TimeUnit.SECONDS);
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

    /**
     * 消费消息(推方式)
     * @param topic
     * @param tages
     */
    public void consumer(String topic, String[] tages) throws Exception{
        DefaultMQPushConsumer consumer = getConsumer();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tages.length; i++) {
            sb.append(tages[i]).append(" || ");
        }
        sb.substring(0, sb.length()-4);

        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(topic, sb.toString());
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            msgs.forEach(msg ->{
                logger.info("====消费消息{}", JSONObject.parseObject(msg.getBody(), Sms.class).toString());
            });
//            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);

            // 标记该消息已经被成功消费
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 启动消费者实例
        consumer.start();
    }
    /**
     * 获取生产者实例并启动生产者
     * @return
     */
    private DefaultMQProducer getProducer(){
        //实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(config.getGroupName());
        if(producer != null){
            //设置NameServer的地址
            producer.setNamesrvAddr(config.getNameServer());
            try {
                //启动Producer实例
                producer.start();
            } catch (MQClientException e) {
                logger.error("Failed to start Producer instance,{}",e);
            }
            return producer;
        }else {
            logger.info("Rocketmq producer instantiation failed");
            throw new MyException(ResponseCodeEnum.ROCKETMQ_ERROR.getCode(),"Rocketmq producer instantiation failed");
        }
    }

    /**
     * 获取消费者实例
     * @return
     */
    private DefaultMQPushConsumer getConsumer() {
        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(config.getGroupName());

        // 设置NameServer的地址
        consumer.setNamesrvAddr(config.getNameServer());

        if(consumer != null){
            return consumer;
        }
        else {
            logger.info("Rocketmq consumer instantiation failed");
            throw new MyException(ResponseCodeEnum.ROCKETMQ_ERROR.getCode(),"Rocketmq consumer instantiation failed");
        }

/*        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe("TopicTest", "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer Started.%n");*/
    }
}
