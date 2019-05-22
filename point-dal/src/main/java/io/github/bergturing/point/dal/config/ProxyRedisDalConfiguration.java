package io.github.bergturing.point.dal.config;

import io.github.bergturing.point.dal.DalExecutor;
import io.github.bergturing.point.dal.defaults.RedisDalExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 分布式应用锁的配置文件
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/10
 */
@Configuration
public class ProxyRedisDalConfiguration extends AbstractProxyDalConfiguration {
    /**
     * redis服务对象
     */
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public ProxyRedisDalConfiguration(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 分布式应用锁加锁执行器
     *
     * @return 分布式应用锁加锁执行器
     */
    @Bean
    @Override
    public DalExecutor dalExecutor() {
        return new RedisDalExecutor(this.stringRedisTemplate);
    }
}
