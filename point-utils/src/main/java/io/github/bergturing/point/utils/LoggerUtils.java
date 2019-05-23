package io.github.bergturing.point.utils;

import org.slf4j.Logger;

/**
 * 日志工具类
 *
 * @author bergturing@qq.com
 */
public class LoggerUtils {
    /**
     * debug日志
     *
     * @param logger  日志打印对象
     * @param message 日志打印信息
     */
    public static void debug(Logger logger, String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    /**
     * debug日志
     *
     * @param logger 日志打印对象
     * @param format 日志打印信息格式字符串
     * @param arg    参数
     */
    public static void debug(Logger logger, String format, Object arg) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, arg);
        }
    }

    /**
     * debug日志
     *
     * @param logger 日志打印对象
     * @param format 日志打印信息格式字符串
     * @param arg1   参数1
     * @param arg2   参数2
     */
    public static void debug(Logger logger, String format, Object arg1, Object arg2) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, arg1, arg2);
        }
    }

    /**
     * debug日志
     *
     * @param logger    日志打印对象
     * @param format    日志打印信息格式字符串
     * @param arguments 参数
     */
    public static void debug(Logger logger, String format, Object... arguments) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, arguments);
        }
    }

    /**
     * debug日志
     *
     * @param logger    日志打印对象
     * @param format    日志打印信息格式字符串
     * @param throwable 可抛出的异常
     */
    public static void debug(Logger logger, String format, Throwable throwable) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, throwable);
        }
    }

    /**
     * debug日志
     *
     * @param logger  日志打印对象
     * @param message 日志打印信息
     */
    public static void info(Logger logger, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    /**
     * info日志
     *
     * @param logger 日志打印对象
     * @param format 日志打印信息格式字符串
     * @param arg    参数
     */
    public static void info(Logger logger, String format, Object arg) {
        if (logger.isInfoEnabled()) {
            logger.info(format, arg);
        }
    }

    /**
     * info日志
     *
     * @param logger 日志打印对象
     * @param format 日志打印信息格式字符串
     * @param arg1   参数1
     * @param arg2   参数2
     */
    public static void info(Logger logger, String format, Object arg1, Object arg2) {
        if (logger.isInfoEnabled()) {
            logger.info(format, arg1, arg2);
        }
    }

    /**
     * info日志
     *
     * @param logger    日志打印对象
     * @param format    日志打印信息格式字符串
     * @param arguments 参数
     */
    public static void info(Logger logger, String format, Object... arguments) {
        if (logger.isInfoEnabled()) {
            logger.info(format, arguments);
        }
    }

    /**
     * info日志
     *
     * @param logger    日志打印对象
     * @param format    日志打印信息格式字符串
     * @param throwable 可抛出的异常
     */
    public static void info(Logger logger, String format, Throwable throwable) {
        if (logger.isInfoEnabled()) {
            logger.info(format, throwable);
        }
    }

    /**
     * warn日志
     *
     * @param logger  日志打印对象
     * @param message 日志打印信息
     */
    public static void warn(Logger logger, String message) {
        if (logger.isWarnEnabled()) {
            logger.warn(message);
        }
    }

    /**
     * warn日志
     *
     * @param logger 日志打印对象
     * @param format 日志打印信息格式字符串
     * @param arg    参数
     */
    public static void warn(Logger logger, String format, Object arg) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, arg);
        }
    }

    /**
     * warn日志
     *
     * @param logger 日志打印对象
     * @param format 日志打印信息格式字符串
     * @param arg1   参数1
     * @param arg2   参数2
     */
    public static void warn(Logger logger, String format, Object arg1, Object arg2) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, arg1, arg2);
        }
    }

    /**
     * warn日志
     *
     * @param logger    日志打印对象
     * @param format    日志打印信息格式字符串
     * @param arguments 参数
     */
    public static void warn(Logger logger, String format, Object... arguments) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, arguments);
        }
    }

    /**
     * warn日志
     *
     * @param logger    日志打印对象
     * @param format    日志打印信息格式字符串
     * @param throwable 可抛出的异常
     */
    public static void warn(Logger logger, String format, Throwable throwable) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, throwable);
        }
    }

    /**
     * error日志
     *
     * @param logger  日志打印对象
     * @param message 日志打印信息
     */
    public static void error(Logger logger, String message) {
        if (logger.isErrorEnabled()) {
            logger.error(message);
        }
    }

    /**
     * error日志
     *
     * @param logger 日志打印对象
     * @param format 日志打印信息格式字符串
     * @param arg    参数
     */
    public static void error(Logger logger, String format, Object arg) {
        if (logger.isErrorEnabled()) {
            logger.error(format, arg);
        }
    }

    /**
     * error日志
     *
     * @param logger 日志打印对象
     * @param format 日志打印信息格式字符串
     * @param arg1   参数1
     * @param arg2   参数2
     */
    public static void error(Logger logger, String format, Object arg1, Object arg2) {
        if (logger.isErrorEnabled()) {
            logger.error(format, arg1, arg2);
        }
    }

    /**
     * error日志
     *
     * @param logger    日志打印对象
     * @param format    日志打印信息格式字符串
     * @param arguments 参数
     */
    public static void error(Logger logger, String format, Object... arguments) {
        if (logger.isErrorEnabled()) {
            logger.error(format, arguments);
        }
    }

    /**
     * error日志
     *
     * @param logger    日志打印对象
     * @param format    日志打印信息格式字符串
     * @param throwable 可抛出的异常
     */
    public static void error(Logger logger, String format, Throwable throwable) {
        if (logger.isErrorEnabled()) {
            logger.error(format, throwable);
        }
    }
}
