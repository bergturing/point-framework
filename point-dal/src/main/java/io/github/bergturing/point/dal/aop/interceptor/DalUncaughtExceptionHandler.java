package io.github.bergturing.point.dal.aop.interceptor;

import java.lang.reflect.Method;

/**
 * 分布式应用锁未捕获的异常处理器
 *
 * @author bergturing@qq.com
 */
public interface DalUncaughtExceptionHandler {
    /**
     * 处理在使用分布式应用锁时没有捕获的异常
     *
     * @param ex     异常对象
     * @param method 执行的方法对象
     * @param params 方法的参数
     */
    void handleUncaughtException(Throwable ex, Method method, Object... params);
}
