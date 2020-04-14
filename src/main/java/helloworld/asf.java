package helloworld;

import com.alibaba.fastjson.JSONObject;

/**
 * @author everywherewego
 * @date 2020-04-05 01:18
 */

public class asf {
    public static void main(String[] args) {

        String a = "{\"名称\":\"/\",\"批复文号\":\"/\",\"编号\":\"/\"}";
        JSONObject j = JSONObject.parseObject(a);
        System.out.println(j);


        System.out.println("重金属".contains("重金"));
    }
}
