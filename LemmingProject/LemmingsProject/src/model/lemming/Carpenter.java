package model.lemming;

import model.Change;
import model.Coordinate;
import model.Observable;

public class Carpenter extends Observable implements StateOfLemming {
	  private Lemming lemming;

	  public Carpenter(Lemming lemming) {
		  this.lemming = lemming;
	  }

	  @Override
		public void move() {
		  	Coordinate last = lemming.getBody();
			Coordinate next = new Coordinate(last.getX() + lemming.getDirection().x, last.getY() + lemming.getDirection().y);
			Coordinate nextUp = new Coordinate(last.getX() + lemming.getDirection().x, last.getY() + lemming.getDirection().y-1);
			Coordinate lastUp = new Coordinate(last.getX() , last.getY()-1);
			Coordinate lastDown = new Coordinate(last.getX() , last.getY()+1);
			
			boolean action = true;
			
			if(lemming.getGame().isOut(lastDown) )  // if lastDown is out lemming die
				lemming.die();
			
			else if(lemming.isContitionToChangeType()){
				if( lemming.testVoid(lastDown) ){ // if lastDown is void
					next = lastDown;
					lemming.setAltitudeCounter(lemming.getAltitudeCounter() +1);
					
				}else if( lemming.testLava(lastDown) )  // if lastDown = lava
					lemming.die();
					
				else if(lemming.testTeleporter(lastDown)!=0)  // if lastDown = Teleporter
					next=lemming.getTPposition(lastDown,lemming.testTeleporter(lastDown));
					
				else if( lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame())) )//if lastDown = EndBlock
					lemming.arrived();
				
				else if( next.equals(Coordinate.getEndCoordinate(lemming.getGame())) )//if next = EndBlock
					lemming.arrived();	
				
				else if(lemming.testTeleporter(next)!=0)  // if next = Teleporter
					next=lemming.getTPposition(next,lemming.testTeleporter(next));
				
				else if( lemming.testLava(next) )  // if next = lava
					lemming.die();
					
				else if(lemming.testLemmingBlocker(next) || lemming.getGame().isOut(next) )	// if next = lemming Bloker or is out
					next=lemming.OppositeNext(lemming.getDirection(),next );
					
				else if( lemming.testVoid(next) )  // if next = void 
					lemming.setContitionToChangeType(false);
				
				else if (last.getY()==0 && !lemming.testVoid(next))  // if bodyUp is Out  and next != void
					next=lemming.OppositeNext(lemming.getDirection(),next );  // change direction
					
				else if( lemming.testVoid(nextUp) && lemming.testVoid(lastUp) ) //if next !=void but nextUp = void and lastUp=void
					next = nextUp;
				
				else next=lemming.OppositeNext(lemming.getDirection(),next );  // change direction
				
			}	
			
			if( !lemming.isContitionToChangeType()){
				if(lemming.testVoid(next)){
					lemming.changeBlock(next);
					lemming.addActionCounter();
					
					if (last.getY()==0 && !lemming.testVoid(next)){  // if bodyUp is Out  and next != void
						next=lemming.OppositeNext(lemming.getDirection(),next );  // change direction
						action = false;
					}else if(lemming.testVoid(nextUp)&& lemming.testVoid(lastUp) )
						next = nextUp;
					else{ // can't go nestUp  != void or no passage
						action = false;	
						next = lemming.OppositeNext(lemming.getDirection(),next );  // change direction
					}
					
				}else{  // next != voidBlock
					action = false;
					if( lemming.testLava(next) )  // if next = lava
						lemming.die();
					
					else if(lemming.testLemmingBlocker(next))	// if next = lemming Bloker
						next=lemming.OppositeNext(lemming.getDirection(),next );
						
					else if(lemming.testVoid(nextUp)&& lemming.testVoid(lastUp))
						next = nextUp;
					
					else  next = lemming.OppositeNext(lemming.getDirection(),next );  // change direction
				}
			}
			
			if(lemming.ifAlive()&& !lemming.ifArrived()){
				
				// if AltitudeCounter >= 5 => die
				if( !lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame())) && lemming.getAltitudeCounter() >=5 && !lemming.testVoid(lastDown)) 
					lemming.die();
			
				if(!lemming.testVoid(lastDown)) lemming.setAltitudeCounter(0);  //rest AltitudeCounter
			
				if(lemming.getActionCounter() >=5) action=false;
			
				if( !action && !lemming.ifArrived() && lemming.ifAlive()){
					lemming.addChange(next,Change.ChangeType.LEMMING);
					lemming.changeStateTo(new Simple(lemming) );
				
				}else if( !lemming.ifArrived() && lemming.ifAlive() ){
					lemming.addChange(last,Change.ChangeType.VOID);
					lemming.addChange(next,Change.ChangeType.CARPENTER);
					lemming.setBody(next); 
				}
				
			}else lemming.addChange(last,Change.ChangeType.VOID);

			lemming.notifyObserver();
	  }

}
