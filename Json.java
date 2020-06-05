package RaptorDMG;

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
  private void checkJson() {
    //Try to read file
    try (FileReader fileReader = new FileReader(jsonName)) {
    } catch (IOException e) {
      //Try creating a new file
      try (FileWriter fileWriter = new FileWriter(jsonName)) {
        fileWriter.write("[]");
        fileWriter.flush();
      } catch (IOException ex) {
        //Display error to console
        ex.printStackTrace();
      }
    }
  }

  /*
  Allows the program to save an item to json
   */
  public void addItemToJson(JSONObject newItem) {
    checkJson();

    JSONParser roomParser = new JSONParser();
    JSONArray itemList = new JSONArray();

    //Try opening json file
    try (FileReader reader = new FileReader(jsonName)) {
      //Get the JSONArray from file
      itemList = readJson();

      //Add new item
      itemList.add(newItem);
    } catch (IOException e) {
      e.printStackTrace();
    }

    //Try saving json file
    try (FileWriter fileWriter = new FileWriter(jsonName)) {

      //Write modified JSONArray
      fileWriter.write(itemList.toJSONString());
      fileWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /*
    Return JSONArray from a file
   */
  public JSONArray readJson() {
    //Checks json file
    checkJson();
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonContents = new JSONArray();

    //Read the file
    try (FileReader reader = new FileReader(jsonName)) {

      //Parses the file to JSONArray
      Object jsonObject = jsonParser.parse(reader);
      jsonContents = (JSONArray) jsonObject;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }

    return jsonContents;
  }

  public void saveModification(JSONObject modification, int targetIndex) {

    //Gets the JSONArray
    JSONArray targetArray = readJson();

    //Replaces the item to be modified
    targetArray.set(targetIndex, modification);

    //Writes changes to file
    writeJSONArray(targetArray,jsonName);
  }

  private void writeJSONArray(JSONArray currentContents, String JSONFileName) {

    //Tries to open the JSON file
    try (FileWriter file = new FileWriter(JSONFileName)) {

      //Writes the JSONArray to the JSON file
      file.write(currentContents.toJSONString());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
