package com.lmtech.util;

import com.baomidou.mybatisplus.annotations.TableName;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class ClassTest {

    @Test
    public void testHasAnnotation() {
        boolean result1 = ClassUtil.hasAnnotation(B.class, TableName.class);
        boolean result2 = ClassUtil.hasAnnotation(B.class, RequestMapping.class);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testgetGenericClass() {
        Class clazz = ClassUtil.getGenericClass(GAString.class);
        Assert.assertEquals(String.class, clazz);

        Class clazz1 = ClassUtil.getGenericClass(GAString.class, 0);
        Assert.assertEquals(String.class, clazz1);

        List<Class> clazz2 = ClassUtil.getGenericClassList(GAString.class);
        Assert.assertEquals(String.class, clazz2.get(0));
    }
}

@TableName("aaa")
interface A {

}

@RequestMapping("aaaa")
class B implements A {

}

class GA<T> {

}

class GAString extends GA<String> {

}
