package model;


import java.util.ArrayList;
import java.util.List;

public class Apple extends Observable {	
	private Coordinate appleCoord;

	
	public Apple(Game game) {
		
		registerObserver(game);
		appleCoord = Coordinate.getRandomCoordinateApple(game);
		Coordinate c = new Coordinate(appleCoord.getX() , appleCoord.getY());
		addChange(new Change(c, Change.ChangeType.APPLE));
	}

	
	public void affiche(){
		this.notifyObserver();
	}
	
	public Coordinate getAppleCoord(){
		return appleCoord;
	}
	
	public void newApple(Game game){
		appleCoord = Coordinate.getRandomCoordinateApple(game);
		Coordinate c = new Coordinate(appleCoord.getX() , appleCoord.getY());
		addChange(new Change(c, Change.ChangeType.APPLE));
		this.notifyObserver();
	}


}

