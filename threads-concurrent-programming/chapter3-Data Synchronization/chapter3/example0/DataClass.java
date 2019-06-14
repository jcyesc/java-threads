package chapter3.example0;

public class DataClass {
	public synchronized void print() {
		System.out.println("START " + Thread.currentThread().getName());
		System.out.println("Printing");
		try{
			Thread.sleep(2000);
		} catch(Exception ex) {
		}
		System.out.println("END " + Thread.currentThread().getName());
	}
}
