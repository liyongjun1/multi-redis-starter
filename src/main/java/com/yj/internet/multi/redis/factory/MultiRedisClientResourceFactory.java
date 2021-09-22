package com.yj.internet.multi.redis.factory;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:30
 */
public class MultiRedisClientResourceFactory implements FactoryBean<ClientResources>, DisposableBean {

    private ClientResources clientResources;

    public MultiRedisClientResourceFactory() {
    }

    @Override
    public void destroy() throws Exception {
        if (clientResources != null) {
            clientResources.shutdown();
        }
    }

    @Override
    public ClientResources getObject() throws Exception {
        clientResources = DefaultClientResources.create();
        return clientResources;
    }

    @Override
    public Class<?> getObjectType() {
        return ClientResources.class;
    }
}
