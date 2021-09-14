package com.yj.internet.multi.redis;

import com.yj.internet.multi.redis.properties.MultiRedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by LiYongJun on 2021/9/14 9:28 上午.
 */
@Configuration
@EnableConfigurationProperties(MultiRedisProperties.class)
@Import(MultiRedisRegistryPostProcessor.class)
public class MultiRedisAutoConfiguration {
}
