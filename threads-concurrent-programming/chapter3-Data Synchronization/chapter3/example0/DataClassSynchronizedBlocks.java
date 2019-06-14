package chapter3.example0;


public class DataClassSynchronizedBlocks {

	/**
	 * If there are several Threads executing this method, the first print method will be printed, but for the
	 * other messages, the others Threads must wait till the Thread that got the lock releases it.
	 */
	public void print() {
		System.out.println(Thread.currentThread().getName() + " Without the Lock - DataClassSynchronizedBlocks.print()");
		
		synchronized(this) {
			System.out.println("START " + Thread.currentThread().getName() + " getting the Lock");
			try{
				System.out.println(Thread.currentThread().getName() + " printing");
				Thread.sleep(2000);
			} catch(Exception ex) {
			}
			System.out.println("END " + Thread.currentThread().getName() + " releasing the Lock");
		}
	}
}
