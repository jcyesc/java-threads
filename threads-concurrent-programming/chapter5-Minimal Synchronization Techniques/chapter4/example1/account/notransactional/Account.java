package chapter4.example1.account.notransactional;

public class Account {
	private int balance;
	
	public Account(int balance) {
		this.balance = balance;
	}
	
	public void deposit(int deposit) {
		this.balance += deposit;
	}
	
	public void withdraw(int withdrawal) {
			System.out.println(Thread.currentThread().getName() + " withdrawal: " + withdrawal + ", balance: " + balance);
			int oldBalance = this.balance;
			
			if(this.balance >= withdrawal) {
				try {
					Thread.sleep(100 * withdrawal);
				}catch(InterruptedException ex) {}
				
				balance = balance - withdrawal;
				System.out.println(Thread.currentThread().getName() + " Account.withdraw() - " + 
						"withdrawal: " + withdrawal + ", old balance " + oldBalance +", new balance:" + balance);
			} else {
				System.out.println(Thread.currentThread().getName() + " INVALID WITHDRAW Account.withdraw() - " + 
						"withdrawal: " + withdrawal + ", current balance:" + this.balance);
			}
	}
	
	public int getBalance() {
		return this.balance;
	}
}
