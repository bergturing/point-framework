package io.github.bergturing.point.dal.defaults;

import io.github.bergturing.point.utils.StringUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.Collections;
import java.util.List;

/**
 * redis实现的分布式应用锁
 * 在 StringRedisTemplate 存在时，自动注入
 *
 * @author bergturing@qq.com
 * @date 2019/3/14
 * @see StringRedisTemplate
 */
public class RedisDalExecutor extends AbstractDalExecutor {
    /**
     *
     */
    private static final String REDIS_NX = "NX";

    /**
     *
     */
    private static final String REDIS_PX = "PX";

    /**
     * 锁成功
     */
    private static final String LOCK_OK = "OK";

    /**
     * 解锁时执行的脚本
     */
    private static final String UNLOCK_LUA;

    static {
        // 初始化脚本
        UNLOCK_LUA =
                "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
                        "then " +
                        "    return redis.call(\"del\",KEYS[1]) " +
                        "else " +
                        "    return 0 " +
                        "end ";
    }

    /**
     * redis服务对象
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * redis分布式锁的构造方法
     *
     * @param stringRedisTemplate redis服务对象
     */
    public RedisDalExecutor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected boolean lock(final String group, final String key, final String value, final Long expire) {
        // 加锁
        String executeResult = this.stringRedisTemplate.execute((RedisConnection connection) -> {
            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
            return commands.set(group + key, value, REDIS_NX, REDIS_PX, expire);
        });

        // 返回加锁结果
        return StringUtils.isNotBlank(executeResult) && LOCK_OK.equals(executeResult);
    }

    @Override
    protected boolean unLock(final String group, final String key, String value) {
        // 获取执行结果
        Long executeResult = this.stringRedisTemplate.execute((RedisConnection connection) -> {
            // 处理结果
            Long result = 0L;

            // 创建条件对象
            Object nativeConnection = connection.getNativeConnection();
            List<String> keys = Collections.singletonList(group + key);
            List<String> args = Collections.singletonList(value);

            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本

            // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
            if (nativeConnection instanceof JedisCluster) {
                // 集群模式
                result = (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
            } else if (nativeConnection instanceof Jedis) {
                // 单机模式
                result = (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
            }

            // 返回结果
            return result;
        });

        // 返回判断结果
        return (executeResult != null && executeResult > 0);
    }
}
