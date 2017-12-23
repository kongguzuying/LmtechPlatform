package com.lmtech.infrastructure;

import com.lmtech.annotation.ConfigurationServerAll;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by huang.jb on 2017-7-28.
 */
@ConfigurationServerAll
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.ui.enable}") //该配置项在配置中心管理
    private boolean environmentSpecificBooleanFlag;

    @Bean
    public Docket docketFactory() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("接口文档")
                .description("接口列表")
                .version("1.0")
                .termsOfServiceUrl("")
                .license("")
                .licenseUrl("")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lmtech"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo)
                .enable(environmentSpecificBooleanFlag);
    }
}
