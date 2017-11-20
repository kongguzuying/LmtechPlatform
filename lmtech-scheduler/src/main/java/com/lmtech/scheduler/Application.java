package com.lmtech.scheduler;

import com.lmtech.http.interceptor.LoggingRequestInterceptor;
import com.lmtech.util.RestTemplateBuilder;
import com.lmtech.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

/**
 * 程序主入口
 *
 * @author huang.jb
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(value = {"com.lmtech"})
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@RefreshScope
public class Application {

    @Bean
    @LoadBalanced
    public RestTemplate serviceRestTemplate() {
        RestTemplate restTemplate = RestTemplateBuilder.buildRestTemplate();
        LoggingRequestInterceptor loggingRequestInterceptor = new LoggingRequestInterceptor();
        restTemplate.getInterceptors().add(loggingRequestInterceptor);
        return restTemplate;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = RestTemplateBuilder.buildRestTemplate();
        LoggingRequestInterceptor loggingRequestInterceptor = new LoggingRequestInterceptor();
        restTemplate.getInterceptors().add(loggingRequestInterceptor);
        return restTemplate;
    }

    public static void main(String[] args) {
        //初始化spring工具类
        SpringUtil.initContextByContextAware();

        //启动应用
        SpringApplication.run(Application.class, args);

    }
}
