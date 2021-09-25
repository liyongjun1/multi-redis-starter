package com.yj.internet.multi.redis.factory;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:30
 */
public class MultiRedisTemplateFactory {
    public static final String FACTORY_METHOD = "getObject";

    public static StringRedisTemplate getObject(RedisConnectionFactory connectionFactory) throws Exception {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}
