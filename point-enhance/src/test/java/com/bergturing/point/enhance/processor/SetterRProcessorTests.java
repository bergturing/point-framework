package com.bergturing.point.enhance.processor;

import com.bergturing.point.enhance.entity.Entity;
import org.junit.Test;

/**
 * SetterR处理器的测试类
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
public class SetterRProcessorTests {
    @Test
    public void testSetterR() {
        // 创建实体对象
        Entity entity = new Entity();

        System.out.println(entity.getName());

        // 设置值
//        System.out.println(entity.setNameR("Berg"));

        System.out.println(entity.getName());

        // 判断结果
        assert !"Jack".equals(entity.getName());
    }
}
