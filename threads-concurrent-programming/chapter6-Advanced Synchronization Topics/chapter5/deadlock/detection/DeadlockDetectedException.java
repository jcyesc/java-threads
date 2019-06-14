package chapter5.deadlock.detection;

public class DeadlockDetectedException extends RuntimeException {
    public DeadlockDetectedException(String s) {
    	super(s);
    }
}

