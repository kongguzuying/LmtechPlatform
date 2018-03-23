package com.lmtech.util;

import com.lmtech.ext.MappingJackson2HttpMessageExtConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

public class RestTemplateBuilder {
    /**
     * 构建RestTemplate
     * @return
     */
    public static RestTemplate buildRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        if (!CollectionUtil.isNullOrEmpty(messageConverters)) {
            //处理中文乱码问题
            for (HttpMessageConverter<?> messageConverter : messageConverters) {
                if (messageConverter instanceof StringHttpMessageConverter) {
                    ((StringHttpMessageConverter) messageConverter).setDefaultCharset(Charset.forName("utf-8"));
                    break;
                }
            }
            messageConverters.add(new MappingJackson2HttpMessageExtConverter());
        }
        return restTemplate;
    }
}
