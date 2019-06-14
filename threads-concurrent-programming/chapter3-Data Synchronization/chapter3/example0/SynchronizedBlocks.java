package chapter3.example0;

public class SynchronizedBlocks implements Runnable {
	private DataClassSynchronizedBlocks data;
	
	public SynchronizedBlocks(DataClassSynchronizedBlocks data) {
		this.data = data;
	}
	
	public void run() {
		System.out.println(Thread.currentThread().getName() + " SynchronizedBlocks.run()");
		data.print();
	}
	
	public static void main(String[] args) {
		DataClassSynchronizedBlocks data = new DataClassSynchronizedBlocks();
		Thread thread1 = new Thread(new SynchronizedBlocks(data), "THREAD-1");
		Thread thread2 = new Thread(new SynchronizedBlocks(data), "THREAD-2");
		
		thread1.start();
		thread2.start();
	}
}
