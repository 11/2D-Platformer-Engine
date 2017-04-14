package com.pariah.engine.managers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * AssetManager.java
 * 
 * Manages the loading of assets.
 * Creates a manageable way of loading and retrieving assets.
 * 
 * @author Douglas Rudolph
 */
public class AssetManager 
{
	//private static object declaration used to ensure no duplicates of this object.
	private static AssetManager assetManager=null;
	
	//object that is referenced to retrieve and store images.
	private static HashMap<String, Texture> images;
	
	private AssetManager(){}
	
	/**
	 * disposes assets from the AssetManager
	 */
	public static void disposeAssets()
	{
		images.clear();
	}

	/**
	 * AssetManager Constructor
	 */
	static
	{	
		images=new HashMap<String, Texture>();
	}
	
	/**
	 * Will be used to load all assets 
	 * @param path to file
	 * @param key how to retrieve the file 
	 */
	public  void loadAsset(String path, String key)
	{
		//tries to load a BufferedImage
		try
		{
			Texture image = new Texture(Gdx.files.internal(path));
			images.put(key, image);
		}
		catch(Exception e)
		{
			//print where error occurs
			System.out.println("Image "+path+" was not found");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Used to get specific asset 
	 * @param key: how to retrieve the file
	 * @return a BuffereImage
	 */
	public Texture getAsset(String key)
	{
		return images.get(key);
	}

	/**
	 * Ensures that only one instnace of AssetManager is created 
	 * @return assetManager: The only instance of AssetManger 
	 */
	public static AssetManager createInstance()
	{
		//singleton if statement
		if(assetManager==null)
		{
			assetManager = new AssetManager();
		}
		
		return assetManager;
	}

	/**
	 * Delets the only instance of AssetManager
	 */
	public static void deleteInstance()
	{
		assetManager = null;
	}
	
}

