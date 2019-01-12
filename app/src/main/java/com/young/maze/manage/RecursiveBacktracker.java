package com.young.maze.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecursiveBacktracker {

    private int mWidth;
    private int mHeight;
    private Random mRandom = new Random(System.currentTimeMillis());
    private byte[][] maze;
    private boolean[][] history;
    private List<Location> path;

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
            path = new ArrayList<>();
            path.add(new Location(0, 0));
            generateMaze();
            return maze;
        }
        return null;
    }

    private void generateMaze() {
        while (path.size() > 0) {
            Location location = path.get(path.size() - 1);
            ArrayList<Byte> directions = new ArrayList<>();
            int x = location.getX();
            int y = location.getY();
            byte b = maze[x][y];
            history[x][y] = true;
            for (int i = 0; i < 4; i++) {
                byte d = (byte) (1 << i);
                if ((b & d) == 0) {
                    if (d == 0b0001) {
                        if (checkHistory(x, y - 1)) {
                            directions.add(d);
                        }
                    } else if (d == 0b0010) {
                        if (checkHistory(x, y + 1)) {
                            directions.add(d);
                        }
                    } else if (d == 0b0100) {
                        if (checkHistory(x - 1, y)) {
                            directions.add(d);
                        }
                    } else if (d == 0b1000) {
                        if (checkHistory(x + 1, y)) {
                            directions.add(d);
                        }
                    }
                }
            }
            int directionsSize = directions.size();
            if (directionsSize > 0) {
                int index = Math.abs(mRandom.nextInt()) % directionsSize;
                byte d = directions.get(index).byteValue();
                maze[x][y] = (byte) (maze[x][y] | d);
                if (d == 0b0001) {
                    maze[x][y - 1] = (byte) (maze[x][y - 1] | 0b0010);
                    path.add(new Location(x, y - 1));
                } else if (d == 0b0010) {
                    maze[x][y + 1] = (byte) (maze[x][y + 1] | 0b0001);
                    path.add(new Location(x, y + 1));
                } else if (d == 0b0100) {
                    maze[x - 1][y] = (byte) (maze[x - 1][y] | 0b1000);
                    path.add(new Location(x - 1, y));
                } else if (d == 0b1000) {
                    maze[x + 1][y] = (byte) (maze[x + 1][y] | 0b0100);
                    path.add(new Location(x + 1, y));
                }
            } else {
                path.remove(path.size() - 1);
            }
        }

//
//        history[x][y] = true;
//        byte b = maze[x][y];
//        ArrayList<Byte> directions = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            directions.add((byte) (1 << i));
//        }
//        for (int i = 0; i < 4; i++) {
//            int index = Math.abs(mRandom.nextInt()) % directions.size();
//            byte d = directions.get(index).byteValue();
//            directions.remove(index);
//            if ((b & d) == 0) {
//                if (d == 0b0001) {
//                    if (checkHistory(x, y - 1)) {
//                        maze[x][y] = (byte) (maze[x][y] | d);
//                        maze[x][y - 1] = (byte) (maze[x][y - 1] | 0b0010);
//                        generateMaze(x, y - 1);
//                    }
//                } else if (d == 0b0010) {
//                    if (checkHistory(x, y + 1)) {
//                        maze[x][y] = (byte) (maze[x][y] | d);
//                        maze[x][y + 1] = (byte) (maze[x][y + 1] | 0b0001);
//                        generateMaze(x, y + 1);
//                    }
//                } else if (d == 0b0100) {
//                    if (checkHistory(x - 1, y)) {
//                        maze[x][y] = (byte) (maze[x][y] | d);
//                        maze[x - 1][y] = (byte) (maze[x - 1][y] | 0b1000);
//                        generateMaze(x - 1, y);
//                    }
//                } else if (d == 0b1000) {
//                    if (checkHistory(x + 1, y)) {
//                        maze[x][y] = (byte) (maze[x][y] | d);
//                        maze[x + 1][y] = (byte) (maze[x + 1][y] | 0b0100);
//                        generateMaze(x + 1, y);
//                    }
//                }
//            }
//        }

    }

    private boolean checkHistory(int x, int y) {
        return x >= 0 && y >= 0 && x < mWidth && y < mHeight && !history[x][y];
    }

    class Location {
        private int x;
        private int y;

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
    }
}
