package model.pion;

public class Pion_blanc extends Pion {

	public Pion_blanc(){
		couleur = "Blanc";
	}
	
	public Pion clone(){
		return new Pion_blanc();
	}
}
