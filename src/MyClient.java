import java.io.*;
import java.net.*;
public class MyClient {
public static void main(String[] args) {
	try {      
		Socket s = new Socket("127.0.0.1", 60000);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());
		
		//hello = "HELO\n"
		//authentication = "AUTH user.name\n";
		//connection
		dout.write(("HELO\n").getBytes());
		//change to print
		String str = (String)in.readLine();			
			
		//authentication
		dout.write(("AUTH user.name\n").getBytes());
		str = (String)in.readLine();
		
		dout.write(("REDY\n").getBytes());
		str = (String)in.readLine();
		
		//int JobID = 0;		
		
		dout.write(("QUIT\n").getBytes());
		str = (String)in.readLine();
		

		dout.flush();
		dout.close();
		s.close();
		}
		catch(Exception e){System.out.println(e);}
	}
}

