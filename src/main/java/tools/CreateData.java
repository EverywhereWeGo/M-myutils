package tools;

import com.alibaba.fastjson.JSONObject;
import tools.createdata.ChineseIDCardNumberGenerator;

public class CreateData {
    public static void main(String[] args) {
        JSONObject ja = new JSONObject();
        String lgmc = getChinese((int)getrandmonum(3,5)) + "旅馆";
        ja.put("lgmc", lgmc);
        ja.put("lgdm", "");
        ja.put("zsrmc", "");
        ja.put("zsrzjhm", ChineseIDCardNumberGenerator.getInstance().generate());
        ja.put("rzrq", "");
        ja.put("rzfjh", "");
        ja.put("tfrq", "");

        System.out.println(ja);
    }

    public static char getRandomChar() {
        return (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
    }


    public static String getChinese(int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            sb.append(getRandomChar());
        }
        return sb.toString();
    }

    public static double getrandmonum(int fr, int to) {
        double n = Math.random();
        return  (n * (to - fr) + fr);
    }


}