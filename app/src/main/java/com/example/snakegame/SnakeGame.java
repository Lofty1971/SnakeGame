package com.example.snakegame;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

import java.io.IOException;
import java.util.ArrayList;
/*
Handles Game logic/gameplay loop
 */
class SnakeGame extends SurfaceView implements Runnable, GameStarter, GameEngineBroadcaster {

    private ArrayList<InputObserver>
            inputObservers = new ArrayList();

    private GameState mGameState;
    private SoundEngine mSoundEngine;
    Display mDisplay;
    Renderer mRenderer;
    UIController mUIController;
    SnakeController mSnakeController;
    // Objects for the game loop/thread
    private Thread mThread = null;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    // A snake ssss
    private Snake mSnake;
    // And an apple
    private Apple mApple;


    // This is the constructor method that gets called
    // from SnakeActivity
    public SnakeGame(Context context, Point size) {
        super(context);

        mUIController = new UIController(this);
        mSnakeController = new SnakeController(this, size);
        mGameState = new GameState(this, context);
        mSoundEngine = new SoundEngine(context);
        mDisplay = new Display(size);
        mRenderer = new Renderer(this);
        // Work out how many pixels each block is
        int blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;


        // Call the constructors of our two game objects
        mApple = new Apple(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

        mSnake = new Snake(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

    }

    public void deSpawnRespawn(){

    }

    public void addObserver(InputObserver o){
        inputObservers.add(o);
    }


    // Handles the game loop
    @Override
    public void run() {
        while (mGameState.getThreadRunning()) {
            long frameStartTime = System.currentTimeMillis();
            if(!mGameState.getPaused()) {
                // Update game objects

            }

            mRenderer.draw(mGameState, mDisplay);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        for(InputObserver o: inputObservers){
            o.handleInput(motionEvent, mGameState, mDisplay.getControls());
        }
        return true;
        /*
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (mPaused) {
                    mPaused = false;
                    newGame();

                    // Don't want to process snake direction for this tap
                    return true;
                }

                // Let the Snake class handle the input
                mSnake.switchHeading(motionEvent);
                break;

            default:
                break;

        }
        return true;
        */

    }

    public void stopThread(){
        mGameState.stopEverything();
        try{
            mThread.join();
        }
        catch (InterruptedException e){
            Log.e("Exception", "stopThread()"+e.getMessage());
        }
    }

    public void startThread(){
        mGameState.startThread();

        mThread = new Thread(this);
        mThread.start();
    }
}
