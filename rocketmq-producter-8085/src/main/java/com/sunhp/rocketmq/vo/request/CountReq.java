package com.sunhp.rocketmq.vo.request;

import lombok.Data;

import java.util.Date;

/**
 * @author sunhp
 * @Description
 * @Date 2021/4/21 14:32
 **/
@Data
public class CountReq {
    private Long previousId;
    private Date nextHour;
    private Date nowHour;

    private Long beginId;//统计表存在数据，统计成功条数的开始id
}
