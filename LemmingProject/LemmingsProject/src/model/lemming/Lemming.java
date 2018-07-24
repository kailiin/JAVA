package model.lemming;

import java.util.List;
import model.Change;
import model.Coordinate;
import model.Direction;
import model.Game;
import model.Observable;
import model.block.*;

public class Lemming extends Observable  {
	
	private Coordinate body;
	private Game game;
	private Direction direction;
	private int altitudeCounter;
	private int actionCounter;
	private boolean ContitionToChangeType;
	private boolean alive;
	private boolean arrived;
	private StateOfLemming stateOfLemming;

	public Lemming(Game game) throws Exception {
		stateOfLemming = new Simple(this);
		actionCounter =0;
		ContitionToChangeType=true;
		alive = true;
		arrived = false;
		registerObserver(game);
		direction = Direction.Right;
		body  = Coordinate.getStartCoordinate(game) ;
		this.game = game;
		addChange(new Change(body, Change.ChangeType.LEMMING));
		notifyObserver();
	}
	
	public boolean isContitionToChangeType() {
		return ContitionToChangeType;
	}


	public void setContitionToChangeType(boolean ContitionToChangeType) {
		this.ContitionToChangeType = ContitionToChangeType;
	}
	
	@Override
	public String toString() {
		return "The lemming is ";
	}
	
	public int getActionCounter() {
		return actionCounter;
	}

	public void addActionCounter() {
		actionCounter ++;
	}
	
	public void resetActionCounter() {
		actionCounter = 0;
	}
	
