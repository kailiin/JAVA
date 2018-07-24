package model.pion;

public abstract class Pion {
	protected String couleur;

	public String getCouleur(){
		return couleur;
	}
	
	public boolean equals(Pion pion){
		return(this.couleur.equals(pion.getCouleur()) );
	}
	
	
	public Pion clone(){
		return null;
	}
}
