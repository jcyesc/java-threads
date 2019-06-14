package chapter10.threadpools;

import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {
        int nTasks = 5;
        long n = 7;
        int tpSize = 3;

        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
            tpSize, tpSize, 50000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

        Task[] tasks = new Task[nTasks];
        for (int i = 0; i < nTasks; i++) {
            tasks[i] = new Task(n, "Task " + i);
            tpe.execute(tasks[i]);
        }
        tpe.shutdown();
    }
}
