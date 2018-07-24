package Reseau;

import java.util.Map;
import java.util.TreeMap;

import model.Des;
import model.Joueur;
import model.Partie2;
import model.plateau.Plateau;

public class HandlePartie {
	private Partie2 partie;
	private HandleClient client1, client2;
	
	public HandlePartie(){
		this.partie = new Partie2();
	}
	
	public void SetClient(HandleClient client1, HandleClient client2){
		
		this.client1 = client1;
		this.client2 = client2;
		
		Map<String, String> map = new TreeMap<>();
		
		map.put("type", "tu es");
		map.put("numero", "1");
		//envoi map au clients
		this.client1.getIo().write(map);
		map.put("numero", "2");
		this.client2.getIo().write(map);
		
		commencer_partie();
		
	}
	
	
	
	
	public void commencer_partie(){
	
		this.client1.setJoueur(partie.j1);
		this.client2.setJoueur(partie.j2);
		this.client1.setPartie(partie);
		this.client2.setPartie(partie);
		this.client1.setHp(this);
		this.client2.setHp(this);
	
		set_tour(0);
		client1.start();
		client2.start();
		
		new Timer(this).start();//on lance le timer
	}
	
	public void envoyer_a_tous(Map<String,String> map){
		this.client1.getIo().write(map);
		this.client2.getIo().write(map);
		
	}
	
	public void envoyer_a_un(Map<String, String> map, Joueur j) {
		if ( j == partie.j1 )
			this.client1.getIo().write(map);
		else
			this.client2.getIo().write(map);
	}
	
	public void set_tour(int tour){
		partie.tour = tour;
		Map<String, String> map = new TreeMap<>();
		map.put("type", "tour");
		map.put("le tour de", Integer.toString(partie.tour));
		
		envoyer_a_tous(map);
		lancer_des();
	}

	
	public void lancer_des(){
		partie.de1 = Des.lancerDes();
		partie.de2 = Des.lancerDes();
		Map<String, String> map = new TreeMap<>();
		
		map.put("type", "des");
		map.put("de1", Integer.toString(partie.de1));
		map.put("de2", Integer.toString(partie.de2));
		
		envoyer_a_tous(map);
		
	}
	
	public boolean update(float seconde){
		Joueur j;
		if(partie.tour == 0)
			j = partie.j1;
		else 
			j = partie.j2;
		j.temps -= seconde;
		Map<String,String> mapp = new TreeMap<>();
		mapp.put("type", "temps");
		mapp.put("temps", Float.toString(j.temps));
		envoyer_a_un(mapp,j);
		
		if(j.temps > 0)
			return true;	
		
		finir_la_partie(j);
		return false;
		
	}
	
	public void finir_la_partie(Joueur perdant)
	{
		Map<String,String> map = new TreeMap<>();
		map.put("type", "defaite");
		map.put("joueur", Integer.toString(partie.tour+1));
		envoyer_a_tous(map);
		
		client1.getIo().close();
		client2.getIo().close();
		
	}
}
