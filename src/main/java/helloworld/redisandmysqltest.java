package helloworld;

import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.basicutil.b_DBUtil_ConnectionPool.getConnection;
import static utils.basicutil.c_RedisUtil_ConnectionPool.getJedis;
import static utils.basicutil.c_RedisUtil_ConnectionPool.returnResource;

public class redisandmysqltest {
    public static void main(String[] args) {
        Jedis jedis = getJedis();
        jedis.select(2);
        for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
            long a = System.currentTimeMillis();
            System.out.println("maxdate" + i + ":" + jedis.get("maxdate" + i));
            long b = System.currentTimeMillis();
            System.out.println(b - a);
        }
        returnResource(jedis);


        Connection conn = null;
        try {
            conn = getConnection();
            if (conn != null) {
                for (int i = 0; i <= Integer.parseInt(args[0]); i++) {
                    String sql = "select maxdate from test.max where id=" + i;
                    System.out.println(sql);
                    PreparedStatement statement = conn.prepareStatement(sql);
                    long stime = System.currentTimeMillis();
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        rs.getString("maxdate");
                    }
                    long etime = System.currentTimeMillis();
                    System.out.println(etime - stime);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}