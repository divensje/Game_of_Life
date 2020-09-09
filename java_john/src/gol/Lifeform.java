package gol;

/**
 * This class represents a lifeform.
 */
public class Lifeform {
    private Coordinate coordinate;

    /**
     * Constructs a new lifeform at the specified coordinate
     * @param coordinate
     */
    public Lifeform(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Lifeform(int x, int y) {
        this.coordinate = new Coordinate(x, y);
    }

    public Coordinate getCoordinate() { return coordinate; }
    public void setCoordinate(Coordinate coordinate) { this.coordinate = coordinate; }
}