package io.github.bergturing.point.dal.config;

import io.github.bergturing.point.dal.annotations.EnableDal;
import io.github.bergturing.point.dal.aop.interceptor.DalUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Collection;

import static io.github.bergturing.point.utils.CollectionUtils.isEmpty;

/**
 * 分布式应用锁配置的抽象实现
 *
 * @author bergturing@qq.com
 * @date 2019/5/10
 */
@Configuration
public abstract class AbstractDalConfiguration implements ImportAware {
    /**
     * 启用分布式应用锁的注解属性对象
     */
    protected AnnotationAttributes enableDal;

    /**
     * 分布式锁未捕获的异常的处理器
     */
    protected DalUncaughtExceptionHandler exceptionHandler;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        // 对注解属性对象赋值
        this.enableDal = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableDal.class.getName(), false));
        if (this.enableDal == null) {
            // 注解不存在
            throw new IllegalArgumentException(
                    "@enableDal is not present on importing class " + importMetadata.getClassName());
        }
    }

    /**
     * 设置用户自定义的配置
     *
     * @param configurers 配置对象
     */
    @Autowired(required = false)
    public void setConfigurers(Collection<DalConfigurer> configurers) {
        if (isEmpty(configurers)) {
            return;
        }
        if (configurers.size() > 1) {
            throw new IllegalStateException("Only one DalConfigurer may exist");
        }

        // 获取配置对象
        DalConfigurer configurer = configurers.iterator().next();

        // 设置未捕获的异常处理器
        this.exceptionHandler = configurer.getDalUncaughtExceptionHandler();
    }
}
