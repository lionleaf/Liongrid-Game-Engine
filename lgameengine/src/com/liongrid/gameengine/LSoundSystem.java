/*
 * http://code.google.com/p/replicaisland/source/browse/trunk/src/com/replica/replicaisland/SoundSystem.java 
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liongrid.gameengine;

import java.util.Comparator;

import com.liongrid.gameengine.tools.LFixedSizeArray;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class LSoundSystem extends LBaseObject {
	private static final int MAX_STREAMS = 8;
    private static final int MAX_SOUNDS = 32;
    private static final SoundComparator sSoundComparator = new SoundComparator();
    
    public static final int PRIORITY_LOW = 0;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_HIGH = 2;
    public static final int PRIORITY_MUSIC = 3;

    private SoundPool mSoundPool;
    private LFixedSizeArray<Sound> mSounds;
    private Sound mSearchDummy;
    private boolean mSoundEnabled = true;
    private int[] mLoopingSounds;
    
    
    public LSoundSystem() {
        super();
        mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        mSounds = new LFixedSizeArray<Sound>(MAX_SOUNDS, sSoundComparator);
        mSearchDummy = new Sound();
        mLoopingSounds = new int[MAX_STREAMS];
        for (int x = 0; x < mLoopingSounds.length; x++) {
                mLoopingSounds[x] = -1;
        }
    }
    
    @Override
    public void reset() {
        mSoundPool.release();
        mSounds.clear();
        mSoundEnabled = true;
        for (int x = 0; x < mLoopingSounds.length; x++) {
                mLoopingSounds[x] = -1;
        }
    }

    public Sound load(int resource) {
        final int index = findSound(resource);
        Sound result = null;
        if (index < 0) {
            // new sound.
        	Context context = LGamePointers.context;
           if (context != null) {
               result = new Sound();
               result.resource = resource;
               result.soundId = mSoundPool.load(context, resource, 1);
               mSounds.add(result);
               mSounds.sort(false);
           }
        } else {
            result = mSounds.get(index);
        }
        
        return result;
    }
    
    synchronized public final int play(Sound sound, boolean loop, int priority) {
        int stream = -1;
        if (mSoundEnabled) {
                stream = mSoundPool.play(sound.soundId, 1.0f, 1.0f, priority, loop ? -1 : 0, 1.0f);
                if (loop) {
                        addLoopingStream(stream);
                }
        } 
        
        return stream;
    }
    
    synchronized public final int play(Sound sound, boolean loop, int priority, float volume, float rate) {
        int stream = -1;
        if (mSoundEnabled) {
                stream = mSoundPool.play(sound.soundId, volume, volume, priority, loop ? -1 : 0, rate);
                if (loop) {
                        addLoopingStream(stream);
                }
        } 
        
        return stream;
    }
    
    public final void stop(int stream) {
        mSoundPool.stop(stream);
        removeLoopingStream(stream);
    }
    
    public final void pause(int stream) {
        mSoundPool.pause(stream);
    }
    
    public final void resume(int stream) {
       mSoundPool.resume(stream);
    }
    
    public final void stopAll() {
        final int count = mLoopingSounds.length;
        for (int x = count - 1; x >= 0; x--) {
                if (mLoopingSounds[x] >= 0) {
                        stop(mLoopingSounds[x]);
                }
        }
    }
    
    // HACK: There's no way to pause an entire sound pool, but if we
    // don't do something when our parent activity is paused, looping
    // sounds will continue to play.  Rather that reproduce all the bookkeeping
    // that SoundPool does internally here, I've opted to just pause looping
    // sounds when the Activity is paused.
    public void pauseAll() {
        final int count = mLoopingSounds.length;
        for (int x = 0; x < count; x++) {
                if (mLoopingSounds[x] >= 0) {
                        pause(mLoopingSounds[x]);
                }
        }
    }
    
    private void addLoopingStream(int stream) {
        final int count = mLoopingSounds.length;
        for (int x = 0; x < count; x++) {
                if (mLoopingSounds[x] < 0) {
                        mLoopingSounds[x] = stream;
                        break;
                }
        }
    }
    
    private void removeLoopingStream(int stream) {
        final int count = mLoopingSounds.length;
        for (int x = 0; x < count; x++) {
                if (mLoopingSounds[x] == stream) {
                        mLoopingSounds[x] = -1;
                        break;
                }
        }
    }
    
    private final int findSound(int resource) {
        mSearchDummy.resource = resource;
        return mSounds.find(mSearchDummy, false);
    }

        synchronized public final void setSoundEnabled(boolean soundEnabled) {
                mSoundEnabled = soundEnabled;
        }
        
        public final boolean getSoundEnabled() {
                return mSoundEnabled;
        }
        
        
        
    public class Sound {
        public int resource;
        public int soundId;
    }
    
    /** Comparator for sounds. */
    private final static class SoundComparator implements Comparator<Sound> {
        public int compare(final Sound object1, final Sound object2) {
            int result = 0;
            if (object1 == null && object2 != null) {
                result = 1;
            } else if (object1 != null && object2 == null) {
                result = -1;
            } else if (object1 != null && object2 != null) {
                result = object1.resource - object2.resource;
            }
            return result;
        }
    }

	@Override
	public void update(float dt, LBaseObject parent) {}


	
}
