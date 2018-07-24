package view;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import Reseau.Client;
import model.plateau.Plateau;

public class Plateau_vue extends JPanel{
	
	private Plateau plateau;
	public static int taille_piece = 30;
	public static int hauteur_fleche= 200;
	public Client client;
	
	public Plateau_vue(Plateau plateau){
		this.plateau = plateau;
		//Mouselisner est une interface. mouseadapter implémente l'interface avec des méthodes vides
		addMouseListener(new MouseAdapter() {
		
			int de = -1;
			int aa = 1;
			
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int x = e.getX()/Plateau_vue.taille_piece;
				int y = e.getY()/Plateau_vue.hauteur_fleche;
				int index = x+(y*12);
				if (de == -1) de = index;
				else {
					Map<String,String> map = new TreeMap<String, String>();//servira à l'envoyer au server
					map.put("type", "deplacement");
					map.put("de",Integer.toString(traduire_int(de)));
					map.put("aa", Integer.toString(traduire_int(index)));
					de = -1;
					client.getIo().write(map);
					repaint();//refraiche la vue
				}
				System.out.println(x+" "+y);
				
			}
			
			
			
		});
	}
	
	
	public Plateau getPlateau(){
		return this.plateau;
	}
	
	
	//paindre le composant (tt ce qui s'affiche)
	public void paintComponent(Graphics g){		
		g.setColor(Color.WHITE);
		g.fillRect(0,0, getWidth(), getHeight());//fill sert à remplir tout plein comme dans paint
		g.setColor(Color.BLACK);
		peindre_fleche(g);
		peindre_pion(g);
		//peindre_bar(g);
	}
	
	public void peindre_fleche(Graphics g){
		for(int i=0; i< 12; i++){
			g.fillPolygon(new int[]{0, taille_piece/2, taille_piece},new int[]{0, hauteur_fleche, 0},3);
			if(i%2==0) g.setColor(Color.RED); else g.setColor(Color.BLACK);
			g.fillPolygon(new int[]{0, taille_piece/2, taille_piece},new int[]{hauteur_fleche*2, hauteur_fleche, hauteur_fleche*2 },3);
			g.translate(taille_piece, 0);
			
		}
		g.translate(-12*taille_piece, 0);
	}
	
	public void peindre_pion(Graphics g){
		for(int i=0; i< 12; i++){
			int t = traduire_int(i);
			int nb_pieces_sur_la_fleche = plateau.nbPion(t);
			for(int j=0; j< nb_pieces_sur_la_fleche; j++){
			    if(plateau.getColor(t,j).equals("Noir")) 
			       g.setColor(Color.GRAY);else g.setColor(Color.WHITE);    
			g.fillOval(i*taille_piece, j*taille_piece, taille_piece, taille_piece);
			}
			
				/*{
			g.fillOval(0, 0, taille_piece, taille_piece);
			g.translate(0, taille_piece);
			}
			g.translate()
			g.translate(taille_piece, 0);
			*/
			
		}
		g.translate(0, -taille_piece);
		for( int i=12; i<24; i++){
			int t = traduire_int(i);
			int nb_pieces_sur_la_fleche = plateau.nbPion(t);
			for(int j=0; j< nb_pieces_sur_la_fleche; j++){
				 if(plateau.getColor(t,j).equals("Noir")) 
				     g.setColor(Color.GRAY);else g.setColor(Color.WHITE);   	
			g.fillOval((i-12)*taille_piece,-(j*taille_piece)+hauteur_fleche*2, taille_piece, taille_piece);
			}
		}
		}
	
	/*public void peindre_bar(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(taille_piece*12, 0, taille_piece, hauteur_fleche);
		g.setColor(Color.GREEN);
		g.fillRect(taille_piece*12, hauteur_fleche+10, taille_piece, hauteur_fleche);
	}*/

	
	public int traduire_int(int v){
		if (v < 12)
			return v+12;
		else
			return 23-v;
	}

	}
	
	



