package com.lmtech.autoconfig;

import com.lmtech.annotation.ConfigurationServerAll;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang.jb on 2017-7-28.
 */

@ConfigurationServerAll
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private boolean environmentSpecificBooleanFlag;
    @Value("${swagger.basepackage}")
    private String basePackage;
    @Value("${swagger.headerparams}")
    private String headerParams;

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket docketFactory() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("接口文档")
                .description("接口列表")
                .version("1.0")
                .termsOfServiceUrl("")
                .license("")
                .licenseUrl("")
                .build();

        //设置header参数
        List<Parameter> aParameters = new ArrayList<>();
        if (!StringUtil.isNullOrEmpty(headerParams)) {
            String[] headerParamsArray = headerParams.split(",");
            if (headerParamsArray != null && headerParamsArray.length > 0) {
                for (String headerParam : headerParamsArray) {
                    ParameterBuilder aParameterBuilder = new ParameterBuilder();
                    aParameterBuilder.name(headerParam).defaultValue("").description(headerParam).modelRef(new ModelRef("string")).parameterType("header").required(false).build();
                    aParameters.add(aParameterBuilder.build());
                }
            }
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo)
                .globalOperationParameters(aParameters)
                .enable(environmentSpecificBooleanFlag);
    }
}
