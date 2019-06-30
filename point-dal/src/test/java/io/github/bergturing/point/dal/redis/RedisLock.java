package io.github.bergturing.point.dal.redis;

import io.github.bergturing.point.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * redis分布式锁
 *
 * @author cxk
 */
public class RedisLock {
    /**
     * 默认超时时间：2小时
     * 单位：秒
     */
    private static final int DEFAULT_LOCK_TIMEOUT = 2 * 60 * 60;
    /**
     * 日志打印对象
     */
    private final Logger logger = LoggerFactory.getLogger(RedisLock.class);
    /**
     * redis连接对象
     */
    private RedisTemplate<String, String> redisTemplate;

    /**
     * redis锁的list
     */
    private List<RedisLock> redisLockList;

    /**
     * Lock key path.
     */
    private String lockKey;

    /**
     * redis的超时时间
     * 单位：秒
     */
    private int lockTimeout = DEFAULT_LOCK_TIMEOUT;

    /**
     * 是否已经上锁
     */
    private volatile boolean locked = false;

    /**
     * 单个key值的锁，默认超时时间(2小时)
     *
     * @param redisTemplate redis连接对象
     * @param lockKey       redis锁得key
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
    }

    /**
     * 单个key值的锁，自定义超时时间
     *
     * @param redisTemplate redis连接对象
     * @param lockKey       锁的key值
     * @param lockTimeout   超时时间：单位秒
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey, int lockTimeout) {
        this(redisTemplate, lockKey);
        this.lockTimeout = lockTimeout;
    }

    /**
     * 锁一批数据,多个key，默认超时时间(2小时)
     *
     * @param redisTemplate redis连接对象
     * @param lockKeyList   key的集合
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, List<String> lockKeyList) {
        //已经加了锁的RedisLock,防止其中某笔数据已经锁住,后面需要释放
        if (CollectionUtils.isNotEmpty(lockKeyList)) {
            this.redisLockList = lockKeyList.stream()
                    .distinct()
                    .map(lockKey -> new RedisLock(redisTemplate, lockKey))
                    .collect(Collectors.toList());
        }
    }

    /**
     * 锁一批数据,多个key，自定义超时时间
     *
     * @param redisTemplate redis连接对象
     * @param lockKeyList   key的集合
     * @param lockTimeout   超时时间：单位秒
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, List<String> lockKeyList, int lockTimeout) {
        this(redisTemplate, lockKeyList);
        this.lockTimeout = lockTimeout;
    }

    /**
     * 获取对应key的值
     *
     * @param key key
     * @return value
     */
    private String get(final String key) {
        String result = null;
        try {
            result = this.redisTemplate.execute((RedisCallback<String>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                byte[] data = connection.get(serializer.serialize(key));
                if (data == null) {
                    return null;
                }
                return serializer.deserialize(data);
            });
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
        return result;
    }

    /**
     * 获取旧值，并设置新值
     *
     * @param key   redis的key
     * @param value redis的新值
     * @return redis的旧值
     */
    private String getSet(final String key, final String value) {
        String result = null;
        try {
            result = this.redisTemplate.execute((RedisCallback<String>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                byte[] data = connection.getSet(serializer.serialize(key), serializer.serialize(value));
                return serializer.deserialize(data);
            });
        } catch (Exception e) {
            logger.error("getSet redis error, key : {}", key);
        }
        return result;
    }

    /**
     * 移除key
     *
     * @param lockKey redis的key
     * @return 移除的结果
     */
    private boolean del(final String lockKey) {
        try {
            this.redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                return connection.del(serializer.serialize(lockKey));
            });
            return true;
        } catch (Exception e) {
            logger.error("del redis error, key : {}", lockKey);
        }
        return false;
    }

    /**
     * 设置key的超时时间
     *
     * @param lockKey    redis的key
     * @param expireTime 超时时间，单位秒
     * @return 设置结果
     */
    private Boolean expire(final String lockKey, final long expireTime) {
        Boolean result = Boolean.FALSE;
        try {
            result = this.redisTemplate.execute((RedisCallback<Boolean>) connection ->
                    connection.expire(new StringRedisSerializer().serialize(lockKey), expireTime));
        } catch (Exception e) {
            logger.error("expire redis error, key : {}", lockKey);
        }
        return result;
    }

    /**
     * 设置键值对，并返回是否设置成功
     *
     * @param key   键
     * @param value 值
     * @return 处理结果
     */
    private Boolean setNX(final String key, final String value) {
        Boolean result = Boolean.FALSE;
        try {
            //执行
            result = this.redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                return connection.setNX(serializer.serialize(key), serializer.serialize(value));
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}", key);
        }

        //返回结果
        return result;
    }

    /**
     * 加锁
     * 取到锁加锁，取不到锁就返回
     *
     * @return 是否加锁成功
     */
    public synchronized boolean lock() {
        //锁释放时间
        long lockTime = currtTimeForRedis() + this.lockTimeout;

        //设置值，并判断设置结果
        if (setNX(this.lockKey, String.valueOf(lockTime))) {
            //设置超时时间，释放内存
            expire(this.lockKey, this.lockTimeout);
            this.locked = true;
            return true;
        } else {
            //获取redis里面的时间
            Object result = get(this.lockKey);
            Long currtLockTimeoutStr = result == null ? null : Long.parseLong(result.toString());
            //锁已经失效
            if (currtLockTimeoutStr != null && currtLockTimeoutStr < currtTimeForRedis()) {
                //判断是否为空，不为空时，说明已经失效，如果被其他线程设置了值，则第二个条件判断无法执行
                //获取上一个锁到期时间，并设置现在的锁到期时间
                Long oldLockTimeoutStr = Long.valueOf(getSet(this.lockKey, String.valueOf(lockTime)));
                if (oldLockTimeoutStr.equals(currtLockTimeoutStr)) {
                    //多线程运行时，多个线程签好都到了这里，但只有一个线程的设置值和当前值相同，它才有权利获取锁
                    //设置超时间，释放内存
                    expire(this.lockKey, this.lockTimeout);
                    //返回加锁时间
                    locked = true;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 解锁
     */
    public synchronized void unLock() {
        //获取redis中设置的时间
        String result = get(this.lockKey);
        Long currtLockTimeoutStr = result == null ? null : Long.valueOf(result);
        if (currtLockTimeoutStr != null) {
            this.del(this.lockKey);
            this.locked = false;
        }
    }

    /**
     * 多服务器集群，使用下面的方法，代替System.currentTimeMillis()，获取redis时间，避免多服务的时间不一致问题！！！
     *
     * @return 当前redis服务的时间秒
     */
    private long currtTimeForRedis() {
        return this.redisTemplate.execute((RedisCallback<Long>) redisConnection -> redisConnection.time() / 1000);
    }

    /**
     * 锁List
     *
     * @return 锁的结果
     */
    public boolean lockList() {
        if (CollectionUtils.isEmpty(this.redisLockList)) {
            //change by shencai.li@hand-china.com
            //当列表为空时应返回true
            return true;
        }
        //临时存放lock,方便后面锁有冲突的释放
        List<RedisLock> redisLockListTemp = new ArrayList<RedisLock>();
        for (RedisLock redisLock : this.redisLockList) {
            if (redisLock.lock()) {
                redisLockListTemp.add(redisLock);
            } else {
                for (RedisLock redisLockUnlock : redisLockListTemp) {
                    //获取锁失败,前面锁住的数据需要释放
                    redisLockUnlock.unLock();
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 解锁list
     */
    public void unLockList() {
        if (CollectionUtils.isNotEmpty(this.redisLockList)) {
            for (RedisLock redisLockUnlock : this.redisLockList) {
                redisLockUnlock.unLock();
            }
        }
    }
}
