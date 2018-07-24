package model;

public class Change {
	
	 public static enum ChangeType {  DESTRUCTIBLE, EXPLODER, REPRODUCER, INDESTRUCTIBLE, TELEPORTER, TELEPORTER2, LAVA , ENTRY, EXIT, VOID,
	    	LEMMING, BLOCKER, TUNNELER, DRILLER, BOMBER, CARPENTER, CLIMBER, PARACHUTIST
	    	}

	private Coordinate coordinate;
	private ChangeType changeType;

	public Change(Coordinate coordinate, ChangeType changeType) {
		this.coordinate = coordinate;
		this.changeType = changeType;
	}

	public ChangeType getTypeCoordiante() {
		return changeType;
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}


}
