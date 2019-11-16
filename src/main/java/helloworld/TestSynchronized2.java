package helloworld;

public class TestSynchronized2 {
    public static void main(String[] args) {
//        Test2 t = new Test2();
        Test3 t = new Test3();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                t.A();
            }

        }).start();


        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                t.A();
            }

        }).start();
    }
}

class Test2 {
    private Integer i = 41;

    public void A() {
        synchronized (i) {
            System.out.println(i);
            i = 45;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}

class Test3 {
    private Student s = new Student();

    public void A() {
        synchronized (s) {
            System.out.println(s);
            s.name = "李青";
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

class Student {
    public String name = "亚索";

    @Override
    public String toString() {
        return "Student [name=" + name + "]";
    }
}