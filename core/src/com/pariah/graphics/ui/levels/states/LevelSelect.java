package com.pariah.graphics.ui.levels.states;

import box2dLight.ConeLight;
import box2dLight.PointLight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.pariah.engine.managers.AssetManager;
import com.pariah.engine.managers.AudioManager;
import com.pariah.engine.managers.LevelManager;
import com.pariah.graphics.ui.states.GameState;

/**
 * 
 * @author Douglas Rudolph
 */
public class LevelSelect extends GameState
{
	/**
	 * 
	 */
	@Override
	public void show()
	{
		super.show();
		
		new PointLight(handler, 4, Color.WHITE, 100, 50,60);

		new ConeLight(handler, 3, Color.WHITE, 45, 10, 40, 270f, 8f);
		new ConeLight(handler, 3, Color.WHITE, 45, 30, 40, 270f, 8f);
		new ConeLight(handler, 3, Color.WHITE, 45, 50, 40, 270f, 8f);
		
		levelManager.loadLevel("StorySelect/StorySelect.tmx", box2DWorld, levelCamera);

		audioManager.loadSong("StorySelect/Tension.mp3", "Tension");
		Music levelSong = audioManager.getSong("Tension");
		levelSong.play();
		
		assetManager.loadAsset("StorySelect/tempGraveAsset.png", "BethGrave");
		Texture bethGrave = assetManager.getAsset("BethGrave");
		Image bethGraveImage = new Image(bethGrave);
		bethGraveImage.setBounds(65,125,64,64);
		bethGraveImage.addAction(Actions.sequence(Actions.alpha(0),
				Actions.delay(1f), Actions.fadeIn(3f)));
		
		assetManager.loadAsset("StorySelect/tempGraveAsset.png", "OliverGrave");
		Texture oliverGrave= assetManager.getAsset("OliverGrave");
		Image oliverGraveImage = new Image(oliverGrave);
		oliverGraveImage.setBounds(265,125,64,64);
		oliverGraveImage.addAction(Actions.sequence(Actions.alpha(0),
				Actions.delay(1f), Actions.fadeIn(3f)));
		
		assetManager.loadAsset("StorySelect/tempGraveAsset.png","ErinGrave");
		Texture erinGrave = assetManager.getAsset("ErinGrave");
		Image erinGraveImage = new Image(erinGrave);
		erinGraveImage.setBounds(465,125,64,64);
		erinGraveImage.addAction(Actions.sequence(Actions.alpha(0),
				Actions.delay(1f), Actions.fadeIn(3f)));
		
		assetManager.loadAsset("StorySelect/Background.png", "Background");
		Texture background = (assetManager.getAsset("Background"));

		Image backgroundImage = new Image(background);
		backgroundImage.setBounds(0, -40, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		backgroundImage.addAction(Actions.sequence(Actions.alpha(0),
				Actions.delay(1f), Actions.fadeIn(3f)));

		stage.addActor(backgroundImage);
		stage.addActor(oliverGraveImage);
		stage.addActor(erinGraveImage);
		stage.addActor(bethGraveImage);
		
	}

	/**
	 * 
	 */
	@Override
	public void render(float delta)
	{
		Gdx.gl20.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glClearColor(0, 0, 0, 0);

		box2DWorld.step(1 / 60f, 6, 2);
		stage.act();
		handler.update();
		player.updateMotion();
		
		stage.draw();
		levelManager.render(this.layerTracker);
		debugRenderer.render(box2DWorld, box2DCamera.combined);
		handler.render();
	}

	@Override
	public void dispose()
	{

	}

	@Override
	public void hide()
	{

	}

}
