package model.plateau;

import model.pion.*;

public class Sortie extends Fleche {

	public Sortie(Pion p){
		if(p instanceof Pion_blanc)
			id = 26;
		else if(p instanceof Pion_noir)
			id = 27;
		else System.out.println("Erreur fleche bar");
	}
	
	public Sortie(int id){
		this.id = id;
	}
	
	public Fleche clone(){
		Fleche f = new Sortie(id);
		
		for(Pion p : fleche)
			f.fleche.add(p.clone());
		
		return f;
	}
}
