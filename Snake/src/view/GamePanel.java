package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;

import javax.swing.JComponent;

import controller.GameObjectObserver;
import model.Change;
import model.Direction;
import model.Game;

public class GamePanel extends JComponent implements GameObjectObserver {

	private final EnumMap<Change.ChangeType, Color> color;

	private static final long serialVersionUID = -746841458539162873L;

	private BufferedImage img;

	private int scale;

	public GamePanel(Game game, int scale) {
		this.scale = scale;
		int width = game.getWidth() * scale;
		int height = game.getHeight() * scale;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(width, height));
		game.registerObserver(/*Trou*/ this );
		color = new EnumMap<>(Change.ChangeType.class);
		color.put(Change.ChangeType.SNAKE, Color.BLUE);
		color.put(Change.ChangeType.VOID, Color.black);
		color.put(Change.ChangeType.APPLE, Color.RED);
	

		addKeyListener( //Remplir en utilisant une classe anonyme implémentant KeyAdapter 
			new KeyAdapter(){
				@Override
				public void keyPressed(KeyEvent e){
					switch(e.getKeyCode()){
						case KeyEvent.VK_UP:
							if(game.getSnake().getDirection()!=Direction.Down){
								game.changeSnakeDirection(Direction.Up);						
							}
						break;
						
						case KeyEvent.VK_DOWN:
							if(game.getSnake().getDirection()!=Direction.Up){
								game.changeSnakeDirection(Direction.Down);
							}
						break;
						
						case KeyEvent.VK_LEFT:
							if(game.getSnake().getDirection()!=Direction.Right){
								game.changeSnakeDirection(Direction.Left);
							}
						break;
						
						case KeyEvent.VK_RIGHT:
							if(game.getSnake().getDirection()!=Direction.Left){
								game.changeSnakeDirection(Direction.Right);
							}
						break;
						
						default:
							break;
					}

				}
			}
		);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}

	@Override
	public void update(List<? extends Change> o) {
		Graphics g = img.getGraphics();
		//Trou
		for(Change c:o){
			g.setColor(color.get(c.getChangeType()));
			g.fillRect(c.getCoordinate().getX()*scale, c.getCoordinate().getY()*scale, scale, scale);
		}
		
		g.dispose();
		repaint();
	}

}
