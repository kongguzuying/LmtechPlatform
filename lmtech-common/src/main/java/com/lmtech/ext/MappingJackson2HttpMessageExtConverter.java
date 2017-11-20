package com.lmtech.ext;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class MappingJackson2HttpMessageExtConverter extends MappingJackson2HttpMessageConverter {
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        if (mediaType != null && mediaType.getType().equalsIgnoreCase("text") && mediaType.getSubtype().equalsIgnoreCase("plain")) {
            return true;
        } else {
            return super.canRead(clazz, (Class)null, mediaType);
        }
    }
}
