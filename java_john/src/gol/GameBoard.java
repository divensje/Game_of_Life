package gol;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This class represents the game board that will contain all the life forms. This is where all the action happens!
 */
public class GameBoard {
    /**
     * Width and height of the canvas in pixels
     */
    private final int width;
    private final int height;

    /**
     * The number of rows and columns in the simulation
     */
    private final int rows;
    private final int columns;

    /**
     * The size of a cell in pixels
     */
    private final int cellSize;

    /**
     * A set of all live lifeforms on the gameboard
     */
    private Set<Lifeform> lifeforms = new HashSet<>();

    private static final Color LIVE_CELL_COLOR = Color.GREEN;
    private static final Color EMPTY_CELL_COLOR = Color.LAVENDER;
    private static final Color CELL_BORDER_COLOR = Color.gray(0.5, 0.5);

    private final GraphicsContext graphicsContext;
    private final Stage stage;

    public GameBoard(int width, int height, int cellSize, GraphicsContext graphicsContext, Stage stage) {
        this.width = width;
        this.height = height;
        this.rows = (int)Math.floor(height / cellSize);
        this.columns = (int)Math.floor(width / cellSize);
        this.cellSize = cellSize;
        this.graphicsContext = graphicsContext;
        this.stage = stage;
        initializeBoard();
    }

    public void initializeBoard() {
        // Clear out all lifeforms
        lifeforms = new HashSet<>();

        // Populate the grid
        Random random = new Random();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                boolean createLifeform = random.nextBoolean();
                if (createLifeform) {
                    lifeforms.add(new Lifeform(x, y));
                }
            }
        }

        // Draw dem lifeforms!
        draw();
    }

    private void draw() {
        // Clear everything first
        graphicsContext.setFill(EMPTY_CELL_COLOR);
        graphicsContext.fillRect(0, 0, width, height);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                // First draw the border
                graphicsContext.setFill(CELL_BORDER_COLOR);
                graphicsContext.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);

                // Set the fill color based on if there is a lifeform or not, then fill the cell with the color
                if (isLifeformExistsAtLocation(x, y)) {
                    graphicsContext.setFill(LIVE_CELL_COLOR);
                } else {
                    graphicsContext.setFill(EMPTY_CELL_COLOR);
                }
                graphicsContext.fillRect((x * cellSize) + 1, (y * cellSize) + 1, cellSize - 2, cellSize - 2);
            }
        }
    }

    public void tick() {
        Set<Lifeform> nextIterationLifeforms = new HashSet<>();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Coordinate coordinate = new Coordinate(x, y);

                int neighbors = getNumberOfNeighbors(coordinate);
                if (neighbors == 3) {
                    // There are 3 neighbors, the current location must become alive
                    nextIterationLifeforms.add(new Lifeform(coordinate));

                } else if (neighbors < 2 || neighbors > 3) {
                    // The current location must die
                    // No need to do anything

                } else {
                    // The current location remains as is
                    if (isLifeformExistsAtLocation(coordinate)) {
                        nextIterationLifeforms.add(new Lifeform(coordinate));
                    }
                }
            }
        }

        lifeforms = nextIterationLifeforms;
        draw();
    }

    public void saveState() {
        FileUtils fileUtils = new FileUtils(this, stage);
        fileUtils.saveFile();
    }

    public void loadState() {
        FileUtils fileUtils = new FileUtils(this, stage);
        fileUtils.loadFile();
    }

    private int getNumberOfNeighbors(Coordinate coordinate) {
        int count = 0;
        for (Coordinate coord : coordinate.getNeighboringCoordinates(rows, columns)) {
            if (isLifeformExistsAtLocation(coord)) {
                count++;
            }
        }
        return count;
    }

    public boolean isLifeformExistsAtLocation(int x, int y) {
        Coordinate coordinate = new Coordinate(x, y);
        return isLifeformExistsAtLocation(coordinate);
    }

    public boolean isLifeformExistsAtLocation(Coordinate coordinate) {
        boolean result = false;

        for (Lifeform lifeform : lifeforms) {
            if (lifeform.getCoordinate().isSame(coordinate)) {
                result = true;
                break;
            }
        }

        return result;
    }

    public int getRows() { return rows; }
    public int getColumns() { return columns; }
}