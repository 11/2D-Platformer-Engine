package com.pariah.engine.managers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager
{
	private static AudioManager				audioManager;

	private static HashMap<String, Music>	songHashMap;

	private AudioManager()
	{

	}

	static
	{
		songHashMap = new HashMap<String, Music>();
	}

	public static AudioManager createInstnace()
	{
		if (audioManager == null)
		{
			audioManager = new AudioManager();
		}

		return audioManager;
	}

	public static void deleteInstance()
	{
		audioManager = null;
	}

	public void loadSong(String path, String key)
	{

		Music songToBeAdded = Gdx.audio.newMusic(Gdx.files.internal(path));
		
		songHashMap.put(key, songToBeAdded);
	}
	
	public  Music getSong(String key)
	{
		return songHashMap.get(key);
	}

}
