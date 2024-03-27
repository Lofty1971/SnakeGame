package com.example.snakegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.SoundPool;

final class GameState {
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
    /*
    May need additional variables here like the Point array for segment locations
     */


    void increaseScore(){
        mScore++;
    }

    int getScore(){
        return mScore;
    }

    void pause(){
        mPaused = true;
    }

    void resume(){
        mGameOver = false;
        mPaused = false;
    }

    void stopEverything(){
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }

    boolean getThreadRunning(){
        return mThreadRunning;
    }

    void startThread(){
        mThreadRunning = true;
    }

    private void stopDrawing(){
        mDrawing = false;
    }

    private void startDrawing(){
        mDrawing = true;
    }

    boolean getDrawing(){
        return mDrawing;
    }

    boolean getPaused(){
        return mPaused;
    }

    boolean getGameOver(){
        return mGameOver;
    }



    GameState(GameStarter gs, Context context){
        gameStarter = gs;


    }

    void startNewGame(){
        mScore = 0;
        stopDrawing(); //Drawing mutex lock
        gameStarter.deSpawnRespawn();
        resume();
        startDrawing(); //Drawing mutex unlock
    }

    //Called when snake hits self or edge of screen
    void death(SoundEngine se){
        se.playDeath();
        pause();
        endGame();
    }

    private void endGame(){
        mGameOver = true;
        mPaused = true;
    }
}
