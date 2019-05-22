package io.github.bergturing.point.dal;

import io.github.bergturing.point.dal.exceptions.LockFailureException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * 分布式应用锁测试
 *
 * @author bergturing@qq.com
 * @date 2019/4/11
 */
public class DalUtilsTests extends PointDalApplicationTests {
    /**
     * 日志打印对象
     */
    private static final Logger logger = LoggerFactory.getLogger(DalUtilsTests.class);

    /**
     * 可用资源状态
     */
    private static final String RESOURCE_NEW = "NEW";

    /**
     * redis服务对象
     */
    @Autowired
    private DalExecutor dalExecutor;

    /**
     * 锁单个对象的测试方案
     * <p>
     * 进行100个测试案例，并最终打印测试结果
     * 每个测试案例针对固定个共享资源，使用100个线程去消费
     * <p>
     * 按照设计
     */
    @Test
    public void testSingleLock() {
        // 单个处理的数据
        int sourceId = 1;

        // 处理数据
        this.process(10, () -> this.singleNonLock(sourceId), () -> this.singleLock(sourceId),
                store -> store.select(sourceId));
    }

    /**
     *
     */
    @Test
    public void testMultiLock() {
        // 批处理的资源
        List<Integer> resourceIds = IntStream.rangeClosed(1, 10).boxed().collect(toList());

        // 处理数据
        this.process(10, () -> this.multiNonLock(resourceIds), () -> this.multiLock(resourceIds),
                store -> String.join("\n", store.select(resourceIds).values()));
    }

    /**
     * 通用逻辑处理过程
     *
     * @param loopCount   循环处理的次数
     * @param nonSupplier 不加锁的处理逻辑
     * @param supplier    加锁的处理逻辑
     * @param function    处理结果输出转换函数
     */
    private void process(int loopCount, Supplier<ResourceStore> nonSupplier, Supplier<ResourceStore> supplier, Function<ResourceStore, String> function) {
        // 不加锁的测试结果
        List<ResourceStore> nonLockResult = new ArrayList<>(loopCount);
        // 加锁的测试结果
        List<ResourceStore> lockResult = new ArrayList<>(loopCount);

        // 处理
        for (int i = 1; i <= loopCount; i++) {
            // 不加锁
            nonLockResult.add(nonSupplier.get());
            // 加锁
            lockResult.add(supplier.get());
        }

        // 不加锁
        logger.debug("non lock:");
        nonLockResult.forEach(store -> {
            logger.debug(function.apply(store));
        });

        // 加锁
        logger.debug("lock:");
        lockResult.forEach(store -> {
            logger.debug(function.apply(store));
        });
    }

    /**
     * 单个锁方法   不加锁
     *
     * @param resourceId 处理的资源的标识
     */
    private ResourceStore singleNonLock(final int resourceId) {
        // 返回处理结果
        return this.multiThread(100, (index, store) -> {
            // 获取资源
            String resource = store.select(resourceId);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 校验资源并设置处理结果
            if (RESOURCE_NEW.equals(resource)) {
                store.update(resourceId, store.select(resourceId) + "@");
            }
        });
    }

    /**
     * 单个锁方法    加分布式锁
     *
     * @param resourceId 处理的资源的标识
     * @return 处理结果
     */
    private ResourceStore singleLock(final int resourceId) {
        // 返回处理结果
        return this.multiThread(100, (index, store) -> {
            try {
                DalUtils.singleLock(dalExecutor, Lock.PROTOTYPE.prototypeClone().setKey(String.valueOf(resourceId)),
                        () -> {
                            // 获取资源
                            String resource = store.select(resourceId);
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // 校验资源并设置处理结果
                            if (RESOURCE_NEW.equals(resource)) {
                                store.update(resourceId, store.select(resourceId) + "@");
                            }
                            return true;
                        });
            } catch (LockFailureException ignored) {
            }
        });
    }

    /**
     * 批量处理不加锁
     *
     * @param resourceIds 资源集合
     * @return 处理结果
     */
    private ResourceStore multiNonLock(final List<Integer> resourceIds) {
        // 返回处理结果
        return this.multiThread(100, (index, store) -> {
            // 获取数据
            Map<Integer, String> select = store.select(resourceIds);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            select.forEach((id, resource) -> {
                if (RESOURCE_NEW.equals(resource)) {
                    store.update(id, store.select(id) + "@");
                }
            });
        });
    }

    /**
     * 批量处理加锁
     *
     * @param resourceIds 资源集合
     * @return 处理结果
     */
    private ResourceStore multiLock(final List<Integer> resourceIds) {
        // 返回处理结果
        return this.multiThread(100, (index, store) -> {
            try {
                // 加锁处理
                DalUtils.self(resourceIds.size())
                        .addAll(resourceIds)
                        .lock(this.dalExecutor,
                                () -> {
                                    // 获取数据
                                    Map<Integer, String> select = store.select(resourceIds);
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    select.forEach((id, resource) -> {
                                        if (RESOURCE_NEW.equals(resource)) {
                                            store.update(id, store.select(id) + "@");
                                        }
                                    });
                                    return true;
                                });
            } catch (LockFailureException ignored) {
            }
        });
    }

    /**
     * 多线程执行
     *
     * @param threadCount 执行的线程数
     * @param biConsumer  执行的逻辑
     * @return 执行完之后的
     */
    private ResourceStore multiThread(int threadCount, BiConsumer<Integer, ResourceStore> biConsumer) {
        // 定义资源
        final ResourceStore resourceStore = new ResourceStore();
        // 创建一个同时允许threadCount个线程并发执行的线程池
        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        long start = System.currentTimeMillis();

        // 使用一百个线程同时处理资源，检查分布式锁是否有效
        IntStream.rangeClosed(1, threadCount).boxed()
                .forEach(index -> executor.execute(() -> {
                    biConsumer.accept(index, resourceStore);
                }));
        executor.shutdown();

        try {
            // awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
            while (!executor.awaitTermination(10, TimeUnit.SECONDS)) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        logger.debug("子线程执行时长：{}ms", (end - start));

        return resourceStore;
    }

    /**
     * 资源仓库
     */
    private static class ResourceStore {

        // 资源库
        private Map<Integer, String> resourceMap = Collections.synchronizedMap(new HashMap<>(20));

        {
            // 初始化资源
            for (int i = 1; i < 10; i++) {
                this.resourceMap.put(i, RESOURCE_NEW);
            }
        }

        /**
         * 查询资源
         *
         * @param id 资源标识
         * @return 查询结果
         */
        private String select(int id) {
            return this.resourceMap.get(id);
        }

        /**
         * 更新资源
         *
         * @param id    资源标识
         * @param value 资源值
         */
        private void update(int id, String value) {
            this.resourceMap.put(id, value);
        }

        /**
         * 批量查询资源
         *
         * @param ids 资源标识
         * @return 查询结果
         */
        private Map<Integer, String> select(List<Integer> ids) {
            Map<Integer, String> selectResult = new HashMap<>(ids.size());

            ids.forEach(id -> {
                if (this.resourceMap.containsKey(id)) {
                    selectResult.put(id, this.resourceMap.get(id));
                }
            });

            return selectResult;
        }
    }
}
