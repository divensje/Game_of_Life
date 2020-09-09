package gol;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileUtils {
    private GameBoard gameBoard;
    private Stage stage;

    public FileUtils(GameBoard gameBoard, Stage stage) {
        this.gameBoard = gameBoard;
        this.stage = stage;
    }

    public void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        fileChooser.setInitialFileName("game-of-life.txt");
        fileChooser.setInitialDirectory(new File("./"));  //Default the directory to the current directory
        File file = fileChooser.showSaveDialog(stage);

        if (null != file) {
            try (FileWriter fileWriter = new FileWriter(file)) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO Make this more robust to handle invalid files properly
    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a saved game state");
        fileChooser.setInitialDirectory(new File("./"));  //Default the directory to the current directory
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("txt", "*.txt")
        );
        File file = fileChooser.showOpenDialog(stage);

        if (null != file) {
            Set<Lifeform> newLifeformSet = new HashSet<>();

            try (FileReader fileReader = new FileReader(file)) {
                BufferedReader br = new BufferedReader(fileReader);

                String line = br.readLine();
                int y = 0;
                while (line != null) {

                    // Iterate over the characters in this row, creating a lifeform when a "1" is encountered
                    for (int x = 0; x < line.length(); x++) {
                        char value = line.charAt(x);
                        if ('1' == value) {
                            newLifeformSet.add(new Lifeform(x, y));
                        }
                    }

                    y++;  //Increment the row
                    line = br.readLine();  //Read a new line from the file
                }

                // Update the lifeform set and re-draw the board
                gameBoard.setLifeforms(newLifeformSet);
                gameBoard.draw();

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}