package com.pariah.engine.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pariah.engine.objects.Player;

/**
 * ContanceManager.java
 * 
 * - Manages if the player is in contact with any platformd in a level
 * - SINGLETON (Only ever one instance)
 * 
 * @author Douglas Rudolph
 */
public class ContactManager implements ContactListener
{
	//the only instance of ContactManager
	private static ContactManager	contactManager;

	//private instance of player from the current level thats running
	private Player					player;

	/**
	 * ContactManager constructor 
	 * - The constructor is private so the only way to create an object is through
	 *   createInstance( )
	 */
	private ContactManager(Player player)
	{
		// stores the instance of plauer from the current level thats loaded
		this.player = player;
	}

	/**
	 * Gets when an Box2DEntity begins contact with a platform
	 * Used for when the player begins contact with a platform
	 */
	@Override
	public void beginContact(Contact contact)
	{
		//sets the player is on the ground if the player begins contanct 
		player.setOnGround(true);
	}

	/**
	 * Gets When a Box2DEntity ends contanct with a platform
	 * Used for when the player ends contanct with a platform
	 */
	@Override
	public void endContact(Contact contact)
	{
		//setrs the okayer is not touching ground if the player ends contact
		player.setOnGround(false);
	}

	/** Goes unused in v0.7*/
	@Override
	public void preSolve(Contact contact, Manifold oldManifold)
	{

	}
	
	/** Goes unused in v0.7*/
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse)
	{

	}

	/**
	 * Used to ensure that there is only ever one instance of ContactManager
	 * @return contactManager: The only instnace of ContanctManager 
	 */
	public static ContactManager createInstance(Player player)
	{
		//singleton if statemenet
		if (contactManager == null)
		{
			contactManager = new ContactManager(player);
		}

		//return the only instance of ContactManager
		return contactManager;
	}

	/**
	 * Deletes the instance of ContactManager
	 */
	public static void deleteInstance()
	{
		contactManager = null;
	}

}
