package utils.basicutil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

import static utils.basicutil.a_PropertiesLoadUtil.loadProperties;

public class e_RedisUtil {
    private static JedisPool jedisPool = null;
    //服务器IP地址
    private static String ADDR;
    //端口
    private static int PORT;
    //密码
    private static String AUTH;
    //连接实例的最大连接数
    private static int MAX_ACTIVE;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
    private static int MAX_WAIT;
    //连接超时的时间
    private static int TIMEOUT;
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW;

    /**
     * 初始化Redis连接池
     */

    static {
        try {
            Properties prop = loadProperties("config.properties");
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(Integer.valueOf(prop.getProperty("max_active")));
            config.setMaxIdle(Integer.valueOf(prop.getProperty("max_idle")));
            config.setMaxWaitMillis(Integer.valueOf(prop.getProperty("max_wait")));
            config.setTestOnBorrow(Boolean.valueOf(prop.getProperty("true")));
            ADDR = prop.getProperty("ip");
            PORT = Integer.parseInt(prop.getProperty("port"));
            TIMEOUT = Integer.parseInt(prop.getProperty("timeout"));
            AUTH = prop.getProperty("password_redis");
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     */

    public synchronized static Jedis getJedis() {

        try {

            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /***
     *
     * 释放资源
     */

    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }

    }

}
