package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private MyGameCharacter gameCharacter;
    boolean isWin = false;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameCharacter = new MyGameCharacter(BitmapFactory.decodeResource(getResources(), R.drawable.zzz));
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public boolean isWin(){
        return isWin;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("coordinates touch X", event.getX()+"");
        Log.d("coordinates touch Y", event.getY()+"");
        Log.d("coordinates object X", gameCharacter.getX()+"");
        Log.d("coordinates object Y", gameCharacter.getY()+"");
        isWin = gameCharacter.isWin(event.getX(), event.getY());
        if (isWin) {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            getContext().startActivity(intent);

        }
        return super.onTouchEvent(event);
    }

    public void update() {
        gameCharacter.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        gameCharacter.draw(canvas);
    }
}
