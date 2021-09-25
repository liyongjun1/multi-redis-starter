package com.yj.internet.multi.redis.registry;

import com.yj.internet.multi.redis.factory.jedis.JedisStandaloneRedisConfiguration;
import com.yj.internet.multi.redis.factory.lettuce.LettuceStandaloneRedisConfiguration;
import com.yj.internet.multi.redis.properties.MultiRedisClient;
import com.yj.internet.multi.redis.properties.StandaloneRedisConfig;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

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
