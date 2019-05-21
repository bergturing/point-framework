package com.bergturing.point.dal.exceptions;

/**
 * 加分布式锁失败的异常
 *
 * @author bergturing@qq.com
 * @apiNote 2019/3/15
 */
public class LockFailureException extends Exception {
    private static final long serialVersionUID = 7917659379513120095L;

    /**
     * 加锁失败的所有key
     */
    private String failureKey;

    /**
     * 构造方法
     */
    public LockFailureException() {
        super("lock failure exception");
    }

    /**
     * 构造方法
     *
     * @param failureKey 加锁失败的key
     */
    public LockFailureException(String failureKey) {
        this.failureKey = failureKey;
    }

    public String getFailureKey() {
        return failureKey;
    }
}
