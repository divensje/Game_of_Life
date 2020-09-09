package gol;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class Main extends Application {

    private static final String WINDOW_TITLE = "Game of Life (Java version)";

    /**
     * The width and height of the game board in pixels
     */
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    /**
     * The size of each cell in pxels
     */
    private static final int CELL_SIZE = 20;

    /**
     * The length of time between update ticks in milliseconds
     */
    private static final int UPDATE_RATE = 100;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox(10);
        Scene scene = new Scene(root, WIDTH, HEIGHT + 100);
        final Canvas canvas = new Canvas(WIDTH, HEIGHT);

        // Create all the buttons
        Button btnReset = new Button("Reset");
        Button btnStep = new Button("Step");
        Button btnRun = new Button("Run");
        Button btnStop = new Button("Stop");

        root.getChildren().addAll(canvas, new HBox(10, btnReset, btnStep, btnRun, btnStop));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.show();

        GraphicsContext graphics = canvas.getGraphicsContext2D();

        GameBoard gameBoard = new GameBoard(WIDTH, HEIGHT, CELL_SIZE, graphics);

        // Set up the animation timer
        AnimationTimer runAnimation = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if ((now - lastUpdate) >= TimeUnit.MILLISECONDS.toNanos(UPDATE_RATE)) {
                    gameBoard.tick();
                    lastUpdate = now;
                }
            }
        };

        // Set up the button functionality
        btnReset.setOnAction(l -> gameBoard.initializeBoard());
        btnRun.setOnAction(l -> runAnimation.start());
        btnStep.setOnAction(l -> gameBoard.tick());
        btnStop.setOnAction(l -> runAnimation.stop());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
