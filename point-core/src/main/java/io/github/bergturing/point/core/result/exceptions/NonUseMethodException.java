package io.github.bergturing.point.core.result.exceptions;

/**
 * 不能使用的方法的异常
 *
 * @author bergturing@qq.com
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
