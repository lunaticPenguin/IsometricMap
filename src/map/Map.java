package map;

import java.util.ArrayList;


import main.MyGame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMapPlus;

import tools.Position;
import tools.Vector2i;

public class Map extends TiledMapPlus {

	/**
	 * Stockage des propriétés de la map
	 * (lors de l'implémentation finale, supprimer les accès dégueu
	 * et répétés (mDim) -> implémenter les getters
	 */
	public Vector2i mDim;
	public Vector2i tDim;
	public Vector2i mTDim;
	
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
	
	
	protected boolean blocked[][];
	
	
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
		loadBlockedTiles();
	}
	
	public void render(Camera cam) {
		renderIsometricMap(cam.x, cam.y, 0, 0, 50, 50, null, false);
	}
	
	public void dynamicRender(Camera cam, Graphics g) {
		
		if (this.g == null) {
			this.g = g;
		}
		
		int margin = 150; // en pixels
		
		pt_TL = Position.screenToMemory(cam, 0, 0, tileWidth, tileHeight, width, height);
		pt_BL = Position.screenToMemory(cam, 0, MyGame.Y_WINDOW + margin, tileWidth, tileHeight, width, height);
		pt_TR = Position.screenToMemory(cam, MyGame.X_WINDOW, 0, tileWidth, tileHeight, width, height);
		pt_BR = Position.screenToMemory(cam, MyGame.X_WINDOW, MyGame.Y_WINDOW + margin, tileWidth, tileHeight, width, height);
		
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
		
		optimizedRender(cam.x + additionalXCamOffset, cam.y + additionalYCamOffset, startRenderX, startRenderY, colCount, rowCount, null, false);
		
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
	protected void optimizedRender(int x, int y, int sx, int sy, int width, int height, Layer layer, boolean lineByLine) {
		ArrayList<Layer> drawLayers = layers;
		if (layer != null) {
			drawLayers = new ArrayList<Layer>();
			drawLayers.add(layer);
		}
		
		int nbTiles = 0;
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
				for (int layerIdx = 0; layerIdx < drawLayers.size(); layerIdx++) {
					Layer currentLayer = (Layer) drawLayers.get(layerIdx);
					
					renderX = x - currentLine * mTDim.x + i * tileWidth;
					renderY = y + currentLine * mTDim.y;
					
					renderXTile = sx + i;
					renderYTile = sy + currentLine - i;
					
					currentLayer.render(renderX, renderY, renderXTile, renderYTile, 1, 0, 
							lineByLine, tileWidth, tileHeight);
					++nbTiles;
				}
			}
			++currentLine;
		}
		
		System.out.println(nbTiles);
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
		blocked = new boolean[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int layerIdx = 0; layerIdx < layers.size(); layerIdx++) {
					int id = getTileId(x, y, layerIdx);
					if (id != 0) {
						String b = getTileProperty(id, "blocked", "");
						if (b.equals("true")) {
							blocked[x][y] = true;
						}
					}
				}
			}
		}
	}
	
	public boolean isTileBlocked(int x, int y) {
		return blocked[x][y];
	}
}
