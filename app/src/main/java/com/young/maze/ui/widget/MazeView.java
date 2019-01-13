package com.young.maze.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MazeView extends View {

    private static final String TAG = "MazeView";
    private byte[][] mMaze;
    private Paint mPaint;
    private Paint mPathPaint;
    private int mStrokeWidth;
    private long[] mPath;

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
        mPathPaint = new Paint();
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setColor(0xFFFF0000);
        mPathPaint.setStrokeWidth(2);
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
                float width = Math.min((measuredWidth - 20) * 1.0f / mazeWidth, (measuredHeight - 20) * 1.0f / mazeHeight);
                mStrokeWidth = (int) Math.ceil(width / 10);
                mPaint.setStrokeWidth(mStrokeWidth);
                width = Math.min((measuredWidth - mStrokeWidth) * 1.0f / mazeWidth, (measuredHeight - mStrokeWidth) * 1.0f / mazeHeight);
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
                if (mPath != null) {
                    Path path1 = new Path();
                    for (int i = 0; i < mPath.length; i++) {
                        long path = mPath[i];
                        int x = (int) (path >> 32);
                        int y = (int) path;
                        if (i == 0) {
                            path1.moveTo((x + 0.5F) * width + offsetWidth, (y + 0.5F) * width + offsetHeight);
                        } else {
                            path1.lineTo((x + 0.5F) * width + offsetWidth, (y + 0.5F) * width + offsetHeight);
                        }
                    }
                    canvas.drawPath(path1, mPathPaint);
                }
            }
        }
    }

    public void setMaze(byte[][] maze) {
        mMaze = maze;
        mPath = null;
        invalidate();
    }

    public void setPath(long[] path) {
        mPath = path;
        invalidate();
    }
}
