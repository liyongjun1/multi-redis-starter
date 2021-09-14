package com.yj.internet.multi.redis.properties;

import com.yj.internet.multi.redis.constants.MultiRedisConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by LiYongJun on 2021/9/14 9:31 上午.
 */
@ConfigurationProperties(prefix = MultiRedisConstants.MULTI_REDIS_CONFIG_PREFIX)
public class MultiRedisProperties {
}
