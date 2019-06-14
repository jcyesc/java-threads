package chapter4.example0.method;

public class SharedObject {
	public int[] numbers;
	
	
	public synchronized void produceNumbers() {
		System.out.println(Thread.currentThread().getName() + " SharedObject.produceNumbers()");
		numbers = new int[5];
		
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = (int)(Math.random() * 10); 
		}
		
		System.out.println(Thread.currentThread().getName() + " NOTIFYING");
		this.notify();
	}
	
	public synchronized void consumeNumbers() {
		System.out.println(Thread.currentThread().getName() + " SharedObject.printNumbers()");
		if(numbers == null) {
			try {
				System.out.println(Thread.currentThread().getName() + " WAITING");
				this.wait();
			} catch (InterruptedException e) { }
		}
		
		System.out.println(Thread.currentThread().getName() + " after waiting");
		for (int i = 0; i < numbers.length; i++) {
			System.out.println(numbers[i]);
		}
	}
}
