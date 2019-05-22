package io.github.bergturing.point.core.prototype.exceptions;

/**
 * 克隆失败的异常
 *
 * @author bergturing@qq.com
 */
public class CloneFailureException extends RuntimeException {
    public CloneFailureException() {
    }

    public CloneFailureException(String s) {
        super(s);
    }
}
