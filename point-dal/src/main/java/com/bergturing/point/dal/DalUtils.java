package com.bergturing.point.dal;

import com.bergturing.point.dal.exceptions.LockFailureException;
import com.bergturing.point.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 分布式应用锁工具
 *
 * @author bergturing@qq.com
 * @apiNote 2019/3/15
 */
public class DalUtils {
    /**
     * 锁单个资源对象
     *
     * @param dalExecutor 分布式锁服务对象
     * @param lock        锁对象
     * @param supplier    业务处理逻辑
     * @param <R>         处理结果返回值
     * @return 处理结果
     * @throws LockFailureException 分布式加锁失败异常
     */
    public static <R> Optional<R> singleLock(final DalExecutor dalExecutor,
                                             final Lock lock,
                                             final Supplier<R> supplier) throws LockFailureException {
        // 调用处理过程，并返回结果
        return process(dalExecutor,
                lock,
                supplier,
                Objects::nonNull,
                DalExecutor::singleLock,
                DalExecutor::singleUnLock,
                DalUtils::extSingleKey);
    }

    /**
     * 锁多个资源对象
     *
     * @param dalExecutor 分布式锁服务对象
     * @param lockList    锁对象
     * @param supplier    业务处理逻辑
     * @param <R>         处理结果返回值
     * @return 处理结果
     * @throws LockFailureException 分布式加锁失败异常
     */
    public static <R> Optional<R> multiLock(final DalExecutor dalExecutor,
                                            final List<Lock> lockList,
                                            final Supplier<R> supplier) throws LockFailureException {
        // 调用处理过程，并返回结果
        return process(dalExecutor,
                lockList,
                supplier,
                CollectionUtils::isNotEmpty,
                DalExecutor::multiLock,
                DalExecutor::multiUnLock,
                DalUtils::extMultiKey);
    }

    /**
     * 加锁的执行过程的封装
     *
     * @param dalExecutor     分布式锁服务对象
     * @param data            加锁的数据
     * @param supplier        业务处理逻辑
     * @param paramPredicate  加锁参数校验
     * @param lockPredicate   加锁处理逻辑
     * @param unLockPredicate 释放锁处理逻辑
     * @param <T>             锁对象泛型
     * @param <R>             业务处理逻辑返回的结果泛型
     * @return 业务处理逻辑的处理结果
     * @throws LockFailureException 加锁失败异常
     */
    private static <T, R> Optional<R> process(final DalExecutor dalExecutor,
                                              final T data,
                                              final Supplier<R> supplier,
                                              final Predicate<T> paramPredicate,
                                              final BiPredicate<DalExecutor, T> lockPredicate,
                                              final BiPredicate<DalExecutor, T> unLockPredicate,
                                              final Function<T, String> extKey) throws LockFailureException {
        // 判断逻辑处理对象是否为空
        if (Objects.nonNull(supplier)) {
            // redis服务与锁对象不为空才进行加锁操作
            if (Objects.nonNull(dalExecutor) && paramPredicate.test(data)) {
                // 加锁结果
                boolean lockFlag = false;
                try {
                    // 加锁
                    lockFlag = lockPredicate.test(dalExecutor, data);

                    // 加锁成功
                    if (lockFlag) {
                        // 调用处理逻辑，并返回结果
                        return Optional.ofNullable(supplier.get());
                    } else {
                        // 数据正在使用中，请稍后尝试
                        throw new LockFailureException(extKey.apply(data));
                    }
                } finally {
                    // 如果加锁成功了，才释放锁
                    if (lockFlag) {
                        // 释放锁
                        unLockPredicate.test(dalExecutor, data);
                    }
                }
            } else {
                // 不进行加锁，就直接执行处理逻辑
                return Optional.ofNullable(supplier.get());
            }
        } else {
            // 不存在处理逻辑对象，就返回空对象
            return Optional.empty();
        }
    }

    /**
     * 展开单个锁
     *
     * @param lock 锁对象
     * @return 单个锁的唯一标识
     */
    private static String extSingleKey(Lock lock) {
        return lock.getGroup() + lock.getKey();
    }

    /**
     * 展开多个锁
     *
     * @param lockList 锁对象
     * @return 多个锁的唯一标识
     */
    private static String extMultiKey(List<Lock> lockList) {
        return lockList.stream().map(lock -> lock.getGroup() + lock.getKey()).collect(Collectors.joining(";"));
    }

    /**
     * 自定义锁的对象进行数据加锁，默认锁的个数为1
     *
     * @return 自定义锁构造对象
     */
    public static SelfLockBuilder self() {
        return new SelfLockBuilder();
    }

    /**
     * 自定义锁的对象进行数据加锁
     *
     * @param count 预估的锁的个数
     * @return 自定义锁构造对象
     */
    public static SelfLockBuilder self(int count) {
        return new SelfLockBuilder(count);
    }

    /**
     * 自定义锁构造对象
     */
    public static class SelfLockBuilder {
        /**
         * 默认锁的个数为1
         */
        private static final int DEFAULT_LOCK_COUNT = 1;

        /**
         * 加锁对象
         */
        final private List<Lock> lockList;

        /**
         * 默认锁个数的构造函数
         */
        private SelfLockBuilder() {
            this(DEFAULT_LOCK_COUNT);
        }

        /**
         * 指定锁个数的构造函数
         *
         * @param count 估计可能有的锁的个数
         */
        private SelfLockBuilder(int count) {
            lockList = new ArrayList<>(count);
        }

