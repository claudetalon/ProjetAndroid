package com.m2dl.projetandroid.projetandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by loic on 17/01/15.
 */
public class CameraView extends ImageView implements View.OnTouchListener {

    //private ShapeDrawable mDrawable;
    int x = 0; int y = 0;
    int width = 100; int height = 150;

    private Paint paint;
    private Rect rect;

    public CameraView(Context context) {
        super(context);
        //mDrawable = new ShapeDrawable(new RectShape()); // ici on affiche un oval...
        //mDrawable.getPaint().setColor(0xff74AC23);
        paint = new Paint();
        setOnTouchListener(this);
    }

    public CameraView(Context context, AttributeSet attr) {
        super(context, attr);
        //mDrawable = new ShapeDrawable(new RectShape()); // ici on affiche un oval...
        //mDrawable.getPaint().setColor(0xff74AC23);
        paint = new Paint();
        setOnTouchListener(this);
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //mDrawable.setBounds(x, y, x + width, y + height);
        //mDrawable.draw(canvas);
        paint.setColor(Color.BLACK);
        paint.setAlpha(99);
        paint.setStrokeWidth(3);
        canvas.drawRect(x,x,y,y,paint);
        paint.setStrokeWidth(0);
        setOnTouchListener(this);
    }

    public boolean onTouch(View arg0, MotionEvent arg1) {
        switch (arg1.getAction())
        {
            case MotionEvent.ACTION_MOVE:
            {
                x = (int) arg1.getX();
                y = (int) arg1.getY();
                invalidate(); // pour invalider l'image et forcer un rappel Ã  la methode onDraw de la classe.
            }
        }
        return true;
    }
}