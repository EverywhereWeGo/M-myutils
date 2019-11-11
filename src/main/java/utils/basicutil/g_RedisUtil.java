package utils.basicutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import static utils.basicutil.c_RedisUtil_ConnectionPool.getJedis;
import static utils.basicutil.c_RedisUtil_ConnectionPool.returnResource;

public class g_RedisUtil {
    private static Logger logger = LoggerFactory.getLogger(g_RedisUtil.class);

    public static String getInfoByKey(String key, int db) {
        String info = null;
        Jedis jedis = getJedis();
        if (jedis != null) {
            jedis.select(db);
            info = jedis.get(key);
            logger.info(key + ":" + info);
            returnResource(jedis);
        }
        return info;
    }

    public static String setInfoByKey(String key, String value, int db) {
        String info = null;
        Jedis jedis = getJedis();
        if (jedis != null) {
            jedis.select(db);
            info = jedis.set(key, value);
            returnResource(jedis);
        }
        return info;
    }
}
