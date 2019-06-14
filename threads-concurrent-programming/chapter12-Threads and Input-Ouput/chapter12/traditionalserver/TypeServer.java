package chapter12.traditionalserver;

import java.io.*;
import java.net.*;
import java.util.Date;

public class TypeServer extends TCPServer {
	public void run(Socket data) {
		System.out.println(Thread.currentThread().getName() + "TypeServer.run()");
		try {
			DataOutputStream dos = new DataOutputStream(data.getOutputStream());
			dos.writeByte(TypeServerConstants.WELCOME);
			DataInputStream dis = new DataInputStream(data.getInputStream());
			while(true) {
				System.out.println("Waiting the request " + new Date());
				byte b = dis.readByte();
				System.out.println("Processing the request " + new Date());
				if(b != TypeServerConstants.GET_STRING_REQUEST) {
					System.out.println("Client sent unknown request " + b);
					continue;
				}
				dos.writeByte(TypeServerConstants.GET_STRING_RESPONSE);
				dos.writeUTF("Thisisamultithreadedserver");
				dos.flush();
				System.out.println(Thread.currentThread().getName() + " flush.");
			}
		} catch(Exception ex) {
			System.out.println(Thread.currentThread().getName() + " Client termination: " + ex);
			ex.printStackTrace();
			return;
		}
	}
	
	public static void main(String[] args) throws IOException {
		TypeServer ts = new TypeServer();
		ts.startServer(8900);
		System.out.println("Server ready and waiting");
	}
}
