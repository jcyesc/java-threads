package chapter12.leaderfollower;


/**
 * The design pattern of this example is known as the leader-follower pattern. It relies on the fact
 * that only one thread can execute the accept() method; that is, the internal implementation of the
 * accept() method is synchronized. The thread that obtains that lock can establish the connection with a
 * client and obtain that client's data socket. It can then release the lock, and the next thread in line
 * then obtains the lock and processes the next client.
 * 
 * @author yescas
 *
 */

import java.net.*;
import java.io.*;

public abstract class TCPThrottledServer implements Runnable {
	ServerSocket server = null;
	Thread[] serverThreads;
	volatile boolean done = false;
	
	public synchronized void startServer(int port, int nThreads) throws IOException {
		server = new ServerSocket(port);
		
		serverThreads = new Thread[nThreads];
		for(int i = 0; i < nThreads; i++) {
			serverThreads[i] = new Thread(this);
			serverThreads[i].start();
		}
	}
	
	public synchronized void setDone() {
		done = true;
	}
	
	public void run() {
		while(!done) {
			try {
				Socket data;
				data = server.accept();
				run(data);
			} catch(IOException ex) {
				System.out.println("Accept error " + ex);
			}
		}
	}
	
	public void run(Socket data) {		
	}
}
