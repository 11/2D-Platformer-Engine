package com.pariah.graphics.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.pariah.engine.managers.AssetManager;

/**
 * Entity.java 
 * - Used as a way to create generic renderable objects on screen 
 * 
 * @author Douglas Rudolph
 *
 */
public abstract class Entity extends Image
{
	// x,y, width, height for the entity
	protected float	x;
	protected float	y;
	protected float	width;
	protected float	height;

	//texture for the entity
	protected Texture	texture;

	/**
	 * Entity Constructor
	 * @param x: coordinate
	 * @param y: coordinate
	 * @param width: width of the texture
	 * @param height: height of the texture
	 * @param texturePath: file-path to the texture
	 */
	public Entity(float x, float y, float width, float height,
			Texture texture)
	{		
		//sets the image of the actor to the image being requested
		super(texture);
		 
		this.x=x;
		this.y=y;
		this.width = width;
		this.height = height;

		//assigns the instance fields to the parameters
		setBounds(x, y, width, height);	
	}
	
	/**
	 * Entity Constructor
	 * 
	 * @param x: coordinate
	 * @param y: coordinate
	 * @param texturePath: file-path to the texture
	 */
	public Entity(float x, float y, Texture texture)
	{		
		//sets the image of the actor to the image being requested
		super(texture);
		
		//assigns the instance fields to the parameters
		setPosition(x, y);
		this.x = x;
		this.y = y;		
	}

	/**
	 * @return x: returns the x coordinate of the entity
	 */
	public float getX()
	{
		return x;
	}

	/**
	 * @return y: returns the x coordinate of the entity
	 */
	public float getY()
	{
		return y;
	}

	/**
	 * @return width: returns the width of the entity
	 */
	public float getWidth()
	{
		return width;
	}

	/**
	 * @return height: returns the height of the entity
	 */
	public float getHeight()
	{
		return height;
	}
}
