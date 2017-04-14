package com.pariah.runner;

import com.badlogic.gdx.Game;
import com.pariah.graphics.ui.levels.states.Demo;
import com.pariah.graphics.ui.states.State;

/**
 * MainRunner.java
 * - extends Game
 * - Manages how states are transitioned into one another,
 * What screen is currently being rendered.
 * 
 * @author Douglas Rudolph
 */
public class PariahRunner extends Game
{

	private static PariahRunner	mainRunnerInstance;

	// static instance of the screen that is always rendering
	private static State	currentScreen;

	// called
	static
	{
		currentScreen = new Demo();
	}

	/**
	 * ONLY CALLED ON START UP
	 * 
	 * Sets the screen to Demo on start up
	 */
	@Override
	public void create()
	{
		setScreen(currentScreen);
	}

	/**
	 * 
	 * @return currentScreen gets the current Screen thats being rendered
	 */
	public static State getCurrentScreen()
	{
		return PariahRunner.currentScreen;
	}

	/**
	 * TODO: Set it so the dispose is called on the previous screen
	 * TODO: Called the hide method when switching state
	 * TODO: allow for screens to have transition animations
	 * 
	 * Sets the state to be transitioned into.
	 * 
	 * @param state: The state the game is going to transition to
	 */
	public void setState(State state)
	{
		currentScreen = state;
		currentScreen.show();
	}
	
	public static PariahRunner createInstance()
	{
		if (mainRunnerInstance == null)
		{
			mainRunnerInstance = new PariahRunner();
		}

		return mainRunnerInstance;
	}

	public void disposeInstnace()
	{
		mainRunnerInstance = null;
	}

	public static PariahRunner getMainRunner()
	{
		return mainRunnerInstance;
	}
	
}