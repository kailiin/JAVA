package model.plateau;

import model.pion.*;

public class Bar extends Fleche{

	public int counter = 0;

	public Bar(Pion p){
		if(p instanceof Pion_blanc)
			id = 24;
		else if(p instanceof Pion_noir)
			id = 25;
		else System.out.println("Erreur fleche bar");
	}
	
	public Bar(int id){
		this.id=id;
	}
	
	public boolean ajoutPion(Pion p){
		counter++;
		return super.ajoutPion(p);
	}
	
	public Fleche clone(){
		Fleche f = new Bar(id);
		
		for(Pion p : fleche)
			f.fleche.add(p.clone());
		
		return f;
	}
}
