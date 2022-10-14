package com.sunhp.activiti.we_chat_inter.comparable_test;

/**
 * @author sunhp
 * @Description
 * @Date 2022/9/21 14:18
 **/
public class ComparableMain {
    public static void main(String[] args) {
        ComparableA A = new ComparableA("A", 1);
        ComparableA A_1 = new ComparableA("A", 1);
        ComparableA B = new ComparableA("B", 2);
        System.out.println(A.compareTo(B));
        System.out.println(A.compareTo(A_1));
    }
}
