package com.hwl.maven.web.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huwenl on 2017/5/2.
 */
public class InfoLog {

    public static Logger logger = LoggerFactory.getLogger(InfoLog.class);

    public static void info(String msg) {
        logger.info(msg);
    }
}
