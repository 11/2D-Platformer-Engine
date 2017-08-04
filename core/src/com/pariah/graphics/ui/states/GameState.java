package com.pariah.graphics.ui.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pariah.engine.box2d.Box2DFactory;
import com.pariah.engine.managers.AssetManager;
import com.pariah.engine.managers.AudioManager;
import com.pariah.engine.managers.ContactManager;
import com.pariah.engine.managers.GamePlayInputManager;
import com.pariah.engine.managers.LevelManager;
import com.pariah.engine.managers.LightManager;
import com.pariah.engine.objects.Player;
import com.pariah.runner.Constants;

import box2dLight.RayHandler;

/**
 * Default GameState for all level based Screens
 * 
 * @author Douglas Rudolph
 */
public abstract class GameState extends State
{

	//load managers
	protected GamePlayInputManager	inputManager;
	protected AssetManager			assetManager;
	protected AudioManager			audioManager;
	protected LevelManager			levelManager;
	protected LightManager			lightManager;
	protected ContactManager		contactManager;

	// libGDX game management related objects
	protected OrthographicCamera	box2DCamera;

	// camera that manages the view of the player
	protected OrthographicCamera	levelCamera;

	// main SpriteBatch object that will be used to render textures to the
	// screen
	protected static SpriteBatch	batch;

	// stage holds what objects are being updated and rendered on screen
	protected Stage					stage;


	/*
	 * LevelManager loads .tmx files and manages the creation of box2D bodies
	 * over tiles
	 * World object is used to load all box2D related objects into memory
	 * Box2DDebugRenderer is used to render the box2D bodies as boxes during
	 * debug
	 * RayHandlder loads and renders lights inside a box2D world
	 */
	protected World					box2DWorld;
	protected World					lightWorld;
	protected Box2DDebugRenderer	debugRenderer;
	protected RayHandler			handler;

	// player object
	protected Player				player;

	// layer manager
	protected boolean				layerTracker;

	/**
	 * Loads: camera,
	 * Box2dDebugRenderer,
	 * the physics world,
	 * lights,
	 * player.
	 * and assets for the level
	 */
	@Override
	public void show()
	{
		box2DCamera = new OrthographicCamera();
		box2DCamera.setToOrtho(false, Gdx.graphics.getWidth()/Constants.BOX_SCALE, Gdx.graphics.getHeight()/Constants.BOX_SCALE);
		// Demo 2.) box2DCamera.setToOrtho(false, Gdx.graphics.getWidth()/*/ Constants.BOX_SCALE*/, Gdx.graphics.getHeight()/*/ Constants.BOX_SCALE*/);
		levelCamera = new OrthographicCamera();
		levelCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		box2DWorld = Box2DFactory.createWorld();
		lightWorld = Box2DFactory.createWorld();
		player     = Box2DFactory.createPlayer(box2DWorld);
		
		debugRenderer = new Box2DDebugRenderer();
		
		batch         = new SpriteBatch();
		batch.setProjectionMatrix(box2DCamera.combined);
		
		//TODO: Test adding lights to the level using the box2DWorld
		//handler       = new RayHandler(lightWorld);
		handler       = new RayHandler(box2DWorld);
		handler.setCombinedMatrix(box2DCamera.combined);
		
		stage = new Stage();
		
		inputManager = GamePlayInputManager.createInstance(player);
		Gdx.input.setInputProcessor(inputManager);

		contactManager = ContactManager.createInstance(player);
		assetManager = AssetManager.createInstance();
		levelManager = LevelManager.createInstance();
		audioManager = AudioManager.createInstnace();
		
		lightManager = LightManager.createInstance(handler);


		box2DWorld.setContactListener(contactManager);
	}

	@Override
	public void render(float delta)
	{

	}

	@Override
	public void hide()
	{
		
	}

	/**
	 * 
	 */
	@Override
	public void dispose()
	{
		box2DWorld.dispose();
		stage.dispose();
		batch.dispose();
		debugRenderer.dispose();
		handler.dispose();
		Player.deleteInstance();
		GamePlayInputManager.deleteInstance();
		ContactManager.deleteInstance();

	}

	/**
	 * @return Player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * @return Camera
	 */
	public OrthographicCamera getBox2DCamera()
	{
		return box2DCamera;
	}

	/**
	 * 
	 * @return Camera
	 */
	public OrthographicCamera getLevelCamera()
	{
		return levelCamera;
	}

	/**
	 * @return World
	 */
	public World getWorld()
	{
		return box2DWorld;
	}

	/**
	 * 
	 * @return SpriteBatch
	 */
	public static SpriteBatch getSpriteBatch()
	{
		return batch;
	}

	/**
	 * @param AssetManager
	 */
	public AssetManager getAssetManager()
	{
		return assetManager;
	}

	/**
	 * 
	 * @return RayHandler
	 */
	public RayHandler getRayHandler()
	{
		return handler;
	}

	/**
	 * sets what layer to be rendered
	 * 
	 * @param i
	 */
	public void setLayerTracker(boolean layerTracker)
	{
		this.layerTracker = layerTracker;
	}

	// USELESS
	@Override
	public void resize(int width, int height)
	{

	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}
}
