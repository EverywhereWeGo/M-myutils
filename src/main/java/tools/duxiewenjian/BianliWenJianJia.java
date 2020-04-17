package tools.duxiewenjian;

import java.io.File;
import java.util.ArrayList;

/**
 * @author everywherewego
 * @date 2020-04-17 16:18
 */


public class BianliWenJianJia {
    static ArrayList<String> dires = new ArrayList<String>();
    static ArrayList<String> docs = new ArrayList<String>();

    public static ArrayList<String> getDirecotrys(String filePath) {
        direcotrys(filePath);
        ArrayList<String> re = dires;
        return re;
    }

    public static ArrayList<String> getFiles(String filePath) {
        files(filePath);
        ArrayList<String> re = docs;
        return re;
    }


    /*
     * 所有文件夹
     */
    public static void direcotrys(String filePath) {
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                direcotrys(file.getAbsolutePath());
                dires.add(file.getAbsolutePath());
            }
        }
    }

    //所有文件
    public static void files(String filePath) {
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                files(file.getAbsolutePath());
            } else {
                docs.add(file.getAbsolutePath());
            }
        }
    }
}
