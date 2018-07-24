package view;


import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Game;

public class Gui {
	
	public static JFrame createGui(Game game, int scale) {
		return createGui(0,0,game,scale);
	}
	
	public static JFrame createGui(int x,int y,Game game, int scale) {
		GamePanel gamePanel;	
		JFrame frame = new JFrame("Snake");
		gamePanel = new GamePanel(game, scale);
		frame.add(gamePanel);
		frame.pack();
		frame.setLocation(x, y);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
		gamePanel.requestFocusInWindow();
		frame.setVisible(true);
		return frame;
	}

}
