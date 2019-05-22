package io.github.bergturing.point.enhance.entity;

import io.github.bergturing.point.enhance.annotations.SetterR;

/**
 * 测试使用的实体类
 *
 * @author bergturing@qq.com
 * @date 2019/5/21
 */
@SetterR
public class Entity {
    /**
     * 姓名
     */
    private String name = "Jack";

    public String getName() {
        return name;
    }
}
