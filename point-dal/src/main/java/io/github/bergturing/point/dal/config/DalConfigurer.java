package io.github.bergturing.point.dal.config;

import io.github.bergturing.point.dal.aop.interceptor.DalUncaughtExceptionHandler;

/**
 * 分布式应用锁配置对象
 *
 * @author bergturing@qq.com
 */
public interface DalConfigurer {
    /**
     * 获取分布式应用锁未捕获的异常处理器
     *
     * @return 未捕获的异常处理器
     */
    DalUncaughtExceptionHandler getDalUncaughtExceptionHandler();
}
