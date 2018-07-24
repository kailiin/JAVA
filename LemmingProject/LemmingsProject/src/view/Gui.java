package view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Game;

public class Gui {
	
	public static JFrame createGui(Game game, int scale, int level) {
		GamePanel gamePanel;	
		JFrame frame = new JFrame("Lemmings: Level "+level);
		gamePanel = new GamePanel(game, scale);
		frame.add(gamePanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	
		gamePanel.requestFocusInWindow();
		frame.setVisible(true);
		return frame;
	}

}
