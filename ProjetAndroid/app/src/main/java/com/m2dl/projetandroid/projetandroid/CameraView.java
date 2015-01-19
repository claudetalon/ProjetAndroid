package com.m2dl.projetandroid.projetandroid;

import android.content.Context;
import android.content.res.Configuration;
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

    /*
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
    }*/

    private Paint mRectPaint;

    private int mStartX = 0;
    private int mStartY = 0;
    private int mEndX = 0;
    private int mEndY = 0;
    private boolean mDrawRect = false;
    //private TextPaint mTextPaint = null;

    private OnUpCallback mCallback = null;

    public interface OnUpCallback {
        void onRectFinished(Rect rect);
    }

    public CameraView(final Context context) {
        super(context);
        init();
    }

    public CameraView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * Sets callback for up
     *
     * @param callback {@link OnUpCallback}
     */
    public void setOnUpCallback(OnUpCallback callback) {
        mCallback = callback;
    }

    /**
     * Inits internal data
     */
    private void init() {
        mRectPaint = new Paint();
        mRectPaint.setColor(Color.YELLOW);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(5); // TODO: should take from resources

        //mTextPaint = new TextPaint();
        //mTextPaint.setColor(getContext().getResources().getColor(R.color.holo_green_light));
        //mTextPaint.setTextSize(20);
    }

    @Override
    public boolean onTouch(View v, final MotionEvent event) {

        // TODO: be aware of multi-touches
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDrawRect = false;
                mStartX = (int) event.getX();
                mStartY = (int) event.getY();
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                final int x = (int) event.getX();
                final int y = (int) event.getY();

                if (!mDrawRect || Math.abs(x - mEndX) > 5 || Math.abs(y - mEndY) > 5) {
                    mEndX = x;
                    mEndY = y;
                    invalidate();
                }

                mDrawRect = true;
                break;

            case MotionEvent.ACTION_UP:
                if (mCallback != null) {
                    mCallback.onRectFinished(new Rect(Math.min(mStartX, mEndX), Math.min(mStartY, mEndY),
                            Math.max(mEndX, mStartX), Math.max(mEndY, mStartX)));
                }
                invalidate();
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if (mDrawRect) {
            canvas.drawRect(Math.min(mStartX, mEndX), Math.min(mStartY, mEndY),
                    Math.max(mEndX, mStartX), Math.max(mEndY, mStartY), mRectPaint);
        }
        setOnTouchListener(this);
    }
}
