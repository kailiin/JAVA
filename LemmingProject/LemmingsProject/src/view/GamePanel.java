package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import controller.GameObjectObserver;
import model.Change;
import model.Direction;
import model.Game;
import model.Game.CursorType;
import model.block.Block;
import model.block.DestructibleBlock;
import model.block.EndBlock;
import model.block.Exploder;
import model.block.IndestructibleBlock;
import model.block.Lava;
import model.block.Reproducer;
import model.block.StartBlock;
import model.block.Teleporter;
import model.lemming.Blocker;
import model.lemming.Bomber;
import model.lemming.Carpenter;
import model.lemming.Climber;
import model.lemming.Driller;
import model.lemming.Lemming;
import model.lemming.Parachutist;
import model.lemming.Simple;
import model.lemming.Tunneler;

class MyClickLemming extends MouseAdapter {
	
	private Game game;
	private int scale;
	
	public MyClickLemming(Game game, int scale){
		this.game = game;
		this.scale = scale;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		boolean change = false;
		for(Lemming lemming : game.getListLemmings())
			if(e.getX()/scale == lemming.getBody().getX()  &&   e.getY()/scale == lemming.getBody().getY()
				&& lemming.isContitionToChangeType()  &&  !change){
				change = true;
				switch (game.getCursorType()) {
					case SIMPLE : 
						lemming.changeStateTo(new Simple(lemming) );
						System.out.println("I am a Walker & i am still walking!");
						break;
					case BLOCKER : 
						lemming.changeStateTo(new Blocker(lemming) );
						System.out.println("I am a Blocker & i am not moving!");
						break;
					case TUNNELER : 
						lemming.changeStateTo(new Tunneler(lemming) );
						System.out.println("I am a Tunneler & i am going bottom!");
						break;
					case DRILLER : 
						lemming.changeStateTo(new Driller(lemming) );
						System.out.println("I am a Driller & i am going depth!");
						break;
					case BOMBER: 
						lemming.changeStateTo(new Bomber(lemming) );
						System.out.println("I am a Bomber & i am going to die!");
						break;
					case CARPENTER : 
						lemming.changeStateTo(new Carpenter(lemming) );
						System.out.println("I am a Carpenter & i love my job!");
						break;
					case CLIMBER : 
						lemming.changeStateTo(new Climber(lemming) );
						System.out.println("I am a Climber & i hate walking!");
						break;
					case PARACHUTIST : 
						lemming.changeStateTo(new Parachutist(lemming) );
						System.out.println("I am a Parachutist & i like skydiving!");
						break;
				}
			}
	}
	
}

public class GamePanel extends JComponent implements GameObjectObserver {

	private final EnumMap<Change.ChangeType, Color> color;

	private static final long serialVersionUID = -746841458539162873L;

	private BufferedImage img;

	private int scale;
	
	private Game game;
	
	private MouseAdapter mouseAdapter;

	public GamePanel(Game game, int scale) {
		this.game = game;
		this.scale = scale;
		int width = game.getWidth() * scale;
		int height = game.getHeight() * scale;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(width, height));
		game.registerObserver( this );
		color = new EnumMap<>(Change.ChangeType.class);

		color.put(Change.ChangeType.VOID, Color.WHITE);
		
		color.put(Change.ChangeType.LEMMING, Color.WHITE);
		color.put(Change.ChangeType.BLOCKER, Color.MAGENTA); //dark red
		color.put(Change.ChangeType.TUNNELER, Color.YELLOW); //
		color.put(Change.ChangeType.DRILLER, new Color(240,230,140)); //khaki
		color.put(Change.ChangeType.BOMBER, Color.RED);  //crimson -red
		color.put(Change.ChangeType.CARPENTER, new Color(139,69,19)); //Brown
		color.put(Change.ChangeType.CLIMBER, new Color(176,196,222)); //light steel blue
		color.put(Change.ChangeType.PARACHUTIST, new Color(0,255,255)); //cyan
		
