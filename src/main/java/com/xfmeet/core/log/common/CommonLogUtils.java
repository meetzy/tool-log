package com.xfmeet.core.log.common;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author meetzy
 */
public class CommonLogUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonLogUtils.class);
    
    private static final String LEVEL_INFO = "INFO";
    private static final String LEVEL_DEBUG = "DEBUG";
    private static final String LEVEL_WARN = "WARN";
    private static final String LEVEL_ERROR = "ERROR";
    private static final String LEVEL_TRACE = "TRACE";
    
    public static void log(String level, String s) {
        level = level.toUpperCase();
        switch (level) {
            case LEVEL_DEBUG:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(s);
                }
                break;
            case LEVEL_INFO:
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info(s);
                }
                break;
            case LEVEL_TRACE:
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(s);
                }
                break;
            case LEVEL_WARN:
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(s);
                }
                break;
            case LEVEL_ERROR:
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(s);
                }
                break;
            default:
                LOGGER.info(s);
        }
    }
    
    
    public static String getClassName(Class<?> cla, String type) {
        switch (type) {
            case LogType.SIMPLE:
                return cla.getSimpleName();
            case LogType.CANONICAL:
                return cla.getCanonicalName();
            default:
                return cla.getSimpleName();
        }
    }
    
    public static String getLogString(String className, String methodName, Object arg) {
        return String.format("class:{%s}", className) +
                String.format("->method:{%s}", methodName) +
                String.format("->params:{%s}", JSON.toJSONString(arg));
    }
}
