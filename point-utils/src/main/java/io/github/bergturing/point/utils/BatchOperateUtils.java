package io.github.bergturing.point.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * 分批处理工具类
 *
 * @author bergturing@qq.com
 * @apiNote 2019/1/7
 */
public class BatchOperateUtils {
    /**
     * 日志打印对象
     */
    private static Logger logger = LoggerFactory.getLogger(BatchOperateUtils.class);

    /**
     * 默认批处理的条数
     */
    private static int DEFAULT_BATCH_COUNT_1000 = 1000;

    /**
     * 分批处理
     *
     * @param batchDataList 分批处理的数据对象
     * @param <T>           处理的数据类型
     * @param <R>           处理的结果类型
     * @return 构造对象
     */
    public static <T, R> Builder<T, R> batch(List<T> batchDataList) {
        return new Builder<>(batchDataList);
    }

    /**
     * 构造类
     *
     * @param <T> 处理的数据类型
     * @param <R> 处理的结果类型
     */
    public static class Builder<T, R> {
        /**
         * 分批处理的数据
         */
        private List<T> batchDataList;

        /**
         * 分批处理结果逻辑对象
         */
        private BinaryOperator<R> resultFunction;

        /**
         * 每一个分批处理的分批数据量
         * 默认为1000
         */
        private int batchCount = DEFAULT_BATCH_COUNT_1000;

        /**
         * 是否输出debug日志，默认为true，能否打印出日志也与系统日志设置有关
         * true：打印日志
         * false：不打印日志
         */
        private boolean debug = true;

        /**
         * 构建器的构造函数
         *
         * @param batchDataList 处理的数据
         */
        private Builder(List<T> batchDataList) {
            this.batchDataList = batchDataList;
        }

        /**
         * 无下标处理逻辑
         *
         * @param actionFunction 无下标处理逻辑对象
         * @return 无下标处理逻辑构建对象
         */
        public Builder<T, R> action(Function<List<T>, R> actionFunction) {
            return new ActionBuilder<>(this.batchDataList, actionFunction);
        }

        /**
         * 有下标处理逻辑
         *
         * @param biActionFunction 有下标处理逻辑对象
         * @return 有下标处理逻辑构建对象
         */
        public Builder<T, R> action(BiFunction<Integer, List<T>, R> biActionFunction) {
            return new BiActionBuilder<>(this.batchDataList, biActionFunction);
        }

        /**
         * 设置结果处理逻辑
         *
         * @param resultFunction 结果处理逻辑对象
         * @return 构建对象
         */
        public Builder<T, R> result(BinaryOperator<R> resultFunction) {
            this.resultFunction = resultFunction;
            return this;
        }

        /**
         * 设置分批处理的大小
         *
         * @param batchCount 分批处理的大小
         * @return 构建对象
         */
        public Builder<T, R> batchCount(int batchCount) {
            this.batchCount = batchCount;
            return this;
        }

        /**
         * 是否打印执行日志
         * true：打印日志
         * false：不打印日志
         *
         * @param debug 是否打印执行日志的标识
         * @return 构建对象
         */
        public Builder<T, R> debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        /**
         * 开始处理
         *
         * @return 处理结果
         */
        public R begin() {
            //处理数据的结果
            R result = null;

            //批次处理开始时间
            LocalDateTime start = null;
            //结束时间
            LocalDateTime end = null;

            if (logger.isDebugEnabled() && this.debug) {
                start = LocalDateTime.now();
                logger.debug("当前批次开始处理... 开始时间 {}", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS")));
            }

            //判断数据是否存在
            if (CollectionUtils.isNotEmpty(this.batchDataList)) {
                //处理的数据的数量
                int size = this.batchDataList.size();
                //下标从
                int fromIndex = 0;
                //下标至
                int toIndex = 0;

                //每一个批次处理的数据量
                if (this.batchCount <= 0) {
                    this.batchCount = DEFAULT_BATCH_COUNT_1000;
                }

                if (logger.isDebugEnabled() && this.debug) {
                    logger.debug("处理的数据的总数 {}， 每个批次处理的数据量 {}", size, this.batchCount);
                }

                //分批次处理
                for (int i = 0, batch = (int) Math.ceil(size * 1.0 / this.batchCount); i < batch; i++) {
                    fromIndex = i * this.batchCount;
                    toIndex = (i + 1) * this.batchCount;
                    if (toIndex > size) {
                        toIndex = size;
                    }

                    if (logger.isDebugEnabled() && this.debug) {
                        logger.debug("批次 {}, 下标从 {}， 下标至 {}", i, fromIndex, toIndex);
                    }

                    //处理数据
                    if (null == result || null == this.resultFunction) {
                        //直接获取批次的结果
                        result = this.process(i, this.batchDataList.subList(fromIndex, toIndex));
                    } else {
                        //对每次处理的结果进行处理
                        result = this.resultFunction.apply(result, this.process(i, this.batchDataList.subList(fromIndex, toIndex)));
                    }
                }
            }

            if (logger.isDebugEnabled() && this.debug) {
                end = LocalDateTime.now();
                logger.debug("批次处理完成... 结束时间 {}, 耗时 {}秒", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS")),
                        Duration.between(start, end).toMillis() / 1000.0);
            }

            //返回结果
            return result;
        }

        /**
         * 处理逻辑
         *
         * @param batchIndex 批次下标
         * @param subList    子list
         * @return 处理结果
         */
        protected R process(Integer batchIndex, List<T> subList) {
            return null;
        }
    }

    /**
     * 无下标的构建类
     *
     * @param <T> 操作数据类型
     * @param <R> 结果类型
     */
    public static class ActionBuilder<T, R> extends Builder<T, R> {
        /**
         * 无分批下标的处理逻辑
         */
        private Function<List<T>, R> actionFunction;

        ActionBuilder(List<T> batchDataList, Function<List<T>, R> actionFunction) {
            super(batchDataList);
            this.actionFunction = actionFunction;
        }

        @Override
        protected R process(Integer batchIndex, List<T> subList) {
            return this.actionFunction.apply(subList);
        }
    }

    /**
     * 有下标的构建类
     *
     * @param <T> 操作数据类型
     * @param <R> 结果类型
     */
    public static class BiActionBuilder<T, R> extends Builder<T, R> {
        /**
         * 有分批下标的处理逻辑
         */
        private BiFunction<Integer, List<T>, R> biActionFunction;

        BiActionBuilder(List<T> batchDataList, BiFunction<Integer, List<T>, R> biActionFunction) {
            super(batchDataList);
            this.biActionFunction = biActionFunction;
        }

        @Override
        protected R process(Integer batchIndex, List<T> subList) {
            return this.biActionFunction.apply(batchIndex, subList);
        }
    }
}
