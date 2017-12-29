package com.lmtech.autoconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmtech.annotation.ConfigurationServerAll;
import com.lmtech.model.EntityBase;
import com.lmtech.util.SpringUtil;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Jackson配置，忽略EntityBase的公共属性
 */
@ConfigurationServerAll
public class JsonConfig {
    //TODO 做成初始化加载
    public static void configure() {
        MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) SpringUtil.getObject(MappingJackson2HttpMessageConverter.class);

        ObjectMapper objectMapper = converter.getObjectMapper();
        objectMapper.addMixIn(EntityBase.class, EntityBaseMixIn.class);
    }

    @JsonIgnoreProperties(value = {
        "createDate", "updateDate", "createUser", "createUserName",
        "updateUser", "updateUserName", "delete", "increased" },
        allowSetters = true
    )
    interface EntityBaseMixIn {
    }
}
