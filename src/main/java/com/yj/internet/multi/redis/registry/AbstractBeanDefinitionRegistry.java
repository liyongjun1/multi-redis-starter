package com.yj.internet.multi.redis.registry;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 15:06
 */
public abstract class AbstractBeanDefinitionRegistry<P> implements BeanDefinitionRegistryIFace<P> {

    private static final String REDIS_CONFIG_BEAN_SUFFIX = "multiRedisConfig";
    private static final String REDIS_CLIENT_RESOURCE_BEAN_SUFFIX = "multiRedisClientResource";
    private static final String REDIS_CONNECTION_BEAN_SUFFIX = "multiRedisConnection";

    private BeanDefinitionRegistry registry;

    public AbstractBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void registerBeanDefinition(boolean isPrimary, String redisName, List<String> aliasList, P configProperties, MultiRedisClient redisClient) {
        BeanDefinitionBuilder configBeanDefinitionBuilder = createRedisConfigBeanDefinition(configProperties, redisClient);
        BeanDefinition configBeanDefinition = configBeanDefinitionBuilder.getBeanDefinition();
        String configBeanName = redisName + REDIS_CONFIG_BEAN_SUFFIX;
        registry.registerBeanDefinition(configBeanName, configBeanDefinition);
        registerRedisResource(redisName, isPrimary, configBeanName, aliasList);
    }

    private void registerRedisResource(String redisName, boolean isPrimary, String configBeanName, List<String> aliasList) {
        BeanDefinitionBuilder clientResourceFactoryBuilder = BeanDefinitionBuilder.rootBeanDefinition(MultiRedisClientResourceFactory.class);
        BeanDefinition clientResourceFactoryBeanDefinition = clientResourceFactoryBuilder.getBeanDefinition();
        clientResourceFactoryBeanDefinition.setPrimary(isPrimary);
        String clientResourceBeanName = redisName + REDIS_CLIENT_RESOURCE_BEAN_SUFFIX;
        registry.registerBeanDefinition(clientResourceBeanName, clientResourceFactoryBeanDefinition);

        BeanDefinitionBuilder connectionFactoryBuilder = BeanDefinitionBuilder.rootBeanDefinition(MultiRedisConnectionFactory.class);
        connectionFactoryBuilder.addConstructorArgReference(configBeanName);
        connectionFactoryBuilder.addConstructorArgReference(clientResourceBeanName);
        connectionFactoryBuilder.setFactoryMethod(MultiRedisConnectionFactory.FACTORY_METHOD);
        BeanDefinition connectionFactoryBeanDefinition = connectionFactoryBuilder.getBeanDefinition();
        connectionFactoryBeanDefinition.setPrimary(isPrimary);
        String connectionBeanName = redisName + REDIS_CONNECTION_BEAN_SUFFIX;
        registry.registerBeanDefinition(connectionBeanName, connectionFactoryBeanDefinition);

        BeanDefinitionBuilder redisTemplateBuilder = BeanDefinitionBuilder.rootBeanDefinition(MultiRedisTemplateFactory.class);
        redisTemplateBuilder.addConstructorArgReference(connectionBeanName);
        redisTemplateBuilder.setFactoryMethod(MultiRedisTemplateFactory.FACTORY_METHOD);
        BeanDefinition redisTemplateBeanDefinition = redisTemplateBuilder.getBeanDefinition();
        redisTemplateBeanDefinition.setPrimary(isPrimary);
        String redisTemplateBeanName = redisName;
        registry.registerBeanDefinition(redisTemplateBeanName, redisTemplateBeanDefinition);
        if (aliasList != null && !aliasList.isEmpty()) {
            for (String alias : aliasList) {
                if (alias != null && alias.trim().length() > 0) {
                    registry.registerAlias(redisTemplateBeanName, alias);
                }
            }
        }
    }

}
