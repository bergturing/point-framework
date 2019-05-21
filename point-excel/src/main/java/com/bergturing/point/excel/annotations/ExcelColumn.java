package com.bergturing.point.excel.annotations;

import org.springframework.core.annotation.AliasFor;

/**
 * Excel列的注解
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
public @interface ExcelColumn {
    /**
     * excel列的列名
     *
     * @return excel列的列名
     */
    @AliasFor("value")
    String columnTitle();

    /**
     * excel列的列名
     *
     * @return excel列的列名
     */
    @AliasFor("columnTitle")
    String value();

    /**
     * 单元格默认值
     *
     * @return 单元格默认值
     */
    String cellDefaultValue() default "";
}
