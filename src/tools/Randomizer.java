package tools;

import java.util.Random;

/**
 * Permet de générer des nombres aléatoires depuis n'importe où et de façon
 * très simple
 * 
 * @author Corentin Legros
 */
public class Randomizer {
	
	private static Randomizer instance;
	
	private Random objGenerator;
	
	public static Randomizer getInstance() {
		if (instance == null) {
			instance = new Randomizer();
		}
		return instance;
	}
	
	private Randomizer() {
		objGenerator = new Random();
	}
	
	/**
	 * Génère un entier compris dans un intervalle [min;max]
	 * @param min
	 * @param max
	 * @return int entier aléatoire
	 */
	public int generateRangedInt(int min, int max) {
		min = Math.abs(min) + 1;
		max = Math.abs(max);
		return objGenerator.nextInt(min + max) - min + 1;
	}
}
