package com.yj.internet.multi.redis.registry;

/**
 * Created by LiYongJun on 2021/9/14 10:16 上午.
 */
public interface BeanDefinitionRegistryIFace {

    void registerBeanDefinition(boolean isPrimary, String redisName, List<String> aliasList, P configProperties, MultiRedisClient redisClient);

    BeanDefinitionBuilder createRedisConfigBeanDefinition(P configProperties, MultiRedisClient redisClient);

}
