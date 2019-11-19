package helloworld;

//自定义实现一个栈，但是出栈的时候不把对象删除
public class UserStack {
    private Object[] elements ;
    private  int size =0;//栈顶位置
    private static final int Cap =16;


    public UserStack(){
        elements =new Object[Cap];
    }

    //放入元素
    public void push(Object o){
        elements[size++] =o;
    }
    public Object pop( ){
        Object o =elements[--size];
        return o;
    }

    public static void main(String[] args) {
        UserStack u =new UserStack();
        Object o =new Object();
        System.out.println( o);
        u.push(o);
        Object ret = u.pop();
        System.out.println(ret+" ");
    }
}

