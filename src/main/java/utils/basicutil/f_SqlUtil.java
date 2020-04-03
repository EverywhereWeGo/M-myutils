package utils.basicutil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import po.SamplePojo;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static utils.basicutil.b_DBUtil_ConnectionPool.getConnection;
import static utils.basicutil.b_DBUtil_ConnectionPool.returnConnection;
import static utils.basicutil.i_StringUtil.firsttoUpperCase;
import static utils.basicutil.i_StringUtil.getFirstSubString;


public class f_SqlUtil {
    private final static Logger logger = LoggerFactory.getLogger(f_SqlUtil.class);

    public static void ddlSql(String sql) {
        Connection conn = getConnection();
        try {
            logger.info("语句:" + sql);
            if (null != conn) {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setQueryTimeout(120);
                long stime = System.currentTimeMillis();
                statement.execute();
                long etime = System.currentTimeMillis();
                logger.info("查询时间:" + (etime - stime) + "ms");
            }
        } catch (SQLException e) {
            logger.error("操作失败:" + Arrays.toString(e.getStackTrace()));
        } finally {
            returnConnection(conn);
        }
    }

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

    //插入语句中的字段，必须和json中的key一模一样
    public static void insertSql(String sql, JSONArray ja) {
        Connection conn = getConnection();
        try {
            logger.info("插入语句:" + sql);
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                conn.setAutoCommit(false);
                String[] fileds = getFiledsbySql(sql);
                for (int i = 0; i < ja.size(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    for (int j = 1; j <= fileds.length; j++) {
                        if (jo.containsKey(fileds[j - 1])) {
                            statement.setString(j, jo.getString(fileds[j - 1]));
                        } else {
                            throw new Exception("字段不匹配");
                        }
                    }
                    statement.addBatch();
                }
                statement.executeBatch();
                statement.clearBatch();
                conn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnConnection(conn);
        }
    }

    public static <T> void insertSql(String sql, List<T> listobj) {
        Connection conn = getConnection();
        try {
            logger.info("插入语句:" + sql);
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(sql);
                conn.setAutoCommit(false);
                String[] fileds = getFiledsbySql(sql);
                for (T obj : listobj) {
                    for (int i = 0; i < fileds.length; i++) {
                        String value = obj.getClass().getMethod("get" + firsttoUpperCase(fileds[i])).invoke(obj).toString();
                        statement.setString(i + 1, value);
                    }
                    statement.addBatch();
                }
                statement.executeBatch();
                statement.clearBatch();
                conn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnConnection(conn);
        }
    }


    private static String[] getFiledsbySql(String sql) {
        return getFirstSubString(sql, "(", ")").split(",");
    }


    public static void main(String[] args) {

//        JSONArray a = querySql("select a ,b  from datatest limit 10 ", SamplePojo.);
//        System.out.println(a);
//
//

//
        List l = new LinkedList();
        SamplePojo a = new SamplePojo();
        a.setA("asdf");
        a.setB("asdfasdfasfd");
        l.add(a);
        insertSql("insert into qwer (a,b) values (?,?) ", l);


//        List<SamplePojo> b = querySql(createSelectPreSql("datatest", SamplePojo.class) + " limit 10000", SamplePojo.class);
//        System.out.println(b);

    }
}
