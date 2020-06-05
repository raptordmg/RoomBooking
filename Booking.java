package RaptorDMG;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Booking {

  //Creates some functions used within the class
  private String clientName;
  private LocalDate date;
  private JSONArray currentRooms;
  private ArrayList<String> roomsBooked = new ArrayList<>();

  //Constructs a booking class for booking a room
  public Booking(JSONArray currentRooms, String name, LocalDate date) {
    this.currentRooms = currentRooms;
    this.date = date;
    clientName = name;
  }

  //Constructs a booking class for reading bookings
  public Booking() {

  }

  /*
    Creates the booking
   */
  public void createBooking() {

    //Loads the booking json
    Json booking = loadBookings();

    //Checks if the booking file is empty
    if (booking.readJson().isEmpty()) {
      //Adds a booking to the first suitable room
      addItemToBooking((JSONObject) currentRooms.get(0));
    } else {
      //For each suitable room
      for (int i = 0; i < currentRooms.size(); i++) {

        //Gets the currently selected room
        JSONObject currentRoom = (JSONObject) currentRooms.get(i);

        //Gets the currently booked rooms
        getRoomsBooked();

        //Checks that the current room hasn't already been booked
        if (!(roomsBooked.contains(currentRoom.get("roomNum").toString()))) {
          //Adds the booking
          addItemToBooking(currentRoom);

          //Exits the for loop
          break;
        }

        //Checks if the last suitable room has been checked
        if (i == currentRooms.size() - 1) {
          //Displays error message
          Alert roomsBooked = new Alert(Alert.AlertType.ERROR, "All suitable rooms booked on this day");
          roomsBooked.show();
        }
      }
    }
  }

  /*
    Cancels a booking
   */
  public void cancelBooking(int bookingNum) {
    //Gets the JSONObject to be canceled
    JSONObject cancel = (JSONObject) getBookings().get(bookingNum);

    //Sets isCanceled to true
    cancel.replace("isCanceled", "true");

    //Save the modification
    loadBookings().saveModification(cancel, bookingNum);
  }

  /*
    Checks what room is booked on the selected day
   */
  private void getRoomsBooked() {
    //Loads the booking JSON
    Json booking = loadBookings();

    //For each booking
    for (Object o: booking.readJson()) {

      //Get the current booking
      JSONObject currentBooking = (JSONObject) o;

      //Get the date of the current booking
      LocalDate bookingDate = LocalDate.parse(currentBooking.get("date").toString());

      //Checks if the booking date equals the date of an existing booking and that the booking hasn't already been canceled
      if (date.equals(bookingDate) && currentBooking.get("isCanceled").toString().equals("false")) {
        //Adds the room to the room booked function
        roomsBooked.add(currentBooking.get("roomNum").toString());
      }
    }
  }

  /*
    Adds a booking to the json
   */
  private void addItemToBooking(JSONObject finalRoom) {
    //Creates JSONObject
    JSONObject newBooking = new JSONObject();

    //Assigns values to the JSONObject
    newBooking.put("bookingNum", loadBookings().readJson().size() + 1);
    newBooking.put("date", date.toString());
    newBooking.put("roomNum", finalRoom.get("roomNum"));
    newBooking.put("client", clientName);
    newBooking.put("isCanceled", "false");

    //Adds JSONObject to file
    loadBookings().addItemToJson(newBooking);
  }

  //Gets the bookings from the file
  public JSONArray getBookings() {
    //Loads the file
    Json rooms = loadBookings();

    //Reads the items into a JSONArray
    JSONArray roomArray = rooms.readJson();

    //returns the jsonArray
    return roomArray;
  }

  /*
    Loads the json file with the keys
   */
  private Json loadBookings() {
    return new Json("Booking.json", new String[] {"bookingNum", "date", "roomNum", "client", "isCanceled"});
  }
}