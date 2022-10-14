package com.sunhp.activiti.we_chat_inter.comparable_test;

/**
 * @author sunhp
 * @Description
 * @Date 2022/9/21 16:37
 **/
public class ComparatorMain {
    public static void main(String[] args) {
        ComparatorA comparatorA = new ComparatorA();
        System.out.println(comparatorA.compare("A", "B"));
        System.out.println(comparatorA.compare("B", "A"));
        System.out.println(comparatorA.compare("A", "A"));

    }
}
