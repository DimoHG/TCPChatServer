package clientSide;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
// This thread reads the input from the server and prints it to the user`s console.
public class ClientReader extends Thread {
	private Socket mClientSocket;
	String input;

	public ClientReader(Socket aClientSocket){
		this.mClientSocket=aClientSocket;
	}

	public void run(){
		try {
			BufferedReader buffReader = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
			while(!isInterrupted()){
				input = buffReader.readLine();
				System.out.println(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
