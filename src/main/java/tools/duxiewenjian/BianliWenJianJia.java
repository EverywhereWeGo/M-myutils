package tools.duxiewenjian;

import java.io.File;
import java.util.ArrayList;

/**
 * @author everywherewego
 * @date 2020-04-17 16:18
 */


public class BianliWenJianJia {
    private static ArrayList<String> filelist = new ArrayList<String>();

    public static void main(String[] args) throws Exception {

        String filePath = "/Users/everywherewego/IdeaProjects/spider/王冲_排污许可/src/main/java/com/bfd/apagexukezhenyewushenhe";
        getFiles(filePath);
    }

    /*
     * 通过递归得到某一路径下所有的目录及其文件
     */
    static void getFiles(String filePath) {
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                /*
                 * 递归调用
                 */
                getFiles(file.getAbsolutePath());
                filelist.add(file.getAbsolutePath());
//                System.out.println("显示" + filePath + "下所有子目录及其文件" + file.getAbsolutePath());
//                System.out.println(file.getAbsolutePath());

            } else {
//                System.out.println("显示" + filePath + "下所有子目录" + file.getAbsolutePath());
                System.out.println(file.getAbsolutePath());

            }
        }
    }
}
