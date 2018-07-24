package model;

import java.util.Random;

public class Des {
	private static Random des_random = new Random();

	public static int lancerDes(){
		// random de 0 à 6
		return des_random.nextInt(6) +1;
	}
}
