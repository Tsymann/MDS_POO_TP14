package fr.mds.TP14.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.mds.TP14.models.Case;
import fr.mds.TP14.models.Joueur;

public class Jeu {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public final static int NB_CORVETTE = 1;
	public final static int NB_CROISEUR = 2;
	public final static int NB_DESTROYER = 2;
	public final static int NB_PORTE_AVION = 1;
	
	private static Random rand = new Random();
	private List<Joueur> arrJoueur = new ArrayList<Joueur>();
	
	private int mapX;
	private int mapY;
	private int nbJoueurs;
	
	
	public Jeu(int mapX, int mapY, int nbJoueurs) {
		this.mapX = mapX;
		this.mapY = mapY;
		this.nbJoueurs = nbJoueurs;
		
		for (int i = 0; i < this.nbJoueurs; i++) {
			Joueur monJoueur = new Joueur(mapX, mapY, i);
			this.arrJoueur.add(monJoueur);
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void jouer() {
		boolean fini = false;
		
		while (!fini) {
			for (Joueur joueur : arrJoueur) {
				int cible = trouveCible(joueur.getId());

				if (cible != -1) {
					tire(arrJoueur.get(cible));
					afficheMapJoueur(arrJoueur.get(cible));
					System.out.println();

				} else {
					fini = true;
				}
			}
		}
		
		String gagnant = null;
		for (Joueur joueur : arrJoueur) {
			if (estVivant(joueur)) {
				gagnant = "joueur " + (joueur.getId() + 1);
			}
			afficheMapJoueur(joueur);
			System.out.println();
		}

		System.out.println("Le gagant est : " + gagnant);
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public static boolean estVivant(Joueur joueur) {
		boolean result = false;

		for (Case caseActuelle : joueur.getPosNavire()) {
			String recherche = caseActuelle.getPositionX() + ";" + caseActuelle.getPositionY();

			if (!joueur.getMapTirs().containsKey(recherche)) {
				result = true;
			}
		}

		return result;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public int trouveCible(int attaquant) {
		int adversaire = -1;
		boolean flag = true;
		int i = attaquant;
		System.out.println("attaquant:" + attaquant);
		do {
			if (i + 1 == nbJoueurs) {
				i = 0;
			} else {
				i++;
			}

			Joueur cible = arrJoueur.get(i);
			if (estVivant(cible)) {
				adversaire = i;
				System.out.println("adversaire: " + adversaire);
				flag = false;
			}
		} while (flag && i != attaquant);

		if (i == attaquant) {
			adversaire = -1;
		}
		return adversaire;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void tire(Joueur joueurCible) {
		int x = -1;
		int y = -1;
		boolean shot = false;
		boolean touched = false;
		do {
			x = rand.nextInt(this.mapX) % this.mapX;
			y = rand.nextInt(this.mapY) % this.mapY;

			String posCible = x + ";" + y;

			if (!joueurCible.getMapTirs().containsKey(posCible)) {

				for (Case posNavire : joueurCible.getPosNavire()) {
					String caseBoat = posNavire.getPositionX() + ";" + posNavire.getPositionY();
					if (caseBoat == posCible) {

						touched = true;
						shot = true;
						String idCase = posNavire.getId() + "";
						joueurCible.addTir(posCible, idCase);
					}
				}
				if (!touched) {
					joueurCible.addTir(posCible, "9");
					shot = true;
				}

			}

		} while (!shot);
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void afficheMap() {
		for (int i = 0; i < this.mapX; i++) {
			for (int j = 0; j < this.mapY; j++) {
				System.out.print(0);
			}

			System.out.println();
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	// affichage avec couleur (très lent)
	public void afficheMapJoueur(Joueur joueur) {
		for (int k = 0; k < this.mapX; k++) {
			for (int j = 0; j < this.mapY; j++) {
				String recherche = k + ";" + j;
				if (checkExistsBoat(joueur, k, j) == 0) {
					if (joueur.getMapTirs().containsKey(recherche)) {
						System.out.print(ANSI_BLACK_BACKGROUND + 9 + ANSI_RESET);
					} else {
						System.out.print(ANSI_CYAN_BACKGROUND + ANSI_CYAN + 0 + ANSI_RESET);

					}
				} else {
					if (joueur.getMapTirs().containsKey(recherche)) {
						System.out.print(ANSI_RED_BACKGROUND + ANSI_RED + 7 + ANSI_RESET);
					} else {
						System.out.print(ANSI_GREEN_BACKGROUND+ + checkExistsBoat(joueur, k, j) + ANSI_RESET);
					}

				}
			}
			System.out.println();
		}
	}
	
	// affichage sans couleur
	/*
	public void afficheMapJoueur(Joueur joueur) {
		for (int k = 0; k < this.mapX; k++) {
			for (int j = 0; j < this.mapY; j++) {
				String recherche = k + ";" + j;
				if (checkExistsBoat(joueur, k, j) == 0) {
					if (joueur.getMapTirs().containsKey(recherche)) {
						System.out.print(9);
					} else {
						System.out.print(0);

					}
				} else {
					if (joueur.getMapTirs().containsKey(recherche)) {
						System.out.print(7);
					} else {
						System.out.print(checkExistsBoat(joueur, k, j));
					}

				}
			}
			System.out.println();
		}
	}
	*/
	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public int checkExistsBoat(Joueur monjoueur, int k, int j) {
		int Exist = 0;
		for (Case joueurCases : monjoueur.getPosNavire()) {
			if (k == joueurCases.getPositionX() && j == joueurCases.getPositionY()) {
				Exist = joueurCases.getId();
			}
		}
		return Exist;
	}
}
