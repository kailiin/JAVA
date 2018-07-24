package app;

import model.StartGame;

public class App {

	private static final int SCALE = 30;
	private static final int SPEED = 150;
	private static final int WIDTH = 30;
	private static final int HEIGTH = 13;

	public static void main(String[] args)throws Exception {
		
		StartGame game = new StartGame(SCALE, SPEED, WIDTH, HEIGTH);
		game.start();
		
	}
}
