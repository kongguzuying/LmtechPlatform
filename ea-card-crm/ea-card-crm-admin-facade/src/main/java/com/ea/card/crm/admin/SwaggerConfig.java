package com.ea.card.crm.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by huang.jb on 2017-7-28.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.ui.enable}") //该配置项在配置中心管理
    private boolean environmentSpecificBooleanFlag;

    @Bean
    public Docket docketFactory() {
        ApiInfo apiInfo = new ApiInfo("接口文档", "接口列表", "1.0", "", "", "", "");

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ea"))
                .paths(PathSelectors.any())
                .build()
                .enable(environmentSpecificBooleanFlag);
    }
}
