package com.b_util.basicutil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.basicutil.b_DBUtil_ConnectionPool.getConnection;
import static utils.basicutil.b_DBUtil_ConnectionPool.returnConnection;


public class f_SqlUtil {
    private final static Logger logger = LoggerFactory.getLogger(f_SqlUtil.class);

    public static JSONArray querySql(String sql) {
        JSONArray ja = new JSONArray();
        Connection conn = null;
        try {
            conn = getConnection();
            logger.info("查询语句:" + sql);
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                long stime = System.currentTimeMillis();
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    JSONObject jo = new JSONObject();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        String columname = rs.getMetaData().getColumnLabel(i);
                        jo.put(columname, rs.getString(columname));
                    }
                    ja.add(jo);
                }
                long etime = System.currentTimeMillis();
                logger.info("查询时间:" + (etime - stime) + "ms");
                logger.info("数据量:" + ja.size() + "条");
                return ja;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            returnConnection(conn);
        }
        return null;
    }
}
