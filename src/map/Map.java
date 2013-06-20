package map;

import java.util.ArrayList;
import java.util.HashMap;

import main.MyGame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMapPlus;

import entities.manager.EntityManager;

import pathfinding.Path;
import pathfinding.PathFinder;

import tools.Position;
import tools.Vector2i;

public class Map extends TiledMapPlus {

	
	public static final int TILE_GROUND = 0; // FE
	public static final int TILE_AIR = 1; // FD
	public static final int TILE_WATER = 2; // FB
	
	private HashMap<Integer, Integer> tileMasks;
	
	/**
	 * Stockage des propriétés de la map
	 * (lors de l'implémentation finale, supprimer les accès dégueu
	 * et répétés (mDim) -> implémenter les getters
	 */
	public static Vector2i mDim;
	public static Vector2i tDim;
	public static Vector2i mTDim;
	
	/**
	 * Map screen dimension
	 */
	public Vector2i sDim;
	
	
	protected Vector2i pt_TL;
	protected Vector2i pt_BL;
	
	protected Vector2i pt_TR;
	protected Vector2i pt_BR;
	
	protected Graphics g;
	protected int lateralOffset; // décalage latéral pour scrolling optimisé qui tremble
	
	
	protected static int blocked[][];
	
	// pathfinding attributes
	protected HashMap<Integer, boolean[][]> adjacencyMatrix;
	protected HashMap<Integer, boolean[][]> transitiveClosureMatrix;
	protected PathFinder refPathFinder;
	
	protected EntityManager objEm;
	
	public Map(String ref) throws SlickException {
		
		super(ref);
		/**
		 * Map dimension
		 */
		mDim = new Vector2i(width, height);
		
		/**
		 * Tile dimension
		 */
		tDim = new Vector2i(tileWidth, tileHeight);
		
		/**
		 * miTile dimension
		 */
		mTDim = new Vector2i((int)(tileWidth/2f), (int)(tileHeight/2f));
		
		//int max = 
		sDim = new Vector2i((mDim.x + mDim.y) * mTDim.x, (mDim.x + mDim.y) * mTDim.y);

		pt_TL = new Vector2i();
		pt_BL = new Vector2i();
		
		pt_TR = new Vector2i();
		pt_BR = new Vector2i();
		
		lateralOffset = mTDim.y;
		
		// chargement des masques de types de terrains
		tileMasks = new HashMap<Integer, Integer>();
		tileMasks.put(TILE_GROUND, 0xFE);
		tileMasks.put(TILE_AIR, 0xFD);
		tileMasks.put(TILE_WATER, 0xFB);
		
		// initialisation des matrices d'adjacences et des matrices
		// de fermeture transitive
		int tmpSize = mDim.x * mDim.y;
		adjacencyMatrix = new HashMap<Integer, boolean[][]>();
		transitiveClosureMatrix = new HashMap<Integer, boolean[][]>();
		for (int i = 0 ; i <= 2 ; ++i) {
			adjacencyMatrix.put(i, new boolean[tmpSize][tmpSize]);
			fillBooleanSquaredMatrix(adjacencyMatrix.get(i), true);
			transitiveClosureMatrix.put(i, new boolean[tmpSize][tmpSize]);
		}
		
		loadBlockedTiles();
		//computeTransitiveClosure();
		
		refPathFinder = new PathFinder(this);
		
		objEm = EntityManager.getInstance();
	}
	
	public void render(Camera cam) {
		renderIsometricMap(cam.x, cam.y, 0, 0, 50, 50, null, false);
	}
	
