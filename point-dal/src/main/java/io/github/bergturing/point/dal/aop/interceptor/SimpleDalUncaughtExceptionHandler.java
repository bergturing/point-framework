package io.github.bergturing.point.dal.aop.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import static io.github.bergturing.point.utils.LoggerUtils.error;

/**
 * 分布式应用锁未捕获的异常处理器的简单实现
 *
 * @author bergturing@qq.com
 * @date 2019/5/10
 */
public class SimpleDalUncaughtExceptionHandler implements DalUncaughtExceptionHandler {
    /**
     * 日志打印对象
     */
    private static final Logger logger = LoggerFactory.getLogger(SimpleDalUncaughtExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        error(logger, "Unexpected error occurred invoking dal method: " + method, ex);
    }
}
