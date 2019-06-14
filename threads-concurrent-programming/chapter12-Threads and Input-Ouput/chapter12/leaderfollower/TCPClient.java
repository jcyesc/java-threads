package chapter12.leaderfollower;


import java.io.*;
import java.net.*;
import java.io.IOException;

public class TCPClient {
	private String host;
	private int port;
	
	public TCPClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void print() throws IOException{
		String response = getString();
		System.out.println(response);
	}
	
	public String getString() throws IOException{
		Socket sock = new Socket(host, port);
		DataInputStream reader = new DataInputStream(sock.getInputStream());
		reader.read(); // Welcome
		DataOutputStream writer = new DataOutputStream(sock.getOutputStream());
		byte b = TypeServerConstants.GET_STRING_REQUEST;
		writer.write(b);
		writer.flush();
		b = (byte) reader.readByte();
		if(b != TypeServerConstants.GET_STRING_RESPONSE)
			throw new IllegalStateException("Bad recv state " + b);
		String s = reader.readUTF();
		sock.close();
		
		return s;
		
	}
	
	public static void main(String[] args) throws Exception{
		TCPClient client = new TCPClient("localhost", 4500);
		for(int i = 0; i < 10; i++)
			client.print();
	}
	
}
