package com.pariah.engine.managers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.pariah.engine.box2d.Box2DEntity;
import com.pariah.engine.box2d.Box2DFactory;
import com.pariah.runner.Constants;

/**
 * LevelManager.java
 * 
 * - Takes in a TileSheet made in Tiled
 * - Creates and renders a Level with Box2D entities
 * 
 * @author Douglas Rudolph
 */
public class LevelManager
{
	//layer value for where in the levelLayers array the front or back layer will be found
	private final int						FRONT_LAYER	= 0;
	private final int						BACK_LAYER	= 1;

	//Only instance of LevelManager
	private static LevelManager				levelManager;

	//Renders a tiled map in Orthogonal Presentation
	private OrthogonalTiledMapRenderer		oMapRenderer;

	//Tiled Map for a leve
	private TiledMap						map;

	//Two layers that make up a tile layer
	private ArrayList<TiledMapTileLayer>	levelLayers;

	//isntance of camera that gets used when rendering the tiledmap
	private OrthographicCamera				levelCameraInstance;

	/**
	 * - LevelManager Constructor
	 * - private constructor
	 * 
	 * @param tileMapPath: 			file path to the tile map
	 * @param world:				world the level loads to
	 * @param levelCameraInstance: 	camera the the projection matrix gets set to
	 */
	private LevelManager(String tileMapPath, World world,
			OrthographicCamera levelCameraInstance)
	{
		loadLevel(tileMapPath, world, levelCameraInstance);
	}

	/**
	 * Generic LevelManager constructor
	 */
	private LevelManager()
	{}

	/** Allows for a new tiled map to be loaded in for a new level */
	public void setLevelTileMap(String fileName)
	{
		map = new TmxMapLoader().load(fileName);
		oMapRenderer = Box2DFactory.createTiledMapRenderer(map);
	}

	/**
	 * Load
	 * @param tileMapPath
	 * @param world
	 * @param levelCamera
	 */
	public void loadLevel(String tileMapPath, World world,
			OrthographicCamera levelCamera)
	{
		this.map = new TmxMapLoader().load(tileMapPath);
		this.oMapRenderer = Box2DFactory.createTiledMapRenderer(map);
		setLevelCamera(levelCamera);
		levelLayers = new ArrayList<TiledMapTileLayer>();
		loadTilelayers(world);
	}

	/**
	 * 
	 * Purpose: Loads a tileMap as separate layers into
	 * the game and adds box2D bodies and collision for each tile.
	 * 
	 * Loops through the tiled map by layer
	 * adds box2D bodies for the layer
	 * gives each layer their own box2D tile collision filter mask
	 * 
	 * @author Douglas Rudolph
	 */
	private void loadTilelayers(World world)
	{
		//try to load the front and back layer from a tield map
		//NOTE: a back layer might not be entered into the tiled map
		TiledMapTileLayer frontLayer = (TiledMapTileLayer) map.getLayers().get(
				"Front");
		TiledMapTileLayer backLayer = (TiledMapTileLayer) map.getLayers().get(
				"Back");

		//if the front layer was found, add the front layer to an array
		if (frontLayer != null)
		{
			levelLayers.add(frontLayer);
		}

		//if the back layer was found, add the front layer to an array
		if (backLayer != null)
		{
			levelLayers.add(backLayer);
		}

		//loop through the array for how ever many layers were loaded in and generate box2D Bodies for that layer
		for (int layerCounter = 0; layerCounter < levelLayers.size(); layerCounter++)
		{
			//if its the first layer, assign the category bit to the front layer
			if (layerCounter == FRONT_LAYER)
			{
				generateLayer(levelLayers.get(FRONT_LAYER), world,
						Constants.FRONT_CAT);
			}

			//if its the second layer, assign the category bit to the back layer
			else
				if (layerCounter == BACK_LAYER)
				{
					generateLayer(levelLayers.get(BACK_LAYER), world,
							Constants.BACK_CAT);
				}
		}
	}

	/**
	 * Loops through a single layer and adds box2D Bodies into the game.
	 * 
	 * @param layer: layer being looped through
	 * @param world: world the bodies are being added to
	 * @param categoryBit: front or back bit value being assigned to a layer
	 */
	private void generateLayer(TiledMapTileLayer layer, World world,
			short categoryBit)
	{
		// counts how many tiles a box2D tile should grow to
		int boxSizeCounter = 0;

		// loop through the tile-map as a matrix
		for (int y = 0; y < layer.getHeight(); y++)
		{
			for (int x = 0; x < layer.getWidth(); x++)
			{
				// if there is nothing in the cell, add one to
				// boxSizeCoutner and skip
				if (layer.getCell(x, y) == null)
				{
					boxSizeCounter = 0;
					continue;
				}

				/*
				 * if the current cell isn't empty and the following is empty:
				 * - increase boxSizeCounter for the last tile
				 * - add a box2D body tile to the correct location
				 * - set boxSizeCounter to zero
				 * - skip to the next iteration of the loop
				 */
				if (layer.getCell(x, y) != null
						&& layer.getCell(x + 1, y) == null)
				{
					boxSizeCounter++;
					
					Box2DEntity b = Box2DEntity.createBox2DEntity(
									(((x * 32) - (boxSizeCounter * Constants.TILE_SIZE)) + 32)/ Constants.BOX_SCALE,
									((y * 32) + 16) / Constants.BOX_SCALE, 16* boxSizeCounter/ Constants.BOX_SCALE,
									16 / Constants.BOX_SCALE, categoryBit,
									Constants.PLAYER_CAT, world,
									BodyType.StaticBody);
					
					boxSizeCounter = 0;
					continue;
				}

				// if the cell isn't empty, add one to the boxSizeCounter
				if (layer.getCell(x, y) != null)
				{
					boxSizeCounter++;
				}
			}
		}
	}

