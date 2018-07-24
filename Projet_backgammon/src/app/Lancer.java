package app;

import java.io.IOException;

import Reseau.Server;
import view.Fenetre;

public class Lancer 
{
 public static void main(String args[] ) throws IOException{
	 new Thread() { public void run (){
		 try {
			Server.main(args);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }}.start();
	 
	 new Thread() { public void run (){
		 Fenetre.main(args);
	 }}.start();
	 
	 new Thread() { public void run (){
		 Fenetre.main(args);
	 }}.start();
	 
 }
}
