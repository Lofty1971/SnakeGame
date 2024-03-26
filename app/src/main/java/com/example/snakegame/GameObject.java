package com.example.snakegame;

import android.graphics.Canvas;
import android.graphics.Paint;

interface GameObject {
    void draw(Canvas canvas, Paint paint);
    void move();
}
