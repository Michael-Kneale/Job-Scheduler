import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class MyClient2 {
public static void main(String[] args) {
	try {
		Socket s = new Socket("127.0.0.1", 50000);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());

		//Connection
		dout.write(("HELO\n").getBytes());
		String str = (String)in.readLine();
		System.out.println(str);

		//Authentication
		dout.write(("AUTH user.name\n").getBytes());
		str = (String)in.readLine();
		System.out.println(str);
		
		String[] split;
		String[] split2;
		String[] goodSplit = {"0", "0", "0", "0", "0", "0", "0", "0", "0"};
		int whichServer = -1;
		//String whichServer2;
		
		outerthing:
		while(!str.equals("NONE")) {
			whichServer++;
			if(whichServer > 9) {
				whichServer = 0;
			}
			dout.write(("REDY\n").getBytes());
			str = (String)in.readLine();
			System.out.println(str + "just sent redy");
			
			while(str.contains("JCPL")) {
				dout.write(("REDY\n").getBytes());
				str = (String)in.readLine();
				System.out.println(str + "sent redy after jcpl");
			}
			
			if(str.equals("NONE")) {
				break;
			}
			split = str.split(" ");
						
			dout.write(("GETS Capable " + split[4] + " " + split[5] + " " + split[6] + "\n").getBytes());
			str = (String)in.readLine();
			System.out.println(str + "respopnse to GETS");
						
			while(!str.equals(".")) {
				dout.write(("OK\n").getBytes());
				str = (String)in.readLine();
				System.out.println(str);
				if(!str.equals(".")) {
					split2 = str.split(" ");
					for(int i = 0; i < goodSplit.length; i++) {
						System.out.println(goodSplit[i] + "serverr");
					}
					int split2toBeCompared = Integer.parseInt(split2[4]);
					int goodSplitToBeCompared = Integer.parseInt(goodSplit[4]);
					if(split2toBeCompared > goodSplitToBeCompared) {
						goodSplit = split2;
					}
				}
			}
			Thread.sleep(12);
			dout.write(("SCHD " + split[2] + " " + goodSplit[0] + " " + whichServer + "\n").getBytes());
			str = (String)in.readLine();
			System.out.println(str + "schdresponse");
			//Thread.sleep(200);
			while(!str.contains("OK")) {
				
				str = (String)in.readLine();
				System.out.println(str + "waiting for ok after schd");
			}
			

			for(int i = 0; i < goodSplit.length; i++) {
				goodSplit[i] = "0";
			}

			
		}

		dout.write(("QUIT\n").getBytes());
		str = (String)in.readLine();

		dout.flush();
		dout.close();
		s.close();
		}
		catch(Exception e){System.out.println(e);}
	}
}

