package com.lmtech.infrastructure;

import com.lmtech.http.interceptor.LoggingRequestInterceptor;
import com.lmtech.util.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LbRestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate lbRestTemplate() {
        RestTemplate restTemplate = RestTemplateBuilder.buildRestTemplate();
        LoggingRequestInterceptor loggingRequestInterceptor = new LoggingRequestInterceptor();
        restTemplate.getInterceptors().add(loggingRequestInterceptor);
        //启用连接池
        //restTemplate.setRequestFactory(httpComponentsClientHttpRequestFactory());
        return restTemplate;
    }
}
