package com.lmtech.util;

import com.lmtech.common.ContextManager;
import org.junit.Test;

public class LoggerTest {

    @Test
    public void testLog() {
        ContextManager.buildSerialNumber();
        LoggerManager.debug("debug");
        LoggerManager.info("info");
        LoggerManager.warn("warn");
        LoggerManager.error("error");
        LoggerManager.fatal("fatal");
    }
}
