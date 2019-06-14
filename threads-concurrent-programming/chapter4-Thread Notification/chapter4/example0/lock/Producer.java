package chapter4.example0.lock;

public class Producer implements Runnable {
	private SharedObject sharedObject;
	
	public Producer(SharedObject sharedObject) {
		this.sharedObject = sharedObject;
	}
	
	public void run() {
		System.out.println(Thread.currentThread().getName() + " Producer.run()");
		this.sharedObject.produceNumbers();
	}
}
