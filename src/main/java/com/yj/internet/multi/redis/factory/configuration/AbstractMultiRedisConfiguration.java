package com.yj.internet.multi.redis.factory.configuration;

import com.yj.internet.multi.redis.factory.MultiRedisPoolConfig;
import com.yj.internet.multi.redis.properties.*;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by LiYongJun on 2021/9/14 10:06 上午.
 */
public abstract class AbstractMultiRedisConfiguration implements MultiRedisConfigIFace {
    protected final MultiRedisProperties properties;

    protected AbstractMultiRedisConfiguration(MultiRedisProperties properties) {
        this.properties = properties;
    }

    protected RedisClusterConfiguration getClusterConfiguration(ClusterRedisConfig clusterProperties,
                                                                Optional<String> passWordOptional) {
        RedisClusterConfiguration config = new RedisClusterConfiguration(clusterProperties.getNodes());
        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }
        if (passWordOptional.isPresent()) {
            config.setPassword(RedisPassword.of(passWordOptional.get()));
        }
        return config;
    }

    protected final RedisStandaloneConfiguration getStandaloneConfig(StandaloneRedisConfig standaloneRedisConfig) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(standaloneRedisConfig.getHost());
        config.setPort(standaloneRedisConfig.getPort());
        if (standaloneRedisConfig.getPassword() != null) {
            config.setPassword(RedisPassword.of(standaloneRedisConfig.getPassword()));
        }
        config.setDatabase(standaloneRedisConfig.getDatabase());
        return config;
    }

    protected final RedisSentinelConfiguration getSentinelConfig(SentinelRedisConfig sentinelProperties,
                                                                 Optional<String> passWordOptional) {
        if (sentinelProperties != null) {
            RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.master(sentinelProperties.getMaster());
            config.setSentinels(createSentinels(sentinelProperties));
            if (passWordOptional.isPresent()) {
                config.setPassword(RedisPassword.of(passWordOptional.get()));
            }
            config.setDatabase(sentinelProperties.getDatabase());
            return config;
        }
        return null;
    }

    private List<RedisNode> createSentinels(SentinelRedisConfig sentinel) {
        List<RedisNode> nodes = new ArrayList<>();
        for (String node : sentinel.getNodes()) {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
            }
            catch (RuntimeException ex) {
                throw new IllegalStateException("Invalid redis sentinel " + "property '" + node + "'", ex);
            }
        }
        return nodes;
    }

    protected ConnectionInfo parseUrl(String url) {
        try {
            URI uri = new URI(url);
            boolean useSsl = (url.startsWith("rediss://"));
            String password = null;
            if (uri.getUserInfo() != null) {
                password = uri.getUserInfo();
                int index = password.indexOf(':');
                if (index >= 0) {
                    password = password.substring(index + 1);
                }
            }
            return new ConnectionInfo(uri, useSsl, password);
        }
        catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Malformed url '" + url + "'", ex);
        }
    }

    protected Duration getTimeoutConfig(CommonMultiRedisConfigIFace customConfig) {
        Duration timeout = null;
        if (this.properties.getBase() != null && this.properties.getBase().getTimeout() != null) {
            timeout = this.properties.getBase().getTimeout();
        }
        if (customConfig.getTimeout() != null) {
            timeout = customConfig.getTimeout();
        }
        return timeout;
    }

    protected GenericObjectPoolConfig<?> getPoolConfig(RedisProperties.Pool properties, CommonMultiRedisConfigIFace customRedisConfig) {
        GenericObjectPoolConfig<?> config = new MultiRedisPoolConfig();
        MultiRedisPoolCfg customPool = customRedisConfig.getPool();
        int maxTotal = properties.getMaxActive();
        if (customPool != null && customPool.getMaxActive() != null) {
            maxTotal = customPool.getMaxActive();
        }
        config.setMaxTotal(maxTotal);
        int maxIdle = properties.getMaxIdle();
        if (customPool != null && customPool.getMaxIdle() != null) {
            maxIdle = customPool.getMaxIdle();
        }
        config.setMaxIdle(maxIdle);
        int minIdle = properties.getMinIdle();
        if (customPool != null && customPool.getMinIdle() != null) {
            minIdle = customPool.getMinIdle();
        }
        config.setMinIdle(minIdle);
        Duration timeBetweenEvictionRuns = properties.getTimeBetweenEvictionRuns();
        if (customPool != null && customPool.getTimeBetweenEvictionRuns() != null) {
            timeBetweenEvictionRuns = customPool.getTimeBetweenEvictionRuns();
        }
        if (timeBetweenEvictionRuns != null) {
            config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRuns.toMillis());
        }
        Duration maxWait = properties.getMaxWait();
        if (customPool != null && customPool.getMaxWait() != null) {
            maxWait = customPool.getMaxWait();
        }
        if (maxWait != null) {
            config.setMaxWaitMillis(maxWait.toMillis());
        }
        return config;
    }

    protected static class ConnectionInfo {

        private final URI uri;

        private final boolean useSsl;

        private final String password;

        public ConnectionInfo(URI uri, boolean useSsl, String password) {
            this.uri = uri;
            this.useSsl = useSsl;
            this.password = password;
        }

        public boolean isUseSsl() {
            return this.useSsl;
        }

        public String getHostName() {
            return this.uri.getHost();
        }

        public int getPort() {
            return this.uri.getPort();
        }

        public String getPassword() {
            return this.password;
        }

    }
}
