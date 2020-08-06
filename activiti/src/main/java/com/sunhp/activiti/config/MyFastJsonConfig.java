package com.sunhp.activiti.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunhp
 * @Description 处理null值
 * @Date 2020/4/10 9:37
 **/
public class MyFastJsonConfig extends WebMvcConfigurationSupport{
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                //保留map空的字段
                SerializerFeature.WriteMapNullValue,
                //将String类型的null转化为""
                SerializerFeature.WriteNullStringAsEmpty,
                //将number类型的null转化为0
                SerializerFeature.WriteNullNumberAsZero,
                //将List类型的null转化为[]
                SerializerFeature.WriteNullListAsEmpty,
                //将Boolean类型的null转化为false
                SerializerFeature.WriteNullBooleanAsFalse,
                //避免循环引用
                SerializerFeature.DisableCircularReferenceDetect
        );
        converter.setFastJsonConfig(fastJsonConfig);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        List<MediaType> mediaTypeList = new ArrayList<>();
        //解决中文乱码问题，相当于在Controller的@RequestMapping中加了个属性produces="application/json"
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        converters.add(converter);
    }
}
