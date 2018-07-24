package model.plateau;

public class Regles {
	private Plateau plateau;
	public void utiliser_des(int fleche, int des, boolean direction){
		int fleche_dest;
		if(direction)
			fleche_dest = fleche + des;
		else 
			fleche_dest = fleche - des;
		
		plateau.deplacerPion(fleche, fleche_dest);	
	}
	
	public Regles(Plateau plateau){
		this.plateau= plateau; 
	}

}
