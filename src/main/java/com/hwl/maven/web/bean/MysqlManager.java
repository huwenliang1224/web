package com.hwl.maven.web.bean;


import com.hwl.maven.web.entity.AppCode;
import com.hwl.maven.web.entity.AppStyle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置管理
 *
 * @author huwenl
 */
public class MysqlManager {

    public static Map<String, AppCode> android_master = new ConcurrentHashMap<>();
    public static Map<String, AppCode> ios_master = new ConcurrentHashMap<>();
    public static Map<String, AppCode> android_slave = new ConcurrentHashMap<>();
    public static Map<String, AppCode> ios_slave = new ConcurrentHashMap<>();
    public static boolean isMaster = true;

    public static void initData() throws Exception {
        getData(android_master, ios_master);
        isMaster = true;
    }

    private static void getData(Map<String, AppCode> androidMap, Map<String, AppCode> iosMap) throws Exception {
        Connection con = null;
        try {
            String url = "jdbc:mysql://" + "180.76.181.231" + ":3306/apads?useUnicode=true&amp;characterEncoding=UTF-8";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, "root", "ydads@321");
            PreparedStatement pstmt = con.prepareStatement(getSQL());
            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                String ownerId = res.getString("ownerId");
                String codeId = res.getString("codeId");
                int platForm = res.getInt("platForm");
                String appId = res.getString("appId");
                int style = res.getInt("style");
                int adCount = res.getInt("adCount");
                int imgCount = res.getInt("imgCount");
                String size = res.getString("size");
                String adSlotId = res.getString("adSlotId");
                boolean online = res.getBoolean("online");

                AppStyle app_style = new AppStyle();
                app_style.setCodeId(codeId);
                app_style.setPlatForm(platForm);
                app_style.setAppId(appId);
                app_style.setStyle(style);
                app_style.setSize(size);
                app_style.setAdCount(adCount);
                app_style.setImgCount(imgCount);
                app_style.setAdSlotId(adSlotId);
                app_style.setOnline(online);
                Map<String, AppCode> map = platForm == 1 ? androidMap : iosMap;

                AppCode app_code = map.get(codeId);
                if (app_code == null) {
                    app_code = new AppCode();
                    app_code.setOwnerId(ownerId);
                    app_code.setCodeId(codeId);
                    app_code.getStyleList().add(app_style);
                    map.put(codeId, app_code);
                } else {
                    app_code.getStyleList().add(app_style);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    private static String getSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ");
        sb.append(" c.ownerId, c.codeId,s.platForm,s.appId,s.style,s.size,s.adCount,s.imgCount,s.adSlotId,s.online ");
        sb.append(" from appcode c, appstyle s ");
        sb.append(" where ");
        sb.append(" c.codeId = s.codeId ");
        sb.append(" and c.configStatus = 1 ");
        sb.append(" and c.publishStatus = 1 ");
        sb.append(" and c.logicDelFlg = 0 ");
        sb.append(" and s.styleEnable = 1 ");
        sb.append(" and s.appId IS NOT NULL ");
        sb.append(" and s.adSlotId IS NOT NULL ");
        return sb.toString();
    }

    public static AppCode getAppCode(int platForm, String codeId) {
        if (isMaster) {
            return platForm == 1 ? android_master.get(codeId) : ios_master.get(codeId);
        } else {
            return platForm == 1 ? android_slave.get(codeId) : ios_slave.get(codeId);
        }
    }

    public static List<String> lookup() {
        List<String> keys = new ArrayList<>();
        if (isMaster) {
            keys.add("android:");
            keys.addAll(android_master.keySet());
            keys.add("   ");
            keys.add("ios");
            keys.addAll(ios_master.keySet());
        } else {
            keys.add("android:");
            keys.addAll(android_slave.keySet());
            keys.add("   ");
            keys.add("ios");
            keys.addAll(android_slave.keySet());
        }
        return keys;
    }

    public static synchronized void update() throws Exception {
        Map<String, AppCode> android = null;
        Map<String, AppCode> ios = null;

        if (isMaster) {
            android_slave.clear();
            ios_slave.clear();
            android = android_slave;
            ios = ios_slave;
        } else {
            android_master.clear();
            ios_master.clear();
            android = android_master;
            ios = ios_master;
        }

        getData(android, ios);

        boolean nowstatus = isMaster;

        isMaster = !isMaster;

        if (nowstatus) {
            android_master.clear();
            ios_master.clear();
        } else {
            android_slave.clear();
            ios_slave.clear();
        }
    }

}
