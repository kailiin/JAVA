package model;

import java.util.ArrayList;

public class Resultat_lancer {
	private Des des = new Des();
	private ArrayList<Integer> liste_coup = new ArrayList<Integer>();
	private boolean []tab_utilisation = {false,false,false,false};
	private boolean est_doublet = false;

	public void remetAZero(){
		liste_coup.clear();
		est_doublet = false;
	}

	public void resetResultat(){
		for(int i =0;i<4;i++){
			tab_utilisation[i] = false ;
		}
	}

	public Integer getDesVal(int i){
		return liste_coup.get(i);
	}

	public boolean estDouble(){
		return est_doublet;
	}

	public void lancerLesDes(){
		remetAZero();
		resetResultat();

		liste_coup.add(des.lancerDes());
		liste_coup.add(des.lancerDes());

		if ( getDesVal(0).equals( getDesVal(1) ) ){
			liste_coup.add(getDesVal(0) );
			liste_coup.add(getDesVal(0) ) ;
			est_doublet = true;
		}
	}

	public Integer getCoupNonUtiliser(int nb){
		int cpt = 0 ;
		for(int i = 0 ; i<nombreCoupTotal() ; i++ ){
			if(cpt == nb)
				return liste_coup.get(i);
			else
				cpt++;
		}
		return null;
	}

	public int nombreCoupTotal(){  //2 ou 4
		return liste_coup.size();
	}

	public boolean debutTour() {
		return (nombreCoupLibre() == nombreCoups());
	}
	
	public int nombreCoups() {
		return liste_coup.size();
	}

	public int nombreCoupLibre(){
		int cpt = 0;
		for(int i=0 ; i<nombreCoupTotal() ;i++){
			if( tab_utilisation[i] == false)
				cpt++;
		}
		return cpt;
	}

	public boolean supprimerCoup(Integer i){
		if( !est_doublet){
			if(liste_coup.indexOf(i) != -1 && tab_utilisation[liste_coup.indexOf(i)] == false ) {
				tab_utilisation[liste_coup.indexOf(i)] = true;
				return true;
			} 
		}else{
			for(int j=0 ; j<4 ;j++){
				if(tab_utilisation[j] == false ){
					tab_utilisation[j]= true;
					return true;
				}
			}
		}
		return false;
	}



}
