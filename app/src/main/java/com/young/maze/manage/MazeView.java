package com.young.maze.manage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MazeView extends View {

    private static final String TAG = "MazeView";
    private byte[][] mMaze;
    private Paint mPaint;

    public MazeView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(0xFF000000);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2);
    }

    public MazeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2);
    }

    public MazeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mMaze != null) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            int width = Math.min(measuredWidth, measuredHeight) / mMaze.length;
            for (int i = 0; i < mMaze.length; i++) {
                for (int j = 0; j < mMaze[i].length; j++) {
                    for (int k = 0; k < 4; k++) {
                        int d = 1 << k;
                        byte b = mMaze[i][j];
                        if ((b & d) == 0) {
                            if (d == 0b0001) {
                                Log.d(TAG, "onDraw: " + i + "||" + j);
                                canvas.drawLine(i * width, j * width, i * width + width, j * width, mPaint);
                            } else if (d == 0b0010) {
                                Log.d(TAG, "onDraw: " + i + "||" + j);
                                canvas.drawLine(i * width, (j + 1) * width, i * width + width, (j + 1) * width, mPaint);
                            } else if (d == 0b0100) {
                                Log.d(TAG, "onDraw: " + i + "||" + j);
                                canvas.drawLine(i * width, j * width, i * width, j * width + width, mPaint);
                            } else if (d == 0b1000) {
                                Log.d(TAG, "onDraw: " + i + "||" + j);
                                canvas.drawLine((i + 1) * width, j * width, (i + 1) * width, j * width + width, mPaint);
                            }
                        }
                    }
                }
            }
        }
    }

    public void setMaze(byte[][] maze) {
        mMaze = maze;
        invalidate();
    }
}
