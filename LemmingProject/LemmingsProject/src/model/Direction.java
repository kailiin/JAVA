package model;

public enum Direction {
	Up(0, -1), Down(0, 1), Left(-1, 0), Right(1, 0);

	public int x;
	public int y;

	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
