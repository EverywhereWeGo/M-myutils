package helloworld;

public class A {
    int a = 0;

    public void f1(int a) {
        if (a == 1) {
            Integer i = 0;
            synchronized (i) {

                try {
                    System.out.println("beg1:" + System.currentTimeMillis());
                    Thread.sleep(2000);
                    System.out.println("end2:" + System.currentTimeMillis());
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } else if (a == 2) {
            Integer j = 0;
            synchronized (j) {

                try {
                    System.out.println("beg3:" + System.currentTimeMillis());
                    Thread.sleep(2000);
                    System.out.println("end4:" + System.currentTimeMillis());
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }


    public static void main(String[] args) {
        A a = new A();

        new Thread(new Runnable() {
            @Override
            public void run() {
                a.f1(1);
            }
        }, "1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                a.f1(2);
            }
        }, "2").start();


    }
}
