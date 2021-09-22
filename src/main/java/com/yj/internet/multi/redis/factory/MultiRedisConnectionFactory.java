package com.yj.internet.multi.redis.factory;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:30
 */
public class MultiRedisConnectionFactory {

    public static final String FACTORY_METHOD = "getObject";

    public static RedisConnectionFactory getObject(MultiRedisConfigurationIFace redisConfigurationIFace, ClientResources clientResources) throws Exception {
        return redisConfigurationIFace.redisConnectionFactory(clientResources);
    }

}
