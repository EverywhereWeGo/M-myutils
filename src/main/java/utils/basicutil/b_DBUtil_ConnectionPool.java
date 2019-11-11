package utils.basicutil;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


/*
初始化创建连接队列
每次取从队列最后一个取，如果失效则放在队列最前面，如果有效则使用返回后放在队列末尾
检查程序每分钟取队列第一个检查有效性，没问题则放在最后，有问题则删除旧的并创建新的放在队列最后。
 */
public class b_DBUtil_ConnectionPool {
    private final static Logger logger = LoggerFactory.getLogger(b_DBUtil_ConnectionPool.class);
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
            createConnectionPool();
            timerConnectionVaildCheck();
            logger.info("数据库连接池创建成功，连接数:" + poolsnum + "个");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static void createConnectionPool() {
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

    }

    public synchronized static Connection getConnection() {
        if (connectionQueue.size() == 0) {
            System.out.println("连接池耗尽");
        }
        Connection conn;
        try {
            //每次从最后一个取，有问题放最前面
            for (int i = 0; i < connectionQueue.size(); i++) {
                conn = connectionQueue.removeLast();
                if (conn.isValid(1000)) {
                    return conn;
                } else {
                    connectionQueue.addFirst(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public synchronized static void returnConnection(Connection conn) {
        if (null != conn) {
            connectionQueue.addLast(conn);
        }

    }

    //进行数据库连接有效性检查
    private synchronized static void isvaild() {
        Connection conn = connectionQueue.removeFirst();
        try {
            //如果失效则删除旧的并创建新的
            if (conn.isValid(100)) {
                connectionQueue.addLast(conn);
            } else {
                connectionQueue.addLast(DriverManager.getConnection(url, username, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    //每分钟检测连接队列中的连接有效性
    private synchronized static void timerConnectionVaildCheck() {
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
