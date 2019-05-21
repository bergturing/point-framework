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
    String columnName();

    /**
     * excel列的列名
     *
     * @return excel列的列名
     */
    @AliasFor("columnName")
    String value();
}
