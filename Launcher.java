package fr.mds.TP14;

import fr.mds.TP14.manager.Jeu;

public class Launcher {
	private final static int TAILLE_MAP_X = 18;
	private final static int TAILLE_MAP_Y = 24;
	private final static int NB_JOUEURS = 2;
	
	
	public static void main(String[] args) {
		Jeu jeu = new Jeu(TAILLE_MAP_X, TAILLE_MAP_Y, NB_JOUEURS);
		jeu.jouer();
	}
}
