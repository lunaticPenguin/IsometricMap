package waves;

import java.util.ArrayList;

import pathfinding.Path;
import resources.MusicManager;

import map.Map;
import entities.factory.EntityFactory;
import entities.manager.EntityManager;
import entities.types.creatures.AbstractCreatureEntity;

import tools.Randomizer;
import tools.Vector2i;

/**
 * Permet de gérer les vagues de créatures.
 * Implémenté sous forme d'un singleton pour plus de simplicité
 * 
 * @author Corentin Legros
 *
 */
public class WaveHandler {
	
	protected static WaveHandler instance;
	
	public static WaveHandler getInstance() {
		if (instance == null) {
			instance = new WaveHandler();
		}
		return instance;
	}
	
	private ArrayList<Wave> waves;
	private int intCurrentWave = 0;
	
	private Map map;
	
	private Vector2i mainGoal;
	
	private EntityManager objEntityManager;
	
	private long timer;
	
	private boolean wavesStarted;
	
	private WaveHandler() {
		
		waves = new ArrayList<Wave>();
		
		Wave tmpWave = null;
		tmpWave = new Wave(3, 5000);
		tmpWave.add(new Vector2i(3, 18));
		waves.add(tmpWave);
		
		tmpWave = new Wave(5, 5000);
		tmpWave.add(new Vector2i(3, 18));
		tmpWave.add(new Vector2i(5, 4));
		waves.add(tmpWave);
		
		tmpWave = new Wave(8, 8000);
		tmpWave.add(new Vector2i(3, 18));
		tmpWave.add(new Vector2i(5, 4));
		tmpWave.add(new Vector2i(20, 32));
		waves.add(tmpWave);
		
		
		tmpWave = new Wave(14, 9000);
		tmpWave.add(new Vector2i(3, 18));
		tmpWave.add(new Vector2i(5, 4));
		tmpWave.add(new Vector2i(20, 32));
		tmpWave.add(new Vector2i(30, 32));
		waves.add(tmpWave);
		
		tmpWave = new Wave(21, 9000);
		tmpWave.add(new Vector2i(3, 18));
		tmpWave.add(new Vector2i(5, 4));
		tmpWave.add(new Vector2i(20, 32));
		tmpWave.add(new Vector2i(30, 32));
		tmpWave.add(new Vector2i(32, 29));
		waves.add(tmpWave);

		tmpWave = new Wave(34, 9000);
		tmpWave.add(new Vector2i(3, 18));
		tmpWave.add(new Vector2i(5, 4));
		tmpWave.add(new Vector2i(20, 32));
		tmpWave.add(new Vector2i(30, 32));
		tmpWave.add(new Vector2i(32, 29));
		tmpWave.add(new Vector2i(35, 6));
		waves.add(tmpWave);

		tmpWave = new Wave(42, 9000);
		tmpWave.add(new Vector2i(3, 18));
		tmpWave.add(new Vector2i(5, 4));
		tmpWave.add(new Vector2i(20, 32));
		tmpWave.add(new Vector2i(30, 32));
		tmpWave.add(new Vector2i(32, 29));
		tmpWave.add(new Vector2i(35, 6));
		waves.add(tmpWave);
		
		objEntityManager = EntityManager.getInstance();
		
		timer = System.currentTimeMillis() + 10000;
		wavesStarted = false;
	}
	
	/**
	 * Permet d'initialiser le gestionnaire de vagues
	 * avec le but (tour centrale) et la map
	 * (pour recherche des chemins selon les spawnpoints)
	 * 
	 * @param map
	 * @param goal
	 */
	public void init(Map map, Vector2i goal) {
		this.map = map;
		mainGoal = goal;
	}
	
	public void handle() {
		
		if (System.currentTimeMillis() < timer) {
			return;
		}
		if (!wavesStarted) {
			if (Randomizer.getInstance().generateRangedInt(1, 2) == 1) {
				MusicManager.getInstance().playMusic("ambience_attack_1.ogg", true);
			} else {
				MusicManager.getInstance().playMusic("ambience_attack_2.ogg", true);
			}
		}
		wavesStarted = true;
		
		Wave currentWave = waves.get(intCurrentWave);
			
		int maxSpawnPoints = currentWave.size();
		int i = 0;
		
		AbstractCreatureEntity tmpEntity;
		
		while (i < currentWave.getNbCreatures()) {
			tmpEntity = (AbstractCreatureEntity) objEntityManager.addEntity(EntityFactory.ENTITY_CREATUREJIDIAKO);
			tmpEntity.setM(currentWave.get(Randomizer.getInstance().generateRangedInt(0, maxSpawnPoints - 1)));
			tmpEntity.setIsDiplayed(true);
			
			Path tmpPath = null;
		
			tmpPath = map.findPath(tmpEntity.getM(), mainGoal, Map.TILE_GROUND);
			if (tmpPath != null) {
				tmpEntity.setCurrentPath(tmpPath);
			}
			++i;
		}
		if (intCurrentWave == waves.size() - 1) {
			--intCurrentWave;
		}
		++intCurrentWave;
		
		timer += currentWave.getDuration();
	}
	
	public int getCurrentWave() {
		return intCurrentWave;
	}
}
