package com.example.snakegame;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;

class SnakeController implements InputObserver {

    private float mHalf;

    SnakeController(GameEngineBroadcaster b, Point
            size) {
        b.addObserver(this);
        mHalf = size.x / 2;
    }

    @Override
    public void handleInput(MotionEvent event,
                            GameState gameState,
                            ArrayList<Rect> buttons) {
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int eventType = event.getAction()
                & MotionEvent.ACTION_MASK;
        if (eventType == MotionEvent.ACTION_UP || eventType == MotionEvent.ACTION_POINTER_UP) {
            if(!gameState.getPaused() && !gameState.getGameOver()){
                //game is running
                if(x<mHalf){
                    //touch is on left half
                    gameState.setSnakeToTurnLeft();
                }
                else{
                    //touch is on right half
                    gameState.setSnakeToTurnRight();
                }
            }

        }
    }
}
