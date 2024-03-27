package com.example.snakegame;

import android.content.Context;

public class GameState {
    //Tracks whether the thread is running or not
    private static volatile boolean mThreadRunning = false;
    //Tracks whether the game is paused or not
    private static volatile boolean mPaused = true;
    //Tracks whether the game is over or not
    private static volatile boolean mGameOver = true;
    //Tracks whether the game should draw objects in current frame
    private static volatile boolean mDrawing = false;
    private GameStarter gameStarter;
    private int mScore;
    private boolean snakeToTurnLeft = false;
    private boolean snakeToTurnRight = false;

    GameState(GameStarter gs, Context context) {
        gameStarter = gs;


    }

    void increaseScore() {
        mScore++;
    }

    int getScore() {
        return mScore;
    }

    void pause() {
        mPaused = true;
    }

    void resume() {
        mGameOver = false;
        mPaused = false;
    }

    void stopEverything() {
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }

    boolean getThreadRunning() {
        return mThreadRunning;
    }

    void startThread() {
        mThreadRunning = true;
    }

    private void stopDrawing() {
        mDrawing = false;
    }

    private void startDrawing() {
        mDrawing = true;
    }

    boolean getDrawing() {
        return mDrawing;
    }

    boolean getPaused() {
        return mPaused;
    }

    boolean getGameOver() {
        return mGameOver;
    }

    public boolean isSnakeToTurnRight() {
        return snakeToTurnRight;
    }

    public void setSnakeToTurnRight() {
        snakeToTurnRight = true;
    }

    public boolean isSnakeToTurnLeft() {
        return snakeToTurnLeft;
    }

    public void setSnakeToTurnLeft() {
        snakeToTurnLeft = true;
    }

    void startNewGame() {
        mScore = 0;
        stopDrawing(); //Drawing mutex lock
        gameStarter.deSpawnRespawn();
        resume();
        startDrawing(); //Drawing mutex unlock
    }

    //Called when snake hits self or edge of screen
    void death(SoundEngine se) {
        se.playDeath();
        pause();
        endGame();
    }

    private void endGame() {
        mGameOver = true;
        mPaused = true;
    }
}