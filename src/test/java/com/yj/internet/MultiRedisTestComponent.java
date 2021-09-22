package com.yj.internet;

import org.springframework.stereotype.Component;

/**
 * Created by LiYongJun on 2021/9/14 9:56 上午.
 */
@Component
public class MultiRedisTestComponent implements InitializingBean {
    @Resource(name = "default-redis")
    private StringRedisTemplate defaultRedis;
    @Resource(name = "special-redis")
    private StringRedisTemplate specialRedis;
    @Resource(name = "data-cluster")
    private StringRedisTemplate dataRedis;
    @Resource(name = "data-alias1")
    private StringRedisTemplate dataRedis1;
    @Resource(name = "data-alias2")
    private StringRedisTemplate dataRedis2;
    @Resource(name = "type-cluster")
    private StringRedisTemplate typeRedis;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(dataRedis == dataRedis1);
        System.out.println(dataRedis == dataRedis2);
        System.out.println(defaultRedis == redisTemplate);
        String a = defaultRedis.opsForValue().get("test_default");
        String a_1 = redisTemplate.opsForValue().get("test_default");
        System.out.println(a);
        System.out.println(a_1);
        String b = specialRedis.opsForValue().get("special_20210922");
        System.out.println(b);
        String c = dataRedis.opsForValue().get("data_20210922");
        System.out.println(c);
        String d = typeRedis.opsForValue().get("type_20210922");
        System.out.println(d);
    }
}
