package model;

import model.block.Block;
import model.block.EndBlock;
import model.block.StartBlock;
import model.block.VoidBlock;

public class Coordinate {
	private int y;
	private int x;
	

	public Coordinate(int x, int y) {
		this.y = y;
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
	
	public void setCoordinate(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	//Start Coordinate
	public static Coordinate getStartCoordinate(Game game) throws Exception {
		for(Block b : game.getBlockList()){
			
			if(b.getClass().equals(StartBlock.class)){
				
				//Down
				if(b.getBlockCoord().getY() < game.getHeight()-1)
					if (game.getBlockList().get( (b.getBlockCoord().getY()+1) *game.getWidth() +b.getBlockCoord().getX() +b.getBlockCoord().getY()+1).getClass().equals(VoidBlock.class) )
						return game.getBlockList().get( (b.getBlockCoord().getY()+1) *game.getWidth() +b.getBlockCoord().getX() +b.getBlockCoord().getY()+1).getBlockCoord();
				
				//Right
				if (b.getBlockCoord().getX()< game.getWidth()-1)
					if (game.getBlockList().get( b.getBlockCoord().getY() *game.getWidth() +b.getBlockCoord().getX()+1 +b.getBlockCoord().getY()).getClass().equals(VoidBlock.class) )
						return game.getBlockList().get( b.getBlockCoord().getY() *game.getWidth() +b.getBlockCoord().getX()+1 +b.getBlockCoord().getY()).getBlockCoord();
				
				//Left
				if(b.getBlockCoord().getX()>0)
					if (game.getBlockList().get( b.getBlockCoord().getY() *game.getWidth() +b.getBlockCoord().getX()-1 +b.getBlockCoord().getY()).getClass().equals(VoidBlock.class) )
						return game.getBlockList().get( b.getBlockCoord().getY() *game.getWidth() +b.getBlockCoord().getX()-1 +b.getBlockCoord().getY()).getBlockCoord();
				
				//Up
				if(b.getBlockCoord().getY() >0 )
					if (game.getBlockList().get( (b.getBlockCoord().getY()-1) *game.getWidth() +b.getBlockCoord().getX() +b.getBlockCoord().getY()-1).getClass().equals(VoidBlock.class) )
						return game.getBlockList().get( (b.getBlockCoord().getY()-1) *game.getWidth() +b.getBlockCoord().getX() +b.getBlockCoord().getY()-1).getBlockCoord();
	
			}
		}
		throw new Exception("Lemming spawn Coordinate error");
	}

	//End Coordinate
	public static Coordinate getEndCoordinate(Game game){
		for(Block b : game.getBlockList()){
			if(b.getClass().equals(EndBlock.class))
				return b.getBlockCoord();
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
