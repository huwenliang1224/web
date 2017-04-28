package com.hwl.maven.web.bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by huwenl on 2017/4/28.
 */
public class StartUpManager {

    public static void initStartupParam() throws Exception {
        InputStreamReader reader = null;
        InputStream resourceAsStream = null;
        Properties properties = new Properties();
        try {
            resourceAsStream = StartUpManager.class.getResourceAsStream("/startup.properties");
            reader = new InputStreamReader(resourceAsStream, "UTF-8");
            properties.load(reader);
        } catch (Exception e) {
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }

        try {
            StartUpParam.port = Integer.parseInt(properties.getProperty("port"));
            System.out.println("port:" + StartUpParam.port);
        } catch (NumberFormatException e2) {
            throw e2;
        }
    }
}
