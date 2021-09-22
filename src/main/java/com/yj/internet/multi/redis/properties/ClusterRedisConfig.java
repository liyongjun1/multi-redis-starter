package com.yj.internet.multi.redis.properties;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:30
 */
public class ClusterRedisConfig extends AbstractCommonMultiRedisConfig {
    /**
     * Comma-separated list of "host:port" pairs to bootstrap from. This represents an
     * "initial" list of cluster nodes and is required to have at least one entry.
     */
    private List<String> nodes;

    /**
     * Maximum number of redirects to follow when executing commands across the
     * cluster.
     */
    private Integer maxRedirects;

    public List<String> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public Integer getMaxRedirects() {
        return this.maxRedirects;
    }

    public void setMaxRedirects(Integer maxRedirects) {
        this.maxRedirects = maxRedirects;
    }
}
