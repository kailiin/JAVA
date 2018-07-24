package model.lemming;

import model.Change;
import model.Coordinate;
import model.Observable;

public class Blocker extends Observable implements StateOfLemming  {
		
	  private Lemming lemming;
	  public Blocker(Lemming lemming) {
		  this.lemming = lemming;
	  }

	@Override
	public void move() {
	Coordinate last = lemming.getBody();
	Coordinate lastDown = new Coordinate(last.getX() , last.getY()+1);
	boolean active =false;
			
	if(lemming.isContitionToChangeType()){
			
		if(lemming.getGame().isOut(lastDown) ) { // if lastDown is out lemming die
			lemming.die();
			
		}else if( lemming.testVoid(lastDown) ){ // if lastDown is void
			lemming.setAltitudeCounter(lemming.getAltitudeCounter() +1);
				
		}else if( lemming.testLava(lastDown) )  // if lastDown = lava
			lemming.die();
	
		else if(lemming.testTeleporter(lastDown)!=0)  // if lastDown = Teleporter
			lastDown=lemming.getTPposition(lastDown,lemming.testTeleporter(lastDown));
				
		else if( lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame())) ) //if lastDown = EndBlock
			lemming.arrived();
				
		else active = true;
	
			
		if(lemming.ifAlive()){
			// if AltitudeCounter >= 5 => die
			if( !lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame())) && lemming.getAltitudeCounter() >=5 && !lemming.testVoid(lastDown)) 
				lemming.die();
			
			if(!lemming.testVoid(lastDown)) lemming.setAltitudeCounter(0);  //reset AltitudeCounter
			
			if(!active){
				if( !lemming.ifArrived() && lemming.ifAlive() ){
					lemming.addChange(last,Change.ChangeType.VOID);
					lemming.addChange(lastDown,Change.ChangeType.LEMMING);
					lemming.setBody(lastDown);
				}else lemming.addChange(lemming.getBody(),Change.ChangeType.VOID);
					
			}else{
				lemming.addChange(last,Change.ChangeType.BLOCKER);
				lemming.setContitionToChangeType(false);
				lemming.getGame().addBlockerCounter();
			}
				
		}else lemming.addChange(last,Change.ChangeType.VOID);
	
	
	 }else lemming.addChange(last,Change.ChangeType.BLOCKER);
	
	lemming.notifyObserver();
	}
}
