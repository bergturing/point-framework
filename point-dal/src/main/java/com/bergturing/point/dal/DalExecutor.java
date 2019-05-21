package com.bergturing.point.dal;

import java.util.List;

/**
 * 分布式应用锁的接口
 *
 * @author bergturing@qq.com
 * @apiNote 2019/4/17
 */
public interface DalExecutor {
    /**
     * 单个对象加锁
     *
     * @param lock 单个锁对象
     * @return 加锁的结果
     */
    boolean singleLock(final Lock lock);

    /**
     * 多个对象同时加锁
     *
     * @param lockList 多个锁对象
     * @return 加锁的结果
     */
    boolean multiLock(final List<Lock> lockList);

    /**
     * 单个对象释放锁
     *
     * @param lock 单个锁对象
     * @return 释放锁的结果
     */
    boolean singleUnLock(final Lock lock);

    /**
     * 多个对象同时释放锁
     *
     * @param lockList 多个锁对象
     * @return 释放锁的结果
     */
    boolean multiUnLock(final List<Lock> lockList);
}
