/**
 * user cluster redis集群
 *
 * @Author: LiYongJun
 * @Date: 2020/9/22 11:52
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
