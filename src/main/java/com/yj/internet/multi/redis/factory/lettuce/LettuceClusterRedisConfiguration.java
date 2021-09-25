package com.yj.internet.multi.redis.factory.lettuce;

import com.yj.internet.multi.redis.properties.ClusterRedisConfig;
import com.yj.internet.multi.redis.properties.MultiRedisProperties;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;

import java.util.Optional;

/**
 * user cluster redis集群
 *
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:27
 */
public class LettuceClusterRedisConfiguration extends AbstractLettuceConfiguration {
    public static final String CONFIG_MEMBER_NAME = "cluster";

    private ClusterRedisConfig cluster;

    public LettuceClusterRedisConfiguration(MultiRedisProperties properties, ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers) {
        super(properties, builderCustomizers);
    }

    public RedisConnectionFactory redisConnectionFactory(ClientResources clientResources) {
        LettuceClientConfiguration clientConfig = getLettuceClientConfiguration(clientResources,
                this.properties.getBase().getPool(), cluster);
        return createClusterConnectionFactory(clientConfig, cluster, Optional.empty());
    }

    public void setCluster(ClusterRedisConfig cluster) {
        this.cluster = cluster;
    }

}
