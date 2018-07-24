package Reseau;

import java.net.Socket;
import java.util.Map;

import view.Partie_vue;
import view.Plateau_vue;

public class EcouteurServer extends Thread {
	private InputOutput io;
	private Partie_vue vu;
	private int moi;
	
	
	public EcouteurServer(InputOutput io, Partie_vue vu) {
		this.io = io;
		this.vu = vu;
		
	}
	
	public void run() {
		try {
			while(true) {
			Map<String,String> map = io.read();
			switch(map.get("type")){	
			case "bouger_pion":
				int de = Integer.parseInt(map.get("de"));
				int aa = Integer.parseInt(map.get("aa"));
				vu.getPlateau().deplacerPion(de, aa);
				vu.repaint();
				break;
			case "tu es":
				moi = Integer.parseInt(map.get("numero"));
				break;
			case "des":
				vu.getPartie().de1= Integer.parseInt(map.get("de1"));
				vu.getPartie().de2= Integer.parseInt(map.get("de2"));
				vu.repaint();//l'affichge se met à jour
				break;
			case "tour":
				break;
			case "defaite":
				if(moi == Integer.parseInt(map.get("joueur")))
					vu.temps = "vous avez perdu";
				else
					vu.temps = "vous avez gagné";
				
				vu.repaint();
				return;
				
			case "temps":
				vu.temps = map.get("temps");
				vu.repaint();
				
				break;
			/*case "mettre_bar":
				int aa2 = Integer.parseInt(map.get("aa"));
				moi = Integer.parseInt(map.get("numero"))-1;
				vu.getPlateau().getBar(moi);
				break;*/
			default : System.out.println("type inconnu: "+ map.get("type"));	
				
				
			}
			}
			}catch (Exception e) {
			 e.printStackTrace();
				System.out.println("erreur");
				
		 }
		
	}

}
