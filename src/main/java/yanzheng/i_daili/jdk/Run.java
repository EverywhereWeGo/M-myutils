package yanzheng.i_daili.jdk;

public class Run {
    public static void main(String[] args) {
        Girl girl = new WangMeili();
        Family wf = new Family(girl);
        Girl o = (Girl) wf.genInstance();
        o.date();
        o.watchmovie();
    }
}
