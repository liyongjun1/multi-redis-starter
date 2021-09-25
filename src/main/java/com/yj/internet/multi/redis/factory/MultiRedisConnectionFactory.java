package com.yj.internet.multi.redis.factory;

import com.yj.internet.multi.redis.factory.configuration.MultiRedisConfigIFace;
import io.lettuce.core.resource.ClientResources;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:30
 */
public class MultiRedisConnectionFactory {

    public static final String FACTORY_METHOD = "getObject";

    public static RedisConnectionFactory getObject(MultiRedisConfigIFace redisConfigurationIFace, ClientResources clientResources) throws Exception {
        return redisConfigurationIFace.redisConnectionFactory(clientResources);
    }

}