	public void dynamicRender(Graphics g) {
		
		Camera cam = Camera.getInstance();
		
		if (this.g == null) {
			this.g = g;
		}
		
		int margin = 150; // en pixels
		
		pt_TL = Position.screenToMemory(cam, 0, 0);
		pt_BL = Position.screenToMemory(cam, 0, MyGame.Y_WINDOW + margin);
		pt_TR = Position.screenToMemory(cam, MyGame.X_WINDOW, 0);
		pt_BR = Position.screenToMemory(cam, MyGame.X_WINDOW, MyGame.Y_WINDOW + margin);
		
		int startRenderX = pt_TL.x; // tile de début de rendu X
		int startRenderY = pt_TR.y; // tile de début de rendu Y
		int rowCount = pt_BL.y;
		int colCount = pt_BL.x;
		
		// Place the artificial limit
		rowCount = (rowCount > width) ? width : rowCount;
		colCount = (colCount > height) ? height : colCount;
		
		int additionalXCamOffset = 0;
		int additionalYCamOffset = 0;
		
		if (startRenderX > 0) {
			additionalXCamOffset += mTDim.x * startRenderX;
			additionalYCamOffset += mTDim.y * startRenderX;
			
			colCount -= startRenderX;
		}
		if( startRenderY > 0) {
			additionalXCamOffset -= mTDim.x * startRenderY;
			additionalYCamOffset += mTDim.y * startRenderY;

			rowCount -= startRenderY;
		}

		startRenderX = startRenderX < 0 ? 0 : startRenderX;
		startRenderY = startRenderY < 0 ? 0 : startRenderY;
		
		optimizedRender(cam.x + additionalXCamOffset, cam.y + additionalYCamOffset, startRenderX, startRenderY, colCount, rowCount, false);
		
		g.drawString("render options ["+startRenderX+";"+startRenderY+"]["+colCount+";"+rowCount+"]", 400, 10);
	}
	
	/**
	 * Permet un affichage plus optimisé de rendu que celui par défaut
	 * (affiche un carré composé de 2 fois le nombre de tiles affichées à l'écran)
	 * Permet donc un affichage d'un nombre de tiles constant et des performances améliorées
	 * tile
	 * @param x position pixel d'où commence le rendu
	 * @param y position pixel d'où commence le rendu
	 * @param sx indice X dans le repère isométrique indiquant à quelle tile le rendu doit commencer
	 * @param sy indice Y dans le repère isométrique indiquant à quelle tile le rendu doit commencer
	 * @param width largeur de la ligne de rendu (en nombre de tiles)
	 * @param height hauteur de la ligne de rendu (en nombre de tiles)
	 * @param layer null pour le rendu de tous les calques, sinon affiche seulement le calque spécifié (@see Map.getLayer())
	 * @param lineByLine True if we should render line by line, i.e. giving us a chance to render something else between lines
	 */
	protected void optimizedRender(int x, int y, int sx, int sy, int width, int height, boolean lineByLine) {
		
		int i = 0; // variables utilisée a chaque tour de boucle (déclaration en amont)
		
		/*
		 * numéro de la ligne traitée en diagonale,
		 * + facteur de multiplication pour position des tiles sur le screen
		 */
		int currentLine = 0; // nombre de tiles à afficher en diagonales
		int numLineToReach = width + height;
		int renderX;
		int renderY;
		int renderXTile;
		int renderYTile;
		
		while (currentLine < numLineToReach) {
			
			for (i = 0 ; i <= currentLine ; ++i) {
				
				renderX = x - currentLine * mTDim.x + i * tileWidth;
				renderY = y + currentLine * mTDim.y;
				
				renderXTile = sx + i;
				renderYTile = sy + currentLine - i;
				
				layers.get(0).render(renderX, renderY, renderXTile, renderYTile, 1, 0, 
						lineByLine, tileWidth, tileHeight);
			}
			++currentLine;
		}
		objEm.render(g);
	}
	
	
	protected void renderIsometricMap(int x, int y, int sx, int sy, int width, int height, Layer layer, boolean lineByLine) {
		ArrayList<Layer> drawLayers = layers;
		if (layer != null) {
			drawLayers = new ArrayList<Layer>();
			drawLayers.add(layer);
		}

		int maxCount = width * height;
		int allCount = 0;

		boolean allProcessed = false;

		int initialLineX = x;
		int initialLineY = y;

		int startLineTileX = 0;
		int startLineTileY = 0;
		
		while (!allProcessed) {

			int currentTileX = startLineTileX;
			int currentTileY = startLineTileY;
			int currentLineX = initialLineX;

			int min = 0;
			
			if (height > width) {
				if (startLineTileY < width - 1) {
					min = startLineTileY;
				} else if (width - currentTileX < height) {
					min = width - currentTileX - 1;
				} else {
					min = width - 1;
				}
			} else {
				if (startLineTileY < height - 1) {
					min = startLineTileY;
				} else if (width - currentTileX < height) {
					min = width - currentTileX - 1;
				} else {
					min = height - 1;
				}
			}

			for (int burner = 0; burner <= min; currentTileX++, currentTileY--, burner++) {
				for (int layerIdx = 0; layerIdx < drawLayers.size(); layerIdx++) {
					Layer currentLayer = (Layer) drawLayers.get(layerIdx);
					currentLayer.render(currentLineX, initialLineY,
							currentTileX, currentTileY, 1, 0, lineByLine,
							tileWidth, tileHeight);
				}
				currentLineX += tileWidth;

				allCount++;
			}

			if (startLineTileY < (height - 1)) {
				startLineTileY += 1;
				initialLineX -= tileWidth / 2;
				initialLineY += tileHeight / 2;
			} else {
				startLineTileX += 1;
				initialLineX += tileWidth / 2;
				initialLineY += tileHeight / 2;
			}

			if (allCount >= maxCount) {
				allProcessed = true;
			}
		}
	}
	
