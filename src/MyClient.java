/*import java.io.*;
import java.net.*;

public class MyClient {
public static void main(String[] args) {
	try {
		//Connect to localhost (machine on which program is running) on the server's
		//default port, 50000
		//Create input and output streams to communicate to and receive communications
		//from the server
		Socket s = new Socket("localhost", 50000);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());
		
		//Get user name from the system to be used in authentication
		//Initialise array (for storing data sent from server e.g. job information)
		//Initialise mostCoresType, which keeps track
		//of which server type has the most cores
		String username = System.getProperty("user.name");
		String[] array = {"0", "0", "0", "0", "0", "0", "0", "0", "0"};
		String mostCoresType = "";

		//Communicate with server
		dout.write(("HELO\n").getBytes());
		String str = (String)in.readLine();

		//Authenticate with server
		dout.write(("AUTH " + username + "\n").getBytes());
		str = (String)in.readLine();
		
		//Used to track how many cores the biggest server has
		int mostCores = 0;
		
		//Main scheduling loop continues until the break condition within the code,
		//receiving NONE from the server, is met
		while(true) {
			//Reset the number of servers to be 0 at the start of each loop
			int serverCount = 0;
			
			//Send REDY to server to indicate that
			//client is ready for information from server
			dout.write(("REDY\n").getBytes());
			str = (String)in.readLine();
			
			//Acknowledge each of the JCPL messages (if there are any)
			while(str.contains("JCPL")) {
				dout.write(("REDY\n").getBytes());
				str = (String)in.readLine();
			}
			//Ends loop if server sends NONE, as server has nothing more to send
			if(str.equals("NONE")) {
				break;
			}
			//After the possible JCPL messages, and if NONE was not sent,
			//the server will send a job for scheduling. This code splits
			//the job by space so that each part of the information can
			//easily be handled individually
			array = str.split(" ");
			
			//Send GETS command to find out information about server
			dout.write(("GETS Capable " + array[4] + " " +
				   array[5] + " " + array[6] + "\n").getBytes());
			str = (String)in.readLine();
			String jobID = array[2];
			
			//Split data so number of records can be easily obtained
			array = str.split(" ");
			int noOfRecords = Integer.parseInt(array[1]);
			
			//Send OK to server to begin receiving records
			dout.write(("OK\n").getBytes());		
			
			//Loop continues for the number of records there are
			for(int i = 0; i < noOfRecords; i++) {
				//Read the record and split it by space so it can be easily handled
				str = (String)in.readLine();
				array = str.split(" ");
				
				//Find out current number of cores and server type from record
				int currentCoresNo = Integer.parseInt(array[4]);
				String currentType = array[0];
				
				//If the mostCoresType variable is null (i.e. it's the first time
				//this loop has run) or the current number of cores is greater
				//than the previous greatest number, then the server type with the
				//most cores must be the current type.
				//The most cores is the current number of cores, and the
				//serverCount is set to one, as this is the first record of what is
				//(thus far) the largest server.
				if(mostCoresType.equals(null) || currentCoresNo > mostCores) {
					mostCoresType = currentType;
					mostCores = currentCoresNo;
					serverCount = 1;
				}
				//If the server type with the most cores is the current server type,
				//then increment the variable that keeps track of how many servers
				//of the largest type exist
				if(mostCoresType.equals(currentType)) {
					serverCount++;
				}
			}
			
			//Send OK after all the records have been received
			dout.write(("OK\n").getBytes());
			str = (String)in.readLine();
			
			//Schedule a job to the server with the most cores
			dout.write(("SCHD " + jobID + " " + mostCoresType + " " +
				   (Integer.parseInt(jobID) % serverCount) + "\n").getBytes());
			str = (String)in.readLine();
			
			//Wait for "OK" response from server to indicate that
			//scheduling has successfully completed
			while(!str.equals("OK")) {
				str = (String)in.readLine();
			}
		}

		//Send QUIT to server to smoothly terminate simulation,
		//then close the input/output streams
		dout.write(("QUIT\n").getBytes());
		dout.flush();
		dout.close();
		s.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}*/

