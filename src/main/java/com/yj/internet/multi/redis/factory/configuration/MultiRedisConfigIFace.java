package com.yj.internet.multi.redis.factory.configuration;

import io.lettuce.core.resource.ClientResources;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Created by LiYongJun on 2021/9/14 10:09 上午.
 */
public interface MultiRedisConfigIFace {
    RedisConnectionFactory redisConnectionFactory(ClientResources clientResources);
}
