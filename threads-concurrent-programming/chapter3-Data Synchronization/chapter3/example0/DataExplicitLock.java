package chapter3.example0;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataExplicitLock {
	private Lock lock = new ReentrantLock();
	
	public void print() {
		System.out.println(Thread.currentThread().getName() + " without the Lock - DataExplicitLock.print()");
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " Getting the Lock - print");
		try {
			Thread.sleep(2000);
		} catch(Exception ex) {
		}
		System.out.println(Thread.currentThread().getName() + " printing");
		System.out.println(Thread.currentThread().getName() + " Releasing the Lock - print");
		lock.unlock();
	}
	
	public void save() {
		System.out.println(Thread.currentThread().getName() + " without the Lock - DataExplicitLock.save()");
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " Getting the Lock - save");
		try {
			Thread.sleep(2000);
		} catch(Exception ex) {
		}
		System.out.println(Thread.currentThread().getName() + " saving");
		System.out.println(Thread.currentThread().getName() + " Releasing the Lock - save");
		lock.unlock();
	}
	
	public void execute() {
		lock.lock();
		System.out.println("START " + Thread.currentThread().getName() + " execute()");
		this.print();
		this.save();
		lock.unlock();
		System.out.println("END " + Thread.currentThread().getName() + " execute()");
	}
}
