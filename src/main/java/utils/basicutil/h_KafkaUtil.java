package utils.basicutil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

import static utils.basicutil.a_PropertiesLoadUtil.loadProperties;

public class h_KafkaUtil {
    private static Logger logger = LoggerFactory.getLogger(h_KafkaUtil.class);

    public static void sendMseeageToKafka(JSONArray ja) {
        Properties prop = loadProperties("config.properties");
        Properties props = new Properties();
        props.put("max.request.size", "1024000000");
        props.put("bootstrap.servers", prop.getProperty("bootstrap.servers"));
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("retries", "0");
        Producer<String, String> producer = new KafkaProducer<String, String>(prop);

        for (int i = 0; i < ja.size(); i++) {
            try {
                JSONObject jo = ja.getJSONObject(i);
                producer.send(new ProducerRecord<>(prop.getProperty("topic"), String.valueOf(System.currentTimeMillis()), jo.toString()));
            } catch (Exception e) {
                logger.error(e.getMessage() + ":" + Arrays.toString(e.getStackTrace()));
            }
        }

        producer.close();
    }
}
