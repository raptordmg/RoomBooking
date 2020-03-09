package RaptorDMG;

import javafx.scene.control.Alert;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Json {
    /*
    Makes sure the file exists if not make one
     */
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

    /*
    Allows the program to save a room
     */
    public void addRoomToJson(JSONObject newRoom) {
        final String jsonFileName = "Rooms.json";
        createJson(jsonFileName);
        JSONParser roomParser = new JSONParser();
        JSONArray rooms = new JSONArray();

        try (FileReader reader = new FileReader(jsonFileName)){
            Object obj = roomParser.parse(reader);
            rooms = (JSONArray) obj;
            rooms.add(newRoom);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try (FileWriter fileWriter = new FileWriter(jsonFileName)) {
            fileWriter.write(rooms.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
