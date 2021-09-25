package com.yj.internet.multi.redis.factory.jedis;

import com.yj.internet.multi.redis.factory.configuration.AbstractMultiRedisConfiguration;
import com.yj.internet.multi.redis.properties.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Optional;

/**
 * Created by LiYongJun on 2021/9/14 10:10 上午.
 */
public abstract class AbstractJedisConfiguration extends AbstractMultiRedisConfiguration {
    private final ObjectProvider<JedisClientConfigurationBuilderCustomizer> builderCustomizers;

    protected AbstractJedisConfiguration(MultiRedisProperties properties, ObjectProvider<JedisClientConfigurationBuilderCustomizer> builderCustomizers) {
        super(properties);
        this.builderCustomizers = builderCustomizers;
    }

    protected JedisConnectionFactory createClusterConnectionFactory(JedisClientConfiguration clientConfiguration, ClusterRedisConfig clusterProperties,
                                                                    Optional<String> passWordOptional) {
        return new JedisConnectionFactory(getClusterConfiguration(clusterProperties, passWordOptional), clientConfiguration);
    }

    protected JedisConnectionFactory createSentinelConnectionFactory(JedisClientConfiguration clientConfiguration, SentinelRedisConfig sentinelRedisConfig,
                                                                    Optional<String> passWordOptional) {
        return new JedisConnectionFactory(getSentinelConfig(sentinelRedisConfig, passWordOptional), clientConfiguration);
    }

    protected JedisConnectionFactory createStandaloneConnectionFactory(JedisClientConfiguration clientConfiguration, StandaloneRedisConfig standaloneRedisConfig) {
        return new JedisConnectionFactory(getStandaloneConfig(standaloneRedisConfig), clientConfiguration);
    }

    protected JedisClientConfiguration getJedisClientConfiguration(RedisProperties.Pool pool, String url,
                                                                   CommonMultiRedisConfigIFace customConfig) {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = applyProperties(customConfig, JedisClientConfiguration.builder());
        if (pool == null) {
            pool = new RedisProperties.Pool();
        }
        applyPooling(pool, builder, customConfig);
        if (StringUtils.hasText(url)) {
            customizeConfigurationFromUrl(builder, url);
        }
        customize(builder);
        return builder.build();
    }

    private JedisClientConfiguration.JedisClientConfigurationBuilder applyProperties(CommonMultiRedisConfigIFace customConfig,
                                                                                     JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        if (customConfig.isSsl()) {
            builder.useSsl();
        }
        Duration timeout = getTimeoutConfig(customConfig);
        if(timeout != null) {
            builder.readTimeout(timeout).connectTimeout(timeout);
        }
        return builder;
    }

    private void applyPooling(RedisProperties.Pool pool,
                              JedisClientConfiguration.JedisClientConfigurationBuilder builder,
                              CommonMultiRedisConfigIFace customConfig) {
        builder.usePooling().poolConfig(getPoolConfig(pool, customConfig));
    }

    private void customizeConfigurationFromUrl(JedisClientConfiguration.JedisClientConfigurationBuilder builder, String url) {
        ConnectionInfo connectionInfo = parseUrl(url);
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }
    }

    private void customize(JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        this.builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
    }
}
