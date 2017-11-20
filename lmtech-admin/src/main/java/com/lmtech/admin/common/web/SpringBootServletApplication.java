package com.lmtech.admin.common.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = { "com.lmtech", "com.ea" })
@ComponentScan(basePackages = { "com.lmtech", "com.ea" })
public class SpringBootServletApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        setRegisterErrorPageFilter(false);
        return application.sources(SpringBootServletApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootServletApplication.class, args);
    }
}
