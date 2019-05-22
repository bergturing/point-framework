package io.github.bergturing.point.dal.defaults;

import io.github.bergturing.point.dal.DalExecutor;
import io.github.bergturing.point.dal.Lock;
import io.github.bergturing.point.utils.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * 分布式应用锁服务器抽象实现
 *
 * @author bergturing@qq.com
 * @apiNote 2019/3/14
 */
public abstract class AbstractDalExecutor implements DalExecutor {
    /**
     * 分块锁，为每个分组分配一个并发锁，提升系统的并发性
     * <p>
     * todo 使用并发锁，存在集群资源竞争问题，但不影响业务数据的正确性，后期优化需考虑
     */
    private Map<String, ReentrantLock> blockLockMap = new HashMap<>();

    {
        // 添加默认分组的锁
        this.blockLockMap.put(Lock.DEFAULT_GROUP, new ReentrantLock());
    }

    @Override
    public final boolean singleLock(final Lock lock) {
        // 返回加锁的结果
        return this.process(lock, () ->
                lock.setLocked(this.lock(lock.getGroup(), lock.getKey(), lock.getValue(), lock.getExpire())).isLocked());
    }

    @Override
    public final boolean multiLock(final List<Lock> lockList) {
        // 返回批量加锁结果
        return this.multiProcess(lockList, () -> {
            // 判断是否所有锁对象都加锁成功
            if (lockList.stream().filter(lock -> !lock.isLocked()).allMatch(this::singleLock)) {
                // 返回成功
                return true;
            } else {
                // 解锁
                this.multiUnLock(lockList);
                // 返回失败
                return false;
            }
        });
    }

    @Override
    public final boolean singleUnLock(final Lock lock) {
        // 返回解锁结果
        return this.process(lock, () -> {
            // 判断是否已经上锁
            if (lock.isLocked()) {
                // 解锁
                return lock.setLocked(!this.unLock(lock.getGroup(), lock.getKey(), lock.getValue())).isLocked();
            } else {
                return true;
            }
        });
    }

    @Override
    public final boolean multiUnLock(final List<Lock> lockList) {
        // 返回批量解锁结果
        return this.multiProcess(lockList, () -> {
                    // 解锁
                    lockList.stream().filter(Lock::isLocked).forEach(this::singleUnLock);
                    //反回解锁结果
                    return lockList.stream().allMatch(Lock::isLocked);
                }
        );
    }

    /**
     * 单个加锁解锁通用处理逻辑
     *
     * @param lock     锁对象
     * @param supplier 对锁对象的处理逻辑
     * @return 处理结果
     */
    private boolean process(final Lock lock, final Supplier<Boolean> supplier) {
        // 处理的结果
        boolean result = true;

        // 判断传入的对象是否为空
        if (Objects.nonNull(lock)) {
            // 获取并发锁对象
            ReentrantLock reentrantLock = this.getReentrantLock(lock.getGroup());

            try {
                // 并发锁加锁
                reentrantLock.lock();

                // 调用处理逻辑
                result = supplier.get();
            } finally {
                // 并发锁解锁
                reentrantLock.unlock();
            }
        }

        // 返回处理结果
        return result;
    }

    /**
     * 多个加锁解锁通用处理逻辑
     *
     * @param lockList 锁list
     * @param supplier 对所对象的处理逻辑
     * @return 处理结果
     */
    private boolean multiProcess(final List<Lock> lockList, final Supplier<Boolean> supplier) {
        if (CollectionUtils.isNotEmpty(lockList)) {
            return supplier.get();
        } else {
            return true;
        }
    }

    /**
     * 根据分组获取并发锁
     *
     * @param group 分组
     * @return 并发锁
     */
    private ReentrantLock getReentrantLock(final String group) {
        // 判断当前分组是否有并发锁对象
        if (!this.blockLockMap.containsKey(group)) {
            // 没有并发锁对象，就创建一个并发锁对象
            this.blockLockMap.put(group, new ReentrantLock());
        }

        // 返回分组并发锁对象
        return this.blockLockMap.get(group);
    }

    /**
     * 对指定的key加锁
     *
     * @param group  锁的分组
     * @param key    加锁的key
     * @param expire 锁的存活时间
     * @param value  锁的值
     * @return 加锁结果
     */
    protected abstract boolean lock(final String group, final String key, final String value, final Long expire);

    /**
     * 释放锁
     *
     * @param group 锁的分组
     * @param key   释放锁的key
     * @param value 锁的值
     * @return 释放结果
     */
    protected abstract boolean unLock(final String group, final String key, final String value);
}
