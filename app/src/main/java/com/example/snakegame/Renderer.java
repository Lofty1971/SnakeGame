package com.example.snakegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Renderer {
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;

    Renderer(SurfaceView sh) {
        mSurfaceHolder = sh.getHolder();
        mPaint = new Paint();
    }

    void draw(GameState gs, Display display) {
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 0, 0,
                    0));
            if (gs.getDrawing()) {
                // Draw all the game objects here

            }
            if (gs.getGameOver()) {
                // Draw a background graphic here
            }
            // Now we draw the HUD on top of everything else

            display.draw(mCanvas, mPaint, gs);
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }

    }
}
