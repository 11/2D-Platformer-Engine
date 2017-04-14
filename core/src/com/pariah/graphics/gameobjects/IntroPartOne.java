package com.pariah.graphics.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pariah.graphics.ui.levels.states.LevelSelect;
import com.pariah.graphics.ui.states.GameState;
import com.pariah.runner.PariahRunner;

import box2dLight.RayHandler;

/**
 * 
 * @author Douglas Rudolph
 */
public class IntroPartOne extends GameState
{

	private Stage				stage;
	private World				world;
	private RayHandler			handler;
	private OrthographicCamera	camera;

	private Music				backgroundSong;
	
	/**
	 * 
	 */
	@Override
	public void show()
	{
		super.show();
		// box2d & camera stuff
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.setToOrtho(true);
		handler = new RayHandler(world);
		handler.setCombinedMatrix(camera.combined);
		
		// new
		//new PointLight(handler,200,Color.WHITE,500,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2-150);
		//new PointLight(handler,200,Color.WHITE,400,Gdx.graphics.getWidth()/2+250,100);
		//new PointLight(handler,200,Color.WHITE,400,Gdx.graphics.getWidth()/2,400);

		// music loading
		backgroundSong = Gdx.audio.newMusic(Gdx.files
				.internal("Intro/Main Menu Violin.mp3"));
		backgroundSong.setVolume(1f);
		backgroundSong.play();

		// stage and image loading
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		assetManager.loadAsset("Intro/Thumbs Up.png", "logo");
		assetManager.loadAsset("Intro/Presents.png", "presents");
		assetManager.loadAsset("Intro/Background.png", "background");
		assetManager.loadAsset("Intro/Pariah_edited-bigger file.png", "title");

		// load logo
		Image logo = new Image(assetManager.getAsset("logo"));
		logo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// load presents image
		Image present = new Image(assetManager.getAsset("presents"));
		present.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// load background
		Image background = new Image(assetManager.getAsset("background"));
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// load title
		Image title = new Image(assetManager.getAsset("title"));
		title.setPosition(Gdx.graphics.getWidth()/2- title.getWidth()/2, Gdx.graphics.getHeight()/2-title.getHeight()/2+100);

		// add actions
		logo.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(.5f),
				Actions.delay(1.5f), Actions.fadeOut(1.55f)));

		present.addAction(Actions.sequence(Actions.alpha(0),
				Actions.delay(1.7f), Actions.fadeIn(1f), Actions.delay(2f),
				Actions.fadeOut(2.0f)));

		background.addAction(Actions.sequence(Actions.alpha(0),
				Actions.delay(4.0f), Actions.fadeIn(3.5f)));

		title.addAction(Actions.sequence(
				Actions.alpha(0),
				Actions.delay(6.0f),
				Actions.fadeIn(5.0f),
				Actions.delay(4.0f),
				Actions.repeat(
						200000,
						Actions.sequence(
								Actions.fadeOut(1.0f),
								Actions.fadeIn(3.0f), 
								Actions.delay(2.0f)
										))));

		//add actors to the stage
		stage.addActor(logo);
		stage.addActor(present);
		stage.addActor(background);
		stage.addActor(title);
	}

	/**
	 * renders and updates thes stage
	 * @oaram delta: delta time
	 */
	@Override
	public void render(float delta)
	{
		
		if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.ENTER))
		{
			this.dispose();
			PariahRunner.getMainRunner().setScreen(new LevelSelect());
		}
		
		
		stage.act(Gdx.graphics.getDeltaTime());

		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		//handler.render();

	}

	@Override
	public void dispose()
	{
		super.dispose();
		backgroundSong.dispose();
		stage.clear();
		stage.dispose();
	}

	@Override
	public void resize(int width, int height)
	{

	}

	@Override
	public void hide()
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
