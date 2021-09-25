package com.yj.internet.multi.redis.properties;

import com.yj.internet.multi.redis.constants.MultiRedisConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Created by LiYongJun on 2021/9/14 9:31 上午.
 */
@ConfigurationProperties(prefix = MultiRedisConstants.MULTI_REDIS_CONFIG_PREFIX)
public class MultiRedisProperties {
    private String primary;
    private MultiRedisClient redisClient = MultiRedisClient.JEDIS;
    private MultiRedisBaseCfg base;
    private Map<String, ClusterRedisConfig> cluster;
    private Map<String, StandaloneRedisConfig> standalone;

    public Map<String, ClusterRedisConfig> getCluster() {
        return cluster;
    }

    public void setCluster(Map<String, ClusterRedisConfig> cluster) {
        this.cluster = cluster;
    }

    public Map<String, StandaloneRedisConfig> getStandalone() {
        return standalone;
    }

    public void setStandalone(Map<String, StandaloneRedisConfig> standalone) {
        this.standalone = standalone;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public MultiRedisClient getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(MultiRedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public MultiRedisBaseCfg getBase() {
        return base;
    }

    public void setBase(MultiRedisBaseCfg base) {
        this.base = base;
    }
}
