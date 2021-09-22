package com.yj.internet.multi.redis;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by LiYongJun on 2021/9/14 9:34 上午.
 */
public class MultiRedisRegistryPostProcessor implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private static final Logger logger = LoggerFactory.getLogger(MultiRedisRegistryPostProcessor.class);

    private Environment environment;
    private boolean primaryFound = false;

    private StandaloneRedisBeanDefinitionRegister standaloneRedisBeanDefinitionRegister;

    private ClusterRedisBeanDefinitionRegister clusterRedisBeanDefinitionRegister;

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        BindResult<MultiRedisProperties> multiRedisBindResult = Binder.get(environment).bind(MultiRedisConstants.MULTI_REDIS_CONFIG_PREFIX, MultiRedisProperties.class);
        MultiRedisProperties multiRedisProperties = null;
        if (multiRedisBindResult.isBound()) {
            multiRedisProperties = multiRedisBindResult.get();
        }
        if (multiRedisProperties == null) {
            return;
        }
        String primaryRedisName = multiRedisProperties.getPrimary();
        Map<String, ClusterRedisConfig> clusterRedisMap = null;
        if (multiRedisProperties.getCluster() != null) {
            initClusterRedisBeanDefinitionRegister(registry);
            clusterRedisMap = multiRedisProperties.getCluster();
            registerClusterRedis(clusterRedisMap, primaryRedisName, multiRedisProperties.getRedisClient());
        }
        Map<String, StandaloneRedisConfig> standaloneRedisMap = null;
        if (multiRedisProperties.getStandalone() != null) {
            initStandaloneRedisBeanDefinitionRegister(registry);
            standaloneRedisMap = multiRedisProperties.getStandalone();
            registerStandaloneRedis(standaloneRedisMap, primaryRedisName, multiRedisProperties.getRedisClient());
        }
        checkPrimary(clusterRedisMap, standaloneRedisMap);
    }

    private void checkPrimary(Map<String, ClusterRedisConfig> clusterRedisMap, Map<String, StandaloneRedisConfig> standaloneRedisMap) {
        if (!primaryFound) {
            int totalRedis = clusterRedisMap == null ? 0 : clusterRedisMap.size();
            totalRedis += standaloneRedisMap == null ? 0 : standaloneRedisMap.size();
            if (totalRedis > 1) {
                String err = "发现多个redis，请将其中一个常用的设置为primary, multi-redis.primary = {redis_name}";
                logger.error(err);
                throw new RuntimeException(err);
            }
        }
    }

    private void registerStandaloneRedis(Map<String, StandaloneRedisConfig> standaloneRedisMap, String primaryRedisName, MultiRedisClient redisClient) {
        if (standaloneRedisMap == null) {
            return;
        }
        Set<Map.Entry<String, StandaloneRedisConfig>> standaloneEntrySet = standaloneRedisMap.entrySet();
        for (Map.Entry<String, StandaloneRedisConfig> standaloneEntry : standaloneEntrySet) {
            String standaloneName = standaloneEntry.getKey();
            if (standaloneName == null) {
                continue;
            }
            boolean isPrimary = compareIfPrimary(standaloneName, primaryRedisName);
            StandaloneRedisConfig standaloneRedisConfig = standaloneEntry.getValue();
            standaloneRedisBeanDefinitionRegister.registerBeanDefinition(isPrimary, standaloneName, standaloneRedisConfig.getAlias(), standaloneRedisConfig, redisClient);
        }
    }

    private void registerClusterRedis(Map<String, ClusterRedisConfig> clusterRedisMap, String primaryRedisName, MultiRedisClient redisClient) {
        if (clusterRedisMap == null) {
            return;
        }
        Set<Map.Entry<String, ClusterRedisConfig>> clusterEntrySet = clusterRedisMap.entrySet();
        for (Map.Entry<String, ClusterRedisConfig> clusterEntry : clusterEntrySet) {
            String clusterName = clusterEntry.getKey();
            if (clusterName == null) {
                continue;
            }
            boolean isPrimary = compareIfPrimary(clusterName, primaryRedisName);
            ClusterRedisConfig clusterConfig = clusterEntry.getValue();
            clusterRedisBeanDefinitionRegister.registerBeanDefinition(isPrimary, clusterName, clusterConfig.getAlias(), clusterConfig, redisClient);
        }
    }

    private boolean compareIfPrimary(String redisName, String primaryName) {
        boolean isPrimary = false;
        if (!primaryFound) {
            if (primaryName != null) {
                primaryFound = primaryName.equals(redisName);
                isPrimary = primaryFound;
            }
        }
        return isPrimary;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void initStandaloneRedisBeanDefinitionRegister(BeanDefinitionRegistry registry) {
        if (standaloneRedisBeanDefinitionRegister == null) {
            standaloneRedisBeanDefinitionRegister = new StandaloneRedisBeanDefinitionRegister(registry);
        }
    }

    public void initClusterRedisBeanDefinitionRegister(BeanDefinitionRegistry registry) {
        if (clusterRedisBeanDefinitionRegister == null) {
            clusterRedisBeanDefinitionRegister = new ClusterRedisBeanDefinitionRegister(registry);
        }
    }
}
