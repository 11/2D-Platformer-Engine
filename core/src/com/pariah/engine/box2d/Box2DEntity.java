package com.pariah.engine.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pariah.runner.Constants;

/**
 * 
 * @author Douglas Rudolph
 */
public class Box2DEntity extends Actor
{
	// box2D instance variables
	protected FixtureDef	fdef;
	protected Body			b;
	protected BodyDef		bdef;
	protected Fixture		f;

	// actor instance variables
	private float			x;
	private float			y;
	protected float			width;
	protected float			height;

	/**
	 * @param world
	 * @param bodyType
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	protected Box2DEntity(float x, float y,float width, float height,
			World world, BodyDef.BodyType type)
	{
		// creates a definition for the body
		BodyDef def = new BodyDef();
		def.type = type;
		def.position.set(new Vector2(x/Constants.BOX_SCALE, y/Constants.BOX_SCALE));
		
		// uses the world to create a body
		Body b = world.createBody(def);
		
		// creates a FixureDef to define the properties of the fixture
		// creates a shape to define the shape of the fixture
		// applies the shape to the fixture
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/Constants.BOX_SCALE, height/Constants.BOX_SCALE);
		fdef.shape = shape;
		
		// creates a fixture that can be edited later on for all Box2DEntities
		b.createFixture(fdef);

		// initializes Box2D related instance variables
		this.fdef = fdef;
		this.b = b;

		// initializes all coordinate variables
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * 
	 * @param world
	 * @param bodyType
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	protected Box2DEntity( float x, float y,float width, float height, 
			short cat, short mask, World world, BodyDef.BodyType type)
	{
		// creates a definition for the body
		BodyDef def = new BodyDef();
		def.type = type;
		def.position.set(new Vector2(x, y));

		// uses the world to create a body
		Body b = world.createBody(def);

		// creates a FixureDef to define the properties of the fixture
		// creates a shape to define the shape of the fixture
		// applies the shape to the fixture
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		fdef.shape = shape;
		fdef.filter.categoryBits = cat;
		fdef.filter.maskBits = mask;

		// creates a fixture that can be edited later on for all Box2DEntities
		f = b.createFixture(fdef);
		// initializes Box2D related instance variables
		this.fdef = fdef;
		this.b = b;

		// initializes all coordinate variables
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Used a factory to create a generic Box2DEntities 
	 * @return Box2DEntity
	 */
	public static Box2DEntity createBox2DEntity(float x, float y, float width, float height, 
			World w, BodyType type)
	{
		return new Box2DEntity(x, y, width, height, w, type);
	}

	/**
	 * Used a factory to create Box2DEntities that have a category bit and mask bit
	 * @return Box2DEntity
	 */
	public static Box2DEntity createBox2DEntity(float x, float y, float width, float height, 
			short cat, short mask, World w, BodyType type)
	{
		return new Box2DEntity(x, y, width, height, cat, mask, w, type);
	}

	public Fixture getFixture()
	{
		return f;
	}

	/**
	 * @return FixtureDef
	 */
	public FixtureDef getFixtureDef()
	{
		return this.fdef;
	}

	/**
	 * @param def
	 */
	public void setFixtureDef(FixtureDef def)
	{
		fdef = def;
	}

	/**
	 * @return Body
	 */
	public Body getBody()
	{
		return this.b;
	}

	/**
	 * @return BodyDef
	 */
	public BodyDef getBodyDef()
	{
		return this.bdef;
	}

	/**
	 * @return
	 */
	public float getX()
	{
		return x;
	}

	/**
	 * @param x
	 */
	public void setX(float x)
	{
		this.x = x;
	}

	/**
	 * @return
	 */
	public float getY()
	{
		return y;
	}

	/**
	 * @param y
	 */
	public void setY(float y)
	{
		this.y = y;
	}

	/**
	 * @return
	 */
	public float getWidth()
	{
		return width;
	}

	/**
	 * @param width
	 */
	public void setWidth(float width)
	{
		this.width = width;
	}

	/**
	 * @return
	 */
	public float getHeight()
	{
		return height;
	}

	/**
	 * @param height
	 */
	public void setHeight(float height)
	{
		this.height = height;
	}
}
