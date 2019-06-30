package io.github.bergturing.point.dal;

import io.github.bergturing.point.dal.exceptions.LockFailureException;
import io.github.bergturing.point.dal.redis.RedisLock;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.bergturing.point.utils.LoggerUtils.info;

/**
 * redis锁测试
 *
 * @author bergturing@qq.com
 */
public class RedisLockTests extends PointDalApplicationTests {
    /**
     * 日志打印对象
     */
    private static final Logger logger = LoggerFactory.getLogger(RedisLockTests.class);

    private static final long LOCK_TIMEOUT = 3600;

    private static final String LOCK_GROUP = "test:lock:key:";

    /**
     * redis服务对象
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * redis服务对象
     */
    @Autowired
    private DalExecutor dalExecutor;

    @Test
    public void testSingleThreadLock() {
        int from = 1;
        int to = 1000;
        int forCount = 10;

        long redisCount = 0;
        long dalCount = 0;

        List<String> redisLockList = this.generateRedisData(LOCK_GROUP, from, to);
        List<Lock> dalLockList = this.genetateDalData(LOCK_GROUP, from, to);

        for (int i = 0; i < forCount; i++) {
            redisCount += this.redisLock(redisLockList);
            dalCount += this.dalLock(dalLockList);
        }

        double out = forCount * 1000.0;

        info(logger, "\n每个批次加锁数据量：{}\n循环批次数: {}\n\nredis = {}s\ndal = {}s\n\nredis : dal = {}\n\n",
                to - from + 1, forCount,
                redisCount / out, dalCount / out,
                redisCount * 1.0 / dalCount * 1.0);
    }

    @Test
    public void testMultiThreadLock() {
        int threadCount = 10;
        int from = 1;
        int to = 1000;
        int forCount = 10;

        List<Long> redisResult = Collections.synchronizedList(new ArrayList<>(threadCount));
        List<Long> dalResult = Collections.synchronizedList(new ArrayList<>(threadCount));

        // 线程池
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.execute(() -> {
                long redisCount = 0;
                long dalCount = 0;

                String threadName = "lock:" + Thread.currentThread().getName() + ":";
                List<String> redisLockList = this.generateRedisData(threadName, from, to);
                List<Lock> dalLockList = this.genetateDalData(threadName, from, to);

                for (int j = 0; j < forCount; j++) {
                    redisCount += this.redisLock(redisLockList);
                    dalCount += this.dalLock(dalLockList);
                }

                redisResult.add(redisCount);
                dalResult.add(dalCount);
            });
        }

        executor.shutdown();

        try {
            // awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
            while (!executor.awaitTermination(10, TimeUnit.SECONDS)) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long redisCount = 0;
        long dalCount = 0;

        for (int i = 0; i < threadCount; i++) {
            redisCount += redisResult.get(i);
            dalCount += dalResult.get(i);
        }

        double out = forCount * threadCount * 1000.0;

        info(logger, "\n每个批次加锁数据量：{}\n循环批次数: {}\n线程数: {}\n\nredis = {}s\ndal = {}s\n\nredis : dal = {}\n\n",
                to - from + 1, forCount, threadCount,
                redisCount / out, dalCount / out,
                redisCount * 1.0 / dalCount * 1.0);
    }

    private long redisLock(List<String> lockList) {
        long start = 0;
        long end = 0;

        //获取redis锁
        RedisLock redisLock = new RedisLock(this.redisTemplate, lockList, (int) LOCK_TIMEOUT);

        start = System.currentTimeMillis();
        //锁定数据
        boolean redisLockFlag = redisLock.lockList();

        if (redisLockFlag) {
            // 释放锁
            redisLock.unLockList();

            end = System.currentTimeMillis();
        }

        return end - start;
    }

    private long dalLock(List<Lock> lockList) {
        long start = 0;
        long end = 0;

        try {
            start = System.currentTimeMillis();
            DalUtils.multiLock(this.dalExecutor, lockList, () -> 0);
            end = System.currentTimeMillis();
        } catch (LockFailureException e) {
            e.printStackTrace();
        }

        return end - start;
    }

    private List<String> generateRedisData(String group, int from, int to) {
        //redis锁key对象
        return IntStream.rangeClosed(from, to)
                .boxed()
                .map(index -> group + index)
                .collect(Collectors.toList());
    }

    private List<Lock> genetateDalData(String group, int from, int to) {
        return IntStream.rangeClosed(from, to)
                .boxed()
                .map(index -> new Lock(group, String.valueOf(index), LOCK_TIMEOUT))
                .collect(Collectors.toList());
    }
}
