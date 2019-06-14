package chapter4.example1.account.atomic;

import java.util.Date;

public class WithdrawThread implements Runnable {

	private Account account;
	private int withdrawal;
	
	public WithdrawThread(Account account, int withdrawal) {
		this.account = account;
		this.withdrawal = withdrawal;
	}
	
	public void run() {
		while(account.getBalance() > 0) {
			this.account.withdraw(withdrawal);
		}
		System.out.println("FINAL BALANCE " + account.getBalance());
		System.out.println(Thread.currentThread().getName() + " END " + new Date());
	}
}
