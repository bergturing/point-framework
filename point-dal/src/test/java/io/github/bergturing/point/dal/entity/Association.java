package io.github.bergturing.point.dal.entity;

import io.github.bergturing.point.core.prototype.Prototype;
import io.github.bergturing.point.core.prototype.defaults.AbstractPrototype;
import io.github.bergturing.point.dal.annotations.LockItem;
import io.github.bergturing.point.dal.annotations.LockNode;
import io.github.bergturing.point.dal.annotations.LockUnion;
import io.github.bergturing.point.dal.constants.LockConstants;
import io.github.bergturing.point.dal.enums.Union;

import java.util.List;

/**
 * 关联对象
 *
 * @author bergturing@qq.com
 * @date 2019/5/21
 */
public class Association implements Cloneable {
    /**
     * 初始原型对象
     */
    public static final Prototype<Association> PROTOTYPE = new AbstractPrototype<Association>(new Association()) {
        @Override
        protected Association clone(Association prototype) throws CloneNotSupportedException {
            return (Association) prototype.clone();
        }
    };

    /**
     * 主键
     */
    @LockItem(LockConstants.LOCK_GROUP_ASSOCIATION)
    @LockUnion(Union.UNION_A)
    private Long id;

    /**
     * 关联的实体对象
     */
    @LockNode
    @LockUnion(Union.UNION_A)
    private List<Entity> entityList;

    /**
     * 实体对象直接关联的id
     */
    @LockItem(LockConstants.LOCK_GROUP_CONSTANTS)
    @LockUnion(Union.UNION_A)
    private List<Long> idList;


    @LockItem(LockConstants.LOCK_GROUP_ASSOCIATION)
    @LockUnion(Union.UNION_B)
    public Long getId() {
        return id;
    }

    @LockNode
    @LockUnion(Union.UNION_B)
    public List<Entity> getEntityList() {
        return this.entityList;
    }

    @LockItem(LockConstants.LOCK_GROUP_CONSTANTS)
    @LockUnion(Union.UNION_B)
    public List<Long> getIdList() {
        return idList;
    }

    public Association setIdR(Long id) {
        this.id = id;
        return this;
    }

    public Association setEntityListR(List<Entity> entityList) {
        this.entityList = entityList;
        return this;
    }

    public Association setIdListR(List<Long> idList) {
        this.idList = idList;
        return this;
    }
}
