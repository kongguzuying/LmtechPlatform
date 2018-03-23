package com.lmtech.admin.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by huang.jb on 2017-7-28.
 */

@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    //@Value("${swagger.ui.enable}") //该配置项在配置中心管理
    private boolean environmentSpecificBooleanFlag = true;

    @Bean
    public Docket docketFactory() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(
                new ApiInfo("接口文档", "接口列表", "1.0", "", "", "", "")).enable(environmentSpecificBooleanFlag);
    }
}
