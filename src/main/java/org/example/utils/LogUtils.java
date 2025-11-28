package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
    //Init logger
    private static final Logger logger = LogManager.getLogger(LogUtils.class);

    //1. Info: Write normal information (white/green)
    public static void info(String message) {
        logger.info(message);
    }

    //2. Warn (yellow)
    public static void warn(String message) {
        logger.warn(message);
    }

    //3. Error (red)
    public static void error(String message) {
        logger.error(message);
    }

    //4. Debug
    public static void debug(String message) {
        logger.debug(message);
    }
}
