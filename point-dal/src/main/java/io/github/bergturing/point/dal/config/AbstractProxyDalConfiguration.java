package io.github.bergturing.point.dal.config;

import io.github.bergturing.point.dal.DalExecutor;
import io.github.bergturing.point.dal.annotations.EnableDal;
import io.github.bergturing.point.dal.aop.DalAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

/**
 * 抽象的分布式应用锁代理配置实现
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/10
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public abstract class AbstractProxyDalConfiguration extends AbstractDalConfiguration {
    /**
     * 分布式应用锁的后置处理器
     *
     * @return 分布式应用锁的后置处理器
     */
    @Bean(name = DalManagementConfigUtils.DAL_ANNOTATION_PROCESSOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DalAnnotationBeanPostProcessor dalAdvisor() {
        Assert.notNull(this.enableDal, "@EnableDal annotation metadata was not injected");

        // 分布式应用锁注解bean后置处理器
        DalAnnotationBeanPostProcessor dalAnnotationBeanPostProcessor = new DalAnnotationBeanPostProcessor();
        // 设置分布式锁处理器
        dalAnnotationBeanPostProcessor.setDalExecutor(this.dalExecutor());
        Class<? extends Annotation> customDalAnnotation = this.enableDal.getClass("annotation");
        if (customDalAnnotation != AnnotationUtils.getDefaultValue(EnableDal.class, "annotation")) {
            dalAnnotationBeanPostProcessor.setDalAnnotationType(customDalAnnotation);
        }

        if (this.exceptionHandler != null) {
            dalAnnotationBeanPostProcessor.setExceptionHandler(this.exceptionHandler);
        }

        dalAnnotationBeanPostProcessor.setProxyTargetClass(this.enableDal.getBoolean("proxyTargetClass"));
        dalAnnotationBeanPostProcessor.setOrder(this.enableDal.<Integer>getNumber("order"));
        return dalAnnotationBeanPostProcessor;
    }

    /**
     * 获取分布式应用锁执行器
     *
     * @return 分布式应用锁执行器
     */
    public abstract DalExecutor dalExecutor();
}
