package com.example.myapplication2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class MyGameCharacter {
    private Bitmap image;
    private int x, y;
    private int xVelocity = 2;
    private int yVelocity = 2;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public MyGameCharacter(Bitmap bmp){
        image = bmp;
        x = 100;
        y = 100;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }


    public void update(){
        if (x < 0 && y < 0) {
            x = screenWidth / 2;
            y = screenHeight / 2;
        } else {
            x += xVelocity;
            y += yVelocity;
            if ((x > screenWidth - image.getWidth()) || (x < 0)) {
                xVelocity = xVelocity*-1;
            }
            if ((y > screenHeight - image.getHeight()) || (y < 0)) {
                yVelocity = yVelocity*-1;
            }
        }
    }

    public boolean isWin(float x_touch, float y_touch) {
        double diff = Math.sqrt(Math.pow((x_touch - x - 150), 2) + Math.pow((y_touch - y - 150), 2));
        Log.d("diff", diff + "");
        if (diff < 100) {
            Log.d("win", "winnn");
            return true;
            }
        return false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
