package clientSide;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
//ClientWriter is the thread in which the users sends messages to the server
public class ClientWriter extends Thread{
	Socket mClientSocket;
	String output;
	private Scanner scan;
	
	
	public ClientWriter(Socket aClientSocket){
		this.mClientSocket = aClientSocket;
	}
	
	public void run(){
		try {
			PrintWriter printout = new PrintWriter(mClientSocket.getOutputStream());
			scan = new Scanner(System.in);
			while(true){
				output=scan.nextLine();
				printout.println(output);
				printout.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
