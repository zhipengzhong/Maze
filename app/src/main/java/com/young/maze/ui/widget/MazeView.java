package com.young.maze.ui.widget;

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
    private int mStrokeWidth = 20;

    public MazeView(Context context) {
        super(context);
        initPaint();
    }

    public MazeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MazeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(0xFF000000);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mMaze != null) {
            int mazeWidth = mMaze.length;
            int mazeHeight = mazeWidth > 0 ? mMaze[0].length : 0;
            if (mazeWidth > 0 && mazeHeight > 0) {
                int measuredWidth = getMeasuredWidth();
                int measuredHeight = getMeasuredHeight();
                float width = Math.min((measuredWidth - mStrokeWidth) * 1.0f / mazeWidth, (measuredHeight - mStrokeWidth) * 1.0f / mazeHeight);
                mStrokeWidth = (int) Math.ceil(width / 10);
                mPaint.setStrokeWidth(mStrokeWidth);
                float offsetWidth = (measuredWidth - width * 1.0f * mazeWidth) / 2;
                float offsetHeight = (measuredHeight - width * 1.0f * mazeHeight) / 2;
                for (int i = 0; i < mazeWidth; i++) {
                    for (int j = 0; j < mMaze[i].length; j++) {
                        for (int k = 0; k < 4; k++) {
                            int d = 1 << k;
                            byte b = mMaze[i][j];
                            if ((b & d) == 0) {
                                if (d == 0b0001) {
                                    canvas.drawLine(i * width + offsetWidth, j * width + offsetHeight, i * width + width + offsetWidth, j * width + offsetHeight, mPaint);
                                } else if (d == 0b0010) {
                                    canvas.drawLine(i * width + offsetWidth, (j + 1) * width + offsetHeight, i * width + width + offsetWidth, (j + 1) * width + offsetHeight, mPaint);
                                } else if (d == 0b0100) {
                                    canvas.drawLine(i * width + offsetWidth, j * width + offsetHeight, i * width + offsetWidth, j * width + width + offsetHeight, mPaint);
                                } else if (d == 0b1000) {
                                    canvas.drawLine((i + 1) * width + offsetWidth, j * width + offsetHeight, (i + 1) * width + offsetWidth, j * width + width + offsetHeight, mPaint);
                                }
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
