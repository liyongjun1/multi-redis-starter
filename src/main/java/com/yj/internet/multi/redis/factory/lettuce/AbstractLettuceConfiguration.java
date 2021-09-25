package com.yj.internet.multi.redis.factory.lettuce;

import com.yj.internet.multi.redis.factory.configuration.AbstractMultiRedisConfiguration;
import com.yj.internet.multi.redis.properties.*;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;
import java.util.Optional;

/**
 * Created by LiYongJun on 2021/9/22 14:26
 */
public abstract class AbstractLettuceConfiguration extends AbstractMultiRedisConfiguration {
    private static final long REDIS_TOPOLOGY_REFRESH_SECONDS = ClusterTopologyRefreshOptions.DEFAULT_REFRESH_PERIOD;
    private final ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers;

    public AbstractLettuceConfiguration(MultiRedisProperties properties,
                                        ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers) {
        super(properties);
        this.builderCustomizers = builderCustomizers;
    }

    protected LettuceConnectionFactory createClusterConnectionFactory(LettuceClientConfiguration clientConfiguration,
                                                                      ClusterRedisConfig clusterProperties,
                                                                      Optional<String> passWordOptional) {
        return new LettuceConnectionFactory(getClusterConfiguration(clusterProperties, passWordOptional), clientConfiguration);
    }

    protected LettuceConnectionFactory createStandaloneConnectionFactory(LettuceClientConfiguration clientConfiguration,
                                                                         StandaloneRedisConfig standaloneRedisConfig) {

        return new LettuceConnectionFactory(getStandaloneConfig(standaloneRedisConfig), clientConfiguration);
    }

    protected LettuceClientConfiguration getLettuceClientConfiguration(ClientResources clientResources, RedisProperties.Pool pool,
                                                                       CommonMultiRedisConfigIFace customConfig) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = createBuilder(pool, customConfig);
        applyProperties(builder, customConfig);
        builder.clientResources(clientResources);
        customize(builder);
        ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enableAllAdaptiveRefreshTriggers()
                .enablePeriodicRefresh()
                .refreshPeriod(Duration.ofSeconds(REDIS_TOPOLOGY_REFRESH_SECONDS))
                .build();
        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                .timeoutOptions(TimeoutOptions.enabled()).topologyRefreshOptions(topologyRefreshOptions).build();
        builder.clientOptions(clusterClientOptions);
        return builder.build();
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool pool, CommonMultiRedisConfigIFace customConfig) {
        if (pool == null) {
            //return LettuceClientConfiguration.builder();
            pool = new RedisProperties.Pool();
        }
        return LettucePoolingClientConfiguration.builder().poolConfig(getPoolConfig(pool, customConfig));
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder applyProperties(
            LettuceClientConfiguration.LettuceClientConfigurationBuilder builder, CommonMultiRedisConfigIFace customConfig) {
        if (customConfig.isSsl()) {
            builder.useSsl();
        }
        Duration timeout = getTimeoutConfig(customConfig);
        if (timeout != null) {
            builder.commandTimeout(timeout);
        }
        if (customConfig.getLettuce() != null) {
            LettuceCfg lettuce = customConfig.getLettuce();
            if (lettuce.getShutdownTimeout() != null && !lettuce.getShutdownTimeout().isZero()) {
                builder.shutdownTimeout(lettuce.getShutdownTimeout());
            }
        }
        return builder;
    }

    private void customize(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        this.builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
    }
}
