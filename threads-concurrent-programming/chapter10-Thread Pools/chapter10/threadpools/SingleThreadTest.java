package chapter10.threadpools;

public class SingleThreadTest {

	public static void main(String[] args) {
		int nTasks = 5;
		int fib = 7;
		SingleThreadAccess sta = new SingleThreadAccess();
		for (int i = 0; i < nTasks; i++)
			sta.invokeLater(new Task(fib, "Task " + i));
		sta.shutdown();
	}
}
