package tools.sql;

/*
输入文本生成pojo
 */

import static tools.pinying.ChineseToPinyin.getFirstPinYin;
import static utils.basicutil.i_StringUtil.firsttoUpperCase;

public class DocToPojo {
    //根据字段名生成
    public static void get1() {
        String docen = "parentxzqh,xzqhbh,jjdwbh,jjdwmc,jjdbh,gljjdbh,lhlxbh,bjfsbh,n_auto,tfhm,jdlx,jjybh,jjyxm,bjsj,jjsj,bjsd,bjdh,bjdhyhxm,bjdhyhdz,bjrxm,bjrxb,lxdh,sfdz,bjnr,bcjjnr,gxdwbh,gxdwmc,ywxajbs,bjlb,bjlx,bjxl,sfcs,zddwxzb,zddwyzb,ywwxwz,ywbzxl,ywbkry,ywssry,sfsw,sfsqsb,sgcd,bjcph,sfxysj,ywty,tyfx,cllx,zhsglx,qhwlb,rswxz,jzlb,jzjg,qhcs,rksjc,gxsjc,d_uploadtime,d_gathertime,d_firsttime,d_localtime,memo,n_source,ldgbh,sddwxzb,sddwyzb,x,y,gjsj,ajbm,rywksj";
        String docch = "parentxzqh,xzqhbh,jjdwbh,jjdwmc,jjdbh,gljjdbh,lhlxbh,bjfsbh,n_auto,tfhm,jdlx,jjybh,jjyxm,bjsj,jjsj,bjsd,bjdh,bjdhyhxm,bjdhyhdz,bjrxm,bjrxb,lxdh,sfdz,bjnr,bcjjnr,gxdwbh,gxdwmc,ywxajbs,bjlb,bjlx,bjxl,sfcs,zddwxzb,zddwyzb,ywwxwz,ywbzxl,ywbkry,ywssry,sfsw,sfsqsb,sgcd,bjcph,sfxysj,ywty,tyfx,cllx,zhsglx,qhwlb,rswxz,jzlb,jzjg,qhcs,rksjc,gxsjc,d_uploadtime,d_gathertime,d_firsttime,d_localtime,memo,n_source,ldgbh,sddwxzb,sddwyzb,x,y,gjsj,ajbm,rywksj";
//        String docen = "id,clzp,hphm,hpzl,cllx,clpp,clys,syxz,cljcqk,ryjcqk,clzt,sfzdcllx,zdclx,ccdjrq,yqwjy,jyqz,yqwbf,bfqz,fzjg_c,syr,lxdh_c,lxdz_c,kkid,sfd,mdd,jcmj,jcsj,zjs_sfz,fjs_sfz,ck_sfz,update_time,is_deleted";
//        String docch = "台账id,车辆照片,号牌号码,号牌种类,车辆类型,车辆品牌,车辆颜色,使用性质,车俩检查情况,人员检查情况,车辆状态,是否重点车辆类型,重点车类型,初次登记日期,逾期未检验,检验期至,逾期未报废,报废日期,发证机关_车,所有人,联系电话,联系地址,卡口id,始发地,目的地,检查民警姓名,检查时间,主驾驶身份证,副驾驶身份证,乘客身份证集合,更新时间,是否删除";
        String[] docens = docen.split(",");
        String[] docchs = docch.split(",");
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = new StringBuffer();
        String settemp = "public String get{0}() { return {1}; }";
        String gettemp = "public void set{0}(String {1}) { this.{1} = {1}; }";
        for (int i = 0; i < docens.length; i++) {
            String set = settemp.replace("{0}", firsttoUpperCase(docens[i])).replace("{1}", docens[i]);
            String get = gettemp.replace("{0}", firsttoUpperCase(docens[i])).replace("{1}", docens[i]);
            sb1.append("//").append(docchs[i]).append("\nprivate String ").append(docens[i]).append(";\n");
            sb2.append(set).append("\n");
            sb3.append(get).append("\n");
        }

        System.out.println(sb1.toString());
        System.out.println(sb2.toString());
        System.out.println(sb3.toString());
    }

    //根据sql注释生成
    public static void get2() {
        String doc = "台账id," + "车辆照片," + "号牌号码," + "号牌种类," + "车辆类型," + "车辆品牌," + "车辆颜色," + "使用性质," + "车俩检查情况,"
                + "人员检查情况," + "车辆状态," + "是否重点车辆类型," + "重点车类型," + "初次登记日期," + "逾期未检验," + "检验期至," + "逾期未报废," + "报废日期,"
                + "发证机关_车," + "所有人," + "联系电话," + "联系地址," + "卡口id," + "始发地," + "目的地," + "检查民警姓名," + "检查时间," + "主驾驶身份证,"
                + "副驾驶身份证," + "乘客身份证集合," + "更新时间," + "是否删除";

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

    public static void main(String[] args) {
        get1();
//        get2();

    }

}
