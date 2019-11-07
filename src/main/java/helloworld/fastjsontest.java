package helloworld;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class fastjsontest {
    public static void main(String args[]) {
        JSONArray ja = new JSONArray();
        for (int i = 0; i < 10; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(i + "", i + "");
            ja.add(jsonObject);
        }
        System.out.println(ja);
        System.out.println(ja.getJSONObject(1).toString());
    }
}
