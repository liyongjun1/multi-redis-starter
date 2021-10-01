multi-redis-starter
=======================
A custom starter with spring boot to support multiple redis data sources

Usage
--------
+ add Dependencies:
```xml
    <dependency>
        <groupId>com.yj.internet</groupId>
        <artifactId>multi-redis-starter</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
```
+ common configurations, which configure the connection timeout and lettuce pool size.
```properties
# set the primary redis name, we can Autowire the primary RedisTemplate directly without Qualifier name
multi-redis.primary=xxx
# support two kinds of redis client [lettuce / jedis (default)]
multi-redis.redisClient=lettuce
#Redis Connection timeout.( ISO-8601 duration format)
multi-redis.base.timeout=2s
#Maximum amount of time a connection allocation should block before throwing an exception when the pool is exhausted. Use a negative value to block indefinitely.( ISO-8601 duration format)
multi-redis.base.pool.max-wait=2000ms
#Maximum number of connections that can be allocated by the pool at a given time. Use a negative value for no limit.
multi-redis.base.pool.max-active=10
#Maximum number of "idle" connections in the pool. Use a negative value to indicate an unlimited number of idle connections.
multi-redis.base.pool.max-idle=5
#Target for the minimum number of idle connections to maintain in the pool. This setting only has an effect if both it and time between eviction runs are positive.
multi-redis.base.pool.min-idle=0
```
---
+ custom redis cluster configuration, we need set prefix of configuration to be `multi-redis.cluster`, we can also
  specifically set pool size to override the size in common configurations. e.g.(Let us suppose,  for example, 
  that we have a specific cluster redis, whose name is `fooCluster`), 
```properties
#Comma-separated list of "host:port" pairs to bootstrap from. This represents an "initial" list of cluster nodes and is required to have at least one entry.
multi-redis.cluster.fooCluster.nodes=1.2.3.4:11396,1.2.3.4:11397,1.2.3.4:11398
#Maximum number of redirects to follow when executing commands across the cluster.
multi-redis.cluster.fooCluster.max-redirects=5
#The alias of given name(if you have multiple aliases, separate them with a comma [,])
multi-redis.cluster.fooCluster.alias=fooAlias1,fooAlias2

#####Override common configurations####
#Override multi-redis.base.timeout
multi-redis.cluster.fooCluster.timeout=20ms
#Override multi-redis.base.pool.max-active
multi-redis.cluster.fooCluster.pool.max-active=3
#Override multi-redis.base.pool.max-idle
multi-redis.cluster.fooCluster.pool.max-idle=1
#Override multi-redis.base.pool.min-idle
multi-redis.cluster.fooCluster.pool.min-idle=2
#Overridemulti-redis.base.pool.max-wait
multi-redis.cluster.fooCluster.pool.max-wait=4s
```
---
+ custom standalone redis configuration, we need set prefix of configuration to be `multi-redis.standalone`, we can also
  specifically set pool size to override the size in common configurations. (Let us suppose, for example, that we have 
  a specific cluster redis, whose name is `barStandalone`)
```properties
#Redis server host.
multi-redis.standalone.barStandalone.host=1.2.3.4
#Database index used by the connection factory.
multi-redis.standalone.barStandalone.database=0
#Redis server port.
multi-redis.standalone.barStandalone.port=6379

#####Override common configurations####
#Override multi-redis.base.timeout
multi-redis.standalone.barStandalone.timeout=50ms
#Override multi-redis.base.pool.max-active
multi-redis.standalone.barStandalone.pool.max-active=3
#Override multi-redis.base.pool.max-idle
multi-redis.standalone.barStandalone.pool.max-idle=1
#Override multi-redis.base.pool.min-idle
multi-redis.standalone.barStandalone.pool.min-idle=2
#Overridemulti-redis.base.pool.max-wait
multi-redis.standalone.barStandalone.pool.max-wait=4s
```
---
+ the redis template can be auto wired by bean name(`fooCluster`, `barStandalone`). e.g.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class FooBarService {
    @Autowired
    @Qualifier("fooCluster")//Same with @Qualifier("fooAlias1") and @Qualifier("fooAlias2")
    private StringRedisTemplate fooRedisTemplate;

    @Autowired
    @Qualifier("barStandalone")
    private StringRedisTemplate barRedisTemplate;
}
```
