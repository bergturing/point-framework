package io.github.bergturing.point.utils.entity;

/**
 * 实体工具类的辅助测试对象
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/19
 */
public class Entity {
    /**
     * id   该字段用于测试通过方法设置值
     */
    private Long id;

    /**
     * 名称   该字段用于测试通过字段设置值
     */
    private String name;

    /**
     * 年龄   该字段用于测试通过方法获取值
     */
    private Long age;

    /**
     * 备注   该字段用于测试通过字段获取值
     */
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
