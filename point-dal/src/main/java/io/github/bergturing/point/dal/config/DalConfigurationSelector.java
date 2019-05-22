package io.github.bergturing.point.dal.config;

import io.github.bergturing.point.dal.annotations.EnableDal;
import io.github.bergturing.point.dal.enums.DalType;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 分布式应用锁配置的选择器
 *
 * @author bergturing@qq.com
 */
public class DalConfigurationSelector implements ImportSelector {
    /**
     * AspectJ实现分布式应用锁代理的配置
     */
    private static final String DAL_ASPECT_CONFIGURATION_CLASS_NAME =
            "com.hand.hus.dal.annotations.AspectJDalConfiguration";

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 获取注解对象
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(importingClassMetadata
                        .getAnnotationAttributes(EnableDal.class.getName(), false));
        if (attributes == null) {
            // 注解不存在
            throw new IllegalArgumentException(
                    "@enableDal is not present on importing class " + importingClassMetadata.getClassName());
        }

        // 获取代理方式
        AdviceMode adviceMode = attributes.getEnum("mode");
        // 获取分布式应用锁类型
        DalType dalType = attributes.getEnum("dalType");
        // 获取导入类名
        String[] imports = selectImports(adviceMode, dalType);

        //结果为空
        if (imports == null) {
            throw new IllegalArgumentException("Unknown AdviceMode: " + adviceMode +
                    " Or Distributed Application Lock Type: " + dalType);
        }

        // 返回需要导入的类名
        return imports;
    }

    /**
     * 获取导入的类名
     *
     * @param adviceMode 代理类型
     * @param dalType    分布式应用锁的类型
     * @return 导入的类名
     */
    private String[] selectImports(AdviceMode adviceMode, DalType dalType) {
        switch (adviceMode) {
            case PROXY:
                switch (dalType) {
                    case REDIS:
                        return new String[]{ProxyRedisDalConfiguration.class.getName()};
                    default:
                        return null;
                }
            case ASPECTJ:
                return new String[]{DAL_ASPECT_CONFIGURATION_CLASS_NAME};
            default:
                return null;
        }
    }
}
