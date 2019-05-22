package io.github.bergturing.point.dal.aop;

import io.github.bergturing.point.dal.DalExecutor;
import io.github.bergturing.point.dal.aop.interceptor.DalExecutionInterceptor;
import io.github.bergturing.point.dal.aop.interceptor.DalUncaughtExceptionHandler;

/**
 * 注解式分布式应用锁的拦截器
 *
 * @author bergturing@qq.com
 * @date 2019/5/10
 */
public class AnnotationDalExecutionInterceptor extends DalExecutionInterceptor {
    public AnnotationDalExecutionInterceptor(DalExecutor dalExecutor) {
        super(dalExecutor);
    }

    public AnnotationDalExecutionInterceptor(DalExecutor dalExecutor, DalUncaughtExceptionHandler exceptionHandler) {
        super(dalExecutor, exceptionHandler);
    }
}
