package com.bergturing.point.dal.aop;

import com.bergturing.point.dal.DalExecutor;
import com.bergturing.point.dal.annotations.DistributedApplicationLock;
import com.bergturing.point.dal.aop.interceptor.DalUncaughtExceptionHandler;
import com.bergturing.point.dal.aop.interceptor.SimpleDalUncaughtExceptionHandler;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 分布式应用锁注解的Advisor
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/10
 */
public class DalAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    /**
     * 分布式应用锁的未捕获异常处理器
     */
    private DalUncaughtExceptionHandler exceptionHandler;

    /**
     * 通知
     */
    private Advice advice;

    /**
     * 切面
     */
    private Pointcut pointcut;

    /**
     * 无参构造方法
     */
    public DalAnnotationAdvisor() {
        this(null, null);
    }

    /**
     * 构造方法
     *
     * @param dalExecutor      分布式应用锁的执行器
     * @param exceptionHandler 未捕获异常的处理器
     */
    public DalAnnotationAdvisor(DalExecutor dalExecutor, DalUncaughtExceptionHandler exceptionHandler) {
        Set<Class<? extends Annotation>> dalAnnotationTypes = new LinkedHashSet<>(2);
        dalAnnotationTypes.add(DistributedApplicationLock.class);

        if (exceptionHandler != null) {
            this.exceptionHandler = exceptionHandler;
        } else {
            this.exceptionHandler = new SimpleDalUncaughtExceptionHandler();
        }

        // 构建通知
        this.advice = this.buildAdvice(dalExecutor, this.exceptionHandler);
        // 构建切面
        this.pointcut = this.buildPointcut(dalAnnotationTypes);
    }

    /**
     * 设置分布式应用锁的处理器
     *
     * @param dalExecutor 分布式应用锁的处理器
     */
    public void setDalExecutor(DalExecutor dalExecutor) {
        // 重新构建通知
        this.advice = this.buildAdvice(dalExecutor, this.exceptionHandler);
    }

    /**
     * 设置分布式应用锁监听的注解的类型
     *
     * @param dalAnnotationType 分布式应用锁监听的注解的类型
     */
    public void setDalAnnotationType(Class<? extends Annotation> dalAnnotationType) {
        Assert.notNull(dalAnnotationType, "'dalAnnotationType' must not be null");

        Set<Class<? extends Annotation>> dalAnnotationTypes = new HashSet<Class<? extends Annotation>>();
        dalAnnotationTypes.add(dalAnnotationType);

        // 重新构建切面
        this.pointcut = buildPointcut(dalAnnotationTypes);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    /**
     * 构建通知
     *
     * @param dalExecutor      分布式应用锁处理器
     * @param exceptionHandler 未捕获异常处理器
     * @return 通知
     */
    protected Advice buildAdvice(DalExecutor dalExecutor, DalUncaughtExceptionHandler exceptionHandler) {
        return new AnnotationDalExecutionInterceptor(dalExecutor, exceptionHandler);
    }

    /**
     * 构建切面
     *
     * @param dalAnnotationTypes 分布式应用锁监听的注解的类型
     * @return 切面
     */
    protected Pointcut buildPointcut(Set<Class<? extends Annotation>> dalAnnotationTypes) {
        ComposablePointcut result = null;

        // 处理监听
        for (Class<? extends Annotation> dalAnnotationType : dalAnnotationTypes) {
            Pointcut cpc = new AnnotationMatchingPointcut(dalAnnotationType, true);
            Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(dalAnnotationType);

            if (result == null) {
                result = new ComposablePointcut(cpc);
            } else {
                result.union(cpc);
            }
            result = result.union(mpc);
        }

        // 返回切面
        return result;
    }
}
