package com.sunhp.activiti.we_chat_inter.intf_test;

import java.util.Optional;

import com.alibaba.fastjson.JSONObject;

/**
 * @author sunhp
 * @Description
 * @Date 2022/10/24 16:40
 **/
public class JsonMainTest {
    public static void main(String[] args) {
        String str = "{\"name\": {\"value\": \"532123******1131\"}, \"prcie\": {\"value\": \"2022-10-24 15:22:12\"}}";
        // str = null;
        JSONObject nameObj;
        try {
            nameObj = (JSONObject) Optional.ofNullable(JSONObject.parseObject(str))
                .map(obj -> obj.get("name"))
                .get();
            nameObj.put("color", "测试颜色");
            JSONObject jsonObject = JSONObject.parseObject(str);
            jsonObject.put("name", nameObj);
            System.out.println(jsonObject.toJSONString());
        } catch (Exception e) {
            System.out.println("捕获异常,处理批量参数中的部门参数丢失，或者所有参数异常问题，其余代码继续往下走");
            System.out.println(e.getMessage());
        }
    }
}
