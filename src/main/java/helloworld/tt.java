package helloworld;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import static utils.basicutil.b_DBUtil_ConnectionPool.getConnection;
import static utils.basicutil.b_DBUtil_ConnectionPool.returnConnection;
import static utils.basicutil.d_Dateutil.dateToString;


public class tt {


    public static void main(String[] args) {


        Connection conn = getConnection();

        String sql = "insert into test.datatest(a , b, c,etldate) values (?,?,?,?)";

        try {

            PreparedStatement prep = conn.prepareStatement(sql);


            // 将连接的自动提交关闭，数据在传送到数据库的过程中相当耗时

            conn.setAutoCommit(false);

            long start = System.currentTimeMillis();


            for (int i = 0; i < 30; i++) {


                long start2 = System.currentTimeMillis();


                // 一次性执行插入10万条数据

                for (int j = 0; j < 100000; j++) {


                    prep.setString(1, i + "");

                    prep.setString(2, j + "");

                    prep.setString(3, i + j + "");

                    prep.setString(4, dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));


                    // 将预处理添加到批中

                    prep.addBatch();


                }


                // 预处理批量执行

                prep.executeBatch();

                prep.clearBatch();

                conn.commit();


                long end2 = System.currentTimeMillis();


                // 批量执行一次批量打印执行依次的时间

                System.out.print("inner" + i + ": ");

                System.out.println(end2 - start2);


            }


            long end = System.currentTimeMillis();

            System.out.print("total: ");

            System.out.println(end - start);


        } catch (SQLException e) {


            e.printStackTrace();

        } finally {
            System.out.println(1);
            returnConnection(conn);


        }


    }






}
