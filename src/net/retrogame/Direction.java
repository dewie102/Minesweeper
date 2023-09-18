package net.retrogame;

public enum Direction {
    NORTHWEST   (-1, -1),
    NORTH       (0, -1),
    NORTHEAST   (1, -1),
    EAST        (1, 0),
    SOUTHEAST   (1, 1),
    SOUTH       (0, 1),
    SOUTHWEST   (-1, 1),
    WEST        (-1, 0);

    public int x;
    public int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
