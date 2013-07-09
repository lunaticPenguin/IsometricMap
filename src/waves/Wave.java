package waves;

import java.util.ArrayList;

import tools.Vector2i;

/**
 * Représente un objet vague.
 * Il est une liste de point de spawns possibles,
 * spécifie un nombre de créatures bien défini,
 * indique une durée d'attente avant le commencement de celle-ci
 * et possède un état (terminée ou pas)
 * 
 * @author Corentin Legros
 *
 */
public class Wave extends ArrayList<Vector2i> {

	// les différents états possible d'une vague
	public final static int STATE_NOTSTARTED = 0;
	public final static int STATE_ONGOING = 5;
	public final static int STATE_TERMINATED = 10;
	
	// l'état de la vague
	private int state;
	
	private long duration;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nbCreatures;
	
	public Wave(final int nbCreatures, long duration) {
		this.nbCreatures = nbCreatures;
		this.duration = duration;
	}
	
	/**
	 * @return le nombre de creatures de la vague
	 */
	public int getNbCreatures() {
		return nbCreatures;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}
}
