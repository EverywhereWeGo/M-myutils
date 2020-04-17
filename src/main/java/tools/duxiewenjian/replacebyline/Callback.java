package tools.duxiewenjian.replacebyline;

public interface Callback {
    /**
     * 对每一行作何处理
     *
     * @param s
     * @return
     */
    StringBuffer eachline(String s, StringBuffer sb);
}
