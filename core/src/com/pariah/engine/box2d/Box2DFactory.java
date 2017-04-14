package com.pariah.engine.box2d;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.pariah.engine.objects.Player;
import com.pariah.runner.Constants;

public class Box2DFactory
{

	public static OrthogonalTiledMapRenderer createTiledMapRenderer(TiledMap map)
	{
		return new OrthogonalTiledMapRenderer(map);
	}

	public static World createWorld()
	{
		World w = new World(new Vector2(0f, -50f), true);
		return w;
	}

	public static Player createPlayer(World world)
	{
		return Player.createInstance(30, 30, 1, 1, Constants.PLAYER_CAT,
				Constants.FRONT_CAT, world, BodyType.DynamicBody);
	}

}
