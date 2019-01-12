package com.young.maze.manage;

import java.util.ArrayList;
import java.util.Random;

public class RecursiveBacktracker {

    private int mWidth;
    private int mHeight;
    private Random mRandom = new Random(System.currentTimeMillis());
    private byte[][] maze;
    private boolean[][] history;

    public RecursiveBacktracker() {
    }

    public RecursiveBacktracker(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public byte[][] createMaze() {
        if (mWidth > 0 && mHeight > 0) {
            maze = new byte[mWidth][mHeight];
            history = new boolean[mWidth][mHeight];
            generateMaze(0, 0);
            return maze;
        }
        return null;
    }

    private void generateMaze(int x, int y) {
        history[x][y] = true;
        byte b = maze[x][y];
        ArrayList<Byte> directions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            directions.add((byte) (1 << i));
        }
        for (int i = 0; i < 4; i++) {
            int index = Math.abs(mRandom.nextInt()) % directions.size();
            byte d = directions.get(index).byteValue();
            directions.remove(index);
            if ((b & d) == 0) {
                if (d == 0b0001) {
                    if (checkHistory(x, y - 1)) {
                        maze[x][y] = (byte) (maze[x][y] | d);
                        maze[x][y - 1] = (byte) (maze[x][y - 1] | 0b0010);
                        generateMaze(x, y - 1);
                    }
                } else if (d == 0b0010) {
                    if (checkHistory(x, y + 1)) {
                        maze[x][y] = (byte) (maze[x][y] | d);
                        maze[x][y + 1] = (byte) (maze[x][y + 1] | 0b0001);
                        generateMaze(x, y + 1);
                    }
                } else if (d == 0b0100) {
                    if (checkHistory(x - 1, y)) {
                        maze[x][y] = (byte) (maze[x][y] | d);
                        maze[x - 1][y] = (byte) (maze[x - 1][y] | 0b1000);
                        generateMaze(x - 1, y);
                    }
                } else if (d == 0b1000) {
                    if (checkHistory(x + 1, y)) {
                        maze[x][y] = (byte) (maze[x][y] | d);
                        maze[x + 1][y] = (byte) (maze[x + 1][y] | 0b0100);
                        generateMaze(x + 1, y);
                    }
                }
            }
        }

    }

    private boolean checkHistory(int x, int y) {
        return x >= 0 && y >= 0 && x < mWidth && y < mHeight && !history[x][y];
    }
}
