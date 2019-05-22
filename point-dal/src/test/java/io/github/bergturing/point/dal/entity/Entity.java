package io.github.bergturing.point.dal.entity;

import io.github.bergturing.point.core.prototype.Prototype;
import io.github.bergturing.point.core.prototype.defaults.AbstractPrototype;
import io.github.bergturing.point.dal.annotations.LockItem;
import io.github.bergturing.point.dal.annotations.LockUnion;
import io.github.bergturing.point.dal.constants.LockConstants;
import io.github.bergturing.point.dal.enums.Union;

/**
 * 实体对象
 *
 * @author bergturing@qq.com
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
    @LockItem(LockConstants.LOCK_GROUP_ENTITY)
    @LockUnion(Union.UNION_A)
    private Long id;

    /**
     * 方法字段
     *
     * @return 属性值
     */
    @LockItem(LockConstants.LOCK_GROUP_ENTITY)
    @LockUnion(Union.UNION_B)
    public Long getId() {
        return id;
    }

    public Entity setIdR(Long id) {
        this.id = id;
        return this;
    }
}
