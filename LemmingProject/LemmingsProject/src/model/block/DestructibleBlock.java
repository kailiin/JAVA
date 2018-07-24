package model.block;

import java.util.ArrayList;
import java.util.List;

import model.Coordinate;
import model.Game;

public class DestructibleBlock extends Block {

	public DestructibleBlock(int x, int y, Game game){	
		super(x,y,game);
	}
	
	public List<Coordinate> getAround(){	//get around  2 case
		List<Coordinate> around =new ArrayList<Coordinate>();
		for(int i=-3;i<2;i++){
			for(int j=-2 ;j<3 ;j++){
				if(coord.getX()+i >0 && coord.getY()+j>0)
				around.add( new Coordinate(coord.getX()+i, coord.getY()+j) );
			}
		}
		return around;
	}
}

