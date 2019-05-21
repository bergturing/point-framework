package com.bergturing.point.core.result.defaults;

import com.bergturing.point.core.result.MethodResult;

/**
 * 方法结果的实现
 *
 * @param <S> 状态泛型
 * @param <T> 结果类型
 * @author bergturing@qq.com
 * @apiNote 2018/08/06
 * @see MethodResult 接口的默认实现，实现了内部的通用功能
 */
public class MethodResultImpl<S, T> implements MethodResult<S, T> {
    /**
     * 处理结果状态
     */
    private S status;

    /**
     * 结果值
     */
    private T result;

    /**
     * 计数
     */
    private int total;

    /**
     * 结果消息
     */
    private StringBuilder message;

    private MethodResultImpl(S status) {
        this.status = status;
        this.message = new StringBuilder();
    }

    /**
     * 静态工厂方法
     *
     * @param status 状态
     * @param <S>    状态泛型
     * @param <T>    结果类型泛型
     * @return 方法结果封装对象
     */
    public static <S, T> MethodResultImpl<S, T> of(S status) {
        return new MethodResultImpl<>(status);
    }

    @Override
    public final S getStatus() {
        return this.status;
    }

    @Override
    public final MethodResult<S, T> setStatus(S status) {
        this.status = status;
        return this;
    }

    @Override
    public final T getResult() {
        return this.result;
    }

    @Override
    public final MethodResult<S, T> setResult(T result) {
        this.result = result;
        return this;
    }

    @Override
    public final int getTotal() {
        return total;
    }

    @Override
    public final MethodResult<S, T> setTotal(int total) {
        this.total = total;
        return this;
    }

    @Override
    public final String getMessage() {
        return this.message.toString();
    }

    @Override
    public final MethodResult<S, T> append(String message) {
        this.message.append(message);
        return this;
    }
}