	protected void loadBlockedTiles() {
		blocked = new int[width][height];
		for (int x = 0 ; x < width ; x++) {
			for (int y = 0 ; y < height ; y++) {
				for (int layerIdx = 0 ; layerIdx < layers.size() ; layerIdx++) {
					int id = getTileId(x, y, layerIdx);
					if (id != 0) {
						
						int value = 0;
						if (getTileProperty(id, "ground_stop", "").equals("true")) {
							value = 0x01;
							//setTileBlocked(x, y, TILE_GROUND);
						}
						if (getTileProperty(id, "air_stop", "").equals("true")) {
							value |= 0x02;
							//setTileBlocked(x, y, TILE_AIR);
						}
						if (getTileProperty(id, "water_stop", "").equals("true")) {
							value |= 0x04;
							//setTileBlocked(x, y, TILE_WATER);
						}
						blocked[x][y] = value;
					}
				}
			}
		}
	}
	
	
	/**
	 * Permet de tester si une tile est bloquante pour le type de map "GROUND"
	 * 
	 * @param int x
	 * @param int y
	 * @return boolean
	 */
	public boolean isTileBlocked(int x, int y) {
		return isTileBlocked(x, y, TILE_GROUND);
	}
	
	/**
	 * Chaque tile donne ou bloque l'accès aux "types d'unités" (air/eau/sol (jeu de mot trop ouf :D ))
	 * @param int x
	 * @param int y
	 * @param int type_block (TILE_GROUND, TILE_WATER, TILE_AIR)
	 * @return
	 */
	public boolean isTileBlocked(int x, int y, int blockingType) {
		return (blocked[x][y] | tileMasks.get(blockingType)) == 255;
	}
	
