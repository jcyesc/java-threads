package chapter2.example1;

public class Factorial {
    public static void main(String[] args) {
        int n = 6;
        System.out.print(n + "! is ");
        int fact = 1;
        while (n > 1)
            fact *= n--;
        System.out.println(fact);
    }
}
