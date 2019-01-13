package com.young.maze.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.young.maze.R;
import com.young.maze.manage.BFS;
import com.young.maze.ui.widget.MazeView;
import com.young.maze.manage.RecursiveBacktracker;

public class MainActivity extends AppCompatActivity {

    private byte[][] mMaze;
    private long[] mPath;
    private MazeView mMazeView;
    private EditText mWidth;
    private EditText mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMazeView = findViewById(R.id.mv);
        mWidth = findViewById(R.id.width);
        mHeight = findViewById(R.id.height);

        RecursiveBacktracker recursiveBacktracker = new RecursiveBacktracker(20, 26);
        mMaze = recursiveBacktracker.createMaze();
        mMazeView.setMaze(mMaze);
        mPath = new BFS().pathFinding(mMaze, 0, 0, 19, 25);
        mMazeView.setPath(mPath);
    }

    public void generateMaze(View view) {
        int width = Math.abs(TextUtils.isEmpty(mWidth.getText().toString().trim()) ? 0 : Integer.parseInt(mWidth.getText().toString().trim()));
        int height = Math.abs(TextUtils.isEmpty(mHeight.getText().toString().trim()) ? 0 : Integer.parseInt(mHeight.getText().toString().trim()));
        RecursiveBacktracker recursiveBacktracker = new RecursiveBacktracker(width, height);
        mMaze = recursiveBacktracker.createMaze();
        mMazeView.setMaze(mMaze);
    }

    public void pathFinding(View view) {
        if (mMaze != null) {
            int mazeWidth = mMaze.length;
            int mazeHeight = mazeWidth > 0 ? mMaze[0].length : 0;
            if (mazeWidth > 0 && mazeHeight > 0) {
                mPath = new BFS().pathFinding(mMaze, 0, 0, mazeWidth - 1, mazeHeight - 1);
                mMazeView.setPath(mPath);
            }
        }
    }
}
