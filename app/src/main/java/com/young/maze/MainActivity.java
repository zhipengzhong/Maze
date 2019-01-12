package com.young.maze;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.young.maze.manage.MazeView;
import com.young.maze.manage.RecursiveBacktracker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecursiveBacktracker recursiveBacktracker = new RecursiveBacktracker(80, 80);
        byte[][] maze = recursiveBacktracker.createMaze();
        MazeView view = findViewById(R.id.mv);
        if (maze != null) {
            for (byte[] bytes : maze) {
                for (byte aByte : bytes) {
                    System.out.println(aByte);
                }
            }
        }
        view.setMaze(maze);
    }
}
