package com.ea.card.crm.service.test;

import com.lmtech.common.ContextManager;
import org.junit.Test;

public class VariableTest {

    private String a = "a" + getX();

    private static String getA() {
        return "a" + getX();
    }

    @Test
    public void test() {
        ContextManager.setValue("a", "A");
        System.out.println(getA());

        ContextManager.setValue("a", "B");
        System.out.println(getA());

    }

    public static String getX() {
        String value = ContextManager.getValue("a");
        return value;
    }
}
