package tools.sql;

import com.alibaba.fastjson.JSONArray;

import static utils.basicutil.f_SqlUtil.querySql;
import static utils.basicutil.i_StringUtil.firsttoUpperCase;

public class SqltoPojo {
    public static void main(String[] args) {
        JSONArray a = querySql("SELECT sys_name as sysName, cur_status as  curStatus , abnormal_flag as abnormalFlag from t_business_sys_import_statistic");
        System.out.println(a);

//        String docen = "id,clzp,hphm,hpzl,cllx,clpp,clys,syxz,cljcqk,ryjcqk,clzt,sfzdcllx,zdclx,ccdjrq,yqwjy,jyqz,yqwbf,bfqz,fzjg_c,syr,lxdh_c,lxdz_c,kkid,sfd,mdd,jcmj,jcsj,zjs_sfz,fjs_sfz,ck_sfz,update_time,is_deleted";
//        String docch = "台账id,车辆照片,号牌号码,号牌种类,车辆类型,车辆品牌,车辆颜色,使用性质,车俩检查情况,人员检查情况,车辆状态,是否重点车辆类型,重点车类型,初次登记日期,逾期未检验,检验期至,逾期未报废,报废日期,发证机关_车,所有人,联系电话,联系地址,卡口id,始发地,目的地,检查民警姓名,检查时间,主驾驶身份证,副驾驶身份证,乘客身份证集合,更新时间,是否删除";
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = new StringBuffer();
        String settemp = "public String get{0}() { return {1}; }";
        String gettemp = "public void set{0}(String {1}) { this.{1} = {1}; }";
        for (int i = 0; i < a.size(); i++) {
            String eachelement = a.getJSONObject(i).getString("column_name");
            String ch = a.getJSONObject(i).getString("column_comment");
            String set = settemp.replace("{0}", firsttoUpperCase(eachelement)).replace("{1}", eachelement);
            String get = gettemp.replace("{0}", firsttoUpperCase(eachelement)).replace("{1}", eachelement);
            sb1.append("//").append(ch).append("\nprivate String ").append(eachelement).append(";\n");
            sb2.append(set).append("\n");
            sb3.append(get).append("\n");
        }

        System.out.println(sb1.toString());
        System.out.println(sb2.toString());
        System.out.println(sb3.toString());
    }
}
