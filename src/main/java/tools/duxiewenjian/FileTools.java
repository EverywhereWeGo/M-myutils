package tools.duxiewenjian;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class FileTools {
    public static List<String> readLine(String filePath) {
        List<String> res = new LinkedList<>();
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String s = null;
            try {
                while ((s = br.readLine()) != null) {
                    if (s != null) {
                        res.add(s);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void writeFile(String filePath, String context) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.write(context);
        out.flush();
        out.close();

    }
}