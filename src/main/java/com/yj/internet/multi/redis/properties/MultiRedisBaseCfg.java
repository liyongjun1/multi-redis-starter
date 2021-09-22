package com.yj.internet.multi.redis.properties;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:52
 */
public class MultiRedisBaseCfg {

    /**
     * Connection timeout.
     */
    private Duration timeout;

    /**
     * Redis pool configuration.
     */
    private RedisProperties.Pool pool;

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public RedisProperties.Pool getPool() {
        return pool;
    }

    public void setPool(RedisProperties.Pool pool) {
        this.pool = pool;
    }
}
