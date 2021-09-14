package com.yj.internet.multi.redis;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by LiYongJun on 2021/9/14 9:34 上午.
 */
public class MultiRedisRegistryPostProcessor implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

    }
}
