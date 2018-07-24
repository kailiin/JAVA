package Reseau;

import java.net.Socket;

import view.Partie_vue;
import view.Plateau_vue;

public class Client {
  private Socket s;
  private EcouteurServer ecoute;
  private InputOutput io;
  
  
  public Client(String addr, Partie_vue vu) throws Exception{
	  s = new Socket(addr, 12000);
	  io = new InputOutput(s.getInputStream(), s.getOutputStream());
	  ecoute = new EcouteurServer(io, vu);
	  ecoute.start();
  }
  
  
  public InputOutput getIo(){
	  return io;
  }
  
}
