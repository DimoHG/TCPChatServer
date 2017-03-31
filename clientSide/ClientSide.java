package clientSide;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
//ClientSide is the starting point of the client and it creates the ClientWriter and ClientReader threads
//to start the communication with the server.
public class ClientSide {
	static boolean userNameIsAvailable = false;
	public static void main(String[] args){
	Socket socket;
	try {
		
		socket = new Socket("localhost",1211);
		ClientWriter clientWriter = new ClientWriter(socket);
		ClientReader clientReader = new ClientReader(socket);
		clientWriter.start();
		clientReader.start();
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	
}
