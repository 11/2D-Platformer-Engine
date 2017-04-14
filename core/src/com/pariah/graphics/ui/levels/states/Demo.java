package com.pariah.graphics.ui.levels.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.pariah.graphics.ui.states.GameState;
import com.pariah.runner.Constants;

/**
 * Demo.java
 * 
 * Used Strictly for testing new ideas for the game and adding
 * mechanics.
 * 
 * @author Douglas Rudolph
 */
public class Demo extends GameState
{
	/**
	 * Loads: camera, Box2dDebugRenderer, the physics world, lights, player. and
	 * assets for the level
	 */
	@Override
	public void show()
	{
		super.show();
		levelManager.loadLevel("Demo/testTileMap.tmx", box2DWorld,levelCamera);
		
		audioManager.loadSong("Demo/2_silver_moons.mp3", "BackgroundSong");
		Music backgroundSong=audioManager.getSong("BackgroundSong");
		backgroundSong.play();

		// Stage loading		
		assetManager.loadAsset("Demo/Background.png", "Background");
		Image background = new Image(assetManager.getAsset("Background"));
		background.setSize(Gdx.graphics.getWidth() * 1.5f,
				Gdx.graphics.getHeight());
		background.addAction(Actions.sequence(Actions.alpha(0),
				Actions.fadeIn(2.0f)));
		stage.addActor(background);
	}

	/**
	 * updates the physics world, renders the world after an update clears the
	 * buffer scrolls the tile sheet left and right
	 */
	@Override
	public void render(float delta)
	{
		// update camera, stage
		box2DWorld.step(1 / 60f, 6, 2);

		box2DCamera.position.set(player.getBody().getPosition().x, player
				.getBody().getPosition().y, 0);
		box2DCamera.update();

		levelCamera.position.set(player.getBody().getPosition().x
				* Constants.BOX_SCALE, player.getBody().getPosition().y
				* Constants.BOX_SCALE, 0);
		levelCamera.update();

		stage.act(delta);

		// clearing buffer
		Gdx.gl20.glClearColor(0, 0, 0, 0);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// render stage
		stage.draw();
		debugRenderer.render(box2DWorld, box2DCamera.combined);
		levelManager.render(layerTracker);
		debugRenderer.render(box2DWorld, box2DCamera.combined);


		// grab input
		player.updateMotion();
	}

	@Override
	public void dispose()
	{
		super.dispose();
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
