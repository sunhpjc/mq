package com.sunhp.rocketmq.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.sunhp.rocketmq.dao.SmsSqlOptimizeDao;
import com.sunhp.rocketmq.entity.SmsStatistics;
import com.sunhp.rocketmq.dao.SmsStatisticsDao;
import com.sunhp.rocketmq.enums.ResponseCodeEnum;
import com.sunhp.rocketmq.service.SmsStatisticsService;
import com.sunhp.rocketmq.utils.TimeUtils;
import com.sunhp.rocketmq.vo.request.CountReq;
import com.sunhp.rocketmq.vo.response.CountTotalResp;
import com.sunhp.rocketmq.vo.response.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * (SmsStatistics)表服务实现类
 *
 * @author sunhp
 * @since 2021-04-20 11:38:48
 */
@Service("smsStatisticsService")
public class SmsStatisticsServiceImpl implements SmsStatisticsService {
    //每次相差一个小时
    private static final int INT_INTERVAL_HOUR = 1;

    @Resource
    private SmsStatisticsDao smsStatisticsDao;
    @Resource
    private SmsSqlOptimizeDao smsSqlOptimizeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SmsStatistics queryById(Object id) {
        return this.smsStatisticsDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SmsStatistics> queryAllByLimit(int offset, int limit) {
        return this.smsStatisticsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param smsStatistics 实例对象
     * @return 实例对象
     */
    @Override
    public SmsStatistics insert(SmsStatistics smsStatistics) {
        this.smsStatisticsDao.insert(smsStatistics);
        return smsStatistics;
    }

    /**
     * 修改数据
     *
     * @param smsStatistics 实例对象
     * @return 实例对象
     */
    @Override
    public SmsStatistics update(SmsStatistics smsStatistics) {
        this.smsStatisticsDao.update(smsStatistics);
        return this.queryById(smsStatistics.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Object id) {
        return this.smsStatisticsDao.deleteById(id) > 0;
    }

    /**
     * 获取每小时数据
     *
     * @return
     */
    @Override
    public ResultVO getCount() {
        boolean flag = false;
//
//        Date date = new Date();
//        CountReq countReq = new CountReq();
//        String nextHour = TimeUtils.getLastHourTime(date, 1);
//        SmsStatistics smsStatistics = smsStatisticsDao.getPreviousId();
//        String nowHour = "";
//        if(null == smsStatistics){
//            flag = true;
//            nowHour = TimeUtils.getLastHourTime(date, 0);
//            countReq.setNowHour(nowHour);
//        }
//        else {
//            Long previousId = smsStatistics.getPreviousId();
//            String timeFlag = smsStatistics.getTimeFlag();
//
//
//            countReq.setPreviousId(previousId);
//        }
//        countReq.setNextHour(nextHour);
//
//        CountTotalResp countTotalResp = smsSqlOptimizeDao.selectCountTotal(countReq);
//        Long countTotal = countTotalResp.getCountTotal();
//
//        if(0 != countTotal){
//            Long previousId = countTotalResp.getPreviousId();
//            Long endId = previousId + countTotal - 1;
//
//            SmsStatistics smsStatistics1 = new SmsStatistics();
//            smsStatistics1.setTimeFlag(nowHour);
//            smsStatistics1.setStatus(1);
//            smsStatistics1.setCreateUser("ucn");
//            smsStatistics1.setUpdateUser("ucn");
//            smsStatistics1.setPreviousId(endId);
//            if(flag){
//                //插入数据
//                smsStatistics1.setSmsTotal(countTotal);
//
//                CountReq countReq1 = new CountReq();
//                countReq1.setNowHour(nowHour);
//                countReq1.setPreviousId(endId);
//                Long countSuccess = smsSqlOptimizeDao.selectCountSuccess(countReq1);
//                smsStatistics1.setSmsSuccess(null == countSuccess ? 0 : countSuccess);
//                smsStatisticsDao.insert(smsStatistics1);
//            }
//            else {
//                //更新数据
//                Long smsTotal = smsStatistics.getSmsTotal();
//                Long smsSuccess = smsStatistics.getSmsSuccess();
//
//                CountReq countReq2 = new CountReq();
//                countReq2.setBeginId(previousId);
//                countReq2.setPreviousId(endId);
//                Long countSuccess1 = smsSqlOptimizeDao.selectCountSuccess(countReq2);
//                smsStatistics1.setSmsSuccess(null == countSuccess1 ? 0 : countSuccess1 + smsSuccess);
//                smsStatistics1.setSmsTotal(countTotal + smsTotal);
//                smsStatisticsDao.update(smsStatistics1);
//            }
//        }
        return new ResultVO(ResponseCodeEnum.SUCCESS);
    }

    /**
     * 获取每小时数据
     *
     * @return
     */
    @Override
    public ResultVO getCount1() {
        //查询统计表是否有数据，没有数据统计最近一个月的，有数据看最后一条数据是否与当前时间匹配（匹配：统计当前小时，不匹配：统计最后一条数据，直到当前小时）
        SmsStatistics smsStatistics = smsStatisticsDao.getPreviousId();
        Date date = new Date();
        if (null == smsStatistics) {
            //没有数据统计最近30天的
            Date lastDayTimeDate = TimeUtils.getLastDayTimeDate(date, -29);
            while (lastDayTimeDate.before(date) || lastDayTimeDate.equals(date)) {
                Date startTime = lastDayTimeDate;
                Date endTime = TimeUtils.getLastHourTimeDate(startTime, 1);
                SmsStatistics smsStatistics1 = getSmsStatistics(startTime, endTime);
                smsStatisticsDao.insert(smsStatistics1);
                lastDayTimeDate = TimeUtils.getLastHourTimeDate(lastDayTimeDate, 1);
            }
        } else {
            Date timeFlag = smsStatistics.getTimeFlag();
            Date timeFlagFormat = TimeUtils.getLastHourTimeDate(timeFlag, 0);
            Date dateFormat = TimeUtils.getLastHourTimeDate(date, 0);

            if (timeFlagFormat.before(dateFormat)) {
                //不匹配：统计最后一条数据的下一小时，直到当前小时
                Date startTime = TimeUtils.getLastHourTimeDate(timeFlagFormat, 1);
                Date endTime1 = TimeUtils.getLastHourTimeDate(startTime, 1);

                while (timeFlagFormat.before(dateFormat) || timeFlagFormat.equals(dateFormat)) {
//                    Date startTime = timeFlagFormat;
//                    Date endTime = TimeUtils.getLastHourTimeDate(startTime, 1);
//                    SmsStatistics smsStatistics1 = getSmsStatistics(startTime, endTime);
//                    smsStatisticsDao.insert(smsStatistics1);
//                    timeFlagFormat = TimeUtils.getLastHourTimeDate(timeFlagFormat, 1);
                }
            } else {
                //匹配：统计当前到之前72小时，全做更新
                Date timeFlag1 = timeFlagFormat;
                Date startTime = TimeUtils.getLastHourTimeDate(timeFlag1, -71);

                while (startTime.before(timeFlag1) || startTime.equals(timeFlag1)) {
                    Date endTime1 = TimeUtils.getLastHourTimeDate(startTime, 1);
                    SmsStatistics smsStatistics1 = getSmsStatistics(startTime, endTime1);
                    smsStatistics1.setTimeFlag(startTime);
                    smsStatisticsDao.updateByTime(smsStatistics1);
                    startTime = TimeUtils.getLastHourTimeDate(startTime, 1);
                }
            }
        }
        return null;
    }


    /**
     * 获取每小时数据
     *
     * @return
     */
    @Override
    public ResultVO getCount2() {
        //查询统计表是否有数据，没有数据统计最近一个月的，有数据看最后一条数据是否与当前时间匹配（匹配：统计当前小时，不匹配：统计最后一条数据，直到当前小时）
        SmsStatistics smsStatistics = smsStatisticsDao.getPreviousId();
        Date date = new Date();
        if (null == smsStatistics) {
            //没有数据统计最近30天的
            Date lastDayTimeDate = TimeUtils.getLastDayTimeDate(date, -29);
            dealDate(lastDayTimeDate, date, 0);
        } else {
            Date timeFlag = smsStatistics.getTimeFlag();
            Date timeFlagFormat = TimeUtils.getLastHourTimeDate(timeFlag, 0);
            Date dateFormat = TimeUtils.getLastHourTimeDate(date, 0);
            if (timeFlagFormat.before(dateFormat)) {
                //不匹配：统计最后一条数据的下一小时，直到当前小时
                Date startTime = TimeUtils.getLastHourTimeDate(timeFlagFormat, 1);
                dealDate(startTime, dateFormat, 0);
            } else {
                long l = System.currentTimeMillis();
                //匹配：统计当前到最近12小时，全做更新
                Date startTime = TimeUtils.getLastHourTimeDate(timeFlagFormat, -11);
                dealDate(startTime, dateFormat, 1);
                System.out.println("耗时"+ (System.currentTimeMillis() - l));
            }
        }
        return new ResultVO(ResponseCodeEnum.SUCCESS);
    }

    /**
     * 0:插入操作，1:更新操作
     * @param startTime
     * @param nowTime
     * @param method
     */
    private void dealDate(Date startTime, Date nowTime, int method) {
        while (startTime.before(nowTime) || startTime.equals(nowTime)) {
            long l = System.currentTimeMillis();
            Date endTime = TimeUtils.getLastHourTimeDate(startTime, INT_INTERVAL_HOUR);
            SmsStatistics smsStatistics1 = getSmsStatistics(startTime, endTime);
            if (0 == method) {
                smsStatisticsDao.insert(smsStatistics1);
            } else if (1 == method) {
                smsStatisticsDao.updateByTime(smsStatistics1);
            }
            startTime = TimeUtils.getLastHourTimeDate(startTime, INT_INTERVAL_HOUR);
            System.out.println("每次循环耗时" + (System.currentTimeMillis() - l));
        }
    }

    /**
     * 处理时间Bean
     * @param startTime
     * @param endTime
     * @return
     */
    private SmsStatistics getSmsStatistics(Date startTime, Date endTime) {
        CountReq countReq = new CountReq();
        countReq.setPreviousId(0L);
        countReq.setNowHour(startTime);
        countReq.setNextHour(endTime);
        Long countSuccess = smsSqlOptimizeDao.selectCountSuccess(countReq);
        CountTotalResp countTotalResp = smsSqlOptimizeDao.selectCountTotal(countReq);

        SmsStatistics smsStatistics = new SmsStatistics();
        smsStatistics.setTimeFlag(startTime);
        smsStatistics.setStatus(1);
        smsStatistics.setCreateUser("ucn");
        smsStatistics.setUpdateUser("ucn");
        smsStatistics.setSmsTotal(null == countTotalResp.getCountTotal() ? 0 : countTotalResp.getCountTotal());
        smsStatistics.setSmsSuccess(null == countSuccess ? 0 : countSuccess);

        return smsStatistics;
    }
}