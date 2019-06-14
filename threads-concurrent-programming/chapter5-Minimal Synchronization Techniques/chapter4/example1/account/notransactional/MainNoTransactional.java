package chapter4.example1.account.notransactional;

import java.util.Date;

// java -classpath . chapter4/example1/account/atomic/MainAtomic 

public class MainNoTransactional {
	public static void main(String[] args) {
		System.out.println("MainNoTransactional.main() " + new Date());
		Account account = new Account(120);
		Thread thread1 = new Thread(new WithdrawThread(account, 1), "THREAD-1");
		Thread thread2 = new Thread(new WithdrawThread(account, 2), "THREAD-2");
		Thread thread3 = new Thread(new WithdrawThread(account, 3), "THREAD-3");
		Thread thread4 = new Thread(new WithdrawThread(account, 4), "THREAD-4");
		Thread thread5 = new Thread(new WithdrawThread(account, 5), "THREAD-5");
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		
	}
}
