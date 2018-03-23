package com.lmtech.autoconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmtech.annotation.ConfigurationServerAll;
import com.lmtech.model.EntityBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.annotation.PostConstruct;

/**
 * Jackson配置，忽略EntityBase的公共属性
 */
@ConfigurationServerAll
public class JsonConfig {

    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @PostConstruct
    public void configure() {
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
