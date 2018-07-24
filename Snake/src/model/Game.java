package model;

import java.util.List;

import controller.GameObjectObserver;

public class Game extends Observable implements GameObjectObserver {

	private int sleep;
	private int height;
	private int width;
	private Snake snake;
	private Apple apple;
	

	public Game(int sleep, int width, int height) {
	    //Trou
		this.sleep=sleep;
		this.width=width;
		this.height=height;			
		this.snake=new Snake(this);
		this.apple =new Apple(this);
	}

	// ajout pour gamepanel
	public Snake getSnake(){
		return snake;
	}
	
	public Apple getApple(){
		return apple;
	}
	
	@Override
	public void update(List<? extends Change> changes) {
	    //Trou
		for(Change c : changes){
			addChange(c);
		}
		notifyObserver();
		
	}
	
	public void changeSnakeDirection(Direction direction) {
		snake.setDirection(direction);
	}
	
	public boolean isOut(Coordinate c) {
		if (c.getX() < 0 || c.getY() < 0)
			return true;
		if (c.getX() >= width || c.getY() >= height)
			return true;
		return false;
	}
	
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}

	public void step() {
	    //Trou
		snake.move();
		apple.affiche();
		this.notifyObserver();
	    try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void run() {
	    //Trou
		while(snake.isAlive()){
			step();
		}
	}

	
}