		addKeyListener( 
				new KeyAdapter(){
					@Override
					public void keyPressed(KeyEvent e){
						switch(e.getKeyCode()){
							case KeyEvent.VK_1:
								System.out.println("The option Blocker is activated");
								game.changeCursor(CursorType.BLOCKER);
							break;
							
							case KeyEvent.VK_2:
								System.out.println("The option Tunneler is activated");
								game.changeCursor(CursorType.TUNNELER);
							break;
							
							case KeyEvent.VK_3:
								System.out.println("The option Driller is activated");
								game.changeCursor(CursorType.DRILLER);
							break;
							
							case KeyEvent.VK_4:
								System.out.println("The option Bomber is activated");
								game.changeCursor(CursorType.BOMBER);
							break;
							
							case KeyEvent.VK_5:
								System.out.println("The option Charpenter is activated");
								game.changeCursor(CursorType.CARPENTER);
							break;
							
							case KeyEvent.VK_6:
								System.out.println("The option Climber is activated");
								game.changeCursor(CursorType.CLIMBER);
							break;
							
							case KeyEvent.VK_7:
								System.out.println("The option Parachutist is activated");
								game.changeCursor(CursorType.PARACHUTIST);
							break;
							
							case KeyEvent.VK_0:
								System.out.println("No option is activated");
								game.changeCursor(CursorType.SIMPLE);
							break;
							
							case KeyEvent.VK_K:
								System.out.println("You are killed all the rest of Lemmings");
								game.killAllLemmings();
							break;
						}

					}
				}
			);

		mouseAdapter = new MyClickLemming(game,scale);
		addMouseListener(mouseAdapter);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);

		ImageIcon Start= new javax.swing.ImageIcon("Picture/Start.png");
		ImageIcon End = new javax.swing.ImageIcon("Picture/exitt.png");
		ImageIcon Lava = new javax.swing.ImageIcon("Picture/Lava.png");
		ImageIcon Destructible = new javax.swing.ImageIcon("Picture/Destructible.png");
		ImageIcon Indestructible = new javax.swing.ImageIcon("Picture/Indestructible.png");
		ImageIcon Reproducer = new javax.swing.ImageIcon("Picture/Reproducer.png");
		ImageIcon Exploder = new javax.swing.ImageIcon("Picture/Exploder.png");
		ImageIcon Teleporter = new javax.swing.ImageIcon("Picture/Teleporter.png");
		Teleporter tp = null;
		
		for(Block block : game.getBlockList())
			if(block.getClass().equals(StartBlock.class))
				g.drawImage(Start.getImage(), block.getBlockCoord().getX()*scale, block.getBlockCoord().getY()*scale, null);
		
			else if(block.getClass().equals(EndBlock.class))
				g.drawImage(End.getImage(), block.getBlockCoord().getX()*scale, block.getBlockCoord().getY()*scale, null);
			
			else if(block.getClass().equals(Lava.class))
				g.drawImage(Lava.getImage(), block.getBlockCoord().getX()*scale, block.getBlockCoord().getY()*scale, null);
			
			else if(block.getClass().equals(DestructibleBlock.class))
				g.drawImage(Destructible.getImage(), block.getBlockCoord().getX()*scale, block.getBlockCoord().getY()*scale, null);
			
			else if(block.getClass().equals(IndestructibleBlock.class))
				g.drawImage(Indestructible.getImage(), block.getBlockCoord().getX()*scale, block.getBlockCoord().getY()*scale, null);
			
			else if(block.getClass().equals(Reproducer.class))
				g.drawImage(Reproducer.getImage(), block.getBlockCoord().getX()*scale, block.getBlockCoord().getY()*scale, null);
			
			else if(block.getClass().equals(Exploder.class))
				g.drawImage(Exploder.getImage(), block.getBlockCoord().getX()*scale, block.getBlockCoord().getY()*scale, null);
			
			else if(block.getClass().equals(Teleporter.class)){
				tp = (Teleporter) block;
				g.drawImage(Teleporter.getImage(), block.getBlockCoord().getX()*scale, block.getBlockCoord().getY()*scale, null);
			}
		
			if( tp!=null && game.getBlockList().get(tp.getBlockCoord().getY()*game.getWidth()
					+tp.getBlockCoord().getX() + tp.getBlockCoord().getY()).getClass().equals(Teleporter.class))
				g.drawImage(Teleporter.getImage(), tp.getcoord2().getX()*scale, tp.getcoord2().getY()*scale, null);
		
		ImageIcon Right = new javax.swing.ImageIcon("Picture/Right.png");
		ImageIcon Left = new javax.swing.ImageIcon("Picture/Left.png");
		for(Lemming lemming : game.getListLemmings())
			if(lemming.getDirection() == Direction.Right)
				g.drawImage(Right.getImage(), lemming.getBody().getX()*scale, lemming.getBody().getY()*scale, null);
			else g.drawImage(Left.getImage(), lemming.getBody().getX()*scale, lemming.getBody().getY()*scale, null);
	}

	@Override
	public void update(List<? extends Change> o) {
		Graphics g = img.getGraphics();
		for(Change c:o){
			g.setColor(color.get(c.getTypeCoordiante()));
			g.fillRect(c.getCoordinate().getX()*scale, c.getCoordinate().getY()*scale, scale, scale);
		}
		g.dispose();
		repaint();
	}

}
