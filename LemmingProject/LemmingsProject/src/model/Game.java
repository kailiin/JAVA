package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.GameObjectObserver;
import model.block.*;
import model.lemming.Lemming;

public class Game extends Observable implements GameObjectObserver {
	
	public static enum CursorType { 
		SIMPLE, BLOCKER, TUNNELER, DRILLER, BOMBER, CARPENTER, CLIMBER, PARACHUTIST 
	}
	
	private CursorType cursorType;
	private final int NBLEMMINGS = 7;
	private int stepCounter;
	private int blockerCounter;
	private int sleep;
	private int height;
	private int width;
	private List<Lemming> lemmings;
	private List<Block> blockList;
	private int conditonToWin;

	public Game(int sleep, int width, int height, String f) throws Exception {
		stepCounter = 0;
		blockerCounter =0;
		this.sleep = sleep;
		this.width = width;
		this.height = height;	
		blockList = map(f);
		conditonToWin = 0;
		lemmings = new ArrayList<Lemming>(); 
		cursorType = CursorType.SIMPLE;
	}
	
	public void changeCursor(CursorType cursor){
		cursorType = cursor;
	}
	
	public CursorType getCursorType(){
		return cursorType;
	}

	public List<Lemming> getListLemmings(){
		return lemmings;
	}
	
	public List<Block> getBlockList(){
		return blockList;
	}
	
	public void getsizeblock(){
		System.out.println(blockList.size()); 
	}
	
	public void addBlockerCounter(){
		blockerCounter++;
	}
	
	public void killAllLemmings(){
		  for(Lemming l : lemmings)
		   l.die();
		 }
	
