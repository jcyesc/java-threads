package chapter5.deadlock.detection;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MainTestLock {

	//
	// Testing routines here
	//
	// These are very simple tests -- more tests will have to be written
	private static Lock a = new DeadlockDetectingLock(false, true);
	private static Lock b = new DeadlockDetectingLock(false, true);
	private static Lock c = new DeadlockDetectingLock(false, true);
	private static Condition wa = a.newCondition();
	private static Condition wb = b.newCondition();
	private static Condition wc = c.newCondition();

	private static void delaySeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ex) {
		}
	}

	private static void awaitSeconds(Condition c, int seconds) {
		try {
			c.await(seconds, TimeUnit.SECONDS);
		} catch (InterruptedException ex) {
		}
	}

	private static void testOne() {
		// This Thread first gets the A lock.
		new Thread(new Runnable() {
			public void run() {
				System.out.println("Thread ONE grab a");
				a.lock();
				delaySeconds(2);
				System.out.println("Thread ONE grab b");
				b.lock();
				delaySeconds(2);
				a.unlock();
				b.unlock();
			}
		}).start();

		// This Thread first gets the B lock.
		new Thread(new Runnable() {
			public void run() {
				System.out.println("Thread TWO grab b");
				b.lock();
				delaySeconds(2);
				System.out.println("Thread TWO grab a");
				a.lock();
				delaySeconds(2);
				a.unlock();
				b.unlock();
			}
		}).start();
	}

	private static void testTwo() {
		new Thread(new Runnable() {
			public void run() {
				System.out.println("thread one grab a");
				a.lock();
				delaySeconds(2);
				System.out.println("thread one grab b");
				b.lock();
				delaySeconds(10);
				a.unlock();
				b.unlock();
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				System.out.println("thread two grab b");
				b.lock();
				delaySeconds(2);
				System.out.println("thread two grab c");
				c.lock();
				delaySeconds(10);
				b.unlock();
				c.unlock();
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				System.out.println("thread three grab c");
				c.lock();
				delaySeconds(4);
				System.out.println("thread three grab a");
				a.lock();
				delaySeconds(10);
				c.unlock();
				a.unlock();
			}
		}).start();
	}

	private static void testThree() {
		new Thread(new Runnable() {
			public void run() {
				System.out.println("Thread ONE grab b");
				b.lock();
				System.out.println("Thread ONE grab a");
				a.lock();
				delaySeconds(2);
				/*
				 When we invoke awaitSeconds, the B lock is released, so Thread Two Grab it, and then, Thread TWO
				 tries to GRAB Lock A, but lock A is hold for Thread ONE that is waiting for the Lock B, here is 
				 when the DEADLOCK happens.  
				 */
				System.out.println("Thread ONE WAITS on b");
				awaitSeconds(wb, 10);
				System.out.println("Thread ONE - AFTER WAITING");
				a.unlock();
				b.unlock();
			}
		}, "Thread-ONE").start();

		new Thread(new Runnable() {
			public void run() {
				delaySeconds(1);
				System.out.println("Thread TWO grab b");
				b.lock();
				System.out.println("Thread TWO grab a");
				a.lock();
				System.out.println("Thread TWO - After grabbing lock a");
				delaySeconds(10);
				b.unlock();
				a.unlock();
			}
		}, "Thread-TWO").start();
	}

	public static void main(String args[]) {
		int test = 3;

		if (args.length > 0)
			test = Integer.parseInt(args[0]);

		switch (test) {
			case 1:
				testOne(); // 2 threads deadlocking on grabbing 2 locks
			break;
			case 2:
				testTwo(); // 3 threads deadlocking on grabbing 2 out of 3 locks
			break;
			case 3:
				testThree(); // 2 threads deadlocking on 2 locks with CV wait
			break;
			default:
				System.err.println("usage: java DeadlockDetectingLock [ test# ]");
		}

		delaySeconds(60);
		System.out.println("--- End Program ---");
		System.exit(0);
	}
}
