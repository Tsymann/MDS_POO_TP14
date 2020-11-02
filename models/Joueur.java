package fr.mds.TP14.models;

import fr.mds.TP14.manager.Jeu;

import java.util.*;

public class Joueur {
	private List<Navire> arrNavires = new ArrayList<Navire>();
	private List<Case> posNavire = new ArrayList<Case>();
	private Map<String, String> mapTirs = new HashMap<>();
	
	private int id;
	private int mapX;
	private int mapY;
	
	public Joueur(int mapX, int mapY, int id) {

		this.id = id;
		this.mapX = mapX;
		this.mapY = mapY;

		for (int i = 0; i < Jeu.NB_CORVETTE; i++) {
			Corvette corvette = new Corvette();
			this.placeBateau(corvette);
			this.arrNavires.add(corvette);
		}
		for (int i = 0; i < Jeu.NB_CROISEUR; i++) {
			Croiseur croiseur = new Croiseur();
			this.placeBateau(croiseur);
			this.arrNavires.add(croiseur);
		}
		for (int i = 0; i < Jeu.NB_DESTROYER; i++) {
			Destroyer destroyeur = new Destroyer();
			this.placeBateau(destroyeur);
			this.arrNavires.add(destroyeur);
		}
		for (int i = 0; i < Jeu.NB_PORTE_AVION; i++) {
			PorteAvion PorteAvion = new PorteAvion();
			this.placeBateau(PorteAvion);
			this.arrNavires.add(PorteAvion);
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void placeBateau(Navire navire) {
		Random rand = new Random();

		int x = rand.nextInt(this.mapX) % this.mapX;
		int y = rand.nextInt(this.mapY) % this.mapY;
		int direction = rand.nextInt(2) % 2;

		if (estPlacable(navire, x, y, direction)) {
			placeBateauDansCarte(navire, x, y, direction);
		} else {
			placeBateau(navire);
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public boolean estPlacable(Navire navire, int x, int y, int direction) {
		boolean notExist = true;

		for (int i = 0; i < navire.getTaille(); i++) {
			for (Case posNavire : this.getPosNavire()) {
				switch (direction) {
				case 0:
					if (x + i >= this.mapX) {
						notExist = false;
					} else
					if (x + i == posNavire.getPositionX() && y == posNavire.getPositionY()) {
						notExist = false;
					}
					break;
				case 1:
					if (y + i >= this.mapY) {
						notExist = false;
					} else
					if (y + i == posNavire.getPositionY() && x == posNavire.getPositionX()) {
						notExist = false;
					}
					break;
				}
			}
		}
		return notExist;
	}

	//--------------------------------------------------------------------------------------------------------------------------------------------------------------

	public void placeBateauDansCarte(Navire navire, int x, int y, int direction) {
		switch (direction) {
		case 0:
			for (int i = 0; i < navire.getTaille(); i++) {
				Case addPosNavire = new Case(navire.getId(), (x + i), y);
				this.posNavire.add(addPosNavire);

			}
			break;
		case 1:
			for (int i = 0; i < navire.getTaille(); i++) {
				Case addPosNavire = new Case(navire.getId(), x, (y + i));
				this.posNavire.add(addPosNavire);
			}
			break;
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void addTir(String key, String value) {
		this.mapTirs.put(key, value);
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public List<Navire> getArrNavires() {
		return this.arrNavires;
	}
	public List<Case> getPosNavire() {
		return this.posNavire;
	}
	public Map<String, String> getMapTirs() {
		return this.mapTirs;
	}
	public int getMapX() {
		return this.mapX;
	}
	public int getMapY() {
		return this.mapY;
	}
	public int getId() {
		return this.id;
	}
}
