package Reseau;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Server extends Thread {
	private int port;
	ServerSocket ss;
	private boolean stop = false;

 public Server(int port) throws IOException {
	this.port = port;
	this.start();
}
 
 public void run() {
	 try (ServerSocket ss = new ServerSocket(port)) { 
		 System.out.println("server started");
		 while (!stop) {
			 try {
				 
				 Socket s1 = ss.accept();
				 Socket s2 = ss.accept();
				 HandlePartie hp = new HandlePartie();
				 hp.SetClient(new HandleClient(s1),new HandleClient(s2) );
				 
				 
				 
			 } catch (SocketTimeoutException ex) { } }
	}catch (Exception e) {
		System.out.println("Could not bind port " + port);
		 Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e); }
 }
 
 public synchronized void finish(){ 
	 stop = true; 
 }
	public static void main(String[] args) throws IOException{
		new Server(12000);	
	}

}
