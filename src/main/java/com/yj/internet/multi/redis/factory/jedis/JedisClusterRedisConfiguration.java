package com.yj.internet.multi.redis.factory.jedis;

import com.yj.internet.multi.redis.properties.ClusterRedisConfig;
import com.yj.internet.multi.redis.properties.MultiRedisProperties;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;

import java.util.Optional;

/**
 * user cluster redis集群
 *
 * @Author: LiYongJun
 * @Date: 2021/9/22 11:52
 */
public class JedisClusterRedisConfiguration extends AbstractJedisConfiguration {
    public static final String CONFIG_MEMBER_NAME = "cluster";

    private ClusterRedisConfig cluster;

    public JedisClusterRedisConfiguration(MultiRedisProperties properties, ObjectProvider<JedisClientConfigurationBuilderCustomizer> builderCustomizers) {
        super(properties, builderCustomizers);
    }

    public RedisConnectionFactory redisConnectionFactory(ClientResources clientResources) {
        JedisClientConfiguration clientConfig = getJedisClientConfiguration(properties.getBase().getPool(), cluster.getUrl(), cluster);
        return createClusterConnectionFactory(clientConfig, cluster, Optional.empty());
    }

    public void setCluster(ClusterRedisConfig cluster) {
        this.cluster = cluster;
    }

}
