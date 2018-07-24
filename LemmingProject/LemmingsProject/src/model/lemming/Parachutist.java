package model.lemming;

import model.Change;
import model.Coordinate;
import model.Observable;

public class Parachutist extends Observable implements StateOfLemming {
	  private Lemming lemming;

	  public Parachutist(Lemming lemming) {
		  this.lemming = lemming;
		  lemming.setAltitudeCounter(0);
	  }

	@Override
	 public void move(){ 
	  	Coordinate last = lemming.getBody();
		Coordinate next = new Coordinate(last.getX() + lemming.getDirection().x, last.getY() + lemming.getDirection().y);
		Coordinate nextUp = new Coordinate(last.getX() + lemming.getDirection().x, last.getY() + lemming.getDirection().y-1);
		Coordinate lastUp = new Coordinate(last.getX() , last.getY()-1);
		Coordinate lastDown = new Coordinate(last.getX() , last.getY()+1);
		
		lemming.setAltitudeCounter(0);
		boolean endAction = false;
		
		if(lemming.getGame().isOut(lastDown) ) { // if lastDown is out lemming die
			lemming.die();
		}else{
			if( lemming.isContitionToChangeType()){ 
				if( lemming.testLava(lastDown) )  // if lastDown = lava
					lemming.die();
				
				else if(lemming.testTeleporter(lastDown)!=0)  // if lastDown = Teleporter
					next=lemming.getTPposition(lastDown,lemming.testTeleporter(lastDown));
				
				else if( lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame() )) )//if lastDown = EndBlock
					lemming.arrived();
				
				else if(lemming.testVoid(lastDown) ) // if lastDown is void
					lemming.setContitionToChangeType(false);		
				
				else if( next.equals(Coordinate.getEndCoordinate(lemming.getGame() )) )//if next = EndBlock
					lemming.arrived();	
				
				else if( lemming.testLava(next) )  // if next = lava
					lemming.die();
				
				else if(lemming.testTeleporter(next)!=0)  // if next = Teleporter
					next=lemming.getTPposition(next,lemming.testTeleporter(next));
			
				else if(lemming.testLemmingBlocker(next) || lemming.getGame().isOut(next) )	// if next = lemming Bloker or is out
					next=lemming.OppositeNext(lemming.getDirection(),next );
				
				else if( lemming.testVoid(next) )  // if next = void
					lemming.setBody(next);
			
				else if (last.getY()==0 && !lemming.testVoid(next))  // if bodyUp is Out  and next != void
					next=lemming.OppositeNext(lemming.getDirection(),next );  // change direction
			
				
				else if( lemming.testVoid(nextUp) && lemming.testVoid(lastUp) ) //if next !=void but nextUp = void 
					next = nextUp;
			
				else next=lemming.OppositeNext(lemming.getDirection(),next ); //change direction
			}
		
		
			if( !lemming.isContitionToChangeType() ){ // if lastDown is void
				
				if( lemming.testVoid(lastDown)){
					lemming.addActionCounter();
					if(lemming.getActionCounter() %2 ==0){  //slowed 
						next=lastDown;
					}else next = last;
					
				}else if( lemming.testLava(lastDown) )  // if down_block = lava
					lemming.die();
				
				else if( lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame() )) )//if lastDown = EndBlock
					lemming.arrived();
				
				else{ // lastdown = block
					next=last;
					endAction = true;
				}		
		}
		
		if(lemming.ifAlive() && !lemming.ifArrived() ){
			
			if(!endAction   ) {
				lemming.addChange(last,Change.ChangeType.VOID);
				lemming.addChange(next,Change.ChangeType.PARACHUTIST);
				lemming.setBody(next);
				
			}else if(endAction){
				lemming.changeStateTo(new Simple(lemming) );
				lemming.addChange(next,Change.ChangeType.LEMMING);
			}
		}else lemming.addChange(last,Change.ChangeType.VOID);
		
		lemming.notifyObserver();
		
	}

	}

}
