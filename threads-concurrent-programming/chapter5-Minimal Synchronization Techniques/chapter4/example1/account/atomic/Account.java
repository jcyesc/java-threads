package chapter4.example1.account.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class Account {
	private AtomicInteger balance;
	
	public Account(int balance) {
		this.balance = new AtomicInteger(balance);
	}
	
	public void deposit(int deposit) {
		this.balance.addAndGet(deposit);
	}
	
	// Doing transactional operation without synchronized the Thread.
	public void withdraw(int withdrawal) {
		//System.out.println(Thread.currentThread().getName() + " Account.withdraw() - " + 
		//		"withdrawal: " + withdrawal + ", balance:" + bal);
		// This loop is necessary because we are not synchronizing the method, and another
		// thread can modify the value of balance, so we compare the value in the method
		// compareAndSet.
		while(true) {
			int bal = this.balance.get();
			if(bal >= withdrawal) {
				int newBalance = bal - withdrawal;
				try {
					Thread.sleep(100 * withdrawal);
				} catch (InterruptedException e) {}
				if(this.balance.compareAndSet(bal, newBalance)) {
					System.out.println(Thread.currentThread().getName() + " Account.withdraw() - " + 
							"withdrawal: " + withdrawal + ", balance " + bal +", new balance:" + newBalance);
					break;
				}
				System.out.println(Thread.currentThread().getName() + " TRYING WITHDRAWING - " + 
						"withdrawal: " + withdrawal + ", old balance " + bal +", current balance:" + this.balance);
				
			} else {
				System.out.println(Thread.currentThread().getName() + " INVALID WITHDRAW Account.withdraw() - " + 
						"withdrawal: " + withdrawal + ", current balance:" + this.balance);
				break;
			}
		}
	}
	
	public int getBalance() {
		return this.balance.get();
	}
}
