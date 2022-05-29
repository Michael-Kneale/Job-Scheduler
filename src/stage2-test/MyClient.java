import java.io.*;
import java.net.*;

class Server {
	String serverType;
	int serverID;
	int serverLimit;
	String serverLimitString;
	float serverHourlyRate;
	int serverCoresNo;
	int serverMemory;
	int serverDisk;
	
	//Used to construct the Server using the server variables obtained from reading records
	public Server(String[] array) {
		      this.serverType = array[0];
		      this.serverID = Integer.parseInt(array[1]);
		      if(!(array[2] instanceof String)) {
		      	this.serverLimit = Integer.parseInt(array[2]);
		      }
		      else {
		      	this.serverLimitString = array[2];
		      }
		      this.serverHourlyRate = Float.parseFloat(array[3]);
		      this.serverCoresNo = Integer.parseInt(array[4]);
		      this.serverMemory = Integer.parseInt(array[5]);
		      this.serverDisk = Integer.parseInt(array[6]);
	}
}
	
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
		String username = System.getProperty("user.name");
		String[] array = {"0", "0", "0", "0", "0", "0", "0", "0", "0"};
		Server server = new Server(array);
		Server firstServer = new Server(array);
		
		//Communicate with server
		dout.write(("HELO\n").getBytes());
		String str = (String)in.readLine();

		//Authenticate with server
		dout.write(("AUTH " + username + "\n").getBytes());
		str = (String)in.readLine();
		
		//Main scheduling loop continues until the break condition within the code,
		//receiving NONE from the server, is met
		for(;;) {			
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
				   
			String jobID = array[2];
			int jobMemory = Integer.parseInt(array[4]);
			str = (String)in.readLine();
			
			//Split data so number of records can be easily obtained
			array = str.split(" ");
			int noOfRecords = Integer.parseInt(array[1]);
			
			//Send OK to server to begin receiving records
			dout.write(("OK\n").getBytes());		
			
			//Keeps track of whether the first server has been created
			Boolean noFirstServer = true;
			
			
			//Loop continues for the number of records there are
			for(int i = 0; i < noOfRecords; i++) {
				
				//Read record information and split it so that information
				//is easily obtained
				str = (String)in.readLine();
				array = str.split(" ");

				//Obtain information that helps figure out which server fits
				String thing = array[2];
				
				int serverMemory = Integer.parseInt(array[4]);	
				int waitingJobs = Integer.parseInt(array[7]);
				int runningJobs = Integer.parseInt(array[8]);
				
				//firstServer is created using the first record, and
				//is only used if a server that fits is not found
				if(noFirstServer) {
					firstServer = new Server(array);
					noFirstServer = false;
				}
				
				if(jobMemory <= serverMemory && (waitingJobs == 0 ||
				   runningJobs == 0)) {
					server = new Server(array);
					break;
				}
				
				else if(i == noOfRecords - 1) {
					server = firstServer;
				}
				
			}
			
			//Send OK after all the records have been received
			dout.write(("OK\n").getBytes());
			str = (String)in.readLine();
			
			//Schedule a job
			dout.write(("SCHD " + jobID + " " + server.serverType + " " +
				   server.serverID + "\n").getBytes());
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

