package com.liongrid.gameengine;

import java.io.IOException;

import android.R.bool;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

public class LMusic extends LBaseObject implements OnCompletionListener{
	private MediaPlayer mMediaPlayer;
	/**
	 * Might be set from another thread. Beware of concurrency issues!!
	 */
	private boolean mIsPrepared = false;

	public LMusic(String filename) {
		Context context = LGamePointers.context;
		//TODO WAT DO IF CONTEXT NULL?!
		mMediaPlayer = new MediaPlayer();
		
		try{
			//TODO prepare might stall a bit too long?!
			AssetFileDescriptor assDescriptor = context.getAssets().openFd(filename);
			
			mMediaPlayer.setDataSource(assDescriptor.getFileDescriptor(), 
					assDescriptor.getStartOffset(),assDescriptor.getLength());
			
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.prepare();
			mIsPrepared = true;
			mMediaPlayer.setOnCompletionListener(this);
		} catch(Exception e){
			throw new RuntimeException("Couldn`t load music");
		}
	}

	public void dispose(){
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
		}
		mMediaPlayer.release();
	}

	public boolean isLooping(){
		return mMediaPlayer.isLooping();
	}

	public boolean isPlaying(){
		return mMediaPlayer.isPlaying();
	}

	public boolean isStopped(){
		return !mIsPrepared;
	}

	public void play(){
		if(mMediaPlayer.isPlaying()){
			Log.d("Infecto music", "wooho, we are playing");
			return;
		}
			

		try {
			if (mIsPrepared) {

				mMediaPlayer.prepare();

			}
			mMediaPlayer.start();
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setLooping(boolean looping){
		mMediaPlayer.setLooping(looping);
	}
	
	public void setVolume(float volume){
		mMediaPlayer.setVolume(volume, volume);
	}
	
	public void stop() {
		mMediaPlayer.stop();
		synchronized (this) {
			mIsPrepared = false;
		}
	}
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		synchronized (this) {
			mIsPrepared = false;
		}
		
	}


	@Override
	public void update(float dt, LBaseObject parent) {

	}

	@Override
	public void reset() {

	}

	public void pause() {
		mMediaPlayer.pause();
		
	} 

	public void resume() {
		play();
	}

}
