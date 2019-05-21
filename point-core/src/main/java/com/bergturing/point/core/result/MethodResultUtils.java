package com.bergturing.point.core.result;

import com.bergturing.point.core.result.defaults.MethodResultImpl;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 方法结果封装的工厂接口
 *
 * @author bergturing@qq.com
 * @apiNote 2018/08/06
 * @see MethodResult
 */
public class MethodResultUtils {
    /**
     * 结果封装对象
     *
     * @param status 状态
     * @param <S>    状态泛型
     * @param <T>    结果类型泛型
     * @return 方法结果封装对象
     */
    public static <S, T> MethodResult<S, T> result(S status) {
        return MethodResultImpl.of(status);
    }

    /**
     * 执行处理结果
     *
     * @param methodResult 方法结果对象
     * @param <S>          状态泛型
     * @param <T>          结果类型泛型
     */
    public static <S, T> ProcessBuilder<S, T> process(MethodResult<S, T> methodResult) {
        return new ProcessBuilder<S, T>(methodResult);
    }

    /**
     * 方法返回结果是否为成功
     *
     * @param methodResult 方法返回结果
     * @param <S>          状态泛型
     * @param <T>          结果值泛型
     * @return 判断结果
     */
    public static <S, T> Boolean isSuccess(MethodResult<S, T> methodResult) {
        // 返回判断结果
        return isSuccess(methodResult, () -> Boolean.TRUE);
    }

    /**
     * 方法返回结果是否为成功
     *
     * @param methodResult    方法返回结果
     * @param successStatuses 成功的状态
     * @param <S>             状态泛型
     * @param <T>             结果值泛型
     * @return 判断结果
     */
    public static <S, T> Boolean isSuccess(MethodResult<S, T> methodResult, S[] successStatuses) {
        // 返回判断结果
        return isSuccess(methodResult, () -> {
            if (Objects.isNull(successStatuses)) {
                return Boolean.FALSE;
            }

            if (Arrays.asList(successStatuses).contains(methodResult.getStatus())) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        });
    }

    /**
     * 方法返回结果是否为成功
     *
     * @param methodResult 方法返回结果
     * @param supplier     最终的判断逻辑
     * @param <S>          状态泛型
     * @param <T>          结果值泛型
     * @return 判断结果
     */
    public static <S, T> Boolean isSuccess(MethodResult<S, T> methodResult, BooleanSupplier supplier) {
        // 结果为空
        if (Objects.isNull(methodResult)) {
            return false;
        }

        // 状态值
        S status = methodResult.getStatus();
        // 状态值为空
        if (Objects.isNull(status)) {
            return false;
        }

        // 判断状态值是否为Boolean类型
        if (status instanceof Boolean) {
            return (Boolean) status;
        } else {
            // 否则就是取判断逻辑结果
            return supplier.getAsBoolean();
        }
    }

    /**
     * 方法返回结果处理的构建对象
     *
     * @param <S> 状态泛型
     * @param <T> 结果值泛型
     */
    public static class ProcessBuilder<S, T> {
        /**
         * 成功标识的集合
         */
        private S[] successStatuses;

        /**
         * 方法返回结果对象
         */
        private MethodResult<S, T> methodResult;

        /**
         * 根据 方法返回结果对象 创建 方法返回结果处理的构建对象
         *
         * @param methodResult 方法返回结果对象
         */
        public ProcessBuilder(MethodResult<S, T> methodResult) {
            this.methodResult = methodResult;
        }

        /**
         * 根据 方法返回结果处理的构建对象 构造 方法返回结果处理的构建对象
         *
         * @param processBuilder 方法返回结果处理的构建对象
         */
        ProcessBuilder(ProcessBuilder<S, T> processBuilder) {
            this.successStatuses = processBuilder.successStatuses;
            this.methodResult = processBuilder.methodResult;
        }

        /**
         * 设置成功的状态值集合
         *
         * @param successStatuses 成功的状态值集合
         * @return 构建对象
         */
        public ProcessBuilder<S, T> setSuccessStatus(S[] successStatuses) {
            this.successStatuses = successStatuses;
            return this;
        }

        /**
         * 方法返回结果处理的构建对象(Consumer)
         *
         * @param success 成功的消费逻辑对象
         * @param failure 失败的消费逻辑对象
         * @return 方法返回结果处理的构建对象(Consumer)
         */
        public ConsumerProcessBuilder<S, T> consumer(Consumer<MethodResult<S, T>> success,
                                                     Consumer<MethodResult<S, T>> failure) {
            // 创建并返回 方法返回结果处理的构建对象(Consumer)
            return new ConsumerProcessBuilder<>(this, success, failure);
        }

