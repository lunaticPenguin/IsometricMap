package map;

/**
 * Permet de calculer la fermeture transitive d'une matrice d'adjacence
 * relative à un type de map (GROUND,WATER,AIR..etc)
 */
public class TransitiveClosureThread extends Thread {

	protected boolean[][] adjacencyMatrix;
	protected boolean[][] transitiveClosureMatrix;
	
	/**
	 * Permet de concevoir un thread tout en spécifiant
	 * la matrice d'adjacence et sa fermeture transitive (référence).
	 * 
	 * @param boolean[][] adjacencyMatrix matrice d'adjacence
	 * @param boolean[][] transitiveClosureMatrix matrice de fermeture transitive
	 */
	public TransitiveClosureThread(boolean[][] adjacencyMatrix, boolean[][] transitiveClosureMatrix) {
		super();
		this.adjacencyMatrix = adjacencyMatrix;
		this.transitiveClosureMatrix = transitiveClosureMatrix;
	}
	
	public void run() {
		
		int size = adjacencyMatrix.length;
		for (int i = 0 ; i < size ; ++i) {
			for(int j = 0 ; j < size ; ++j) {
				transitiveClosureMatrix[i][j] = adjacencyMatrix[i][j];
			}
		}
		
		for (int k = 0 ; k < size ; ++k) {
			for (int i = 0 ; i < size ; ++i) {
				for (int j = 0 ; j < size ; ++j) {
					transitiveClosureMatrix[i][j] = transitiveClosureMatrix[i][j] || (transitiveClosureMatrix[i][k] && transitiveClosureMatrix[k][j]);
				}
			}
		}
	}
}
