package helloworld;

import java.sql.*;

public class HiveDBUtil {
    public static Connection conn=null;

    public static Connection getConn()throws Exception{
        if(conn==null){
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            conn=  DriverManager.getConnection("jdbc:hive2://192.168.163.120:10000/hive","hive","hive");
        }
        return conn;
    }

    public static void close(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try{
            System.out.println(getConn());
            Connection conn = getConn();
            PreparedStatement a = conn.prepareStatement("select * from bfd_temp.dim_customer_info");
            ResultSet b = a.executeQuery();






        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