        /**
         * 方法返回结果处理的构建对象(Function)
         *
         * @param success 成功的函数逻辑对象
         * @param failure 失败的函数逻辑对象
         * @return 方法返回结果处理的构建对象(Function)
         */
        public <R> FunctionProcessBuilder<S, T, R> function(Function<MethodResult<S, T>, R> success,
                                                            Function<MethodResult<S, T>, R> failure) {
            // 创建并返回 方法返回结果处理的构建对象(Function)
            return new FunctionProcessBuilder<S, T, R>(this, success, failure);
        }

        /**
         * 方法返回结果是否为成功
         *
         * @return 方法返回结果是否成功
         */
        Boolean isSuccess() {
            if (Objects.isNull(this.successStatuses)) {
                return MethodResultUtils.isSuccess(this.getMethodResult());
            } else {
                return MethodResultUtils.isSuccess(this.getMethodResult(), this.successStatuses);
            }
        }

        /**
         * 获取方法执行结果对象
         *
         * @return 方法执行结果对象
         */
        MethodResult<S, T> getMethodResult() {
            return this.methodResult;
        }
    }

    /**
     * 方法返回结果处理的构建对象
     * 处理返回结果的方式为消费(Consumer)
     *
     * @param <S> 状态泛型
     * @param <T> 结果值泛型
     */
    public static class ConsumerProcessBuilder<S, T> extends ProcessBuilder<S, T> {
        /**
         * 成功的消费逻辑
         */
        private Consumer<MethodResult<S, T>> success;

        /**
         * 失败的消费逻辑
         */
        private Consumer<MethodResult<S, T>> failure;

        /**
         * 根据 方法返回结果处理的构建对象 构造 方法返回结果处理的构建对象(Consumer)
         *
         * @param processBuilder 方法返回结果处理的构建对象
         * @param success        成功的消费逻辑对象
         * @param failure        失败的消费逻辑对象
         */
        ConsumerProcessBuilder(ProcessBuilder<S, T> processBuilder,
                               Consumer<MethodResult<S, T>> success,
                               Consumer<MethodResult<S, T>> failure) {
            super(processBuilder);
            this.success = success;
            this.failure = failure;
        }

        /**
         * 执行
         */
        public void process() {
            // 方法返回结果是否为成功
            Boolean success = this.isSuccess();

            // 成功
            if (success && Objects.nonNull(this.success)) {
                this.success.accept(this.getMethodResult());
            }

            // 失败
            if (!success && Objects.nonNull(this.failure)) {
                this.failure.accept(this.getMethodResult());
            }
        }
    }

    /**
     * 方法返回结果处理的构建对象
     * 处理返回结果的方式为函数(Function)
     *
     * @param <S> 状态泛型
     * @param <T> 结果值泛型
     * @param <R> 执行结果的泛型
     */
    public static class FunctionProcessBuilder<S, T, R> extends ProcessBuilder<S, T> {
        /**
         * 成功的处理函数
         */
        private Function<MethodResult<S, T>, R> success;

        /**
         * 失败的处理函数
         */
        private Function<MethodResult<S, T>, R> failure;

        /**
         * 根据 方法返回结果处理的构建对象 构造 方法返回结果处理的构建对象(Function)
         *
         * @param processBuilder 方法返回结果处理的构建对象
         * @param success        成功的函数逻辑对象
         * @param failure        失败的函数逻辑对象
         */
        FunctionProcessBuilder(ProcessBuilder<S, T> processBuilder,
                               Function<MethodResult<S, T>, R> success,
                               Function<MethodResult<S, T>, R> failure) {
            super(processBuilder);
            this.success = success;
            this.failure = failure;
        }

        /**
         * 执行
         */
        public Optional<R> process() {
            // 方法返回结果是否为成功
            Boolean success = this.isSuccess();

            // 成功
            if (success && Objects.nonNull(this.success)) {
                return Optional.of(this.success.apply(this.getMethodResult()));
            }

            // 失败
            if (!success && Objects.nonNull(this.failure)) {
                return Optional.of(failure.apply(this.getMethodResult()));
            }

            // 返回空结果
            return Optional.empty();
        }
    }
}
