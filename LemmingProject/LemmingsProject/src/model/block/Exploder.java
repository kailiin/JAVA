package model.block;


import java.util.ArrayList;
import java.util.List;

import model.Coordinate;
import model.Game;

public class Exploder extends DestructibleBlock  {
	
	public Exploder(int x, int y, Game game) {
		super(x, y, game);
	}
	
	@Override
	public List<Coordinate> getAround(){	//get around  1 case
		List<Coordinate> around =new ArrayList<Coordinate>();
		for(int i=-1;i<2;i++){
			for(int j=-1 ;j<2 ;j++){
				around.add( new Coordinate(coord.getX()+i, coord.getY()+j) );
			}
		}
		return around;
	}

}
