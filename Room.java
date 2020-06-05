package RaptorDMG;

import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Room {

  private LocalDate date;
  private String name;

  //Constructs a room
  public Room(LocalDate date, String clientName) {
    this.date = date;
    this.name = clientName;
  }

  //Add default constructor
  public Room() {

  }

  /*
    Finds a room that meets the requirements
   */
  public void findRoom(int computers, boolean includeBreakout, boolean hasSmartboard,
      boolean hasPrinter) {
    JSONArray suitableRooms = new JSONArray();
    //loop for each room
    for (int i = 0; i < getRooms().size(); i++) {
        //Get the current room
        JSONObject currentRoom = (JSONObject) getRooms().get(i);
        int computersAvailable = 0;

        //Decides whether to use breakouts or not in room selection
        if (includeBreakout) {
          computersAvailable = Integer.parseInt(
              currentRoom.get("computers").toString()) + Integer
                  .parseInt(currentRoom.get("breakout").toString());
        } else {
          computersAvailable = Integer.parseInt(currentRoom.get("computers").toString());
        }

        //Checks the room has the required features
        if (hasSmartboard == Boolean.parseBoolean(currentRoom.get("hasSmartboard").toString())
            || !hasSmartboard) {
          if (hasPrinter == Boolean.parseBoolean(currentRoom.get("hasPrinter").toString())
              || !hasPrinter) {
            if (computersAvailable >= computers) {
              suitableRooms.add(currentRoom);
            }
          }
        }
    }

    //Checks if a suitable room has been found
    if (!suitableRooms.isEmpty()) {
      //Creates a new booking
      Booking newBooking = new Booking(suitableRooms,name, date);
      newBooking.createBooking();
    } else {
      //Tells the user if no suitable room has been found
      Alert noRoomFound = new Alert(AlertType.ERROR, "No suitable room was found");
      noRoomFound.show();
    }
  }

  /*
    Gets the rooms from json
   */
  public JSONArray getRooms() {
    //Loads the rooms json
    Json rooms = loadRooms();

    //Reads the JSON file into a JSONArray
    JSONArray roomArray = rooms.readJson();

    return roomArray;
  }

  //Loads the rooms json
  private Json loadRooms() {
    return new Json("Rooms.json",
        new String[]{"roomNum", "computers", "breakout", "hasSmartboard", "hasPrinter"});
  }
}