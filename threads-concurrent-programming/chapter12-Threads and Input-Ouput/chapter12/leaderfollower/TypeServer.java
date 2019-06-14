package chapter12.leaderfollower;


import java.io.*;
import java.net.*;
import java.io.DataOutputStream;



public class TypeServer extends TCPThrottledServer {
	public void run(Socket data) {
		try {
			System.out.println(Thread.currentThread().getName());
			DataOutputStream dos =
				new DataOutputStream(data.getOutputStream());
			dos.writeByte(TypeServerConstants.WELCOME);
			DataInputStream dis = new DataInputStream(data.getInputStream());
			byte b = dis.readByte();
			if(b != TypeServerConstants.GET_STRING_REQUEST) {
				System.out.println("Client sent unknown request " + b);
				return;
			}
			dos.writeByte(TypeServerConstants.GET_STRING_RESPONSE);
			dos.writeUTF("This is a test string");
			dos.flush();
		} catch(Exception ex) {
			System.out.println("Client terminating: " + ex);
			return;
		} finally {
			try {
				data.close();
			} catch(IOException ioe) {
				
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		int port = 4500;
		int numberThreads = 10;
		TypeServer ts = new TypeServer();
		ts.startServer(port, numberThreads);
		System.out.println("Server ready and waiting");
	}
}
