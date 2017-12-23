package com.lmtech.annotation;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConditionalServerAll implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        try {
            Class.forName("com.lmtech.server.all.config.EnableServerAll");
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        }
    }
}
