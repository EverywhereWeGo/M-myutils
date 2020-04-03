package yanzheng.b_dongtaibianyi;

import com.itranswarp.compiler.JavaStringCompiler;

import java.io.IOException;
import java.util.Map;

public class compilewhenrun {
    public static Object getObject() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        JavaStringCompiler compiler = new JavaStringCompiler();
        Map<String, byte[]> results = compiler.compile("asdf.java", JAVA_SOURCE_CODE);
        Class<?> clazz = compiler.loadClass("other.asdf", results);
        // try instance:
//        BIConversion.User user = (BIConversion.User) clazz.newInstance();
        everything a = (everything) clazz.newInstance();
        a.work();
        return a;
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        JavaStringCompiler compiler = new JavaStringCompiler();
        Map<String, byte[]> results = compiler.compile("asdf.java", JAVA_SOURCE_CODE);
        Class<?> clazz = compiler.loadClass("other.asdf", results);
        // try instance:
//        BIConversion.User user = (BIConversion.User) clazz.newInstance();
        everything a = (everything) clazz.newInstance();


    }

    static final String JAVA_SOURCE_CODE = "package other;\n" +
            "\n" +
            "public class asdf extends everything {\n" +
            "\n" +
            "    Integer b;\n" +
            "    String a;\n" +
            "    Boolean c;\n" +
            "\n" +
            "    @Override\n" +
            "    public void work() {\n" +
            "        System.out.println(\"hello\");\n" +
            "    }\n" +
            "}\n";
}