package com.yj.internet.multi.redis.factory.lettuce;

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
