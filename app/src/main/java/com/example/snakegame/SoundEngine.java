package com.example.snakegame;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;

/*
Handles sound playing
 */
public class SoundEngine {
    private SoundPool mSP;
    private int mGetApple = -1;
    private int mSnakeDeath = -1;

    SoundEngine(Context c){
        // Initialize the SoundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            AssetManager assetManager = c.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("get_apple.ogg");
            mGetApple = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_death.ogg");
            mSnakeDeath = mSP.load(descriptor, 0);

        } catch (IOException e) {
            // Error
        }
    }

    void playDeath(){
        mSP.play(mSnakeDeath, 1, 1, 0, 0, 1);
    }

    void playEat(){
        mSP.play(mGetApple, 1, 1, 0, 0, 1);
    }

}
