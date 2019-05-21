package com.bergturing.point.core.result.exceptions;

/**
 * 结果值为多个值异常
 *
 * @author bo.he02@hand-china.com
 * @apiNote 2019/3/18
 */
public class MultiResultException extends RuntimeException {
    private static final long serialVersionUID = -3428649482235767637L;

    /**
     * 构造方法
     */
    public MultiResultException() {
        super("multi result exception");
    }
}
