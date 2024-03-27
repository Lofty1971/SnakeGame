package com.example.snakegame;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

class UIController implements InputObserver{
    public UIController(GameEngineBroadcaster b){
        b.addObserver(this);
    }

    @Override
    public void handleInput(MotionEvent event,
                            GameState gameState, ArrayList<Rect> buttons) {
        int i = event.getActionIndex();
        int x = (int) event.getX(i); //X location of input
        int y = (int) event.getY(i); //y loc of input
        // sets eventType to comparable int of what action occurred
        int eventType = event.getAction() & MotionEvent.ACTION_MASK;
        //if last finger left screen here or if a finger left screen here
        if(eventType==MotionEvent.ACTION_UP || eventType == MotionEvent.ACTION_POINTER_UP){
            if(buttons.get(Display.PAUSE).contains(x, y)){
                //Pause button was pressed
                if(!gameState.getPaused()){
                    gameState.pause();
                }
                else if (gameState.getGameOver()) {
                    gameState.startNewGame();
                }
                else if (gameState.getPaused() && !gameState.getGameOver()) {
                    gameState.resume();
                }
            }
            else if (buttons.get(Display.LEFT).contains(x, y)) {
                // Handle left button press
                gameState.changeSnakeDirection(Direction.LEFT);
            }
            else if (buttons.get(Display.RIGHT).contains(x, y)) {
                // Handle right button press
                gameState.changeSnakeDirection(Direction.RIGHT);
            }
        }
    }
}
