package com.yj.internet.multi.redis.properties;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:52
 */
public class SentinelRedisConfig extends AbstractCommonMultiRedisConfig {

    /**
     * Database index used by the connection factory.
     */
    private int database = 0;

    /**
     * Name of the Redis server.
     */
    private String master;

    /**
     * Comma-separated list of "host:port" pairs.
     */
    private List<String> nodes;

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getMaster() {
        return this.master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public List<String> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

}
