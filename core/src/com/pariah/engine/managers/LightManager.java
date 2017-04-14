package com.pariah.engine.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.pariah.runner.Constants;

import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * LightManager.java
 * 
 * 
 * @author Douglas Rudolph
 */
public class LightManager
{

	//The only instance of light manager 
	private static LightManager	lightManager;

	//Objects from the object layer from the TiledMap
	private MapObjects			lightLayer;

	//Used to load and render the lights in a level
	private RayHandler			lightHandler;
	
	/**
	 * Static LightManger Constructor 
	 * sets the only instnace of LightManager to null
	 */
	static
	{
		lightManager = null;
	}

	private LightManager(RayHandler lightHandler)
	{
		this.lightHandler = lightHandler;
	}

	/**
	 * Sets the level for the light to be loaded
	 * @param String tileMapPath: 
	 */
	public void loadLevel(String tileMapPath)
	{
		TiledMap map= new TmxMapLoader().load(tileMapPath);
		createLights(map);
	}
	
	/**
	 * Runs through the object layer of a TileMap and 
	 * and adds lights into a Box2D world.
	 */
	private void createLights(TiledMap map)
	{
		lightLayer = map.getLayers().get("Lights").getObjects();

		for (int count = 0; count < lightLayer.getCount(); count++)
		{
			RectangleMapObject obj = (RectangleMapObject) lightLayer.get(count);

			new PointLight(lightHandler, 10, Color.WHITE, 30,
					obj.getRectangle().x / Constants.BOX_SCALE,
					obj.getRectangle().y / Constants.BOX_SCALE);
		}
	}

	/**
	 * Rendesr the lights of a level
	 * @param RayHandler handler: used to render the lights inside of a GameState
	 */
	public void renderLights()
	{
		lightHandler.render();
	}

	/**
	 * Ensures there is only ever one instance of LightManager
	 *  
	 * @param handler:			handler used to add lights to the world and to be rendered
	 * @param lightWorld: 		world the lights being rendered inside of
	 * @param lightCamera: 		camrea used to project the lights to the level
	 * @return lightManager:	the only instance of LightManager.java
	 */
	public static LightManager createInstance(RayHandler handler)
	{
		//singleton if statement
		if (lightManager == null)
		{
			lightManager = new LightManager(handler);
		}

		return lightManager;
	}

	/**
	 * Deletes the only instanc of LightManager
	 */
	public static void deleteInstance()
	{
		lightManager = null;
	}

}
