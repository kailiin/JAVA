package model.block;

import model.*;

public class Block extends Observable {
	protected Coordinate coord;
	protected Game game;
	
	public Block(int x, int y, Game game){
		this.game =game;
		coord = new Coordinate(x, y);
		registerObserver(game);
	}
	
	public Block getBlock(){
		return this;
	}

	public Coordinate getBlockCoord(){
		return coord;
	}

}
