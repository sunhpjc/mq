package com.sunhp.utils.commons;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sunhp.utils.csv.CsvUtils;
import com.sunhp.utils.data.SendSms;

/**
 * @author sunhp
 * @Description
 * @Date 2022/12/22 11:37
 **/
@SpringBootTest
public class CsvUtilsTest {
    @Autowired
    CsvUtils csvUtils;

    /**
     * 线上产品模板topcardwebM20221219110000000044
     */
    @Test
    public void generateSqlWithContentOnline(){
        String csvFilePath = "C:\\Users\\jccfc\\Desktop\\ucn日常\\20221222年结短信\\source\\topcardwebM20221219110000000044.csv";
        String csvWriteFilePath = "C:\\Users\\jccfc\\Desktop\\ucn日常\\20221222年结短信\\topcardwebM\\topcardwebM";
        SendSms sendSms = new SendSms();
        sendSms.setAppCode("topcardwebM");
        sendSms.setBatchNo("20221223");
        sendSms.setSmsMark("JWTD");
        sendSms.setTemplateId("topcardwebM20221219110000000044");
        sendSms.setCreateUser("topcardwebM");
        sendSms.setUpdateUser("topcardwebM");
        sendSms.setTotal(2);
        csvUtils.generateSqlWithContent(csvFilePath, csvWriteFilePath, sendSms);
    }

    /**
     * 线下产品模板topcardweb20221219160000000009
     */
    @Test
    public void generateSqlWithContentOffline(){
        String csvFilePath = "C:\\Users\\jccfc\\Desktop\\ucn日常\\20221222年结短信\\source\\topcardweb20221219160000000009.csv";
        String csvWriteFilePath = "C:\\Users\\jccfc\\Desktop\\ucn日常\\20221222年结短信\\topcardweb\\topcardweb";
        SendSms sendSms = new SendSms();
        sendSms.setAppCode("topcardweb");
        sendSms.setBatchNo("20221223");
        sendSms.setSmsMark("JWTD");
        sendSms.setTemplateId("topcardweb20221219160000000009");
        sendSms.setCreateUser("topcardweb");
        sendSms.setUpdateUser("topcardweb");
        sendSms.setTotal(2);
        csvUtils.generateSqlWithContent(csvFilePath, csvWriteFilePath, sendSms);
    }
}
