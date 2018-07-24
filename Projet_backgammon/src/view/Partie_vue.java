package view;

import java.awt.Color;
import java.awt.Graphics;
import Reseau.Client;
import model.Partie2;

public class Partie_vue extends Plateau_vue{
	private Partie2 partie;
	public String temps ="";
	
	public Partie_vue(Partie2 partie){
		super(partie.plateau);
		this.partie = partie;
		try {
			this.client = new Client("localhost", this);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	
	
	public Partie2 getPartie() {
		return partie;
	}


	public void setPartie(Partie2 partie) {
		this.partie = partie;
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawString("Des: "+ partie.de1+" et "+ partie.de2, 0, hauteur_fleche*2 + 40);
		g.drawString(temps, 0, hauteur_fleche*2 + 60);
		
	}
	
	

}
