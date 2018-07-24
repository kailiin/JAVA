package model;

import model.pion.*;

public class Joueur{


	private String pseudo;
	private Pion pion;
	private int bar; //Numéro de Bar.
	private int sortie; //n° sortie
	public int numero;

	public float temps = 120; 

	
	
	//Constructeur de Joueur à partir d'un pseudonyme et d'un pion.	
	public Joueur(String ps, Pion pi) {
		this.pseudo = ps;
		this.pion = pi;
		if(pion instanceof Pion_noir) {
			bar = 24;
			sortie = 26;
			numero = 1;
		}
		else if(pion instanceof Pion_blanc) {
			bar = 25;
			sortie = 27;
			numero = 0;
		}
		else {
			System.out.println("Erreur Joueur");
		}
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String p) {
		pseudo = p;
	}

	public Pion getPion() {
		return pion;
	}

	public void setPion(Pion p) {
		pion = p;
	}
	

	public int getIdBar() {
		return bar;
	}
	
	public int getIdSortie() {
		return sortie;
	}
	

}
