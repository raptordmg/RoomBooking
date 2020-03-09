package RaptorDMG;

import javafx.scene.control.Alert;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Json {
    private void createJson(String jsonFileName) {
        try (FileReader fileReader = new FileReader(jsonFileName)) {

        } catch (IOException e) {
            Alert IOError = new Alert(Alert.AlertType.WARNING, jsonFileName + " not found\nCreating new file");
            IOError.show();
            try (FileWriter fileWriter = new FileWriter(jsonFileName)){
                fileWriter.write("[]");
                fileWriter.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
