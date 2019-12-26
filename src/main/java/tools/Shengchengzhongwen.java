package tools;

public class Shengchengzhongwen {
    public static void main(String[] args) {

        char a = getRandomChar();
        double num = getrandmonum(2, 4);

        System.out.println(a);
        System.out.println(num);


    }


    public static double getrandmonum(int fr, int to) {
        double n = Math.random();
        System.out.println(n);
        return  (n * (to - fr) + fr);
    }

    public static char getRandomChar() {
        return (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
    }

}
