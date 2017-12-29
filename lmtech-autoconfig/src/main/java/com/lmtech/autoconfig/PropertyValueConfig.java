package com.lmtech.autoconfig;

import com.lmtech.annotation.ConfigurationServerAll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@ConfigurationServerAll
public class PropertyValueConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        //忽略未找到value值时抛异常
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }
}
