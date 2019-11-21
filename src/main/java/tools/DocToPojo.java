package tools;
/*
输入文本生成pojo
 */

import static tools.ChineseToPinyin.firsttoUpperCase;
import static tools.ChineseToPinyin.getFirstPinYin;

public class DocToPojo {
    public static void main(String[] args) {

        String doc = "号牌号码," +
                "号牌种类," +
                "车辆类型," +
                "车辆品牌," +
                "车辆颜色," +
                "使用性质," +
                "车辆检查情况," +
                "车辆状态," +
                "是否重点车辆类型," +
                "初次登记日期," +
                "逾期未检验," +
                "逾期未报废," +
                "发证机关," +
                "所有人," +
                "联系电话," +
                "联系地址," +
                "卡口名称," +
                "始发地," +
                "目的地," +
                "检查民警," +
                "检查时间";

        String[] docs = doc.split(",");
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = new StringBuffer();
        String settemp = "public String get{0}() { return {1}; }";
        String gettemp = "public void set{0}(String {1}) { this.{1} = {1}; }";
        for (String s : docs) {
            String pysx = getFirstPinYin(s);
            String set = settemp.replace("{0}", firsttoUpperCase(pysx)).replace("{1}", pysx);
            String get = gettemp.replace("{0}", firsttoUpperCase(pysx)).replace("{1}", pysx);
            sb1.append("//").append(s).append("\nprivate String ").append(pysx).append(";\n");
            sb2.append(set).append("\n");
            sb3.append(get).append("\n");
        }

        System.out.println(sb1.toString());
        System.out.println(sb2.toString());
        System.out.println(sb3.toString());

    }


}
