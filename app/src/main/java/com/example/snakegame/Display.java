package com.example.snakegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

class Display {
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private int buttonWidthScalar = 16;
    private int buttonHeightScalar = 7;
    private int buttonPaddingScalar = 90;
    private int leftRightButtonVisibility = 0;
    private int pauseButtonVisibility = 100;
    private int numBlocksWide = 40;
    private int numBlocksHigh;
    private int mBlockSize;
    private Bitmap background;
    private Typeface comicSansTypeface;
    private ArrayList<Rect> controls;

    static int LEFT = 0;
    static int RIGHT = 1;
    static int PAUSE = 2;

    Display(Context context, Point size){
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x/50;
        mBlockSize = mScreenWidth/numBlocksWide;
        numBlocksHigh = mScreenHeight / (mScreenWidth/numBlocksWide);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.mario);
        comicSansTypeface = Typeface.createFromAsset(context.getAssets(), "font/comic_sans.ttf");

        prepareControls();
    }

    private void prepareControls(){
        int buttonWidth = mScreenWidth / buttonWidthScalar;
        int buttonHeight = mScreenHeight / buttonHeightScalar;
        int buttonPadding = mScreenWidth / buttonPaddingScalar;

        Rect left = new Rect(0, 0, mScreenWidth/2, mScreenHeight);
        Rect right = new Rect(mScreenWidth/2, 0, mScreenWidth, mScreenHeight);
        Rect pause = new Rect(
                mScreenWidth-buttonPadding-buttonWidth,
                mScreenHeight-buttonHeight-buttonPadding,
                mScreenWidth-buttonPadding,
                mScreenHeight-buttonPadding
                );
        //partially pointless, maybe change from ArrayList to just Pause being passed since it's the only control drawn?
        controls = new ArrayList<>();
        controls.add(LEFT, left);
        controls.add(RIGHT, right);
        controls.add(PAUSE, pause);
    }

    void draw(Canvas c, Paint p, GameState gs){
        //c.drawBitmap(background, 0, 0, null);
        p.setTypeface(comicSansTypeface);
        p.setTextSize(mTextFormatting);
        c.drawText("Score: "+gs.getScore(),mTextFormatting,mTextFormatting, p);
        Rect bounds = new Rect();
        p.getTextBounds("Ivan Gutierrez", 0, "Ivan Gutierrez".length(), bounds);
        int xPos = (c.getWidth()-bounds.width());
        int yPos = (int) (( - p.ascent()));
        c.drawText("Austin Loft", xPos, yPos, p);
        c.drawText("Ivan Gutierrez", xPos, yPos * 2, p);

        if(gs.getGameOver()){
            centerText(c, p, "Tap To Play!");
        }
        if(gs.getPaused() && !gs.getGameOver()){
            centerText(c, p, "PAUSED");
        }
        drawControls(c, p);
    }

    //to calculate the center of the screen including size of text offset
    private void centerText(Canvas c, Paint p, String s){
        Rect bounds = new Rect();
        p.setTextSize(mTextFormatting*5);
        //bounds is set to the border of text
        p.getTextBounds(s, 0, s.length(), bounds);
        //equals x value where text should start to be centered (subtracting width of text
        int xPos = ((c.getWidth()/2)-(bounds.width() / 2));
        //equals y value of where text should start to be centered
        int yPos = (int) ((c.getHeight() / 2) - ((p.descent() + p.ascent()) / 2)) ;
        c.drawText(s,
                xPos, yPos, p);
    }

    private void drawControls(Canvas c, Paint p){
        //not using a loop here due to only having 3 controls and needing special alpha values for 2 of 3
        // alpha of 10 for nearly transparent button
        p.setColor(Color.argb(leftRightButtonVisibility, 255,255,255));
        Rect r = controls.get(0); //left
        c.drawRect(r.left, r.top, r.right, r.bottom, p);
        r = controls.get(1); //right
        c.drawRect(r.left, r.top, r.right, r.bottom, p);
        r = controls.get(2);
        p.setColor(Color.argb(pauseButtonVisibility, 255,255,255));
        c.drawRect(r.left, r.top, r.right, r.bottom, p);
        p.setTextSize(mTextFormatting);
        p.setColor(Color.argb(255,255,255,255));
        c.drawText("Play/Pause", r.left-250, r.top + 100, p);
    }

    ArrayList<Rect> getControls(){
        return controls;
    }

    public int getNumBlocksHigh() {
        return numBlocksHigh;
    }

    public int getNumBlocksWide() {
        return numBlocksWide;
    }

    public int getBlockSize() {
        return mBlockSize;
    }
}