	public List<Block> map(String f) throws Exception{
		List<Block> m = new ArrayList<Block>();
		StartBlock startB = null;
		int nbStartBlock =0;
		int nbEndtBlock =0;
		
		boolean teleporterInList = false;
		Teleporter tele = new Teleporter(100, 100, this,new Coordinate(100, 100));
		
		File file = new File(f);
	    FileReader fr;
	    String str = "";
	    try {
		      fr = new FileReader(file);
		      int read = 0;
		      
		      while((read = fr.read()) != -1)
		        str += (char)read;

		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	    int x = 0; // column counter
	    int y = 0; // line counter
	    for(int i=0; i < str.length();i++){
	    	char c = str.charAt(i);
	    	if((int)c == 10 ){
	    		//Return to the line
	    		x=-1;
	    		y++;
	    	}else if((int)c == 73){ //I
	    		// Indestructible block
	    		IndestructibleBlock b = new IndestructibleBlock(x,y,this);
	    		m.add(b);
	    		Coordinate coor = new Coordinate(x,y);
    			addChange(new Change(coor, Change.ChangeType.INDESTRUCTIBLE));
    			
	    	}else if((int)c == 66){ //B
	    		// Destructible block
	    		DestructibleBlock b = new DestructibleBlock(x,y,this);
	    		m.add(b);
	    		Coordinate coor = new Coordinate(x,y);
    			addChange(new Change(coor, Change.ChangeType.DESTRUCTIBLE));
    		
	    	}else if((int)c == 88){ //X
	    		// Exploder block
	    		Exploder b = new Exploder(x, y, this);
	    		m.add(b);
	    		Coordinate coor = new Coordinate(x,y);
    			addChange(new Change(coor, Change.ChangeType.EXPLODER));
    			
	    	}else if((int)c == 82){ //R
	    		//Reproducer block
	    		Reproducer b = new Reproducer(x, y, this);
	    		m.add(b);
	    		Coordinate coor = new Coordinate(x,y);
    			addChange(new Change(coor, Change.ChangeType.REPRODUCER));
	    		
	    	}else if((int)c == 84){ //T
	    		//Teleporter coord1
    			Coordinate coor = new Coordinate(x,y);
    			addChange(new Change(coor, Change.ChangeType.TELEPORTER));
    			
    			m.add(tele);
    			tele.setCoord1(x, y);
    			teleporterInList= true;
	    			
	    	}else if((int)c == 116){ //t
	    		//Teleporter coord2	
	    		IndestructibleBlock b = new IndestructibleBlock(x,y,this);
	    		m.add(b);
	    		
	    		teleporterInList= true;
	    		Coordinate coor = new Coordinate(x,y);
    			addChange(new Change(coor, Change.ChangeType.TELEPORTER2));
	    	
    			tele.setCoord2(x, y);
	
	    	}else if((int)c == 71){ //G
	    		//lava block
	    		Lava b = new Lava(x,y,this);
	    		m.add(b);
	    		Coordinate coor = new Coordinate(x,y);
    			addChange(new Change(coor, Change.ChangeType.LAVA));
    			
    		}else{
    			if((int)c == 83){ //S
    				//entry
    	    		StartBlock b = new StartBlock(x,y,this);
    	    		m.add(b);
    	    		Coordinate coor = new Coordinate(x,y);
        			addChange(new Change(coor, Change.ChangeType.ENTRY));
        			startB = b;
        			nbStartBlock++;
        			
    	    	}else if((int)c == 69){ //E
    	    		//exit
    	    		EndBlock b = new EndBlock(x,y,this);
    	    		m.add(b);
    	    		Coordinate coor = new Coordinate(x,y);
        			addChange(new Change(coor, Change.ChangeType.EXIT));
        			nbEndtBlock++;
        			
    	    	}else{
    	    	//void
    			VoidBlock b = new VoidBlock(x,y,this);
    			m.add(b);
    			Coordinate coor = new Coordinate(x,y);
    			addChange(new Change(coor, Change.ChangeType.VOID));
    	    	}
    		}
	    	x++;
	    }

	    
	    if(nbStartBlock == 0 ) throw new Exception("Map Error : StartBlock Missing !!");
	    else if(nbStartBlock >1 ) throw new Exception("You must have one StartBlock ! ");
	    
	    if (nbEndtBlock == 0 ) throw new Exception("Map Error : EndBlock Missing !!");
	    else if (nbEndtBlock >1) throw new Exception("You must have one EndBlock !");

	    if(!testAroundVoidBlock(m,startB.getBlockCoord() )) throw new Exception("Map Error : StartBlock !!");
	    
	    if(teleporterInList &&tele.getcoord2().getX() ==100 ) throw new Exception("Map Error : Teleporter : no destination !!");
	    if(teleporterInList &&tele.getBlockCoord().getX() ==100 ) throw new Exception("Map Error : Teleporter : no source !!");
	    
		return m;
	}
	
	public boolean testAroundVoidBlock( List<Block> list , Coordinate c){
		//Down
		if(c.getY() < height-1)
			if(list.get( (c.getY()+1)*width + c.getX() + c.getY()+1 ).getClass().equals(VoidBlock.class))
				return true;
		
		//Up
		if(c.getY() >0 )
			if (list.get( (c.getY()-1)*width + c.getX() + c.getY()-1).getClass().equals(VoidBlock.class))
				return true;
		
		//Right			
		if (c.getX()<width-1)
			if(list.get( c.getY()*width + c.getX()+1 + c.getY() ).getClass().equals(VoidBlock.class))
				return true;
		//Left
		if(c.getX()>0)
			if(list.get( c.getY()*width + c.getX()-1 + c.getY() ).getClass().equals(VoidBlock.class))
				return true;

		return false;
	}
	
	
	@Override
	public void update(List<? extends Change> changes) {
		for(Change c : changes){
			addChange(c);
		}
		notifyObserver();
		
	}
	
	public void changeLemmingDirection(Lemming l,Direction direction) {
		l.setDirection(direction);
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

	//add a new Lemming
	public void addLemming()throws Exception{
		Lemming lemming = new Lemming(this);
		this.lemmings.add(lemming);
		notifyObserver();
	}
	
	// In Game
	public void step()throws Exception {
		for(int i=0 ; i<lemmings.size() ;i++){
			lemmings.get(i).move();
			
			if(!lemmings.get(i).ifAlive()){
				lemmings.remove(i);
			}else{
				if(lemmings.get(i).ifArrived()){
					lemmings.remove(i);
					conditonToWin++;
				}
			}
			
			notifyObserver();
		
	    	try {
	    		Thread.sleep(sleep);
	    	} catch (InterruptedException e) {
	    		Thread.currentThread().interrupt();
	    	}
		}
 
	}

	
	public boolean win(){
		if ( conditonToWin >= 3 ) return true;
		return false;
	}
	
	public boolean run() throws Exception{
		int nbLemmings = 0;
		while(lemmings.size()- blockerCounter != 0 || stepCounter == 0){
			if(nbLemmings < NBLEMMINGS)
				if((stepCounter+2) % 2 == 0){ // A Lemming born each 2 steps
					addLemming(); 
					nbLemmings++;
					notifyObserver();
				}
			step();
			stepCounter++;
		}
		
		if(win()) return true;
		else return false;
	}
	
}