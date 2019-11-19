package utils.basicutil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.basicutil.b_DBUtil_ConnectionPool.getConnection;
import static utils.basicutil.b_DBUtil_ConnectionPool.returnConnection;


public class f_SqlUtil {
    private final static Logger logger = LoggerFactory.getLogger(f_SqlUtil.class);

    public static JSONArray querySql(String sql) {
        ResultSet rs = queryAndReturnResultSet(sql);
        try {
            return resultSetToJson(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> querySql(String sql, Class<T> clazz) {
        ResultSet rs = queryAndReturnResultSet(sql);
        try {
            return resultSetToObject(rs, clazz);
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONArray resultSetToJson(ResultSet rs) throws SQLException {
        JSONArray ja = new JSONArray();
        int colCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            JSONObject jo = new JSONObject();
            for (int i = 1; i <= colCount; i++) {
                String columname = rs.getMetaData().getColumnLabel(i);
                jo.put(columname, rs.getString(columname));
            }
            ja.add(jo);
        }
        return ja;
    }

    private static <T> List<T> resultSetToObject(ResultSet rs, Class<T> clazz) throws SQLException, InstantiationException, IllegalAccessException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        List<T> list = new ArrayList<T>();
        //业务对象的属性数组
        Field[] fields = clazz.getDeclaredFields();
        while (rs.next()) {
            T obj = clazz.newInstance();
            for (int i = 1; i <= colCount; i++) {
                Object value = rs.getObject(i);
                //寻找该列对应的对象属性
                for (Field f : fields) {
                    //如果匹配进行赋值
                    if (f.getName().equalsIgnoreCase(rsmd.getColumnLabel(i))) {
                        boolean flag = f.isAccessible();
                        //设置实有变量可以在反射情况下被访问
                        f.setAccessible(true);
                        f.set(obj, value);
                        f.setAccessible(flag);
                    }
                }
            }
            list.add(obj);
        }
        return list;
    }

    private static ResultSet queryAndReturnResultSet(String sql) {
        ResultSet rs = null;
        Connection conn = getConnection();
        try {
            logger.info("查询语句:" + sql);
            if (null != conn) {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setQueryTimeout(120);
                long stime = System.currentTimeMillis();
                rs = statement.executeQuery(sql);
                long etime = System.currentTimeMillis();
                logger.info("查询时间:" + (etime - stime) + "ms");
                rs.last();
                logger.info("数据量:" + rs.getRow() + "条");
                rs.beforeFirst();
            }
        } catch (SQLException e) {
            logger.error("sql查询失败:" + Arrays.toString(e.getStackTrace()));
        } finally {
            returnConnection(conn);
        }
        return rs;
    }


    private static void insertSql(String sql, JSONArray ja) {
        Connection conn = getConnection();
        try {
            logger.info("插入语句:" + sql);
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                conn.setAutoCommit(false);
                for (int i = 0; i < ja.size(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    int k = 1;
                    for (String s : jo.keySet()) {
                        statement.setString(k++, jo.getString(s));
                    }
                    statement.addBatch();
                }
                statement.executeBatch();
                statement.clearBatch();
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        JSONArray a = querySql("select a as asdf ,b as qwer  from datatest limit 10 ");
        JSONObject jo = a.getJSONObject(0);
        for (String s : jo.keySet()) {
            System.out.println(s);
        }

        insertSql("insert into hahhaa (a,b) values (?,?) ", a);

//        List<SamplePojo> b = querySql(createSelectPreSql("datatest", SamplePojo.class) + " limit 10000", SamplePojo.class);
//        System.out.println(b);

    }
}
