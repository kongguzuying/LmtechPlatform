package com.lmtech.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jayway.jsonpath.internal.JsonFormatter;
import org.junit.Test;

public class JsonUtilTest {

    @Test
    public void testToJsonFormat() {
        String json = "{\"aaa\":            123}";
        System.out.println(StringUtil.replaceBlank(json));
    }
}
