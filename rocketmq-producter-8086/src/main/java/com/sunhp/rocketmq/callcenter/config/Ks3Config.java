package com.sunhp.rocketmq.callcenter.config;

import com.ksyun.ks3.dto.CallBackConfiguration;
import com.ksyun.ks3.dto.GetObjectResult;
import com.ksyun.ks3.dto.Ks3Object;
import com.ksyun.ks3.dto.ObjectMetadata;
import com.ksyun.ks3.http.HttpClientConfig;
import com.ksyun.ks3.service.Ks3;
import com.ksyun.ks3.service.Ks3Client;
import com.ksyun.ks3.service.Ks3ClientConfig;
import com.ksyun.ks3.service.common.StorageClass;
import com.ksyun.ks3.service.request.GetObjectRequest;
import com.ksyun.ks3.service.request.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;

/**
 * @Description 金山云服务
 * @Param
 * @return
 **/
@Component
@Configuration
public class Ks3Config {

    public static final Duration DEFAULT_EXPIRE_TIME = Duration.ofDays(365);
    private final Logger logger = LoggerFactory.getLogger(Ks3Config.class);

    @Value("${ks3.endpoint}")
    private String endpoint;
    @Value("${ks3.bucketName}")
    private String bucketName;
    @Value("${ks3.accessKeyId}")
    private String accessKeyId;
    @Value("${ks3.accessKeySecret}")
    private String accessKeySecret;
    @Value("${ks3.ossPath}")
    private String ossPath;
    /**
     * 初始化客户端
     *
     * @return
     */
    private Ks3 initClient() {
        Ks3ClientConfig config = new Ks3ClientConfig();
        config.setEndpoint(endpoint);
        /**
         *true：表示以自定义域名访问
         *false：表示以KS3的外网域名或内网域名访问，默认为false
         */
        config.setDomainMode(false);
        config.setProtocol(Ks3ClientConfig.PROTOCOL.http);
        /**
         *true表示以   endpoint/{bucket}/{key}的方式访问
         *false表示以  {bucket}.endpoint/{key}的方式访问
         *如果domainMode设置为true，pathStyleAccess可忽略设置
         */
        config.setPathStyleAccess(false);
        HttpClientConfig hconfig = new HttpClientConfig();
        config.setHttpClientConfig(hconfig);
        Ks3 client = new Ks3Client(accessKeyId, accessKeySecret, config);
        return client;
    }

    /**
     * @Description 上传文件
     **/
    public String ossUpLoad(String objectName, byte[] data, CallBackConfiguration callBackConfig) throws Exception {
        Ks3 client = initClient();
        if (ossPath.indexOf("/") == -1) {
            ossPath = ossPath + "/";
        }
        ObjectMetadata meta = new ObjectMetadata();
        PutObjectRequest request = new PutObjectRequest(bucketName, ossPath + objectName,new ByteArrayInputStream(data), meta);
        // 设置为标准存储
        request.setStorageClass(StorageClass.Standard);
        // 可以指定内容的长度,否则程序会把整个输入流缓存起来,可能导致jvm内存溢出
        meta.setContentLength(data.length);
//        request.setCannedAcl(CannedAccessControlList.PublicRead);
        if (callBackConfig != null) {
            request.setCallBackConfiguration(callBackConfig);
        }
        logger.info("=》上传文件fileName:{}", bucketName);
        client.putObject(request);
        String fileUrl = client.generatePresignedUrl(bucketName, ossPath + objectName, (int) DEFAULT_EXPIRE_TIME.getSeconds());
        logger.info("《=上传文件fileName:{},返回上传后的路径：{}", bucketName, fileUrl);
//        if (fileUrl.contains("?")) {
//            // 移除 encodedParams ?AccessKeyId=
//            fileUrl = fileUrl.substring(0, fileUrl.indexOf("?"));
//        }
//        logger.info("上传文件fileName:{},返回上传后截取后的路径：{}", bucketName, fileUrl);
        return fileUrl;
    }
    /**
     * 根据objectName查看URL
     * @param objectName
     * @return
     * @throws Exception
     */
    public String getUrlByObjectName(String objectName) throws Exception {
        Ks3 client = initClient();
        if (ossPath.indexOf("/") == -1) {
            ossPath = ossPath + "/";
        }
        logger.info("=》上传文件fileName:{}", objectName);
        String fileUrl = client.generatePresignedUrl(bucketName, ossPath + objectName, (int) DEFAULT_EXPIRE_TIME.getSeconds());
        logger.info("《=上传文件fileName:{},返回上传后的路径：{}", bucketName);
//        if (fileUrl.contains("?")) {
//            // 移除 encodedParams ?AccessKeyId=
//            fileUrl = fileUrl.substring(0, fileUrl.indexOf("?"));
//        }
//        logger.info("上传文件fileName:{},返回上传后截取后的路径：{}", bucketName, fileUrl);
        return fileUrl;
    }


    /**
     * 文件下载
     *
     * @param objectName：文件名
     * @return
     */
    public InputStream ossDownload(String objectName) throws Exception {
    	logger.info("=》金山云文件下载开始fileName:{}", objectName);
        if (ossPath.indexOf("/") == -1) {
            ossPath = ossPath + "/";
        }
        Ks3 client = initClient();
        GetObjectRequest request = new GetObjectRequest(bucketName, ossPath + objectName);
        // 只接受数据的0-10字节,通过控制该项可以实现分块下载
        /*request.setRange(0,10);*/
        GetObjectResult result = client.getObject(request);
        Ks3Object object = result.getObject();
        ObjectMetadata meta = object.getObjectMetadata();
        // 当分块下载时获取文件的实际大小,而非当前小块的大小
        Long length = meta.getInstanceLength();
        // 获取object的输入流
        InputStream inputStream = object.getObjectContent();
        logger.info("《=金山云文件下载结束fileName:{}", objectName);
        return inputStream;
    }

    public static void main(String[] args) throws Exception {
//        File file = new File("E:\\IDEA\\workspace2\\mq\\rocketmq-producter-8085\\src\\main\\dataBak.sql");
//        InputStream inputStream = new FileInputStream(file);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
//        byte[] b = new byte[1024];
//        int n;
//        while ((n = inputStream.read(b)) != -1) {
//            byteArrayOutputStream.write(b, 0, n);
//        }
//        byte[] bytes = byteArrayOutputStream.toByteArray();
//        String s = new Ks3Config().ossUpLoad("database_tab_date", bytes,null);
//        System.out.println(s);

        InputStream inputStream1 = new Ks3Config().ossDownload("database_tab_date");
        int num;
        byte[] bytes1 = new byte[1024];
        OutputStream outputStream = new FileOutputStream("E:\\IDEA\\workspace2\\mq\\rocketmq-producter-8085\\src\\main\\ks3down\\dataBak.sql", true);
        while ((num = inputStream1.read(bytes1)) != -1) {
            outputStream.write(bytes1, 0, num);
            outputStream.flush();
        }
    }
}
