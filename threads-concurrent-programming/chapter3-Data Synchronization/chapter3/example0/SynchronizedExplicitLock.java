package chapter3.example0;

public class SynchronizedExplicitLock implements Runnable {
	private DataExplicitLock data;
	
	public SynchronizedExplicitLock(DataExplicitLock data) {
		this.data = data;
	}
	
	
	public void run() {
		System.out.println(Thread.currentThread().getName() + " SynchronizedExplicitLock.run()");
		this.data.print();
		try {
			Thread.sleep(2000);
		} catch(Exception ex) {
		}
		this.data.save();
		this.data.execute();
	}
	
	public static void main(String[] args) {
		DataExplicitLock data = new DataExplicitLock();
		Thread thread1 = new Thread(new SynchronizedExplicitLock(data), "Thread-1");
		Thread thread2 = new Thread(new SynchronizedExplicitLock(data), "Thread-2");
		
		thread1.start();
		thread2.start();
	}
}










