package tools.duxiewenjian.replacebyline;

public interface Callback {
    /**
     * 对每一行作何处理
     *
     * @param i  第几行
     * @param s  传入的每一行
     * @param sb 传出的值
     */
    void eachline(int i, String s, StringBuffer sb);
}
