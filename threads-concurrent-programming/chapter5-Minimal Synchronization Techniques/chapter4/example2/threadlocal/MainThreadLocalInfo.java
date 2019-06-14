package chapter4.example2.threadlocal;

/**
Thread Local Variables

Any thread can, at any time, define a thread local variable that is private to that particular 
thread. Other threads that define the same variable create their own copy of the variable. This 
means that thread local variables cannot be used to share state between threads; changes to the 
variable in one thread are private to that thread and not reflected in the copies held by other 
threads. But it also means that access to the variable need never be synchronized since it's 
impossible for multiple threads to access the variable. Thread local variables have other uses, of 
course, but their most common use is to allow multiple threads to cache their own data rather than 
contend for synchronization locks around shared data.
 
ThreadLocal

In this example you will notice that each thread that is created will have their own copy of the ThreadLocal variable,
so you can store info related to the Thread in a ThreadLocalVariable. A ThreadLocal variable doesn't need to be 
synchronized in order to modify.

*/
public class MainThreadLocalInfo implements Runnable {
	
	private ThreadLocalInfo info;
	
	public MainThreadLocalInfo(ThreadLocalInfo info) {
		this.info = info;
	}
	
	public void run() {
		String threadName = Thread.currentThread().getName();
		info.setLocalInfo(threadName);
		info.setNoThreadLocal(threadName);
		info.print();
	}
	
	public static void main(String[] args) {
		ThreadLocalInfo info = new ThreadLocalInfo();
		info.setLocalInfo(Thread.currentThread().getName());
		Thread thread1 = new Thread(new MainThreadLocalInfo(info), "THREAD-1");
		Thread thread2 = new Thread(new MainThreadLocalInfo(info), "THREAD-2");
		Thread thread3 = new Thread(new MainThreadLocalInfo(info), "THREAD-3");
		
		thread1.start();
		thread2.start();
		thread3.start();
		info.print();
	}
}
