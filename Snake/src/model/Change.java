package model;

public class Change {
	
    public static enum ChangeType { SNAKE,APPLE, VOID }

	private Coordinate coordinate;
	private ChangeType changeType;

	public Change(Coordinate coordinate, ChangeType changeType) {
		this.coordinate = coordinate;
		this.changeType = changeType;
	}

	public ChangeType getChangeType() {
		return changeType;
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}
}
