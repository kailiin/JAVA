package model;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.Gui;

public class StartGame {
	
	private Game game;
	private int scale;
	private int speed;
	private int width;
	private int heigth;
	private int level;
	private boolean finish;
	private JFrame frame;
	
	public StartGame(int SCALE, int SPEED, int WIDTH, int HEIGTH)throws Exception{
		finish = false;
		scale = SCALE;
		speed = SPEED;
		width = WIDTH;
		heigth = HEIGTH;
		level = 0;
		JOptionPane.showMessageDialog(frame,"Please read the HowToPlay file before you start");
		String msg = JOptionPane.showInputDialog(null, "Please enter the level number you want to play ( 1 to "+levelsNumber()+" )", "LEVEL", JOptionPane.QUESTION_MESSAGE);
		File f = new File("Level/"+msg+".txt");
		while(!f.exists()){
			if(msg == null || msg.trim().isEmpty()){
				if(msg == null) return;
				else{
					// Level 1 by default
					level = 1;
					game = new Game(speed,width,heigth,"Level/"+Integer.toString(level)+".txt");
					frame = Gui.createGui(game, scale,level);
					return;
				}
			}else{			
				JOptionPane.showMessageDialog(null,"Please inser a number for an existing level!");
				msg = JOptionPane.showInputDialog(null, "Please enter the level number you want to play", "LEVEL", JOptionPane.QUESTION_MESSAGE);
				f = new File(msg+".txt");
			}
		}
		level = Integer.parseInt(msg);
		game = new Game(speed,width,heigth,"Level/"+Integer.toString(level)+".txt");
		frame = Gui.createGui(game, scale, level);
		// test if we have selected the last level
		if(levelsNumber() == level) finish = true;
	}
	 
	public void changeGame()throws Exception{
	  	level++;
	  	File f = new File("Level/"+Integer.toString(level)+".txt");
	  	if(!f.exists()) { 
	  		finish = true;
	  	    return;
	  	}
	  	frame.dispose();
	  	System.out.println("Level "+level);
		game =  new Game(speed,width,heigth,"Level/"+String.valueOf(level)+".txt");
		frame = Gui.createGui(game, scale, level);
		game.run();
	}
	
	public int levelsNumber(){
		int n = 1;
		File file = new File("Level/"+Integer.toString(n)+".txt");
		while(file.exists()){
		  	n++;
		  	file = new File("Level/"+Integer.toString(n)+".txt");
		}
		return n-1;
	}
	
	public void start()throws Exception{
		if(level == 0){
			JOptionPane.showMessageDialog(null,"You have left the game!");
			return;
		}else{
			System.out.println("Level "+level);
			game.run();
			while(game.run()) {
				changeGame();
				if(finish) break;
			}
			if (!game.run()) finish = false;
			// You win only if you succeed the last level
			if(finish) JOptionPane.showMessageDialog(null,"YOU WIN :D !!!");
			else JOptionPane.showMessageDialog(null,"YOU LOSE :( !!!");
		}
	}
}
