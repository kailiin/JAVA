package model.block;

import model.Coordinate;
import model.Game;

public class Teleporter extends IndestructibleBlock{
	private Coordinate coord2;
	
	public Teleporter(int x, int y, Game game ,Coordinate c2) {
		super(x, y, game);
		coord2=c2;
	}

	public void setCoord1(int x, int y ){
		coord = new Coordinate(x, y);
	}

	public void setCoord2(int x, int y ){
		coord2 = new Coordinate(x, y);
	}


	public Coordinate getcoord2(){
		return coord2;
	}
	
}
