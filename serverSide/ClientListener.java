package serverSide;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// The ClientListener`s job is to accept messages 
//from its user and add them to the mMsgQueue of the ServerMsgDispatcher.

public class ClientListener extends Thread {
	private Client mClient;
	private ServerMsgDispatcher mDispatcher;
	private BufferedReader mSocketReader;
	
    
	public ClientListener(Client aClient, ServerMsgDispatcher aServerMsgDispatcher)	 throws IOException {
	mClient = aClient;
	mSocketReader = new BufferedReader(new InputStreamReader(mClient.getmClientSocket().getInputStream()));
	mDispatcher = aServerMsgDispatcher;
	}
	@Override
	public void run(){
		try{
			while(!isInterrupted()){
				String msg = mSocketReader.readLine();
				if(msg==null){
					break;
				}
				mDispatcher.dispatchMSG(mClient, msg);
			}
		}catch(IOException e){
			System.out.println("Error communicating with "+ mClient.getmUserName());
		}
		mDispatcher.deleteClient(mClient);
	}
}
