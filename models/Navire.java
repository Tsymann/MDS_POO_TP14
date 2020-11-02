package fr.mds.TP14.models;

public abstract class Navire {
	protected int id;
	protected int taille;
	protected boolean direction;

	public Navire(int id, int taille) {
		this.id = id;
		this.taille = taille;
	}

	public int getId() {
		return id;
	}

	public int getTaille() {
		return taille;
	}

	public boolean isDirection() {
		return direction;
	}

}
