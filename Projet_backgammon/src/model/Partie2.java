package model;

import model.pion.Pion_blanc;
import model.pion.Pion_noir;
import model.plateau.Plateau;

public class Partie2 {
	
	public Joueur j1, j2;
	public Plateau plateau;
	public int tour;
	public int de1, de2;
	
	public Partie2(){
		this.j1 = new Joueur("tuto", new Pion_blanc()) ;
		this.j2 = new Joueur("tata", new Pion_noir());
		this.plateau = new Plateau();
		this.tour = -1;
		this.de1 = 1;
		this.de2 = 1;
	}
	

}
