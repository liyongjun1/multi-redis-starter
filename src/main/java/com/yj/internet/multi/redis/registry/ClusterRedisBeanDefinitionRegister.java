package com.yj.internet.multi.redis.registry;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 15:06
 */
public class ClusterRedisBeanDefinitionRegister extends AbstractBeanDefinitionRegistry<ClusterRedisConfig>{

    public ClusterRedisBeanDefinitionRegister(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public BeanDefinitionBuilder createRedisConfigBeanDefinition(ClusterRedisConfig configProperties, MultiRedisClient redisClient) {
        BeanDefinitionBuilder beanDefinitionBuilder;
        if (redisClient == MultiRedisClient.JEDIS) {
            BeanDefinitionBuilder clusterConfigBuilder = BeanDefinitionBuilder.rootBeanDefinition(JedisClusterRedisConfiguration.class);
            beanDefinitionBuilder = clusterConfigBuilder.addPropertyValue(JedisClusterRedisConfiguration.CONFIG_MEMBER_NAME, configProperties);
        } else {
            BeanDefinitionBuilder clusterConfigBuilder = BeanDefinitionBuilder.rootBeanDefinition(LettuceClusterRedisConfiguration.class);
            beanDefinitionBuilder = clusterConfigBuilder.addPropertyValue(LettuceClusterRedisConfiguration.CONFIG_MEMBER_NAME, configProperties);
        }
        return beanDefinitionBuilder;
    }
}
