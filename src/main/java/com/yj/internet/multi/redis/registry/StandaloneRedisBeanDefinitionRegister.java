package com.yj.internet.multi.redis.registry;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 15:06
 */
public class StandaloneRedisBeanDefinitionRegister extends AbstractBeanDefinitionRegistry<StandaloneRedisConfig>{

    public StandaloneRedisBeanDefinitionRegister(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public BeanDefinitionBuilder createRedisConfigBeanDefinition(StandaloneRedisConfig configProperties, MultiRedisClient redisClient) {
        BeanDefinitionBuilder beanDefinitionBuilder;
        if (redisClient == MultiRedisClient.JEDIS) {
            BeanDefinitionBuilder standaloneConfigBuilder = BeanDefinitionBuilder.rootBeanDefinition(JedisStandaloneRedisConfiguration.class);
            beanDefinitionBuilder = standaloneConfigBuilder.addPropertyValue(JedisStandaloneRedisConfiguration.CONFIG_MEMBER_NAME, configProperties);
        } else {
            BeanDefinitionBuilder standaloneConfigBuilder = BeanDefinitionBuilder.rootBeanDefinition(LettuceStandaloneRedisConfiguration.class);
            beanDefinitionBuilder = standaloneConfigBuilder.addPropertyValue(LettuceStandaloneRedisConfiguration.CONFIG_MEMBER_NAME, configProperties);
        }
        return beanDefinitionBuilder;
    }
}
