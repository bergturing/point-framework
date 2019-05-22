package io.github.bergturing.point.dal.aop;

import io.github.bergturing.point.dal.DalExecutor;
import io.github.bergturing.point.dal.aop.interceptor.DalUncaughtExceptionHandler;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

/**
 * 分布式应用锁后置处理器
 *
 * @author bergturing@qq.com
 */
public class DalAnnotationBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {
    /**
     * 需要进行加分布式应用锁的注解
     */
    private Class<? extends Annotation> dalAnnotationType;

    /**
     * 分布式应用锁的执行器
     */
    private DalExecutor dalExecutor;

    /**
     * 分布式应用锁未捕获的异常处理器
     */
    private DalUncaughtExceptionHandler exceptionHandler;

    /**
     * 默认构造函数
     */
    public DalAnnotationBeanPostProcessor() {
        this.setBeforeExistingAdvisors(true);
    }

    /**
     * 设置需要进行加分布式应用锁的注解
     *
     * @param dalAnnotationType 需要进行加分布式应用锁的注解
     */
    public void setDalAnnotationType(Class<? extends Annotation> dalAnnotationType) {
        Assert.notNull(dalAnnotationType, "'dalAnnotationType' must not be null");
        this.dalAnnotationType = dalAnnotationType;
    }

    /**
     * 设置分布式应用锁的执行器
     *
     * @param dalExecutor 分布式应用锁的执行器
     */
    public void setDalExecutor(DalExecutor dalExecutor) {
        this.dalExecutor = dalExecutor;
    }

    /**
     * 设置分布式应用锁未捕获的异常处理器
     *
     * @param exceptionHandler 分布式应用锁未捕获的异常处理器
     */
    public void setExceptionHandler(DalUncaughtExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);

        DalAnnotationAdvisor advisor = new DalAnnotationAdvisor(this.dalExecutor, this.exceptionHandler);
        if (this.dalAnnotationType != null) {
            advisor.setDalAnnotationType(this.dalAnnotationType);
        }

        // 设置Advisor的工厂
        advisor.setBeanFactory(beanFactory);
        // 设置Advisor对象
        this.advisor = advisor;
    }
}
