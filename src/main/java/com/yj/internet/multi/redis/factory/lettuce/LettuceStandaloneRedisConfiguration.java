package com.yj.internet.multi.redis.factory.lettuce;

import com.yj.internet.multi.redis.properties.MultiRedisProperties;
import com.yj.internet.multi.redis.properties.StandaloneRedisConfig;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;

/**
 * lettuce standalone redis
 *
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:30
 */
public class LettuceStandaloneRedisConfiguration extends AbstractLettuceConfiguration {

    public static final String CONFIG_MEMBER_NAME = "standaloneRedisConfig";

    private StandaloneRedisConfig standaloneRedisConfig;

    public LettuceStandaloneRedisConfiguration(MultiRedisProperties properties, ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers) {
        super(properties, builderCustomizers);
    }

    public RedisConnectionFactory redisConnectionFactory(ClientResources clientResources) {
        LettuceClientConfiguration clientConfig = getLettuceClientConfiguration(clientResources,
                this.properties.getBase().getPool(), standaloneRedisConfig);
        return createStandaloneConnectionFactory(clientConfig, standaloneRedisConfig);
    }

    public void setStandaloneRedisConfig(StandaloneRedisConfig standaloneRedisConfig) {
        this.standaloneRedisConfig = standaloneRedisConfig;
    }
}
