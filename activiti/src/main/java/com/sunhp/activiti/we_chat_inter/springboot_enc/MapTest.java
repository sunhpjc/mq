package com.sunhp.activiti.we_chat_inter.springboot_enc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * @author sunhp
 * @Description
 * @Date 2022/9/26 16:41
 **/
public class MapTest {
    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>(11);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        day = 19;
        if (day < 20) {
            calendar.add(Calendar.MONDAY, -1);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 20);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(formatter.format(calendar.getTime()));
    }
}
