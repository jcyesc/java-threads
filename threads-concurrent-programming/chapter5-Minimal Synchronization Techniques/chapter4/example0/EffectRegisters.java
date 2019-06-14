package chapter4.example0;

import java.util.Date;

public class EffectRegisters implements Runnable {

	private boolean done = false;
	
	public void run() {
		while(!done) {
			System.out.println(Thread.currentThread().getName() + " - " + (new Date()));
		}
	}
	
	public void setDone() {
		this.done = true;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("EffectRegisters.main()");
		EffectRegisters er = new EffectRegisters();
		Thread thread1 = new Thread(er, "Thread-1");
		thread1.start();
		Thread.currentThread().sleep(1000);
		er.setDone();
	}
}
