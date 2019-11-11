package helloworld;

import redis.clients.jedis.Jedis;

import java.util.Properties;

import static utils.basicutil.a_PropertiesLoadUtil.loadProperties;
import static utils.basicutil.c_RedisUtil_ConnectionPool.getJedis;
import static utils.basicutil.c_RedisUtil_ConnectionPool.returnResource;

public class setmaxdate {
    private static Properties prop = loadProperties("config.properties");

    public static void main(String[] args) throws InterruptedException {
        try {


            Jedis jedis = getJedis();
            jedis.select(2);
            System.out.println(jedis.get("maxdate1"));
            Thread.sleep(10 * 60 * 1000);
            System.out.println(jedis.set("maxdate1", "nihao"));
            System.out.println(jedis.get("maxdate1"));
            returnResource(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
