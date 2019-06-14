package chapter12.traditionalserver;

import java.io.*;
import java.net.*;
import java.util.Date;

public class TCPClient {
	private Socket sock;
	private DataInputStream reader;
	private DataOutputStream writer;
	
	public TCPClient(String host, int port) throws IOException {
		sock = new Socket(host, port);
		reader = new DataInputStream(sock.getInputStream());
		reader.read(); // Welcome
		writer = new DataOutputStream(sock.getOutputStream());
		String s = getString();
		System.out.println(s);
	}
	
	private String getString() throws IOException {
		System.out.println("TCPClient.getString()");
		byte b = TypeServerConstants.GET_STRING_REQUEST;
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.write(b);
		writer.flush();
		System.out.println("Reading " + new Date());
		b = (byte) reader.readByte();
		System.out.println("Finish reading byte " + new Date());
		if(b != TypeServerConstants.GET_STRING_RESPONSE)
			throw new IllegalStateException("Bad recv state " + b);
		return reader.readUTF();
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("TCPClient.main()");
		TCPClient client = new TCPClient("localhost", 8900);
		System.out.println(client.getString());
		//client.sock.close();
	}
}
