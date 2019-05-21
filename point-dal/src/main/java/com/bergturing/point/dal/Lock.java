package com.bergturing.point.dal;

import com.bergturing.point.core.prototype.Prototype;
import com.bergturing.point.core.prototype.defaults.AbstractPrototype;
import com.bergturing.point.dal.exceptions.LockGroupInvalidException;
import com.bergturing.point.dal.exceptions.LockKeyInvalidException;
import com.bergturing.point.utils.StringUtils;

import java.util.UUID;

/**
 * 分布式锁对象
 *
 * @author bergturing@qq.com
 * @apiNote 2019/3/14
 */
public class Lock implements Cloneable {
    /**
     * 默认分组
     */
    public static final String DEFAULT_GROUP = "hus:lock:default:";
    /**
     * 默认锁的存活时间：30分钟 单位 毫秒(mms)
     */
    public static final long DEFAULT_EXPIRE = 30 * 60 * 1000L;
    /**
     * 初始化原型对象
     */
    public static final Prototype<Lock> PROTOTYPE = new AbstractPrototype<Lock>(new Lock()) {
        @Override
        protected Lock clone(Lock prototype) throws CloneNotSupportedException {
            return (Lock) prototype.clone();
        }
    };
    /**
     * 默认key
     */
    private static final String DEFAULT_KEY = "-1";
    /**
     * 锁所属的分组
     */
    private String group;

    /**
     * 锁的key值
     */
    private String key;

    /**
     * 锁的存活时间：单位 毫秒(mms)
     */
    private Long expire;

    /**
     * key的值
     */
    private String value;

    /**
     * 是否已经锁上
     */
    private boolean locked;

    /**
     * 默认构造函数
     */
    private Lock() {
        this(DEFAULT_GROUP, DEFAULT_KEY, DEFAULT_EXPIRE);
    }

    /**
     * 使用默认分组，默认锁的存活时间的构造函数
     *
     * @param key 锁的key值
     */
    public Lock(String key) {
        this(key, DEFAULT_GROUP);
    }

    /**
     * 根据group、key和默认存活时间创建一个锁对象
     *
     * @param group 锁的分组
     * @param key   锁的key
     */
    public Lock(String group, String key) {
        this(group, key, DEFAULT_EXPIRE);
    }

    /**
     * 根据group、key和expire创建一个锁对象
     *
     * @param group  锁的group
     * @param key    锁的key
     * @param expire 锁的存活时间
     */
    public Lock(String group, String key, Long expire) {
        // 生成key的一个唯一的值
        this.setValue(UUID.randomUUID().toString());
        this.valueOf(group, key, expire);
    }

    public String getGroup() {
        return group;
    }

    public Lock setGroup(String group) {
        if (StringUtils.isNotBlank(group)) {
            this.group = group;
        } else {
            throw new LockGroupInvalidException();
        }

        return this;
    }

    public String getKey() {
        return key;
    }

    public Lock setKey(String key) {
        if (StringUtils.isNotBlank(key)) {
            this.key = key;
        } else {
            throw new LockKeyInvalidException();
        }

        return this;
    }

    public Long getExpire() {
        return expire;
    }

    public Lock setExpire(Long expire) {
        this.expire = expire;

        return this;
    }

    public String getValue() {
        return value;
    }

    private Lock setValue(String value) {
        this.value = value;
        return this;
    }

    public boolean isLocked() {
        return locked;
    }

    public Lock setLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    /**
     * 设置值
     *
     * @param group  分组
     * @param key    key值
     * @param expire 存活时间
     * @return Lock对象
     */
    public Lock valueOf(String group, String key, Long expire) {
        return this.setGroup(group).setKey(key).setExpire(expire);
    }
}