	public void changeStateTo(StateOfLemming newstate){
		this.stateOfLemming = newstate;
	}

	
	public Game getGame() {
		return game;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	
	public int getAltitudeCounter() {
		return altitudeCounter;
	}

	public void setAltitudeCounter(int altitudeCounter) {
		this.altitudeCounter = altitudeCounter;
	}
	
	public Coordinate getBody(){
		return body;
	}
	
	public void setBody(Coordinate body){
		this.body = body;
	}
	
	public void arrived(){
		arrived = true;
	}
	
	public void die(){
		alive = false;
	}
	
	public boolean ifArrived(){
		return arrived;
	}
	
	public boolean ifAlive(){
		return alive;
	}
	
	public void move()throws Exception{
		stateOfLemming.move();
	}
	
	public void addChange(Coordinate c, Change.ChangeType type){
		addChange(new Change(c,type));
	}
	
	public void notifyObsever(){
		notifyObserver();
	}
	
	
	public Coordinate OppositeNext(Direction d, Coordinate last){
		if(direction == Direction.Right)
			direction = Direction.Left;
		else 
			direction = Direction.Right;
		Coordinate next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
		return next ;
	}
	
	public boolean testLemmingBlocker(Coordinate c){
		for(Lemming l : game.getListLemmings()){
			if(l.getBody().equals(c) && l.stateOfLemming.getClass().equals(Blocker.class) ) {
			return true;
			}										
		}
		return false;
	}
	
	public void changeBlock(Coordinate c){
		DestructibleBlock d = new DestructibleBlock(c.getX(), c.getY(), game);
		game.getBlockList().set( c.getY()*game.getWidth() + c.getX() +c.getY()  , d);
		addChange(new Change(c, Change.ChangeType.DESTRUCTIBLE));
	}

	
	public void activeBomber(){
		DestructibleBlock d = new DestructibleBlock(body.getX(), body.getY(), game);
		List<Coordinate> around = d.getAround();
			
		for(int i=0 ; i<around.size() ; i++){
			if(testReproducer( around.get(i) ) )
				changeBlock(around.get(i) );
				
			else if( testExploder(around.get(i) ) )
				activeExploder(around.get(i) );
			
			else if( testDestructible( around.get(i) ) )
				destroyBlock( around.get(i) );
		}
		
	}
	
	public boolean testLava(Coordinate c){
		if( game.getBlockList().get(c.getY()*game.getWidth() + c.getX() +c.getY()).getClass().equals(Lava.class)) 
			return true;
		return false;
	}
	
	public boolean testVoid(Coordinate c){
			if( game.getBlockList().get(c.getY()*game.getWidth() + c.getX() +c.getY()).getClass().equals(VoidBlock.class))
				return true;
			
		return false;
	}
	
//Destructible Block 
	public boolean testDestructible(Coordinate c){
		DestructibleBlock b = new DestructibleBlock(c.getX(),c.getY(),game);
		if( game.getBlockList().get(c.getY()*game.getWidth() + c.getX() +c.getY()).getClass().equals(b.getClass()) ) return true;
		return false;
	}
	
	public void destroyBlock(Coordinate c){
		VoidBlock v = new VoidBlock(c.getX(), c.getY(), game);
		game.getBlockList().set( c.getY()*game.getWidth() + c.getX() +c.getY(), v) ;
		addChange(new Change(c, Change.ChangeType.VOID));	
	}
	
// Teleporter
	public int testTeleporter(Coordinate c){
		Teleporter t =null;
		for(Block b : game.getBlockList()){
			if	(b.getClass() == Teleporter.class) 
				t = (Teleporter) b;
		}
		
		if( game.getBlockList().get(c.getY()*game.getWidth() + c.getX() +c.getY()).getClass() == (Teleporter.class)
				&& c.getX()==t.getBlockCoord().getX() && c.getY()==t.getBlockCoord().getY() ){
			
			t = (Teleporter) game.getBlockList().get(c.getY()*game.getWidth() + c.getX() +c.getY());
			
			Coordinate tpPosition = new Coordinate(t.getcoord2().getX(), t.getcoord2().getY()) ; 
			if(game.testAroundVoidBlock(game.getBlockList(), tpPosition) ) {
				
				Coordinate tpDown = new Coordinate(t.getcoord2().getX(), t.getcoord2().getY()+1) ; //down
				Coordinate tpLeft = new Coordinate(t.getcoord2().getX()-1, t.getcoord2().getY()) ; //left
				Coordinate tpRight = new Coordinate(t.getcoord2().getX()+1, t.getcoord2().getY()) ; //right
				Coordinate tpUp = new Coordinate(t.getcoord2().getX(), t.getcoord2().getY()-1) ; //up
				
				if(tpPosition.getY() < game.getHeight()-1)
					if(testVoid(tpDown))return 2;  //down is void
				
				if(tpPosition.getX()>0)
					if(testVoid(tpLeft) && this.getDirection()==Direction.Left)return 3;  //left is void and direct is left 
			
				if(tpPosition.getX()<game.getWidth()-1)
					if(testVoid(tpRight) && this.getDirection() == Direction.Right)return 4;  //right is void and direction is right
			
				if(tpPosition.getX()>0)
					if(testVoid(tpLeft))return 3;  // direction = right but can't go right -> test left
			
				if(tpPosition.getX()<game.getWidth()-1)
					if(testVoid(tpRight))return 4;  // direction = left but can't go left -> test right
				
				if(tpPosition.getY() >0)
					if(testVoid(tpUp))return 1; //tpDown is void
			}
		}
		return 0;
	}
	
	public Coordinate getTPposition(Coordinate c, int i){
		Teleporter t = (Teleporter) game.getBlockList().get(c.getY()*game.getWidth() + c.getX() +c.getY());
		Coordinate tpPosition =null;
		if(i==1) tpPosition = new Coordinate(t.getcoord2().getX(), t.getcoord2().getY()-1);
		else if(i==2) tpPosition = new Coordinate(t.getcoord2().getX(), t.getcoord2().getY()+1);
		else if(i==3) tpPosition = new Coordinate(t.getcoord2().getX()-1, t.getcoord2().getY());
		else if(i==4) tpPosition = new Coordinate(t.getcoord2().getX()+1, t.getcoord2().getY());

		return tpPosition;
	}

//Reproducer
	public boolean testReproducer(Coordinate c){
		if( game.getBlockList().get(c.getY()*game.getWidth() + c.getX() +c.getY()).getClass().equals( Reproducer.class ) ) return true;
		return false;
	}
	//if true lemming -> changeBlock(c)
	
//Exploder	
	public boolean testExploder(Coordinate c){
		if( game.getBlockList().get(c.getY()*game.getWidth() + c.getX() +c.getY()).getClass().equals(Exploder.class) ) return true;
		return false;
	}
	
	public void activeExploder(Coordinate c){
		Exploder e = (Exploder) game.getBlockList().get(c.getY()*30 + c.getX() +c.getY() );

		VoidBlock vb = new VoidBlock(c.getX(), c.getY(), game);
		game.getBlockList().set( c.getY()*game.getWidth() + c.getX() +c.getY(), vb) ;
		addChange(new Change(c, Change.ChangeType.VOID));
		
		//kill around lemmings
		for(int i=0 ; i < e.getAround().size();i++ ){
			for(Lemming l : game.getListLemmings()){
				if( l.inAround( e.getAround().get(i) ) ) l.die(); 
			}
		}
		
	}
	
	public boolean inAround(Coordinate c){
		if(body.equals(c) ) return true;
		return false;
	}
	
	
	
}
