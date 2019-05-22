package io.github.bergturing.point.dal.aop.interceptor;

import io.github.bergturing.point.dal.DalExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import static io.github.bergturing.point.utils.LoggerUtils.error;

/**
 * 分布式应用锁的支持
 *
 * @author bergturing@qq.com
 * @date 2019/5/10
 */
public abstract class BaseDalExecutionAspectSupport {

    /**
     * 日志打印对象
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseDalExecutionAspectSupport.class);

    /**
     * 分布式应用锁的执行器
     */
    private DalExecutor dalExecutor;

    /**
     * 未捕获的异常处理器
     */
    private DalUncaughtExceptionHandler exceptionHandler;

    /**
     * 构造方法
     *
     * @param dalExecutor 分布式应用锁的执行器
     */
    public BaseDalExecutionAspectSupport(DalExecutor dalExecutor) {
        this(dalExecutor, new SimpleDalUncaughtExceptionHandler());
    }

    /**
     * 构造方法
     *
     * @param dalExecutor      分布式应用锁的执行器
     * @param exceptionHandler 未捕获的异常处理器
     */
    public BaseDalExecutionAspectSupport(DalExecutor dalExecutor, DalUncaughtExceptionHandler exceptionHandler) {
        this.dalExecutor = dalExecutor;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * 设置未捕获的异常处理器
     *
     * @param exceptionHandler 未捕获的异常处理器
     */
    public void setExceptionHandler(DalUncaughtExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * 获取分布式应用锁的执行器
     *
     * @return 分布式应用锁的执行器
     */
    public DalExecutor getDalExecutor() {
        return dalExecutor;
    }

    /**
     * 分布式应用锁的执行器
     *
     * @param dalExecutor 分布式应用锁的执行器
     */
    public void setDalExecutor(DalExecutor dalExecutor) {
        this.dalExecutor = dalExecutor;
    }

    /**
     * 处理错误
     *
     * @param ex     异常对象
     * @param method 调用的方法
     * @param params 调用的方法参数
     */
    protected void handleError(Throwable ex, Method method, Object... params) {
        try {
            this.exceptionHandler.handleUncaughtException(ex, method, params);
        } catch (Throwable ex2) {
            error(logger, "Exception handler for dal method '" + method.toGenericString() +
                    "' threw unexpected exception itself", ex2);
        }
    }
}
