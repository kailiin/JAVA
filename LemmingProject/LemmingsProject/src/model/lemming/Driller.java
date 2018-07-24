package model.lemming;

import model.Change;
import model.Coordinate;
import model.Observable;

public class Driller extends Observable implements StateOfLemming {
	  private Lemming lemming;

	  public Driller(Lemming lemming) {
		  this.lemming = lemming;
	  }

	@Override
	public void move() {
	  	Coordinate last = lemming.getBody();
		Coordinate next = new Coordinate(last.getX() + lemming.getDirection().x, last.getY() + lemming.getDirection().y);
		Coordinate nextUp = new Coordinate(last.getX() + lemming.getDirection().x, last.getY() + lemming.getDirection().y-1);
		Coordinate lastUp = new Coordinate(last.getX() , last.getY()-1);
		Coordinate lastDown = new Coordinate(last.getX() , last.getY()+1);
		
		boolean testDrill = false;
		
		if(!testDrill){
			if( lemming.testVoid(lastDown) ){ // if lastDown is void
				next = lastDown;
				lemming.setAltitudeCounter(lemming.getAltitudeCounter() +1);
			
			}else if( lemming.testLava(lastDown) )  // if lastDown = lava
				lemming.die();
			
			else if(lemming.testTeleporter(lastDown)!=0)  // if lastDown = Teleporter
				next=lemming.getTPposition(lastDown,lemming.testTeleporter(lastDown));
		
			else if( lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame() )) )//if lastDown = EndBlock
				lemming.arrived();
			
			else if( lemming.testReproducer(lastDown)){  // if lastDown = Reproducer
				testDrill = true;
				lemming.setContitionToChangeType(false);
				
			}else if( lemming.testExploder(lastDown)){ // if lastDown = Exploder
				testDrill = true;
				lemming.setContitionToChangeType(false);
			
			}else if( lemming.testDestructible(lastDown)){ // if lastDown = Destructible   destroy the block and next = lastDown
				testDrill = true;
				lemming.setContitionToChangeType(false);
			
			}else if( next.equals(Coordinate.getEndCoordinate( lemming.getGame() ))  )//if next = EndBlock
				lemming.arrived();
		
			else if( lemming.testLava(next) )  // if next = lava
				lemming.die();
			
			else if(lemming.testTeleporter(next)!=0)  // if next = Teleporter
				next=lemming.getTPposition(next,lemming.testTeleporter(next));
			
			else if(lemming.testLemmingBlocker(next) || lemming.getGame().isOut(next) ){	// if next = lemming Bloker or is out
				next=lemming.OppositeNext(lemming.getDirection(),next );
			
			}else if( lemming.testVoid(next) )  // if next = void
				lemming.setBody(next);
			
			else if (last.getY()==0 && !lemming.testVoid(next))  // if bodyUp is Out  and next != void
				next=lemming.OppositeNext(lemming.getDirection(),next );  // change direction
				
			else if( lemming.testVoid(nextUp) && lemming.testVoid(lastUp) ){ //if next !=void but nextUp = void and lastUp=void
				next = nextUp;
		
			}else{
				next=lemming.OppositeNext(lemming.getDirection(),next ); //change direction
			}
		}
		
		if(testDrill){
			lemming.addActionCounter();
			 if( lemming.testReproducer(lastDown)){  // if lastDown = Reproducer
					lemming.changeBlock(lastDown);
					next=last;
					
				}else if( lemming.testExploder(lastDown)){ // if lastDown = Exploder
					lemming.activeExploder(lastDown);
					lemming.die();
				
				}else if( lemming.testDestructible(lastDown)){ // if lastDown = Destructible   destroy the block and next = lastDown
					lemming.destroyBlock(lastDown);
					next=lastDown;
				}else { // if next = void
					testDrill = false;
					next = lastDown;
				}
		}
		
			// if AltitudeCounter >= 5 => die
			if( !lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame() )) && lemming.getAltitudeCounter() >=5 && !lemming.testVoid(lastDown))
				lemming.die();
		
			if(!lemming.testVoid(lastDown)) lemming.setAltitudeCounter(0);  //rest AltitudeCounter
		
			if(lemming.getActionCounter() >=5) testDrill=false;
			
		
			if(!testDrill && !lemming.ifArrived() && lemming.ifAlive() ){
				lemming.addChange(last,Change.ChangeType.VOID);
				lemming.addChange(next,Change.ChangeType.LEMMING);
				lemming.setBody(next);
				lemming.changeStateTo(new Simple(lemming));
			
			}else if(testDrill && !lemming.ifArrived() && lemming.ifAlive() && !lemming.isContitionToChangeType()){
				lemming.addChange(last,Change.ChangeType.VOID);
				lemming.addChange(next,Change.ChangeType.DRILLER);
				lemming.setBody(next);
				
			}else lemming.addChange(last,Change.ChangeType.VOID);

		lemming.notifyObserver();
  }

}
