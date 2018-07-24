package Reseau;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

import model.Joueur;
import model.Partie2;
import model.plateau.Fleche;

public class HandleClient extends Thread {
	
	private InputOutput io;
	private Partie2 partie;
	private Joueur joueur;
	private HandlePartie hp;
	
	
	public HandleClient(Socket socket) throws Exception {
		this.io = new InputOutput(socket.getInputStream(), socket.getOutputStream()); 
		/*while(true) 
			io.write(io.read());*/
	}
	
	public InputOutput getIo() {
		return this.io;
	}
	
	public void setPartie(Partie2 partie) {
		this.partie = partie;
	}
	
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	
	public void setHp(HandlePartie hp) {
		this.hp = hp;
	}
	
	public void run()
	{
		try {
			
		while(true)
		{
			Map<String,String> map = io.read();
				
			synchronized(hp){
			faire_le_traitement(map);
			}
			
		}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//io.close();//fermeture de socket
			System.out.println("server :client terminé");
			return;
		}
		
	}
	
	public void faire_le_traitement(Map<String, String> map) throws Exception {
		
			
			if(partie.tour != joueur.numero) {
				System.out.println("ce n'est pas ton tour");
				return;
			}
			if(Integer.parseInt(map.get("de") ) < 0 || Integer.parseInt(map.get("de") ) > 23 
					||Integer.parseInt(map.get("aa") ) < 0 || Integer.parseInt(map.get("aa")) >23 ){
				
				System.out.println("vous n'etes pas dans le plateau");
				return;
				
			}
			
			Fleche de = partie.plateau.getFleche(Integer.parseInt(map.get("de")));
			Fleche aa = partie.plateau.getFleche(Integer.parseInt(map.get("aa")));
			if(de.est_vide()){
				System.out.println("la liste est vide");
				return;
			}
			
			if(!de.dernierPion().getCouleur().equals(joueur.getPion().getCouleur())){
				System.out.println("ce n'est pas ton pion");
				return;
			}
			
			if(!aa.est_vide() && aa.dernierPion().getCouleur().equals(de.dernierPion().getCouleur()) ){
				System.out.println("deplacement imposible: trop de pions adverse");
				return;
			}
			
			int distance = Integer.parseInt(map.get("aa")) - Integer.parseInt(map.get("de"));
			if ( partie.tour == 1)
				distance = - distance;
			
			if(distance != partie.de1 && distance != partie.de2 && distance!= partie.de1 + partie.de2){
				System.out.println("deplacement non autorisé: de1 ="+partie.de1+", de2 ="+partie.de2+" et d="+distance);
				return;
				
			}
			
			
					if(partie.de1 == distance) partie.de1 = 0;
			else 	if(partie.de2 == distance) partie.de2 = 0;
			else   							   partie.de1 = partie.de2 =0;
					
					
			
			
					
			Map<String,String> mapp = new TreeMap<>();
			mapp.put("type", "bouger_pion");
			mapp.put("de", map.get("de"));
			mapp.put("aa", map.get("aa"));
			
			partie.plateau.deplacerPion(Integer.parseInt(map.get("de")),Integer.parseInt(map.get("aa")));
			hp.envoyer_a_tous(mapp);
			
			if(partie.de1 ==0 && partie.de2 == 0 ){
				
			
			if(partie.tour == 0) 
				hp.set_tour(1);
			else 
				hp.set_tour(0);
			}
			
	}
	

}
