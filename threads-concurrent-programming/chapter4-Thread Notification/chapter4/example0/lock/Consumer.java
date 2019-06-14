package chapter4.example0.lock;

public class Consumer implements Runnable {
	private SharedObject sharedObject;
	
	public Consumer(SharedObject sharedObject) {
		this.sharedObject = sharedObject;
	}
	
	
	public void run() {
		System.out.println(Thread.currentThread().getName() + " Consumer.run()");
		this.sharedObject.consumeNumbers();
		
	}
}
