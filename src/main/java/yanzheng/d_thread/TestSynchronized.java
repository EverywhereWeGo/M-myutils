package yanzheng.d_thread;

public class TestSynchronized {
    int wocao = 0;

    public int test1() {


        synchronized (this) {
            wocao++;

            System.out.println("?" + wocao);

            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }
        return wocao;
    }

    public synchronized void test2() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }
    }

    public synchronized void test3() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final TestSynchronized myt1 = new TestSynchronized();
        final TestSynchronized myt2 = new TestSynchronized();
        final TestSynchronized myt3 = new TestSynchronized();

        Thread test1 = new Thread(new Runnable() {
            public void run() {
                int a = myt1.test1();
                System.out.println("dddd" + a);
            }
        }, "test1");
        Thread test2 = new Thread(new Runnable() {
            public void run() {
                int a = myt1.test1();
                System.out.println("asdf" + a);
            }
        }, "test2");




//        Thread test1 = new Thread(new Runnable() {
//            public void run() {
//                myt2.test2();
//            }
//        }, "test1");
//
//        Thread test2 = new Thread(new Runnable() {
//            public void run() {
//                myt2.test3();
//            }
//        }, "test3");


        test1.start();
//        Thread.sleep(1000);

        test2.start();

    }

}