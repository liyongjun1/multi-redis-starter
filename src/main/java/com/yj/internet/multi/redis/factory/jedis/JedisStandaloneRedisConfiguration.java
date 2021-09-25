package com.yj.internet.multi.redis.factory.jedis;

import com.yj.internet.multi.redis.properties.MultiRedisProperties;
import com.yj.internet.multi.redis.properties.StandaloneRedisConfig;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;

/**
 * standalone redis
 *
 * @Author: LiYongJun
 * @Date: 2021/9/22 11:55
 */
public class JedisStandaloneRedisConfiguration extends AbstractJedisConfiguration {

    public static final String CONFIG_MEMBER_NAME = "standaloneRedisConfig";

    private StandaloneRedisConfig standaloneRedisConfig;

    public JedisStandaloneRedisConfiguration(MultiRedisProperties properties, ObjectProvider<JedisClientConfigurationBuilderCustomizer> builderCustomizers) {
        super(properties, builderCustomizers);
    }

    public RedisConnectionFactory redisConnectionFactory(ClientResources clientResources) {
        JedisClientConfiguration clientConfig = getJedisClientConfiguration(properties.getBase().getPool(), standaloneRedisConfig.getUrl(), standaloneRedisConfig);
        return createStandaloneConnectionFactory(clientConfig, standaloneRedisConfig);
    }

    public void setStandaloneRedisConfig(StandaloneRedisConfig standaloneRedisConfig) {
        this.standaloneRedisConfig = standaloneRedisConfig;
    }
}
