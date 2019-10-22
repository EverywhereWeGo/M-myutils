package utils.basicutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

public class b_DBUtil_ConnectionPool {
    private static LinkedList<Connection> connectionQueue;

    private static String driverclass;
    private static String url;
    private static String username;
    private static String password;
    private static int poolsnum;

    static {
        try {
            Properties prop = a_PropertiesLoadUtil.loadProperties("config.properties");
            driverclass = prop.getProperty("driverclass");
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            poolsnum = (null == prop.getProperty("poolsnum")) ? 3 : Integer.valueOf(prop.getProperty("poolsnum"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            Class.forName(driverclass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized static Connection getConnection() {
        try {

            if (connectionQueue == null) {
                connectionQueue = new LinkedList<Connection>();
                for (int i = 0; i < poolsnum; i++) {
                    Connection conn = DriverManager.getConnection(url, username, password);
                    connectionQueue.push(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionQueue.poll();
    }

    public static void returnConnection(Connection conn) {
        connectionQueue.push(conn);
    }


    public static void main(String[] args) throws InterruptedException {
        Connection conn = getConnection();
        Thread.sleep(100000000);


    }
}
