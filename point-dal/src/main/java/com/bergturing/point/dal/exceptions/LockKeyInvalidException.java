package com.bergturing.point.dal.exceptions;

/**
 * 锁的key值无效的异常
 *
 * @author bergturing@qq.com
 * @apiNote 2019/3/14
 */
public class LockKeyInvalidException extends RuntimeException {
    private static final long serialVersionUID = -4343688130161744058L;

    /**
     * 构造方法
     */
    public LockKeyInvalidException() {
        super("lock key invalid exception");
    }
}