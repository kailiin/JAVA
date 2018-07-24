package view;

import javax.swing.JFrame;
import model.Partie2;
import model.plateau.Plateau;

public class Fenetre extends JFrame{
	
	public static void main(String args[]){
		Fenetre f= new Fenetre();
		f.add(new Partie_vue(new Partie2()));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(Plateau_vue.taille_piece*12+100, Plateau_vue.hauteur_fleche*2+100);
		f.setVisible(true);
		
	}

}
