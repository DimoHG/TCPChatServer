package serverSide;

import java.net.Socket;
// This is the POJO, used to contain the whole client data in one place.
public class Client {
	private Socket mClientSocket;
	private ClientListener mClientListener;
	private String mUserName;
	
	public Client(Socket aClientSocket){
	mClientSocket = aClientSocket;
	}

	public Socket getmClientSocket() {
		return mClientSocket;
	}

	public void setmClientSocket(Socket mClientSocket) {
		this.mClientSocket = mClientSocket;
	}

	public ClientListener getmClientListener() {
		return mClientListener;
	}

	public void setmClientListener(ClientListener mClientListener) {
		this.mClientListener = mClientListener;
	}

	public String getmUserName() {
		return mUserName;
	}

	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	
	
}
