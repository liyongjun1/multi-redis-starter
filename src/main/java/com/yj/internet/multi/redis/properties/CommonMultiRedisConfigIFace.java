package com.yj.internet.multi.redis.properties;

import java.time.Duration;

/**
 * @Author: LiYongJun
 * @Date: 2021/9/22 14:30
 */
public interface CommonMultiRedisConfigIFace {

    boolean isSsl();

    Duration getTimeout();

    MultiRedisPoolCfg getPool();

    LettuceCfg getLettuce();
}
