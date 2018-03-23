package com.ea.card.crm.admin;

import com.lmtech.http.interceptor.LoggingRequestInterceptor;
import com.lmtech.util.RestTemplateBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

/**
 * 程序主入口
 *
 * @author huang.jb
 */
@Configuration
@EnableAutoConfiguration
@EnableFeignClients(basePackages = {"com.lmtech", "com.ea"})
@ComponentScan(value = {"com.ea.card.crm", "com.lmtech"})
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableAspectJAutoProxy
@EnableScheduling
@RefreshScope
@EnableTransactionManagement
public class Application {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = RestTemplateBuilder.buildRestTemplate();
        LoggingRequestInterceptor loggingRequestInterceptor = new LoggingRequestInterceptor();
        restTemplate.getInterceptors().add(loggingRequestInterceptor);
        return restTemplate;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
