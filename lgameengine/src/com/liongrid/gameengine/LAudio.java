package com.liongrid.gameengine;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;


public class LAudio extends LBaseObject {

	AssetManager assets;
	SoundPool soundPool;
	HashMap<String,Integer> sounds = new HashMap<String, Integer>();
	
	
	public LAudio(Context context, int maxSimiltaneousSounds) {
		this.assets = context.getAssets();
		this.soundPool = new SoundPool(maxSimiltaneousSounds, AudioManager.STREAM_MUSIC, 0);
	}
	
	
	/**
	 * Loads a sound and saves it in a hashmap. Remember the key :)
	 * @param key - the hashmap key. Used in playSound()
	 * @param fileName 
	 */
	public void loadSound(String key, String fileName){
		AssetFileDescriptor assetDescriptor;
		try {
			assetDescriptor = assets.openFd(fileName);
			sounds.put(key, soundPool.load(assetDescriptor,0 ));
		} catch (IOException e) {
			throw new RuntimeException("Couldn`t load sound '"+fileName+"'");
		}
		
	}
	
	/**
	 * @param key - the key used in addSound
	 * @param volume - 0 is silent. 1 is max
	 */
	public void playSound(String key,float volume){
		soundPool.play(sounds.get(key), volume, volume, 0, 0, 1);
	}
	
	@Override
	public void update(float dt, LBaseObject parent) {
		
	}

	@Override
	public void reset() {		
	}

}
