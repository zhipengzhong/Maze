package com.young.maze.manage;

import android.util.Log;

import java.util.ArrayList;

public class BFS {

    private static final String TAG = "BFS";
    private int mMazeWidth;
    private int mMazeHeight;
    private boolean[][] history;

    public long[] pathFinding(byte[][] maze, int bx, int by, int ex, int ey) {
        if (maze != null) {
            mMazeWidth = maze.length;
            mMazeHeight = mMazeWidth > 0 ? maze[0].length : 0;
            if (mMazeWidth > 0 && mMazeHeight > 0) {
                history = new boolean[mMazeWidth][mMazeHeight];
                Location location = path(maze, bx, by, ex, ey);
                if (location != null) {
                    long[] path = new long[location.getStep() + 1];
                    int index = location.getStep();
                    while (location != null && location.getStep() > 0) {
                        long p = location.getX();
                        p = p << 32;
                        p += location.getY();
                        path[index] = p;
                        index--;
                        location = location.getPrev();
                    }
                    return path;
                }
                return null;
            }
        }
        return null;
    }

    private Location path(byte[][] maze, int bx, int by, int ex, int ey) {
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location(bx, by));
        while (locations != null && locations.size() > 0) {
            ArrayList<Location> locations1 = new ArrayList<>();
            for (Location location : locations) {
                int x = location.getX();
                int y = location.getY();
                byte b = maze[x][y];
                for (int i = 0; i < 4; i++) {
                    byte d = (byte) (1 << i);
                    if ((b & d) == d) {
                        int nx = x;
                        int ny = y;
                        if (d == 0b0001) {
                            ny--;
                        } else if (d == 0b0010) {
                            ny++;
                        } else if (d == 0b0100) {
                            nx--;
                        } else if (d == 0b1000) {
                            nx++;
                        }
                        if (checkHistory(nx, ny)) {
                            Location e = new Location(nx, ny);
                            e.setPrev(location);
                            e.setStep(location.step + 1);
                            locations1.add(e);
                            history[nx][ny] = true;
                            if (nx == ex && ny == ey) {
                                return e;
                            }
                        }
                    }

                }
            }
            locations.clear();
            locations = locations1;
        }
        return null;
    }

    private boolean checkHistory(int x, int y) {
        return x >= 0 && y >= 0 && x < mMazeWidth && y < mMazeHeight && !history[x][y];
    }


    class Location {
        private int x;
        private int y;
        private Location prev;
        private int step;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Location getPrev() {
            return prev;
        }

        public void setPrev(Location prev) {
            this.prev = prev;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }
    }
}
