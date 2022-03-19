import java.io.*;
import java.net.*;
public class MyClient {
public static void main(String[] args) {
	try {      
		Socket s = new Socket("127.0.0.1", 60000);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());
		
		String serverMessage;
		
		//Connection
		dout.write(("HELO\n").getBytes());
		String str = (String)in.readLine();
		System.out.println(str);
		
		//Authentication
		dout.write(("AUTH user.name\n").getBytes());
		str = (String)in.readLine();
		System.out.println(str);
		
		dout.write(("REDY\n").getBytes());
		str = (String)in.readLine();
		System.out.println(str);
		
		dout.write(("GETS All\n").getBytes());
		str = (String)in.readLine();
		System.out.println(str);
						
		dout.write(("OK\n").getBytes());
		str = (String)in.readLine();
		System.out.println(str);
		
		String[] split = str.split(" ");
		
		
		if(!str.equals("NONE")) {
			for(int JobID = 0; JobID < 10; JobID++) {
				//serverMessage = in.readLine();
				//System.out.println("Server " + serverMessage);
				dout.write(("OK\n").getBytes());
				str = (String)in.readLine();
				System.out.println(str);
				dout.write(("SCHD " + JobID + " joon" + " \n").getBytes());
				str = (String)in.readLine();
				System.out.println(str);

			}
		}
		dout.write(("QUIT\n").getBytes());
		str = (String)in.readLine();
		
		for (String t : split)
  			System.out.println(t);

		dout.flush();
		dout.close();
		s.close();
		}
		catch(Exception e){System.out.println(e);}
	}
}

