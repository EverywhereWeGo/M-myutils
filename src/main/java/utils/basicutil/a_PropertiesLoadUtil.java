package utils.basicutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class a_PropertiesLoadUtil {
    public static Properties loadProperties(String fileName) {
        Properties prop = new Properties();
        InputStream in = a_PropertiesLoadUtil.class.getClassLoader().getResourceAsStream(fileName);
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

    //实现类似python 配置文件中section的功能
    public static Map<String, String> loadPropertiesGetSetciontoMap(String fileName, String sectionName) {
        Map<String, String> sectioMap = new HashMap<String,String>();
        InputStream in = a_PropertiesLoadUtil.class.getClassLoader().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            String tempStr;

            boolean flag = false;
            while ((tempStr = reader.readLine()) != null) {
                if ("".equals(tempStr)) {
                    continue;
                }
                if (("[" + sectionName + "]").equals(tempStr)) {
                    flag = true;
                    continue;
                }
                if (flag) {
                    if (tempStr.startsWith("[") && tempStr.endsWith("]")) {
                        break;
                    } else {
                        sectioMap.put(tempStr.substring(0, tempStr.indexOf("=")), tempStr.substring(tempStr.indexOf("=") + 1));
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sectioMap;
    }

    public static void main(String[] args) {
        Map<String, String> a = loadPropertiesGetSetciontoMap("config.properties", "basic-requestheader");
        System.out.println(a);
    }
}
