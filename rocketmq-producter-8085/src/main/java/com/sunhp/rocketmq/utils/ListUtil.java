package com.sunhp.rocketmq.utils;

import com.sunhp.rocketmq.entity.Sms;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunhp
 * @Description
 * maxSize:list最大值，超过这个值做切分 
 * splitSize:需要切分的大小(需要保证splitSize < maxSize)
 * @Date 2021/1/5 14:47
 **/
public class ListUtil {
    public static <T> List<List<T>> listSplit(List<T> list, Integer maxSize, Integer splitSize){
        List<List<T>> resultList = new ArrayList<>();

        int size = list.size();
        //可以分多少个组，（还有种方式：先整除，再求余判断是否有余数--->有：lenSize+1,无：lenSize不变）
        int lenSize = (size + splitSize -1)/splitSize;
        if(maxSize < size){
            for (int i = 0; i < lenSize; i++) {
                List<T> tList = list.subList(i * splitSize, (i + 1) * splitSize > size ? size : (i + 1) * splitSize);
                resultList.add(tList);
            }
        }
        else{
            resultList.add(list);
        }
        return resultList;
    }

    public static void main(String[] args) {
        List<Sms> smsList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Sms sms = new Sms();
            sms.setPhone("测试"+i);
            smsList.add(sms);
        }

        List<List<Sms>> lists = ListUtil.listSplit(smsList, 10, 9);
        for (List<Sms> listSms : lists
             ) {
            System.out.println(listSms.size());
        }
    }
}
