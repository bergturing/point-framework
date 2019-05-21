package com.bergturing.point.dal.entity;

import com.bergturing.point.core.prototype.Prototype;
import com.bergturing.point.core.prototype.defaults.AbstractPrototype;
import com.bergturing.point.dal.annotations.LockItem;
import com.bergturing.point.dal.annotations.LockUnion;
import com.bergturing.point.dal.enums.Union;

import static com.bergturing.point.dal.constants.LockConstants.LOCK_GROUP_ENTITY;

/**
 * 实体对象
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
public class Entity implements Cloneable {
    /**
     * 初始原型对象
     */
    public static final Prototype<Entity> PROTOTYPE = new AbstractPrototype<Entity>(new Entity()) {
        @Override
        protected Entity clone(Entity prototype) throws CloneNotSupportedException {
            return (Entity) prototype.clone();
        }
    };

    /**
     * 属性字段
     */
    @LockItem(LOCK_GROUP_ENTITY)
    @LockUnion(Union.UNION_A)
    private Long id;

    /**
     * 方法字段
     *
     * @return 属性值
     */
    @LockItem(LOCK_GROUP_ENTITY)
    @LockUnion(Union.UNION_B)
    public Long getId() {
        return id;
    }

    public Entity setIdR(Long id) {
        this.id = id;
        return this;
    }
}
