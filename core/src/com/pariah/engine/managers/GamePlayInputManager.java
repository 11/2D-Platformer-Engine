package com.pariah.engine.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.pariah.engine.objects.Player;
import com.pariah.graphics.ui.states.GameState;
import com.pariah.runner.Constants;
import com.pariah.runner.PariahRunner;

/**
 * - Grabs Player input for GameStates
 * - Player input is updated through an update method on the player
 * - Input gets observed on a different thread
 * - SINGELTON (Only ever one instance)
 * 
 * @author Douglas Rudolph
 */
public class GamePlayInputManager implements InputProcessor
{

	//the only instace of GamePlayInputManagee
	private static GamePlayInputManager gpiManager;
	
	// boolean that flips what platform layer is being rendered
	private boolean	layerFlipper	= true;

	// instance of player
	private Player	player;

	/**
	 * Constructor of GamePlayInputManagers
	 * init's the player to the player of the current level
	 */
	private GamePlayInputManager(Player player)
	{
		this.player = player;
	}

	/**
	 * An overrided method that is called when LEFT_SHIFT, UP, RIGHT, or LEFT
	 * are being pressed.
	 * if shift is pressed, it flips what platform layer is being rendered on
	 * the LevelLoader class
	 * 
	 * if the arrow keys are being pressed, it sets the a boolean to allow the
	 * player to be updated
	 * in the direction of the key that the player is pressing.
	 * 
	 * @param keycode: The key value of the key being pressed
	 */
	@Override
	public boolean keyDown(int keycode)
	{

		switch (keycode)
		{
			case Keys.SHIFT_LEFT:
			{
				// if layer flip is true, flip the layer being rendered and what
				// tiles collision should be checked for
				if (layerFlipper)
				{
					layerFlipper = false;
					updateLayerFlipper();
					player.updateContactSensor(Constants.BACK_CAT);
					break;
				}

				//flip to the other layer to be rendered
				//update what layer the player can collide with
				else
				{
					layerFlipper = true;
					updateLayerFlipper();
					player.updateContactSensor(Constants.FRONT_CAT);
					break;
				}
			}

			// sets the players jump boolean to true
			case Keys.UP:
			{
				player.setJump(true);
				break;
			}

			// sets the players moveRight boolean to true
			case Keys.RIGHT:
			{
				player.setRight(true);
				break;
			}

			// sets the players moveLeft boolean to true
			case Keys.LEFT:
			{
				player.setLeft(true);
				break;
			}
		}

		return true;
	}

	/**
	 * Changes the layer of to be rendered in the current Screen
	 */
	private void updateLayerFlipper()
	{
		if (layerFlipper)
		{
			((GameState) PariahRunner.getCurrentScreen()).setLayerTracker(false);
		}

		if (layerFlipper == false)
		{
			((GameState) PariahRunner.getCurrentScreen()).setLayerTracker(true);
		}
	}

	/**
	 * An override method that is called when LEFT_SHIFT, UP, RIGHT, or LEFT are
	 * being pressed.
	 * if shift is pressed, it flips what platform layer is being rendered on
	 * the LevelLoader class
	 * 
	 * if the arrow keys are being pressed, it sets the a boolean to allow the
	 * player to be updated
	 * in the direction of the key that the player is pressing.
	 * 
	 * @param keycode: The key value of the key being released
	 */
	@Override
	public boolean keyUp(int keycode)
	{
		switch (keycode)
		{
		// sets the player jumpp boolean to false
			case Keys.UP:
			{
				player.setJump(false);
				break;
			}

			// sets the player moveRight boolean to false
			case Keys.RIGHT:
			{
				player.setRight(false);
				break;
			}

			// sets the player moveLeft boolean to false
			case Keys.LEFT:
			{
				player.setLeft(false);
				break;
			}
		}
		return false;
	}

	/**
	 * Ensures that only one instance of GamePlayInputManager is created
	 * @return gpiManager: the only instance of GamePlayInputManager
	 */
	public static GamePlayInputManager createInstance(Player player)
	{
		//singleton if statement
		if(gpiManager==null)
		{
			gpiManager = new GamePlayInputManager(player);
		}
		//return the only instance of GamePlayInputManager
		return gpiManager;
	}

	
	/** Universial way to delete the one instance of GamePLayerInputManager */
	public static void deleteInstance()
	{
		gpiManager=null;
	}
	
	
	
	
	
	// everything below here is useless
	
	@Override
	public boolean keyTyped(char character)
	{
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}

	
}