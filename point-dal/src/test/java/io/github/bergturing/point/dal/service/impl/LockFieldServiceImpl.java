package io.github.bergturing.point.dal.service.impl;

import io.github.bergturing.point.dal.annotations.DistributedApplicationLock;
import io.github.bergturing.point.dal.annotations.LockItem;
import io.github.bergturing.point.dal.annotations.Locking;
import io.github.bergturing.point.dal.entity.Association;
import io.github.bergturing.point.dal.entity.Entity;
import io.github.bergturing.point.dal.enums.Union;
import io.github.bergturing.point.dal.service.ILockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.bergturing.point.dal.constants.LockConstants.LOCK_GROUP_CONSTANTS;
import static io.github.bergturing.point.dal.constants.LockConstants.SERVICE_FIELD;
import static io.github.bergturing.point.utils.LoggerUtils.debug;

/**
 * 对属性加锁的服务实现
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/21
 */
@Service(SERVICE_FIELD)
public class LockFieldServiceImpl implements ILockService {
    /**
     * 日志打印对象
     */
    private static final Logger logger = LoggerFactory.getLogger(LockFieldServiceImpl.class);

    @Override
    @DistributedApplicationLock
    public void lockSingleParam(@Locking @LockItem(LOCK_GROUP_CONSTANTS) Long id) {
        debug(logger, "单个参数锁，单个值 {}", id);
    }

    @Override
    @DistributedApplicationLock
    public void lockSingleParam(@Locking @LockItem(LOCK_GROUP_CONSTANTS) List<Long> idList) {
        debug(logger, "单个参数锁，集合 {}", idList);
    }

    @Override
    @DistributedApplicationLock
    public void lockMultiParam(@Locking @LockItem(LOCK_GROUP_CONSTANTS) Long id,
                               @Locking @LockItem(LOCK_GROUP_CONSTANTS) List<Long> idList) {
        debug(logger, "多个参数锁，{} {}", id, idList);
    }

    @Override
    @DistributedApplicationLock
    public void lockSingleEntity(@Locking(Union.UNION_A) Entity entity) {
        debug(logger, "测试单个实体注解锁 {}", entity);
    }

    @Override
    @DistributedApplicationLock
    public void lockMultiEntity(@Locking(Union.UNION_A) List<Entity> entityList) {
        debug(logger, "测试多个实体注解锁 {}", entityList);
    }

    @Override
    @DistributedApplicationLock
    public void lockMultiParamEntity(@Locking(Union.UNION_A) Entity entity,
                                     @Locking(Union.UNION_A) List<Entity> entityList) {
        debug(logger, "测试多个加锁参数实体注解锁 {} {}", entity, entityList);
    }

    @Override
    @DistributedApplicationLock
    public void lockSingleAssociation(@Locking(Union.UNION_A) Association association) {
        debug(logger, "测试单个关联实体注解锁 {}", association);
    }

    @Override
    @DistributedApplicationLock
    public void lockMultiAssociation(@Locking(Union.UNION_A) List<Association> associationList) {
        debug(logger, "测试多个关联实体注解锁 {}", associationList);
    }

    @Override
    @DistributedApplicationLock
    public void lockMultiParamAssociation(@Locking(Union.UNION_A) Association association,
                                          @Locking(Union.UNION_A) List<Association> associationList) {
        debug(logger, "测试多个加锁参数关联实体注解锁 {} {}", association, associationList);
    }
}
