package com.lmtech.infrastructure;

import com.lmtech.annotation.ConfigurationServerAll;
import com.lmtech.util.SpringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@ConfigurationServerAll
@ImportResource(locations = { "classpath:spring-core.xml" })
@Component
public class SpringConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.initContext(applicationContext);
    }
}
