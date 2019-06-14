package chapter4.example0.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedObject {
	public int[] numbers;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public void produceNumbers() {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " SharedObject.produceNumbers()");
		numbers = new int[5];
		
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = (int)(Math.random() * 10); 
		}
		
		System.out.println(Thread.currentThread().getName() + " NOTIFYING");
		this.condition.signalAll();
		this.lock.unlock();
	}
	
	public void consumeNumbers() {
		this.lock.lock();
		System.out.println(Thread.currentThread().getName() + " SharedObject.printNumbers()");
		if(numbers == null) {
				System.out.println(Thread.currentThread().getName() + " WAITING");
				try {
					this.condition.await();
				} catch (InterruptedException e) {}
		}
		
		System.out.println(Thread.currentThread().getName() + " after waiting");
		for (int i = 0; i < numbers.length; i++) {
			System.out.println(numbers[i]);
		}
		this.lock.unlock();
	}
}
