package com.sunhp.activiti.we_chat_inter.comparable_test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunhp
 * @Description
 * @Date 2022/9/21 14:12
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComparableA implements Comparable<ComparableA> {
    private String param1;
    private int param2;

    @Override
    public int compareTo(ComparableA o) {
        if (this.getParam1()
            .compareTo(o.getParam1()) > 0) {
            return 1;
        } else if (this.getParam1()
            .compareTo(o.getParam1()) == 0) {
            return 0;
        } else {
            return -1;
        }
    }
}
