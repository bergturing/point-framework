package io.github.bergturing.point.dal.service;

import io.github.bergturing.point.dal.entity.Association;
import io.github.bergturing.point.dal.entity.Entity;

import java.util.List;

/**
 * 提供加锁数据的服务接口
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
public interface ILockService {
    /**
     * 单个参数锁，单个值
     *
     * @param id 锁对象
     */
    void lockSingleParam(Long id);

    /**
     * 单个参数锁，集合
     *
     * @param idList 锁对象
     */
    void lockSingleParam(List<Long> idList);

    /**
     * 多个参数锁
     *
     * @param id     单个值
     * @param idList 集合
     */
    void lockMultiParam(Long id, List<Long> idList);

    /**
     * 单个锁对象
     *
     * @param entity 对象
     */
    void lockSingleEntity(Entity entity);

    /**
     * 集合锁对象
     *
     * @param entityList 对象
     */
    void lockMultiEntity(List<Entity> entityList);

    /**
     * 测试多个加锁的参数对象
     *
     * @param entity     对象1
     * @param entityList 对象2
     */
    void lockMultiParamEntity(Entity entity, List<Entity> entityList);

    /**
     * 测试单个锁关联对象
     *
     * @param association 关联对象
     */
    void lockSingleAssociation(Association association);

    /**
     * 测试多个锁关联对象
     *
     * @param associationList 关联对象
     */
    void lockMultiAssociation(List<Association> associationList);

    /**
     * 测试多个加锁的参数关联对象
     *
     * @param association     关联对象1
     * @param associationList 关联对象2
     */
    void lockMultiParamAssociation(Association association, List<Association> associationList);
}
