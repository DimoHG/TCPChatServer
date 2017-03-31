package serverSide;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
/* The ServerMsgDispatcher`s responsibilities are to dispatch messages 
 * from its mMsgQueue to all the users in the mClientsMap.
 */
public class ServerMsgDispatcher extends Thread {
	private ConcurrentLinkedQueue<String> mMsgQueue = new ConcurrentLinkedQueue<String>();
	static ConcurrentHashMap<String,Client> mClientsMap = new ConcurrentHashMap<String, Client>();
	private Object msgQueueLock = new Object();

// addClient method adds new clients to the table of clients
	public void addClient(Client aClient){
		mClientsMap.put(aClient.getmUserName(), aClient);
	}
//deleteClient method deletes clients if they are in the table of clients
	public void deleteClient(Client aClient){
		String userName = aClient.getmUserName();
		if(mClientsMap.containsKey(userName)){
			mClientsMap.remove(userName);
			try {
				aClient.getmClientSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
//dispatchMSG method takes a message and adds the name of the client in the beggining 
	public void dispatchMSG(Client aClient, String aMsg){
		synchronized(msgQueueLock){
			aMsg = aClient.getmUserName()+ " :" + aMsg;
			mMsgQueue.offer(aMsg);
			msgQueueLock.notify(); 
		}
	}
//getMsgFromQueue returns the first message of the queue 
	private String getMsgFromQueue() throws InterruptedException{
		String msg = null;
		synchronized(msgQueueLock){
			while(mMsgQueue.size()==0){	
				msgQueueLock.wait();
			}
		}
		msg = mMsgQueue.poll();
		return msg;
	}

//sendMsgToAllClients takes message as an argument and sends it to all clients one by one via for loop
	private void sendMsgToAllClients(String aMsg){
		Client client = null;
		for(Map.Entry<String, Client> entry : mClientsMap.entrySet()){
			try {
				client = entry.getValue();
				PrintWriter printout = new PrintWriter(client.getmClientSocket().getOutputStream());
				printout.println(aMsg);
				printout.flush();
			} catch (IOException e) {
				deleteClient(client);
			}
		}
	}


	public void run(){
		try {
			while(true){
				String msg = getMsgFromQueue();
				sendMsgToAllClients(msg);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