	/**
	 * Generic render method thatr automatically tells when to render a scene or when to render a level
	 */
	public void render(boolean layer)
	{
		//if its a scene, render the scene
		if (levelLayers.size() == 1)
		{
			renderSceneLayer();
		}
		//if a level, render the level
		else if (levelLayers.size() == 2)
		{
				renderLevelLayer(layer);
		}
		//else there is an error and no level loaded
		else
		{
			System.out.println("FRONT OR BACK LAYER DIDNT LOAD :/");
		}
	}

	/**
	 * renders a single tile layer for certain screens that only have one tile
	 * Ensure that the player can't try to switch screens
	 */
	private void renderSceneLayer()
	{
		if (levelLayers.get(FRONT_LAYER) != null)
		{
			oMapRenderer.setView(levelCameraInstance);
			oMapRenderer.getBatch().setProjectionMatrix(levelCameraInstance.combined);
			oMapRenderer.getBatch().begin();
			oMapRenderer.renderTileLayer(levelLayers.get(FRONT_LAYER));
			oMapRenderer.getBatch().end();
		}

	}

	/**
	 * - Renders the tile map of a level
	 * - Manages what layer will have a lower opacity, and higher opacity
	 * 
	 * @param 
	 */
	private void renderLevelLayer(boolean layer)
	{
		//sets the view to the level camera
		//set the project matrix for the spritebatch to the level camera
		oMapRenderer.setView(levelCameraInstance);
		oMapRenderer.getBatch().setProjectionMatrix(
				levelCameraInstance.combined);

		//begins the render cycle for the tile map
		oMapRenderer.getBatch().begin();

		/*
		 * If the layer is set to true
		 *     - make the front layer visible
		 *     - make the back layer opaque
		 *     - render both layers of the tile map
		 */
		if (layer)
		{
			levelLayers.get(FRONT_LAYER).setOpacity(90);
			levelLayers.get(BACK_LAYER).setOpacity(1);
			oMapRenderer.renderTileLayer(levelLayers.get(FRONT_LAYER));
			oMapRenderer.renderTileLayer(levelLayers.get(BACK_LAYER));
		}

		/*
		 * If the layer is set to false
		 *     - make the back layer visible
		 *     - make the front  layer opaque
		 *     - render both layers of the tile map
		 */
		else
			if (!layer)
			{
				levelLayers.get(FRONT_LAYER).setOpacity(1);
				levelLayers.get(BACK_LAYER).setOpacity(90);
				oMapRenderer.renderTileLayer(levelLayers.get(FRONT_LAYER));
				oMapRenderer.renderTileLayer(levelLayers.get(BACK_LAYER));
			}

		//end the render cycle
		oMapRenderer.getBatch().end();
	}

	/**
	 * Ensures that only one instance of LevelManager is created
	 * 
	 * @param filePath: filepath to the tile map being loaded
	 * @param world: 	world the Box2DEntities are being added to when 
	 * 				  	the level is being loaded
	 * @param camera:   camera used to set the view of the tilemap when being rendered
	 * @return levelManager: the only instance of LevelManager
	 */
	public static LevelManager createInstance(String filePath, World world,
			OrthographicCamera camera)
	{

		//singleton if statement
		if (levelManager == null)
		{
			levelManager = new LevelManager(filePath, world, camera);
		}

		//returns the only instnace of LevelManager
		return levelManager;
	}

	/**
	 * If the same instance of level manager is being used, 
	 * then creating a generic version is needed.
	 * @return LevelMananger: used when only a generic instance of level mananger is needed
	 */
	public static LevelManager createInstance()
	{
		//singleton if statement
		if (levelManager == null)
		{
			levelManager = new LevelManager();
		}

		//return generic instance of LevelManager
		return levelManager;

	}

	/**
	 * Sets the level camera for the LevelManager.
	 */
	public void setLevelCamera(OrthographicCamera levelCamera)
	{
		this.levelCameraInstance = levelCamera;
	}

	/** Deletes the only instance of LevelManager */
	public static void deleteInstance()
	{
		levelManager = null;
	}

}
