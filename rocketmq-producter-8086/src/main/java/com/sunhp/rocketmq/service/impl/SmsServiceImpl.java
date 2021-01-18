package com.sunhp.rocketmq.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sunhp.rocketmq.entity.Sms;
import com.sunhp.rocketmq.dao.SmsDao;
import com.sunhp.rocketmq.enums.ResponseCodeEnum;
import com.sunhp.rocketmq.service.SmsService;
import com.sunhp.rocketmq.utils.ListUtil;
import com.sunhp.rocketmq.utils.RedisUtil;
import com.sunhp.rocketmq.utils.ThreadPoolExecutorUtil;
import com.sunhp.rocketmq.vo.response.ResultVO;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * (Sms)表服务实现类
 *
 * @author makejava
 * @since 2020-12-31 11:27:57
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Resource
    private ThreadPoolExecutorUtil threadPoolExecutorUtil;
    @Resource
    private SmsDao smsDao;
    @Resource
    private PlatformTransactionManager transactionManager;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public Sms queryById(Long id) {
        return this.smsDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Sms> queryAllByLimit(int offset, int limit, int status) {
        return this.smsDao.queryAllByLimit(offset, limit, status);
    }

    /**
     * 流式查询数据
     *
     * @param limit
     * @return
     */
    @Override
    @Transactional
    public ResultVO smsCursor(int limit) {
        Cursor<Sms> smsCursor = smsDao.smsCursor(limit);
        smsCursor.forEach(sms -> {
            System.out.println(sms.toString());
        });
        logger.info("======{},状态{},是否取完{}",smsCursor.getCurrentIndex(),smsCursor.isOpen(),smsCursor.isConsumed());
        return null;
    }

    /**
     * 新增数据
     *
     * @param sms 实例对象
     * @return 实例对象
     */
    @Override
    public Sms insert(Sms sms) {
        this.smsDao.insert(sms);
        return sms;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insertBatch(List<Sms> smsList) {
        ResultVO resultVO = new ResultVO(ResponseCodeEnum.UNEXPECTED_EXCEPTION.getCode(),"短信入库失败");
        if(smsList == null || smsList.size() == 0){
            resultVO = new ResultVO(ResponseCodeEnum.PARAM_ERROR.getCode(),"短信内容为空");
        }
        try {
            this.smsDao.insertBatch(smsList);
        } catch (Exception e) {
            logger.error("短信批量入库失败{}",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultVO;
        }
        resultVO = new ResultVO(ResponseCodeEnum.SUCCESS);
        return resultVO;
    }

    @Override
    public ResultVO buildSms() {
        List<Sms> smsList = new ArrayList<>();
        String batchNo = redisUtil.generatorId();
        for (int i = 0; i < 10; i++) {
            Sms sms = new Sms();
            sms.setBatchNo(batchNo);
            sms.setPhone("1878426891"+i);
            sms.setContent("测试内容："+i);
            smsList.add(sms);
        }
        smsDao.insertBatch(smsList);

        return new ResultVO(ResponseCodeEnum.SUCCESS);
    }

    /**
     * 测试多线程插入数据
     *
     * @return
     */
    @Override
    public ResultVO buildSmsTest() {
        ResultVO resultVO = new ResultVO(ResponseCodeEnum.UNEXPECTED_EXCEPTION);
        List<Sms> smsList = new ArrayList<>();
        String batchNo = redisUtil.generatorId();
        for (int i = 0; i < 10000; i++) {
            Sms sms = new Sms();
            sms.setBatchNo(batchNo);
            sms.setPhone("1878426891"+i);
            sms.setContent("测试内容："+i);
            smsList.add(sms);
        }
/*        logger.info("单线程执行开始");
        long startTime1 = System.currentTimeMillis();
        smsDao.insertBatch(smsList);
        long endTime1 = System.currentTimeMillis();
//        logger.info("当前线程：{}",Thread.currentThread().getName());
        logger.info("单线程耗时：{}ms",endTime1 - startTime1);*/

        logger.info("多线程执行开始");
        List<List<Sms>> lists = ListUtil.listSplit(smsList, 2000, 2000);
        ThreadPoolExecutor pool = threadPoolExecutorUtil.getInstance();
        long startTime2 = System.currentTimeMillis();

        CountDownLatch rollBackLatch = new CountDownLatch(1);
        CountDownLatch mainThreadLatch = new CountDownLatch(1);
        AtomicBoolean rollBackFlag = new AtomicBoolean(false);
        for (int i = 0; i < lists.size(); i++){
            List<Sms> sms = lists.get(i);
            pool.execute(()->{
                if(rollBackFlag.get()){
                    logger.info("线程异常，数据回滚");
                    return;//如果其它线程报错，就停止线程
                }
                DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
                //事务隔离级别，开启新事务，这样会比较安全些
                defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                //获取事务状态
                TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);
                try {
                    smsDao.insertBatch(sms);
                    mainThreadLatch.countDown();
                    rollBackLatch.await();//线程等待
                    if(rollBackFlag.get()){
                        transactionManager.rollback(status);
                    }
                    else {
                        transactionManager.commit(status);
                    }
                } catch (Exception e) {
                    rollBackFlag.set(true);
                    rollBackLatch.countDown();
                    mainThreadLatch.countDown();
                    transactionManager.rollback(status);

                    logger.error("短信入库失败，{}",e);
                }
            });
//            if(i == 5){
//                int j = 8/0;//验证回滚通过
//            }
        }
//        int j = 8/0;//验证回滚通过
        if (!rollBackFlag.get()){
//            int j = 8/0;//验证回滚通过
            try {
                mainThreadLatch.await();
            } catch (InterruptedException e) {
                logger.error("系统异常InterruptedException,{}",e);
            }
            rollBackLatch.countDown();
//            int j = 8/0;//在这里出现异常无法回滚
        }
//        int k = 8/0;//在这里出现异常无法回滚

        long endTime2 = System.currentTimeMillis();
        logger.info("多线程耗时：{}ms",endTime2 - startTime2);

        return new ResultVO(ResponseCodeEnum.SUCCESS);
    }

    /**
     * 修改数据
     *
     * @param sms 实例对象
     * @return 实例对象
     */
    @Override
    public Sms update(Sms sms) {
        this.smsDao.update(sms);
        return this.queryById(sms.getId());
    }

    /**
     * 更新数据
     *
     * @param sms
     * @param status
     * @return
     */
    @Override
    public Sms update(Sms sms, int status) {
        smsDao.updateWithStatus(sms, status);
        return queryById(sms.getId());
    }

    @Override
    public ResultVO updateBatch(List<Sms> smsList, int status) {
        ResultVO resultVO = new ResultVO(ResponseCodeEnum.UNEXPECTED_EXCEPTION);
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("smsList",smsList);

        try {
            smsDao.updateBatch(map);
            resultVO = new ResultVO(ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            logger.error("更新短信状态为:{},更新失败,{}===>短信内容:{}",status,e, JSONObject.toJSONString(smsList));
        }
        return resultVO;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Object id) {
        return this.smsDao.deleteById(id) > 0;
    }
}