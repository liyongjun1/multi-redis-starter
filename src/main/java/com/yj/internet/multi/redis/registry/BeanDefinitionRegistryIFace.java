package com.yj.internet.multi.redis.registry;

import com.yj.internet.multi.redis.properties.MultiRedisClient;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

import java.util.List;

/**
 * Created by LiYongJun on 2021/9/14 10:16 上午.
 */
public interface BeanDefinitionRegistryIFace<P> {

    void registerBeanDefinition(boolean isPrimary, String redisName, List<String> aliasList, P configProperties, MultiRedisClient redisClient);

    BeanDefinitionBuilder createRedisConfigBeanDefinition(P configProperties, MultiRedisClient redisClient);

}