import java.io.*;
import java.net.*;

public class MyClient {
public static void main(String[] args) {
	try {
		//Connect to localhost (machine on which program is running) on the server's
		//default port, 50000
		//Create input and output streams to communicate to and receive communications
		//from the server
		Socket s = new Socket("localhost", 50000);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());
		
		//Get user name from the system to be used in authentication
		//Initialise array (for storing data sent from server e.g. job information)
		//Initialise mostCoresType, which keeps track
		//of which server type has the most cores
		String username = System.getProperty("user.name");
		String[] array = {"0", "0", "0", "0", "0", "0", "0", "0", "0"};
		String mostCoresType = "";

		//Communicate with server
		dout.write(("HELO\n").getBytes());
		String str = (String)in.readLine();

		//Authenticate with server
		dout.write(("AUTH " + username + "\n").getBytes());
		str = (String)in.readLine();
		
		//Used to track how many cores the biggest server has
		int mostCores = 0;
		
		//Main scheduling loop continues until the break condition within the code,
		//receiving NONE from the server, is met
		while(true) {
			//Reset the number of servers to be 0 at the start of each loop
			int serverCount = 0;
			
			//Send REDY to server to indicate that
			//client is ready for information from server
			dout.write(("REDY\n").getBytes());
			str = (String)in.readLine();
			
			//Acknowledge each of the JCPL messages (if there are any)
			while(str.contains("JCPL")) {
				dout.write(("REDY\n").getBytes());
				str = (String)in.readLine();
			}
			//Ends loop if server sends NONE, as server has nothing more to send
			if(str.equals("NONE")) {
				break;
			}
			//After the possible JCPL messages, and if NONE was not sent,
			//the server will send a job for scheduling. This code splits
			//the job by space so that each part of the information can
			//easily be handled individually
			array = str.split(" ");
			
			//Send GETS command to find out information about server
			dout.write(("GETS Capable " + array[4] + " " +
				   array[5] + " " + array[6] + "\n").getBytes());
			str = (String)in.readLine();
			String jobID = array[2];
			
			//Split data so number of records can be easily obtained
			array = str.split(" ");
			int noOfRecords = Integer.parseInt(array[1]);
			
			//Send OK to server to begin receiving records
			dout.write(("OK\n").getBytes());		
			
			//Loop continues for the number of records there are
			for(int i = 0; i < noOfRecords; i++) {
				//Read the record and split it by space so it can be easily handled
				str = (String)in.readLine();
				array = str.split(" ");
				
				//Find out current number of cores and server type from record
				int currentCoresNo = Integer.parseInt(array[4]);
				String currentType = array[0];
				
				//If the mostCoresType variable is null (i.e. it's the first time
				//this loop has run) or the current number of cores is greater
				//than the previous greatest number, then the server type with the
				//most cores must be the current type.
				//The most cores is the current number of cores, and the
				//serverCount is set to one, as this is the first record of what is
				//(thus far) the largest server.
				if(mostCoresType.equals(null) || currentCoresNo > mostCores) {
					mostCoresType = currentType;
					mostCores = currentCoresNo;
					serverCount = 1;
				}
				//If the server type with the most cores is the current server type,
				//then increment the variable that keeps track of how many servers
				//of the largest type exist
				if(mostCoresType.equals(currentType)) {
					serverCount++;
				}
			}
			
			//Send OK after all the records have been received
			dout.write(("OK\n").getBytes());
			str = (String)in.readLine();
			
			//Schedule a job to the server with the most cores
			dout.write(("SCHD " + jobID + " " + mostCoresType + " " +
				   (Integer.parseInt(jobID) % serverCount) + "\n").getBytes());
			str = (String)in.readLine();
			
			//Wait for "OK" response from server to indicate that
			//scheduling has successfully completed
			while(!str.equals("OK")) {
				str = (String)in.readLine();
			}
		}

		//Send QUIT to server to smoothly terminate simulation,
		//then close the input/output streams
		dout.write(("QUIT\n").getBytes());
		dout.flush();
		dout.close();
		s.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}

