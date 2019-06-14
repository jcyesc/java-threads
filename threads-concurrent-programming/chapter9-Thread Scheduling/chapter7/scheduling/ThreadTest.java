package chapter7.scheduling;

public class ThreadTest {

    public static void main(String[] args) {
        int nThreads = 4;
        long n = 7;
        Thread t[] = new Thread[nThreads];

        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new Task(n, "Task " + i));
            t[i].start();
        }
        for (int i = 0; i < t.length; i++) {
            try {
                t[i].join();
            } catch (InterruptedException ie) {}
        }
    }
}
