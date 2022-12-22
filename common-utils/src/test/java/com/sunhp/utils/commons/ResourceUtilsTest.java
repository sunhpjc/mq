package com.sunhp.utils.commons;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sunhp
 * @Description
 * @Date 2022/12/22 10:17
 **/
@SpringBootTest
@Slf4j
public class ResourceUtilsTest {
    @Autowired
    ResourceUtils resourceUtils;

    @Test
    public String getFilePath(){
        String filePath = null;
        try {
            filePath = resourceUtils.getFilePath("application.yml");
        } catch (UnsupportedEncodingException e) {
            log.error("获取文件路径异常:{}", e);
        }
        log.info("文件路径为:{}", filePath);
        return filePath;
    }

    @Test
    public void getFileParentPath(){
        String filePath = resourceUtils.getFileParentPath(getFilePath());
        log.info("文件路径为:{}", filePath);
    }
}
