package com.yj.internet.multi.redis.properties;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:30
 */
public abstract class AbstractCommonMultiRedisConfig implements CommonMultiRedisConfigIFace {

    /**
     * Given a name, register an alias for it.
     */
    private List<String> alias;

    /**
     * Connection URL. Overrides host, port, and password. User is ignored. Example:
     * redis://user:password@example.com:6379
     */
    private String url;

    /**
     * Whether to enable SSL support.
     */
    private boolean ssl;

    /**
     * Connection timeout.
     */
    private Duration timeout;

    /**
     * Redis pool configuration.
     */
    private MultiRedisPoolCfg pool;

    private LettuceCfg lettuce = LettuceCfg.defaultCfg();

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public MultiRedisPoolCfg getPool() {
        return pool;
    }

    public void setPool(MultiRedisPoolCfg pool) {
        this.pool = pool;
    }

    public LettuceCfg getLettuce() {
        return lettuce;
    }

    public void setLettuce(LettuceCfg lettuce) {
        this.lettuce = lettuce;
    }
}
