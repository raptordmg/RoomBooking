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
    private String jsonName;
    private String[] fieldNames;

    public Json(String jsonName, String[] fieldNames) {
        this.jsonName = jsonName;
        this.fieldNames = fieldNames;
    }

    /*
    Makes sure the file exists if not make one
     */
    private void checkJson(String jsonFileName) {
        try (FileReader fileReader = new FileReader(jsonFileName)) { }
        catch (IOException e) {
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
    public void addItemToJson(String jsonFileName,JSONObject newItem) {
        checkJson(jsonFileName);
        JSONParser roomParser = new JSONParser();
        JSONArray itemList = new JSONArray();

        try (FileReader reader = new FileReader(jsonFileName)){
            Object obj = roomParser.parse(reader);
            itemList = (JSONArray) obj;
            itemList.add(newItem);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try (FileWriter fileWriter = new FileWriter(jsonFileName)) {
            fileWriter.write(itemList.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray readJson(String jsonName) {
        checkJson(jsonName);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonContents = new JSONArray();

        try(FileReader reader = new FileReader(jsonName)) {
            Object jsonObject = jsonParser.parse(reader);
            jsonContents = (JSONArray) jsonObject;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonContents;
    }
}
