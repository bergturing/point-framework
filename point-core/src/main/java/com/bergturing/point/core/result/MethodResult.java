package com.bergturing.point.core.result;

/**
 * 用于封装一个方法的返回值
 * 将方法需要返回的结果和对结果描述的一些额外信息封装
 *
 * @param <T> 结果类型
 * @author bo.he02@hand-china.com
 * @apiNote 2018/08/06
 */
public interface MethodResult<S, T> {
    /**
     * 追加结果消息
     *
     * @param message 结果消息
     * @return 当前对象
     */
    MethodResult<S, T> append(String message);

    /**
     * 获取结果消息
     *
     * @return 结果消息
     */
    String getMessage();

    /**
     * 处理结果状态
     *
     * @return 获取状态
     */
    S getStatus();

    /**
     * 设置处理结果状态
     *
     * @param status 处理结果状态
     * @return 当前对象
     */
    MethodResult<S, T> setStatus(S status);

    /**
     * 获取总数
     *
     * @return 总数
     */
    int getTotal();

    /**
     * 设置总数
     *
     * @param total 总数
     * @return 当前对象
     */
    MethodResult<S, T> setTotal(int total);

    /**
     * 获取结果值
     *
     * @return 结果值
     */
    T getResult();

    /**
     * 设置结果值
     *
     * @param result 结果值
     * @return 当前对象
     */
    MethodResult<S, T> setResult(T result);
}
