package com.sunhp.utils.csv;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.sunhp.utils.commons.ListUtils;
import com.sunhp.utils.data.SendSms;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description
 * @Date 2022/12/22 10:05
 **/
@Component
@Slf4j
public class CsvUtils {
    final ListUtils listUtils;

    public CsvUtils(ListUtils listUtils) {this.listUtils = listUtils;}

    /**
     * 使用完整内容生成sql
     * @param csvFilePath 文件路径
     * @param csvWriteFilePath 生成文件路径
     */
    public void generateSqlWithContent(String csvFilePath, String csvWriteFilePath, SendSms sendSms){
        List<String[]> csvList = new ArrayList<>();
        try {
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("GBK"));
            while (reader.readRecord()) {
                csvList.add(reader.getValues());
            }
            reader.close();
            AtomicInteger i = new AtomicInteger();
            List<List<String[]>> listAssignList = listUtils.averageAssign(csvList, sendSms.getTotal());
            listAssignList.stream().forEach(listList->{
                i.getAndIncrement();
                StringBuilder stringBuilder = new StringBuilder("INSERT INTO send_sms\n"
                    + "(batch_no, app_code, template_id, target_phone, sms_status, sms_content, sms_mark, again_mark, again_count, create_time, create_user, update_time, update_user)\n"
                    + "VALUES\n");
                listList.stream().forEach(strings -> {
                    StringBuilder builder = new StringBuilder("(");
                    builder.append("\'" + sendSms.getBatchNo() + "_" + i + "\'").append(",")
                        .append("\'" + sendSms.getAppCode() + "\'").append(",")
                        .append("\'" + sendSms.getTemplateId() + "\'").append(",")
                        .append("\'" + strings[0] + "\'").append(",")
                        .append(0).append(",")
                        .append("\'" + strings[1] + "\'").append(",")
                        .append("\'" + sendSms.getSmsMark() + "\'").append(",")
                        .append(1).append(",")
                        .append(1).append(",")
                        .append("now()").append(",")
                        .append("\'" + sendSms.getCreateUser() + "\'").append(",")
                        .append("now()").append(",")
                        .append("\'" + sendSms.getUpdateUser() + "\'").append("),\n");
                    stringBuilder.append(builder);
                });
                CsvWriter csvWriter = new CsvWriter(csvWriteFilePath + "_" + i + ".sql", ',', Charset.forName("UTF-8"));
                String[] contents = {stringBuilder.toString()};
                try {
                    csvWriter.writeRecord(contents);
                } catch (IOException e) {
                    log.info("输出sql异常:{}", e);
                }
                csvWriter.close();
            });
        } catch (IOException e) {
            log.info("生成sql异常:{}", e);
        }
    }
}
