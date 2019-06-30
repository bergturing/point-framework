package io.github.bergturing.point.dal.redis;

/**
 * 在使用 {@link RedisLockHelper} 提供的工具方法时，会捕获回调方法的异常然后封装成 {@link RuntimeException} 抛出去。
 *
 * @author xin.zhang10@hand-china.com
 */
public class RedisLockException extends RuntimeException {

    public RedisLockException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? message : s;
    }
}
