import java.io.*;
import java.net.*;
public class MyClient {
public static void main(String[] args) {
	try {
		Socket s = new Socket("localhost",6666);
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());
		
		dout.writeUTF("HELO");
		
		String str = (String)dis.readUTF();
		dout.writeUTF("BYE");
		
		str = (String)dis.readUTF();
		
		
		
		dout.flush();
		dout.close();
		s.close();
		}
		catch(Exception e){System.out.println(e);}
	}
}
