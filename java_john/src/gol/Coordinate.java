package gol;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the coordinates of a lifeform
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns true if the coordiante passed in is the same location
     * @param otherCoordinate
     * @return
     */
    public boolean isSame(Coordinate otherCoordinate) {
        return this.x == otherCoordinate.getX()
                && this.y == otherCoordinate.getY();
    }

    /**
     * Gets all 8 neighboring coordinates
     *
     * UL  U  UR
     * L   *  R
     * DL  D  DR
     *
     * (-1,-1)   (0,-1)  (1,-1)
     * (-1,0)   (0,0)   (1,0)
     * (-1,1)   (0,1)   (1,1)
     * @param rows The maximum number of rows in the game board
     * @param columns The maximum number of columns in the game board
     * @return
     */
    public Set<Coordinate> getNeighboringCoordinates(int rows, int columns) {
        // Calculate the coordinates
        Coordinate ul = new Coordinate(x-1, y-1);
        Coordinate u = new Coordinate(x, y-1);
        Coordinate ur = new Coordinate(x+1, y-1);
        Coordinate l = new Coordinate(x-1, y);
        Coordinate r = new Coordinate(x+1, y);
        Coordinate dl = new Coordinate(x-1, y+1);
        Coordinate d = new Coordinate(x, y+1);
        Coordinate dr = new Coordinate(x+1, y+1);

        // Add all valid coordinates to a set
        Set<Coordinate> coordinates = new HashSet<>();
        coordinates.add(ul);
        coordinates.add(u);
        coordinates.add(ur);
        coordinates.add(l);
        coordinates.add(r);
        coordinates.add(dl);
        coordinates.add(d);
        coordinates.add(dr);

        // Remove invalid coordinates
        coordinates.removeIf(coord -> coord.getX() < 0);
        coordinates.removeIf(coord -> coord.getX() > rows-1);
        coordinates.removeIf(coord -> coord.getY() < 0);
        coordinates.removeIf(coord -> coord.getY() > columns-1);

        return coordinates;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
}
