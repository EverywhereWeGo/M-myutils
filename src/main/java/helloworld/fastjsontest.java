package helloworld;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class fastjsontest {
    public static void main(String args[]) throws InterruptedException {
        JSONArray ja = new JSONArray();
        for (int i = 0; i < 5000000; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(i + "", i + "");
            ja.add(jsonObject);
            Thread.sleep((long) 0.1);
        }
        System.out.println("wangbi");
//        System.out.println(ja.getJSONObject(1).toString());
        Thread.sleep(1000000);
    }
}
