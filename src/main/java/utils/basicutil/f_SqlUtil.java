package com.b_util.basicutil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.basicutil.b_DBUtil_ConnectionPool.getConnection;
import static utils.basicutil.b_DBUtil_ConnectionPool.returnConnection;


public class f_SqlUtil {
    public static JSONArray querySql(String sql) {
        JSONArray ja = new JSONArray();
        Connection conn = null;
        try {
            conn = getConnection();
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                System.out.println("查询语句:" + sql);

                long stime = System.currentTimeMillis();
                ResultSet rs = statement.executeQuery(sql);
                long etime = System.currentTimeMillis();
                System.out.println("查询时间:" + (etime - stime) + "ms");

                while (rs.next()) {
                    JSONObject jo = new JSONObject();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        String columname = rs.getMetaData().getColumnName(i);
                        jo.put(columname, rs.getString(columname));
                    }
                    ja.add(jo);
                }
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
