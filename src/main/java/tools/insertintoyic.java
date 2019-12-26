package tools;

import com.alibaba.fastjson.JSONArray;

import static utils.basicutil.f_SqlUtil.insertSql;
import static utils.basicutil.f_SqlUtil.querySql;

public class insertintoyic {
    public static void main(String[] args) {
        JSONArray a = querySql("select * from jjdb_info limit 5000");
        for (int i = 0; i < 100; i++) {
            insertSql("insert into jjdb_info (parentxzqh,xzqhbh,jjdwbh,jjdwmc,jjdbh,gljjdbh,lhlxbh,bjfsbh,n_auto,tfhm,jdlx,jjybh,jjyxm,bjsj,jjsj,bjsd,bjdh,bjdhyhxm,bjdhyhdz,bjrxm,bjrxb,lxdh,sfdz,bjnr,bcjjnr,gxdwbh,gxdwmc,ywxajbs,bjlb,bjlx,bjxl,sfcs,zddwxzb,zddwyzb,ywwxwz,ywbzxl,ywbkry,ywssry,sfsw,sfsqsb,sgcd,bjcph,sfxysj,ywty,tyfx,cllx,zhsglx,qhwlb,rswxz,jzlb,jzjg,qhcs,rksjc,gxsjc,d_uploadtime,d_gathertime,d_firsttime,d_localtime,memo,n_source,ldgbh,sddwxzb,sddwyzb,x,y,gjsj,ajbm,rywksj) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ", a);
        }
    }
}
