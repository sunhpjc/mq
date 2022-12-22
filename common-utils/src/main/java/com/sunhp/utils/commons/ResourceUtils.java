package com.sunhp.utils.commons;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.stereotype.Component;

/**
 * @author sunhp
 * @Description
 * @Date 2022/12/22 10:09
 **/
@Component
public class ResourceUtils {
    /**
     * 获取文件路径
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getFilePath(String fileName) throws UnsupportedEncodingException {
        String path = this.getClass()
            .getClassLoader()
            .getResource(fileName)
            .getPath();
        String filePath = URLDecoder.decode(path, "UTF-8");
        return filePath;
    }

    /**
     * 获取父文件夹路径
     * @param filePath
     * @return
     */
    public String getFileParentPath(String filePath) {
        File file = new File(filePath);
        String parent = file.getParent();
        return parent;
    }
}
