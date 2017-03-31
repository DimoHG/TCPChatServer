package serverSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
// This is the thread which is handed to every new client of the server. It`s idea is to set an unique name 
// to the new user, start a ClientListener and add the new client in the ServerMsgDispatcher mClientsMap.
 

public class NewUserThread extends Thread {
	private Client mClient;
	private BufferedReader buffReader;
	private PrintWriter printout;
	private boolean isNameAvailable = false;




	public NewUserThread(Client aClient) throws IOException{
		mClient = aClient;
		buffReader = new BufferedReader(new InputStreamReader(mClient.getmClientSocket().getInputStream()));
		printout = new PrintWriter (new OutputStreamWriter(mClient.getmClientSocket().getOutputStream()));
	}

	public void run(){
		try {
			while(!isNameAvailable){
				printout.println("Enter your username");
				printout.flush();
				String name = buffReader.readLine();
				if(!ServerMsgDispatcher.mClientsMap.containsKey(name)){
					mClient.setmUserName(name);
					ClientListener clientListener = new ClientListener(mClient, ChatServer.mDispatcher);
					ChatServer.mDispatcher.addClient(mClient);
					printout.println(name + " u have been connected to the chat server");
					printout.flush();
					clientListener.start();
					isNameAvailable = true;
				}else{
					printout.println("That user name is already used");
					printout.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
