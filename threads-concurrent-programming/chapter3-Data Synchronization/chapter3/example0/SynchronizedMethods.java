package chapter3.example0;

public class SynchronizedMethods implements Runnable{
	private DataClass data;
	
	public SynchronizedMethods(DataClass data) {
		this.data = data;
	}
	
	public void run() {
		System.out.println(Thread.currentThread().getName() + " SynchronizedMethods.run()");
		data.print();
	}
	
	public static void main(String[] args) {
		DataClass data = new DataClass();
		Thread thread1 = new Thread(new SynchronizedMethods(data), "THREAD-1");
		Thread thread2 = new Thread(new SynchronizedMethods(data), "THREAD-2");
		
		thread1.start();
		thread2.start();
	}
}
