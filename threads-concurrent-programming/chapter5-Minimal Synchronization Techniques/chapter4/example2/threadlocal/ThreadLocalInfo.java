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
*/

public class ThreadLocalInfo {
	
	private String noThreadLocal;
	
	private ThreadLocal<String> threadLocalVariable = new ThreadLocal<String>() {
		protected String initialValue() {
			return Thread.currentThread().getName();
		}
	};
	
	public void setLocalInfo(String value) {
		threadLocalVariable.set(threadLocalVariable.get() + " - " + value);
	}
	
	public void setNoThreadLocal(String noThreadLocal) {
		this.noThreadLocal += (" - " + noThreadLocal);
	}
	
	public void print() {
		String msg = Thread.currentThread().getName() +
			" - Variable: [" + threadLocalVariable.get() + 
			"], NoThreadLocal [" + this.noThreadLocal + " ] ThreadLocalInfo.print() ";
		
		System.out.println(msg);
	}
}
