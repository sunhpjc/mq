//package com.sunhp.rocketmq.comsumer;
//
//import org.apache.rocketmq.spring.annotation.ConsumeMode;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Service;
//
///**
// * @author sunhp
// * @Description
// * @Date 2020/4/30 11:26
// **/
//@Service
//@RocketMQMessageListener(nameServer = "127.0.0.1:8081",topic = "orderTopic2",consumerGroup = "comsumer2",consumeMode = ConsumeMode.ORDERLY)
//public class Comsumer1 implements RocketMQListener<String> {
//
//    @Override
//    public void onMessage(String message) {
//        System.out.println("==========="+message+"=========");
//    }
//}
