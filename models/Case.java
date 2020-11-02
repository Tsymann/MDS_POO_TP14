package fr.mds.TP14.models;

public class Case {
	private int id;
	private int positionX;
	private int positionY;

	public Case(int id, int x, int y) {
		this.positionX = x;
		this.positionY = y;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}
}
