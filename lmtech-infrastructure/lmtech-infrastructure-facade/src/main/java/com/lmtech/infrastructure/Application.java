package com.lmtech.infrastructure;

import com.lmtech.annotation.ConfigurationServerAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 程序主入口
 *
 * @author huang.jb
 */
@ConfigurationServerAll
@EnableAutoConfiguration
@EnableFeignClients(basePackages = {"com.lmtech"})
@ComponentScan(value = {"com.lmtech"})
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableAspectJAutoProxy
@RefreshScope
@EnableTransactionManagement
public class Application {

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