	/**
	 * Calcule la fermeture transitive de la map
	 */
	protected void computeTransitiveClosure() {
		
		TransitiveClosureThread tmpTCThreads[] = new TransitiveClosureThread[3];
		
		for (int mapIndex = 0 ; mapIndex <= 2 ; ++mapIndex) {
			System.out.println("Loading submap #" + mapIndex + "...");
			tmpTCThreads[mapIndex] = new TransitiveClosureThread(adjacencyMatrix.get(mapIndex), transitiveClosureMatrix.get(mapIndex));
			tmpTCThreads[mapIndex].start();
		}
		
		for (int mapIndex = 0 ; mapIndex <= 2 ; ++mapIndex) {
			try {
				tmpTCThreads[mapIndex].join();
				System.out.println("Submap #" + mapIndex + " loaded.");
			} catch (InterruptedException e) {
				System.out.println("Unable to join TC computation [" + mapIndex + "]");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Permet de tester si un chemin existe entre 2 positions (x;y) de la map.
	 * Cette méthode utilise la technique de la fermeture transitive afin
	 * d'éviter de rechercher des chemins inexistants.
	 * 
	 * @param Vector2i start start point
	 * @param Vector2i end end point
	 * @param int mapType type de map testée
	 * @return boolean
	 */
	public boolean hasPath(Vector2i start, Vector2i end, int mapType) {
		return hasPath(start.x, start.y, end.x, end.y, mapType);
	}
	
	/**
	 * Permet de tester si un chemin existe entre 2 positions (x;y) de la map.
	 * Cette méthode utilise la technique de la fermeture transitive afin
	 * d'éviter de rechercher des chemins inexistants.
	 * 
	 * @param int xStart 
	 * @param int yStart 
	 * @param int xEnd 
	 * @param int yEnd
	 * @param int mapType type de map testée (GROUND,AIR,WATER...)
	 */
	public boolean hasPath(int xStart, int yStart, int xEnd, int yEnd, int mapType) {
		return true;
		/*
		int start = convertOrthoToSquareExpOrtho(xStart, yStart);
		int end = convertOrthoToSquareExpOrtho(xEnd, yEnd);
		return transitiveClosureMatrix.get(mapType)[start][end];
		*/
	}
	
	/**
	 * Permet de convertir une position orthogonale (x;y) en un sommet
	 * stocké dans une matrice carrée
	 * (adjacence ou fermeture transitive, en l'occurence)
	 * 
	 * @param int x
	 * @param int y
	 * @return
	 */
	private int convertOrthoToSquareExpOrtho(int x, int y) {
		return x + y * width;
	}
	
	/**
	 * Permet de remplir une matrice booléenne avec la valeur spécifiée
	 * Utile pour une initialisation.
	 * 
	 * @param boolean[][] matrix matrice à initialiser
	 * @param boolean value valeur à spécifier
	 */
	private void fillBooleanSquaredMatrix(boolean matrix[][], boolean value) {
		for (int i = 0 ; i < matrix.length ; ++i) {
			for (int j = 0 ; j < matrix.length ; ++j) {
				matrix[i][j] = value;
			}
		}
	}
	
	/**
	 * Permet de spécifier qu'une tile est bloquée.
	 * 
	 * @param int x
	 * @param int y
	 * @param int mapType
	 */
	public void setTileBlocked(int x, int y, int mapType) {
		
		int value = 0;
		if (mapType == TILE_GROUND) {
			value = 0x01;
		}
		if (mapType == TILE_AIR) {
			value |= 0x02;
		}
		if (mapType == TILE_WATER) {
			value |= 0x04;
		}
		blocked[x][y] = value;
	}
	
	/**
	 * Permet de spécifier qu'une tile est bloquée.
	 * 
	 * @param int x
	 * @param int y
	 * @param int mapType
	 */
	public void setTileBlocked(Vector2i position, int mapType) {
		setTileBlocked(position.x, position.y, mapType);
	}
	
	/**
	 * Permet de spécifier qu'une tile est libre
	 * @param x
	 * @param y
	 * @param mapType
	 */
	public void setTileReleased(int x, int y, int mapType) {
		blocked[x][y] = 0;
	}
	
	/**
	 * Permet de spécifier qu'une tile est libre
	 * @param x
	 * @param y
	 * @param mapType
	 */
	public void setTileReleased(Vector2i position, int mapType) {
		setTileReleased(position.x, position.y, mapType);
	}
	
	/**
	 * Permet de rechercher un cehmin entre 2 positions
	 * 
	 * @param Vector2i start position de départ
	 * @param Vector2i end position d'arrivée
	 * @param int mapType type de la map (<=> type du terrain)
	 * 
	 * @return Path
	 */
	public Path findPath(Vector2i start, Vector2i end, int mapType) {
		return refPathFinder.computePath(start, end, mapType);
	}
}
