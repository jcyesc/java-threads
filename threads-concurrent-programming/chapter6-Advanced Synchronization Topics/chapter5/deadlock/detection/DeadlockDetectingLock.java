package chapter5.deadlock.detection;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

//
// 	This is a very very slow implementation of a ReentrantLock class and is not for
//	everyday usage. The purpose of this class is to test for deadlocks. The lock()
//	method now throws a DeadlockDetectedException, if a deadlock occurs.
//

public class DeadlockDetectingLock extends ReentrantLock {
    // List of deadlock detecting locks.
    // This array is not thread safe, and must be externally synchronized
    // by the class lock. Hence, it should only be called by static
    // methods.
    private static List deadlockLocksRegistry = new ArrayList();

    private static synchronized void registerLock(DeadlockDetectingLock ddl) {
        if (!deadlockLocksRegistry.contains(ddl))
             deadlockLocksRegistry.add(ddl);
    }

    private static synchronized void unregisterLock(DeadlockDetectingLock ddl) {
        if (deadlockLocksRegistry.contains(ddl))
        	deadlockLocksRegistry.remove(ddl);
    }

    // 	List of threads hard waiting for this lock.
    //	This array is not thread safe, and must be externally synchronized
    //	by the class lock. Hence, it should only be called by static
    //	methods.
    private List hardwaitingThreads = new ArrayList();

    private static synchronized void markAsHardwait(List list, Thread thread) {
        if (!list.contains(thread)) 
        	list.add(thread);
    }

    private static synchronized void freeIfHardwait(List list, Thread thread) {
        if (list.contains(thread)) 
        	list.remove(thread);
    }

    //
    // Deadlock checking methods
    //
    // Given a thread, return all locks that are already owned
    // Must own class lock prior to calling this method
    // This method is not synchronized because when is called, there is a Lock beforehand
    // so nobody can write or delete from the list.
    private static Iterator getAllLocksOwned(Thread thread) {
        DeadlockDetectingLock current;
        ArrayList results = new ArrayList();

        Iterator itr = deadlockLocksRegistry.iterator();
        while (itr.hasNext()) {
            current = (DeadlockDetectingLock) itr.next();
            if (current.getOwner() == thread) 
            	results.add(current);
        }

        return results.iterator(); 
    }

    // Given a lock, return all threads that are hard waiting for the lock
    // Must own class lock prior to calling this method
    private static Iterator getAllThreadsHardwaiting(DeadlockDetectingLock lock) {
        return lock.hardwaitingThreads.iterator();
    }

    // Check to see if a thread can perform a hard wait on a lock
    private static synchronized boolean canThreadWaitOnLock(Thread thread, DeadlockDetectingLock lock) {
        Iterator locksOwned = getAllLocksOwned(thread);
        while (locksOwned.hasNext()) {
            DeadlockDetectingLock currentLock = (DeadlockDetectingLock) locksOwned.next();

            // Thread can't wait if lock is already owned. This is the end condition
            // for the recursive algorithm -- as the initial condition should be
            // already tested for.
            if (currentLock == lock)
            	return false;

            Iterator waitingThreads = getAllThreadsHardwaiting(currentLock);
            while (waitingThreads.hasNext()) {
                Thread otherthread = (Thread) waitingThreads.next();
                // In order for the thread to safely wait on the lock, it can't
                //   own any locks that have waiting threads that already owns
                //   lock. etc. etc. etc. recursively etc.
                if (!canThreadWaitOnLock(otherthread, lock)) {
                	return false;
                }
            }
        }

        return true;
    }

    //
    // Core Constructors
    //
    public DeadlockDetectingLock() {
    	this(false, false);
    }

    public DeadlockDetectingLock(boolean fair) {
        this(fair, false);
    }

    private boolean debugging;

    public DeadlockDetectingLock(boolean fair, boolean debug) {
        super(fair);
        debugging = debug;
        registerLock(this);
    }

    //
    // Core Methods
    //
    public void lock() {
        // Note: Owner can't change if current thread is owner. It is
        //       not guaranteed otherwise. Other owners can change due to
        //       condition variables.
        if (isHeldByCurrentThread()) {
            if (debugging)
            	System.out.println("Already Own Lock");
            
            super.lock();
            // The thread has the lock, so it's not waiting anymore,
            /// we release the thread for the hard waiting list.
            freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            return;
        }

        // Note: The wait list must be marked before it is tested because
        //       there is a race condition between lock() method calls.
        markAsHardwait(hardwaitingThreads, Thread.currentThread());
        if (canThreadWaitOnLock(Thread.currentThread(), this)) {
        	if (debugging)
        		System.out.println("Waiting For Lock - " + Thread.currentThread().getName());
            super.lock();
            freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            if (debugging)
            	System.out.println("Got New Lock - " + Thread.currentThread().getName());
        } else {
            throw new DeadlockDetectedException("DEADLOCK");
        }
    }

    //
    // Note: It is debatable whether this is a hard or soft wait. Even if
    //       interruption is common, we don't know if the interrupting thread
    //       is also involved in the deadlock. As a compromise, we'll just
    //       not allow interrupts. This method is disabled.
    public void lockInterruptibly() throws InterruptedException {
        lock();
    }

    //
    // Note: It is not necessary to override the tryLock() methods. These
    //     methods perform a soft wait -- there is a limit to the wait. It
    //     not possible to deadlock when locks are not waiting indefinitely.
    //
    // Note 1: Deadlocks are possible with any hard wait -- this includes
    //      the reacquitition of the lock upon return from an await() method.
    //      As such, condition variables will mark for the future hard
    //      wait, prior to releasing the lock.
    // Note 2: There is no need to check for deadlock on this end because
    //      a deadlock can be created whether the condition variable owns the
    //      lock or is reacquiring it. Since we are marking *before* giving
    //      up ownership, the deadlock will be detected on the lock() side
    //      first. It is not possible to create a new deadlock just by releasing
    //      locks.
    public class DeadlockDetectingCondition implements Condition {
    	Condition embedded;

        protected DeadlockDetectingCondition(ReentrantLock lock, Condition embedded) {
        	this.embedded = embedded;
        }

        // Note: The algorithm can detect a deadlock condition if the thead is
        //    	 either waiting for or already owns the lock, or both. This is why
        //    we have to mark for waiting *before* giving up the lock.
        public void await() throws InterruptedException {
            try {
                markAsHardwait(hardwaitingThreads, Thread.currentThread());
                embedded.await();
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
        }

        public void awaitUninterruptibly() {
            markAsHardwait(hardwaitingThreads, Thread.currentThread());
            embedded.awaitUninterruptibly();
            freeIfHardwait(hardwaitingThreads, Thread.currentThread());
        }

        public long awaitNanos(long nanosTimeout) throws InterruptedException {
            try {
                markAsHardwait(hardwaitingThreads, Thread.currentThread());
                return embedded.awaitNanos(nanosTimeout);
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
        }

        public boolean await(long time, TimeUnit unit) throws InterruptedException {
            try {
                markAsHardwait(hardwaitingThreads, Thread.currentThread());
                return embedded.await(time, unit);
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
        }

        public boolean awaitUntil(Date deadline) throws InterruptedException {
            try {
                markAsHardwait(hardwaitingThreads, Thread.currentThread());
                return embedded.awaitUntil(deadline);
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
        }

        public void signal() {
        	embedded.signal();
        }

        public void signalAll() {
        	embedded.signalAll();
        }
    }

    // Return a condition variable that support detection of deadlocks
    public Condition newCondition() {
    	return new DeadlockDetectingCondition(this, super.newCondition());
    }
}

