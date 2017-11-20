package com.lmtech.util;

import org.junit.Test;

public class MathTest {

    @Test
    public void testAbs() {
        double a = -0.01;
        System.out.println(Math.abs(a));

        double b = 0.01;
        System.out.println(Math.abs(b));
    }
}
