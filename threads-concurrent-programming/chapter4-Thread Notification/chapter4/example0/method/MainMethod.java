package chapter4.example0.method;

public class MainMethod {
	public static void main(String[] args) {
		System.out.println("MainMethod.main()");
		SharedObject sharedObject = new SharedObject();
		Thread producer = new Thread(new Producer(sharedObject), "Producer");
		Thread consumer = new Thread(new Consumer(sharedObject), "Consumer");
		
		consumer.start();
		producer.start();
	}
}
