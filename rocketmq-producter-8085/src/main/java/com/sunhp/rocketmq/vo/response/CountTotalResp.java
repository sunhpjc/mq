package com.sunhp.rocketmq.vo.response;

import lombok.Data;

/**
 * @author sunhp
 * @Description
 * @Date 2021/4/21 9:46
 **/
@Data
public class CountTotalResp {
    private Long previousId;
    private Long countTotal;
}
