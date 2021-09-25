package com.yj.internet.multi.redis.factory;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:30
 */
public class MultiRedisPoolConfig extends GenericObjectPoolConfig {
    public MultiRedisPoolConfig() {
        this.setTestWhileIdle(true);
        this.setMinEvictableIdleTimeMillis(60000L);
        this.setTimeBetweenEvictionRunsMillis(30000L);
        this.setNumTestsPerEvictionRun(-1);
    }
}
