package chapter5.deadlock.detection;

public class MainTestLock2 {
	public static void main(String[] args) {
		DeadlockDetectingLock lock = new DeadlockDetectingLock();
		lock.lock();
		
		System.out.println("MainTestLock2.main()");
		
		lock.unlock();
	}
}
