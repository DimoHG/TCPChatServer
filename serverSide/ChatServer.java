package serverSide;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* Thats the entry point of the server. It opens a server socket, starts ServerMsgDispatcher thread 
 * and infinitely accepts client connections and creates NewUserThread thread to handle them.
 */
public class ChatServer {
	public static final String KEEP_ALIVE_MESSAGE = "keep-alive";
	private static ServerSocket mServerSocket;
	public static ServerMsgDispatcher mDispatcher;
	
	
	public static void main(String... args){
		bindServerSocket();
		mDispatcher = new ServerMsgDispatcher();
		mDispatcher.start();
		handleConnections();
	}
	
	private static void bindServerSocket(){
		try {
			mServerSocket = new ServerSocket(1211);
		} catch (IOException e) {
			System.out.println("Can not start listening on the current port");
			e.printStackTrace();
		}
	}
	
	// handleConnections method accepts new clients and submits their NewUserThread to the executor
	private static void handleConnections(){
		ExecutorService executor = Executors.newCachedThreadPool();	
			try {
				while(true){
				Socket clientSocket = mServerSocket.accept();
				Client client = new Client(clientSocket);
				executor.submit(new NewUserThread(client));
				}
			} catch (IOException e) {
				e.printStackTrace();
				
			}
	}
	
	
	
}
