package chapter4.example0.block;

public class MainBlock {
	public static void main(String[] args) {
		System.out.println("MainBlock.main()");
		SharedObject sharedObject = new SharedObject();
		Thread producer = new Thread(new Producer(sharedObject), "Producer");
		Thread consumer = new Thread(new Consumer(sharedObject), "Consumer");
		
		consumer.start();
		producer.start();
	}
}
