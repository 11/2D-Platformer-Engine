package com.pariah.engine.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.pariah.engine.box2d.Box2DEntity;
import com.pariah.runner.Constants;

/**
 * Player.java
 * 
 * - Used in each level of the game. 
 * - The player gets added into the instance
 *   of world used with all other box2D entities
 * - The player of the game controls one instance of this class in each level.
 * - SINGLETON (Only ever one instnace of this class)
 * 
 * @author Douglas Rudolph
 */
public class Player extends Box2DEntity
{

	// local singleton player instance
	private static Player	playerInstance	= null;

	// stores if the player is moving left
	private boolean			moveLeft;

	// stores if the player is moving right
	private boolean			moveRight;

	// stores if the player can jump
	private boolean			jump;

	// stores if the player is on the ground
	private boolean			isOnGround;

	// TileSheetAnimation object used to animate the
	// player depending on what direction he is moving
	//private TileSheetAnimation	animation;

	/**
	 * Player Constructor
	 * 
	 * 
	 * 
	 * @param w
	 * @param type
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param cat
	 * @param layerMask
	 * @param manager
	 */
	private Player(float x, float y, float width, float height, short cat,
			short startingLayerMask, World w, BodyType type)
	{
		super(x, y, width, height, cat, startingLayerMask, w, type);

		moveLeft = false;
		moveRight = false;
		jump = false;
		isOnGround = false;

		//animation = new TileSheetAnimation(new Texture(
		//	Gdx.files.internal("Demo/Player15.png")));
	}

	/**
	 * Updates what platforms the player can collide with
	 * 
	 * @param mask: the mask bit value of platforms the player can collide with
	 */
	public void updateContactSensor(short mask)
	{
		// gets the filter data from the user
		Filter filter = this.f.getFilterData();

		// sets the filter mask bit of the players body
		filter.maskBits = mask;
		b.getFixtureList().first().setFilterData(filter);

	}

	/**
	 * - Sets if the player is moving left. 
	 * - This method gets called directory inside the GamePlayInputManager
	 * 
	 * @param b
	 */
	public void setLeft(boolean pressingLeft)
	{
		/*
		 * If the player is pressing Left:
		 *     - moving left is set to true
		 *     - moving right is set to false
		 *     (this is done just in case right and left are both being pressed)
		 *     - The more recently pressed button will override of the player moves 
		 *       right or left.     
		 */
		if (pressingLeft)
		{
			moveLeft = true;
			moveRight = false;
		}

		/*
		 * If the player is not pressing Left:
		 *     - moving left is set to false
		 *     - moving right is set to false
		 *     (this is done just in case right and left are both being pressed)
		 *     -The more recently pressed button will override of the player moves 
		 *      right or left.     
		 */
		if (!pressingLeft)
		{
			moveLeft = false;
			moveRight = false;
		}
	}

	/**
	 * - Sets if the player is moving left. 
	 * - This method gets called directory inside the GamePlayInputManager
	 * @param pressingRight: boolean if the player is pressing right
	 */
	public void setRight(boolean pressingRight)
	{

		/*
		 * If the player is pressing Right:
		 *     - moving left is set to false
		 *     - moving right is set to true
		 *     (this is done just in case right and left are both being pressed)
		 *     - The more recently pressed button will override of the player moves
		 *       right or left.     
		 */
		if (pressingRight)
		{
			moveLeft = false;
			moveRight = true;
		}

		/*
		 * If the player is not pressing Right:
		 *     - moving left is set to false
		 *     - moving right is set to false
		 *     (this is done just in case right and left are both being pressed)
		 *     - The more recently pressed button will override of the player moves 
		 *       right or left.     
		 */
		if (!pressingRight)
		{
			moveLeft = false;
			moveRight = false;
		}
	}

	/**
	 * - Sets if the player is jumping. 
	 * - This method gets called directory inside the GamePlayInputManager
	 * @param pressingUp: boolean if the player is pressing up
	 */
	public void setJump(boolean pressingUp)
	{

		/*
		 * If the player is pressing up, on the ground, and jump is false
		 *     - jump is true
		 */
		if (isOnGround && jump == false && pressingUp)
		{
			jump = true;
		}

		/*
		 * If the player is not pressing up
		 *     - jump is false
		 */
		if (pressingUp == false)
		{
			jump = false;
		}
	}

	/**
	 * - Sets if the player is on the ground. 
	 * - This method gets called directory inside the GamePlayInputManager
	 * @param b: boolean if the player is pressing right
	 */
	public void setOnGround(boolean onGround)
	{
		/*
		 * If the player is on the ground
		 *     flag is set so the player is on the ground
		 */
		if (onGround)
		{
			isOnGround = true;
		}

		/*
		 * Otherwise, flag is set so the player is not on the ground
		 */
		if (!onGround)
		{
			isOnGround = false;
		}
	}

	/**
	 * - Checks if the player can move (jump, left, right) 
	 * - Controls how fast the player moves depending if its 
	 *   in the air or not 
	 * - gets put in the
	 * update method of a state
	 */
	public void updateMotion()
	{
		// if player is on ground move faster than in air
		if (moveLeft && moveRight == false && isOnGround)
		{
			b.applyForceToCenter(-70, b.getLinearVelocity().y, true);
		}

		// if player is in air, move slower
		if (moveLeft && moveRight == false && (!isOnGround))
		{
			b.applyForceToCenter(-70, b.getLinearVelocity().y, true);
		}

		// if player is on ground, move faster
		if (moveRight && moveLeft == false && isOnGround)
		{
			b.applyForceToCenter(70, b.getLinearVelocity().y, true);
		}

		// if player is on ground, move slower
		if (moveRight && moveLeft == false && (!isOnGround))
		{
			b.applyForceToCenter(70, b.getLinearVelocity().y, true);
		}

		//if the player is on the ground and up is pressed, allow for jump=
		if (isOnGround && jump)
		{			
			b.applyForceToCenter(b.getAngularVelocity(),
					80 * Constants.BOX_SCALE, true);
		}
	}

	/**
	 * Renders the current frame of the player animation
	 * 
	 * @param spriteBatch: spriteBatch used in the game state to allow for 
	 * 	   			       the player to be rendered
	 */
	public void render(SpriteBatch spriteBatch)
	{
		//animation.render(spriteBatch);
	}

	/**
	 * @param x:	  			   X cooridnate of the player
	 * @param y:				   Y coordinate of the player
	 * @param width:			   Width of the player
	 * @param height:		   Height of the player
	 * @param playerCat:	   Category bit of the player
	 * @param layerMask: 	   Mask bit of the player 
	 * @param manager:         AssetManager used to load the player tile sheet
	 * @param world:   		   The world the player is being added to
	 * @param type:            Body type of the player
	 * 
	 * @return playerInstance: The only instance of player
	 */
	public static Player createInstance(float x, float y, float width,
			float height, short playerCat, short layerMask, World world,
			BodyType type)
	{
		/*
		 * singleton if statement
		 * if the only instance of player is null
		 *     - create a new instance of player
		 */
		if (playerInstance == null)
		{
			playerInstance = new Player(x, y, width, height, playerCat,
					layerMask, world, type);
		}

		//return the instance of player
		return playerInstance;
	}

	/**
	 * Deletes the only instance of player
	 */
	public static void deleteInstance()
	{
		playerInstance = null;
	}

}
