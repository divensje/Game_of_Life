package gol;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    private GameBoard gameBoard;
    private Stage stage;

    public FileUtils(GameBoard gameBoard, Stage stage) {
        this.gameBoard = gameBoard;
        this.stage = stage;
    }

    public void saveFile() {
        try {
            FileWriter fileWriter = new FileWriter("game-of-life.freedfile");

            for (int y = 0; y < gameBoard.getRows(); y++) {
                for (int x = 0; x < gameBoard.getColumns(); x++) {
                    Coordinate coordinate = new Coordinate(x, y);
                    if (gameBoard.isLifeformExistsAtLocation(coordinate)) {
                        fileWriter.write("1");
                    } else {
                        fileWriter.write("0");
                    }
                }
                fileWriter.write(System.lineSeparator());
            }

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
        alertSuccess.setContentText("File saved successfully!");
        alertSuccess.show();
    }

    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a .freedfile");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Freed Files", "*.freedfile"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        File file = fileChooser.showOpenDialog(stage);

        if (null != file) {

        }
    }
}