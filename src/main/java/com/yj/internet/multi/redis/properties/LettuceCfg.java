package com.yj.internet.multi.redis.properties;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:52
 */
public class LettuceCfg {
    /**
     * Shutdown timeout.
     */
    private Duration shutdownTimeout = Duration.ofMillis(100);

    public static LettuceCfg defaultCfg() {
        return new LettuceCfg();
    }

    public Duration getShutdownTimeout() {
        return shutdownTimeout;
    }

    public void setShutdownTimeout(Duration shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }
}