        /**
         * 添加单个锁对象
         *
         * @param lock 锁对象
         * @return 构造对象
         */
        public SelfLockBuilder add(Lock lock) {
            if (Objects.nonNull(lock)) {
                this.lockList.add(lock);
            }
            return this;
        }

        /**
         * 根据一个数据对象，来创建一个锁对象(内部调用属性复制方法)
         *
         * @param object 数据对象
         * @param <T>    数据对象泛型
         * @return 构建对象
         */
        public <T> SelfLockBuilder add(T object) {
            return this.add(object, Lock.PROTOTYPE::prototypeClone);
        }

        /**
         * 根据一个数据对象和转换函数，来创建一个锁对象
         *
         * @param object      数据对象
         * @param mapFunction 转换函数
         * @param <T>         数据对象泛型
         * @return 构建对象
         */
        public <T> SelfLockBuilder add(T object, Function<T, Lock> mapFunction) {
            if (Objects.nonNull(object)) {
                this.lockList.add(mapFunction.apply(object));
            }
            return this;
        }

        /**
         * 使用指定的key和默认分组，默认存活时间增加一个锁对象
         *
         * @param key 锁的key值
         * @return 构造对象
         */
        public SelfLockBuilder add(String key) {
            this.add(Lock.DEFAULT_GROUP, key);
            return this;
        }

        /**
         * 使用指定的分组和key，默认存活时间增加一个锁对象
         *
         * @param group 分组
         * @param key   锁的key
         * @return 构造对象
         */
        public SelfLockBuilder add(String group, String key) {
            this.add(group, key, Lock.DEFAULT_EXPIRE);
            return this;
        }

        /**
         * 根据指定分组、key和存活时间增加一个锁对象
         *
         * @param group  分组
         * @param key    锁的key
         * @param expire 存活时间
         * @return 构造对象
         */
        public SelfLockBuilder add(String group, String key, Long expire) {
            this.add(Lock.PROTOTYPE.prototypeClone().valueOf(group, key, expire));
            return this;
        }

        /**
         * 跟据指定的集合，增加锁对象，默认存活时间
         *
         * @param lockList 集合
         * @param <T>      集合内数据的类型
         * @return 构造对象
         */
        public <T> SelfLockBuilder addAll(List<T> lockList) {
            if (CollectionUtils.isNotEmpty(lockList)) {
                // 如果参数类型是Lock，就直接添加
                if (Lock.class.isAssignableFrom(lockList.get(0).getClass())) {
                    for (T lock : lockList) {
                        this.add(lock);
                    }
                } else {
                    this.addAll(lockList, String::valueOf);
                }
            }
            return this;
        }

        /**
         * 跟据指定的集合，增加锁对象，默认存活时间
         *
         * @param lockList      集合
         * @param parseFunction 解析key值的函数
         * @param <T>           集合内数据的类型
         * @return 构造对象
         */
        public <T> SelfLockBuilder addAll(List<T> lockList, Function<T, String> parseFunction) {
            return this.addAll(Lock.DEFAULT_GROUP, lockList, parseFunction);
        }

        /**
         * 根据指定的分组与集合增加锁对象，默认存活时间
         *
         * @param group    分组
         * @param lockList 集合，取集合内每个对象的toString值用作key
         * @param <T>      集合内数据的类型
         * @return 构造对象
         */
        public <T> SelfLockBuilder addAll(String group, List<T> lockList) {
            return this.addAll(group, lockList, String::valueOf);
        }

        /**
         * 根据指定的分组与集合增加锁对象，默认存活时间
         *
         * @param group         分组
         * @param lockList      集合，取集合内每个对象的toString值用作key
         * @param parseFunction 解析key值的函数
         * @param <T>           集合内数据的类型
         * @return 构造对象
         */
        public <T> SelfLockBuilder addAll(String group, List<T> lockList, Function<T, String> parseFunction) {
            return this.addAll(group, lockList, parseFunction, Lock.DEFAULT_EXPIRE);
        }

        /**
         * 根据指定分组，指定存活时间与集合增加锁对象
         *
         * @param group    分组
         * @param lockList 集合，取集合内每个对象的toString值用作key
         * @param expire   存活时间
         * @param <T>      集合内数据的类型
         * @return 构造对象
         */
        public <T> SelfLockBuilder addAll(String group, List<T> lockList, Long expire) {
            return this.addAll(group, lockList, String::valueOf, expire);
        }

        /**
         * 根据指定分组，指定存活时间与集合增加锁对象
         *
         * @param group         分组
         * @param lockList      集合，取集合内每个对象的toString值用作key
         * @param parseFunction 解析key值的函数
         * @param expire        存活时间
         * @param <T>           集合内数据的类型
         * @return 构造对象
         */
        public <T> SelfLockBuilder addAll(String group, List<T> lockList, Function<T, String> parseFunction, Long expire) {
            if (CollectionUtils.isNotEmpty(lockList) && null != parseFunction) {
                lockList.forEach(lock -> {
                    // 添加锁对象
                    this.add(group, parseFunction.apply(lock), expire);
                });
            }
            return this;
        }

        /**
         * 加锁
         *
         * @param dalExecutor 分布式锁服务对象
         * @param supplier    执行逻辑对象
         * @return 加锁执行结果
         * @throws LockFailureException 加锁失败异常
         */
        public <T> Optional<T> lock(DalExecutor dalExecutor,
                                    Supplier<T> supplier) throws LockFailureException {
            // 调用加锁过程进行加锁
            return DalUtils.multiLock(dalExecutor, this.lockList, supplier);
        }
    }
}
