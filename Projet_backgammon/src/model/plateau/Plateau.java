package model.plateau;

import java.util.ArrayList;

import model.Joueur;
import model.pion.*;

public class Plateau {
	
	private ArrayList<Fleche> list_fleche;
	private ArrayList<Fleche> save_fleche;
	
	public Plateau(){
		list_fleche = new ArrayList<Fleche>(24);
		
		initFleches();
		initPions();
	}
	
	public void initFleches(){ // de 0 à 23
		for(int i = 0 ; i<= 23 ; i++){
			list_fleche.add(i, new Fleche(i));
		}
		
		//  bar  24,25
		list_fleche.add(new Bar(new Pion_noir()));
		list_fleche.add(new Bar(new Pion_blanc()));
		// sortie 26,27
		list_fleche.add(new Sortie(new Pion_noir()));
		list_fleche.add(new Sortie(new Pion_blanc()));
	}
	
	// on ajoute les pions pour les 2 joueurs
	public void initPions(){
		// 2 pions sur 0 et à 23 
		for(int i=0 ; i<2 ; i++){
			ajouterPion(0, new Pion_noir());
			ajouterPion(23, new Pion_blanc());
		}
		
		//5 pions sur 5,12 et 5 sur 11,18
		for(int i=0 ; i<5 ; i++){
			ajouterPion(5, new Pion_blanc());
			ajouterPion(12, new Pion_blanc());
			ajouterPion(11, new Pion_noir());
			ajouterPion(18, new Pion_noir());
		}
		
		// 3 pions sur 7 et 16 
				for(int i=0 ; i<3 ; i++){
					ajouterPion(16, new Pion_noir());
					ajouterPion(7, new Pion_blanc());
				}
		
	}
	
	//Cette méthode permet de vérifier que tous les pions d'un joueur sont dans le jan intérieur afin de commencer à sortir les pions.
	public boolean finalJanInterieur(Joueur joueur) {
		int debut, fin;
		
		if (!getBar(joueur).est_vide()) 
			return false;
		
		// On définit la plage sur laquelle il ne doit y avoir aucun pion du joueur.
		if(joueur.getPion() instanceof Pion_noir) {
			debut = 0;
			fin = 17;
		}
		else {
			debut = 6;
			fin = 23;
		}
		
		// On test par rapport à la plage définie.
		for (int i = debut; i <= fin; i++) {
			if (!estVide(i) && dernierPion(i).equals(joueur.getPion()))
				return false;
		}
		
		return true;
	}
	
	
	// ajouter un pion à une fleche
	public boolean ajouterPion(int nb, Pion p){
		return getFleche(nb).ajoutPion(p);
	}
	
	public Fleche getFleche(int nb){
		return list_fleche.get(nb);
	}
	
	public Pion supprimerPion(int f){
		return getFleche(f).supprimerPion();
	}
	
	public Pion dernierPion(int f){
		return getFleche(f).dernierPion();
	}
	
	public boolean estVide(int f){
		return getFleche(f).est_vide();
	}
	
	//obtenier le dehors selon le joueur
	public Bar getBar(Joueur j){
		return (Bar)getFleche(j.getIdBar());		
	}
	
	public Sortie getSortie(Joueur joueur) {
		return (Sortie)getFleche(joueur.getIdSortie());
	}
	
	public int nombrePion(int fleche) {
		return getFleche(fleche).nombrePion();
	}
	
	//Cette méthode permet de charger l'ensemble des flèches pour la fonction annuler.
	public void chargerUndoListe() {
		list_fleche = save_fleche;
		save_fleche = null;
	}
	
	
	
	
	/* Type de Fleche
	 * Fleche = 1
	 * Bar = 2
	 * Sortie = 3
	*/
	 public static int typeFleche(int f){
		 if(f >= 0 && f <= 23)  
			 return 1;
		 else if (f ==24 || f == 25)
			 return 2;
		 else if (f ==26 || f == 27)
			 return 3;
		 else 
			 return 0;
	 }
	
	 
	 public boolean deplacerPion(int de , int a){
		 /*if( !getFleche(de).est_vide() )*/
			 return ajouterPion(a, supprimerPion(de));
		 /*else 
			 System.out.println("Erreur deplacement");*/
		// return true;
	 }
	 
	 public int nbPion(int f){
		 return getFleche(f).getSize();
	 }
	
	//Cette méthode permet de sauvegarder l'ensemble des flèches par clonage pour la fonction annuler.
	public void sauvegarderUndoListe() {
		save_fleche = new ArrayList<Fleche>(list_fleche.size());
		
		for(Fleche f : list_fleche)
			save_fleche.add(f.clone());
		
	}
	
	public String getColor(int fleche, int pion ){
		return list_fleche.get(fleche).fleche.get(pion).getCouleur();
		
	}
	}
	

