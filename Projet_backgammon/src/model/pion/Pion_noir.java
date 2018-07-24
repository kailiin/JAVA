package model.pion;

public class Pion_noir extends Pion {

	public Pion_noir(){
		couleur = "Noir";
	}
	
	public Pion clone(){
		return new Pion_noir();
	}
}
