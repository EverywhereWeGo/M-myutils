package com.b_util.basicutil;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.b_util.basicutil.f_SqlUtil.querySql;
import static utils.basicutil.a_PropertiesLoadUtil.loadProperties;

public class b_DBUtil_ConnectionPool {
    private static LinkedList<Connection> connectionQueue;

    private static String driverclass;
    private static String url;
    private static String username;
    private static String password;
    private static int poolsnum;

    static {
        try {
            Properties prop = loadProperties("config.properties");
            driverclass = prop.getProperty("driverclass");
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            poolsnum = (null == prop.getProperty("poolsnum")) ? 4 : Integer.valueOf(prop.getProperty("poolsnum"));
            Class.forName(driverclass);
            timerConnectionVaildCheck();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public synchronized static Connection getConnection() {
        try {
            if (connectionQueue == null) {
                connectionQueue = new LinkedList<Connection>();
                for (int i = 0; i < poolsnum; i++) {
                    Connection conn = DriverManager.getConnection(url, username, password);
                    connectionQueue.addLast(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connectionQueue.size() == 0) {
            System.out.println(connectionQueue);
            System.out.println("连接池耗尽");
        } else {
            return connectionQueue.removeFirst();
        }
        return null;
    }


    public static void returnConnection(Connection conn) {
        connectionQueue.addLast(conn);
    }

    //进行数据库连接有效性检查
    private static void isvaild() {
        Connection conn = getConnection();
        try {
            //如果失效则删除旧的并创建新的
            if (conn != null && !conn.isValid(1000)) {
                connectionQueue.remove(conn);
                connectionQueue.addLast(DriverManager.getConnection(url, username, password));
            } else {
                returnConnection(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    //每分钟检测连接队列中的连接有效性
    private static void timerConnectionVaildCheck() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                isvaild();
            }
        }, 60, 60, TimeUnit.SECONDS);
    }


    public static void main(String[] args) throws InterruptedException, SQLException {
        for (int i = 0; i < 100; i++) {
            JSONArray a2 = querySql("select 1");
            querySql("select 1");
            querySql("select 1");
            querySql("select 1");
            querySql("select 1");
            querySql("select 1");
//            System.out.println(a2);
        }
        Thread.sleep(100000);

    }

}
