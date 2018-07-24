package model;

import java.util.ArrayList;
import java.util.List;

public class Snake extends Observable {

	private final int INITSIZE = 30;

	private List<Coordinate> body = new ArrayList<>();
	private Game game;
	private Direction direction;
	private boolean alive = true;

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Snake(Game game) {
		this.game = game;
		registerObserver(game);
		direction = Direction.Right;
		Coordinate start = Coordinate.getRandomCoordinate(game);
		for (int i = 0; i < INITSIZE; i++) {
			Coordinate c = new Coordinate(start.getX() + i, start.getY());
			body.add(c);
			addChange(new Change(c, Change.ChangeType.SNAKE));
		}
	}

	public void move() {
		Coordinate last = body.get(body.size() - 1);
		Coordinate next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
		//Trou
		
		if(game.isOut(next) || (body.contains(next))){
			alive = false;
			System.out.println("You Lost");
			return;
		}
		
		//Apple   augmente la taille
		Coordinate ac = game.getApple().getAppleCoord();
		if(body.contains(ac)){
			game.getApple().newApple(game);	
			for(int i=0 ;i<5;i++){
				body.add(next);
				addChange(new Change(next,Change.ChangeType.SNAKE));
				last = body.get(body.size() - 1);
				next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
				this.notifyObserver();
			}
		}
		
		
		//sup
		Coordinate s = body.get(0);
		body.remove(0);
		addChange(new Change(s,Change.ChangeType.VOID));
	
		//add la tete
		body.add(next);
		addChange(new Change(next,Change.ChangeType.SNAKE));
		
		this.notifyObserver();
		
	}

	public boolean isAlive() {
		return alive;
	}

}
