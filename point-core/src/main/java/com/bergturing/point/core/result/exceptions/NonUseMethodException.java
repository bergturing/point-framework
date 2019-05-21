package com.bergturing.point.core.result.exceptions;

/**
 * 不能使用的方法的异常
 *
 * @author bo.he02@hand-china.com
 * @apiNote 2019/3/18
 */
public class NonUseMethodException extends RuntimeException {
    private static final long serialVersionUID = 1958187915302033433L;

    /**
     * 构造方法
     */
    public NonUseMethodException() {
        super("non used method exception");
    }
}
