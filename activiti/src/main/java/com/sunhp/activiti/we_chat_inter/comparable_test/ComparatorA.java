package com.sunhp.activiti.we_chat_inter.comparable_test;

import java.util.Comparator;

/**
 * @author sunhp
 * @Description
 * @Date 2022/9/21 16:29
 **/
public class ComparatorA implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        if (o1.compareTo(o2) > 0) {
            return 1;
        } else if (o1.compareTo(o2) == 0) {
            return 0;
        } else {
            return -1;
        }
    }
}
