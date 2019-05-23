package io.github.bergturing.point.dal;

import io.github.bergturing.point.dal.entity.Association;
import io.github.bergturing.point.dal.entity.Entity;
import io.github.bergturing.point.dal.service.ILockService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;

/**
 * 分布式应用锁注解的测试
 *
 * @author bergturing@qq.com
 */
public class DalAnnotationTests extends PointDalApplicationTests {
    /**
     * 对字段加锁的服务
     */
    @Autowired
    private ILockService lockFieldService;

    /**
     * 对方法加锁的服务
     */
    @Autowired
    private ILockService lockMethodService;

    /**
     * 测试对字段加锁
     */
    @Test
    public void testField() {
        // 测试参数锁
        this.testParam(this.lockFieldService);
        // 测试实体
        this.testEntity(this.lockFieldService);
        // 测试关联实体
        this.testAssociation(this.lockFieldService);
    }

    /**
     * 测试对方法加锁
     */
    @Test
    public void testMethod() {
        // 测试参数锁
        this.testParam(this.lockMethodService);
        // 测试实体
        this.testEntity(this.lockMethodService);
        // 测试关联实体
        this.testAssociation(this.lockMethodService);
    }

    /**
     * 测试参数锁
     *
     * @param lockService 加锁服务对象
     */
    private void testParam(ILockService lockService) {
        // 单个简单参数值
        Long id = 10L;
        // 单个集合参数值
        List<Long> idList = LongStream.rangeClosed(11L, 20L).boxed().collect(toList());

        // 单个参数参数值锁
        lockService.lockSingleParam(id);

        // 单个参数多个值
        lockService.lockSingleParam(idList);

        // 多个参数
        lockService.lockMultiParam(id, idList);
    }

    /**
     * 测试实体
     *
     * @param lockService 加锁服务对象
     */
    private void testEntity(ILockService lockService) {
        // 单个实体
        Entity entity = Entity.PROTOTYPE.prototypeClone().setIdR(101L);
        // 多个实体
        List<Entity> entityList = this.getEntityList(1, 100);

        // 测试单个实体
        lockService.lockSingleEntity(entity);

        // 测试多个实体
        lockService.lockMultiEntity(entityList);

        // 测试多个注解参数实体
        lockService.lockMultiParamEntity(entity, entityList);
    }

    /**
     * 测试关联实体
     *
     * @param lockService 加锁服务对象
     */
    private void testAssociation(ILockService lockService) {
        // 单个关联实体
        Association association = Association.PROTOTYPE.prototypeClone()
                .setIdR(101L)
                .setEntityListR(this.getEntityList(101 * 10 + 1, 102 * 10))
                .setIdListR(LongStream.rangeClosed(101 * 10 + 1, 102 * 10).boxed().collect(toList()));
        // 多个关联实体
        List<Association> associationList = this.getAssociationList(1L, 100L, 10L);

        // 测试单个关联实体
        lockService.lockSingleAssociation(association);

        // 测试多个关联实体
        lockService.lockMultiAssociation(associationList);

        // 测试多个注解参数关联实体
        lockService.lockMultiParamAssociation(association, associationList);
    }

    /**
     * 获取实体对象
     *
     * @param start 开始值
     * @param end   结束值
     * @return 生成的实体对象
     */
    private List<Entity> getEntityList(long start, long end) {
        // 生成对象
        return LongStream.rangeClosed(start, end)
                .boxed()
                .map(index -> Entity.PROTOTYPE.prototypeClone()
                        .setIdR(index))
                .collect(toList());
    }

    /**
     * 获取关联实体对象
     *
     * @param start       开始值
     * @param end         结束值
     * @param entityCount 每个实体关联的实体数量
     * @return 生成的关联实体对象
     */
    private List<Association> getAssociationList(long start, long end, long entityCount) {
        // 生成对象
        return LongStream.rangeClosed(start, end)
                .boxed()
                .map(index -> Association.PROTOTYPE.prototypeClone()
                        .setIdR(index)
                        .setEntityListR(this.getEntityList(index * entityCount + 1, (index + 1) * entityCount))
                        .setIdListR(LongStream.rangeClosed(index * entityCount + 1, (index + 1) * entityCount)
                                .boxed().collect(toList())))
                .collect(toList());
    }
}
