package com.example.snakegame;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import java.util.ArrayList;


//Handles Game logic/gameplay loop
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

    // A snake ssss
    private Snake mSnake;
    // And an apple
    private Apple mApple;


    // This is the constructor method that gets called from SnakeActivity
    public SnakeGame(Context context, Point size) {
        super(context);

        mUIController = new UIController(this);
        mSnakeController = new SnakeController(this, size);
        mGameState = new GameState(this, context);
        mSoundEngine = new SoundEngine(context);
        mDisplay = new Display(size);
        mRenderer = new Renderer(this);


        // Call the constructors of our two game objects
        mApple = new Apple(context,
                new Point(mDisplay.getNumBlocksWide(),
                        mDisplay.getNumBlocksHigh()),
                mDisplay.getBlockSize());

        mSnake = new Snake(context,
                new Point(mDisplay.getNumBlocksWide(),
                        mDisplay.getNumBlocksHigh()),
                mDisplay.getBlockSize());

    }

    public void deSpawnRespawn() {

    }

    public void addObserver(InputObserver o) {
        inputObservers.add(o);
    }


    // Handles the game loop
    @Override
    public void run() {
        while (mGameState.getThreadRunning()) {
            long frameStartTime = System.currentTimeMillis();
            if (!mGameState.getPaused()) {
                // Update game objects
                mSnake.move();

            }

            mRenderer.draw(mGameState, mDisplay, mSnake, mApple);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        for (InputObserver o : inputObservers) {
            o.handleInput(motionEvent, mGameState, mDisplay.getControls());
        }
        return true;

    }

    public void stopThread() {
        mGameState.stopEverything();
        try {
            mThread.join();
        } catch (InterruptedException e) {
            Log.e("Exception", "stopThread()" + e.getMessage());
        }
    }

    public void startThread() {
        mGameState.startThread();

        mThread = new Thread(this);
        mThread.start();
    }
}