package model.plateau;

import java.util.ArrayList;

import model.pion.*;

public class Fleche {

	protected int id;
	protected ArrayList<Pion> fleche;
	
	public Fleche(){
		this(-1);
	}
	public Fleche(int id){
		this.id = id;
		fleche = new ArrayList<Pion>();
	}
	
	public boolean est_vide(){
		return fleche.isEmpty();
	}
	
	public boolean ajoutPion(Pion p){
		fleche.add(p);
		/*if(est_vide() || dernierPion().equals(p)){
			fleche.add(p);
			return true;
		}*/
		return false;
	}
	
	public Pion supprimerPion(){
		if(est_vide()) return null;
		return fleche.remove(fleche.size()-1);
	}
	
	public int nombrePion() {
		return fleche.size();
	}
	
	public Pion dernierPion(){
		if(est_vide()) return null;
		return fleche.get(fleche.size()-1);
	}
	
	public int getSize(){
		return fleche.size();
	}
	
	public int getId(){
		return id;
	}
	
	public ArrayList<Pion> getList(){
		return fleche;
	}
	
	public Fleche clone(){
		Fleche f = new Fleche(id);
		
		for(Pion p : fleche)
			f.fleche.add(p.clone());
		
		return f;
	}
	
}
