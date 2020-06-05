package RaptorDMG;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

public class UI extends Application {

  private GridPane reportList = new GridPane();
  /*
    Starts up the program
   */
  public void start(Stage stage) {
    //Creates a borderpane for the main screen
    BorderPane mainScreen = new BorderPane();

    //Place the menu bar at the top
    mainScreen.setTop(setButtons());
    mainScreen.setCenter(displayBookingGridPane());

    //Creates the main window
    Scene scene = new Scene(mainScreen, 900, 700);

    //Sets the window title
    stage.setTitle("RoomBooking");

    //Displays the window
    stage.setScene(scene);
    stage.show();
  }

  /*
    Creates the buttons for the menubar
   */
  private FlowPane setButtons() {

    //Creates an array of buttons
    Button[] menuButtons = new Button[]{
        new Button("Create Booking"),
        new Button("Technical Requirement")
    };

    //Creates a menubar
    FlowPane menuBar = new FlowPane();

    //Sets padding for the menubar
    menuBar.setPadding(new Insets(10, 10, 10, 10));

    //Set the background colour
    menuBar.setStyle("-fx-background-color: #97CEF9;");

    //Create spacing between items
    menuBar.setHgap(10);
    menuBar.setVgap(5);

    //Add buttons
    menuBar.getChildren().addAll(menuButtons);

    //Set button action
    menuButtons[0].setOnAction(event -> createBooking());
    menuButtons[1].setOnAction(event -> technicalRequirement());

    return menuBar;
  }
/*
  Open a display to create a booking
 */
  private void createBooking() {
    //Creates a gridpane and adds it to a window
    GridPane createBookingLayout = new GridPane();
    createBookingLayout.setPadding(new Insets(10, 10, 10, 10));
    Scene createBookingScene = new Scene(createBookingLayout, 330, 280);

    Stage createBookingStage = new Stage();
    createBookingStage.setScene(createBookingScene);
    createBookingStage.setTitle("Create Booking");
    createBookingStage.show();

    //Sets text items for the field names
    Text[] fieldNames = new Text[]{
        new Text("Client Name:"),
        new Text("Computers required: "),
        new Text("Include Breakouts: "),
        new Text("Smartboard required: "),
        new Text("Printer required: "),
        new Text("Date:")
    };

    //Create a textfield for the clients name
    TextField clientName = new TextField();

    //Sets the max number of computers a class can have including breakouts
    Integer[] numComputers = new Integer[28];
    for (int i = 0; i < numComputers.length; i++) {
      numComputers[i] = i + 1;
    }

    //Creates a date picker
    DatePicker dateSelector = new DatePicker();

    //Creates a combobox for the computers required
    ComboBox<Integer> computersRequired = new ComboBox<>();
    computersRequired.getItems().addAll(numComputers);

    //Creates a combobox for including breakouts
    ComboBox<String> includeBreakouts = new ComboBox<>();
    includeBreakouts.getItems().addAll("Yes", "No");

    //Creates a combobox for including a smartboard
    ComboBox<String> hasSmartBoard = new ComboBox<>();
    hasSmartBoard.getItems().addAll("Yes", "No");

    //Creates a combobox for including a printer
    ComboBox<String> hasPrinter = new ComboBox<>();
    hasPrinter.getItems().addAll("Yes", "No");

    //Creates a cancel button and sets it's action
    Button cancel = new Button("Cancel");
    cancel.setOnAction(event -> createBookingStage.close());

    //Creates a create booking button and adds a booking
    Button createBooking = new Button("Create Booking");
    createBooking.setOnAction(event -> {if (clientName.getLength() == 0 || computersRequired.getSelectionModel().isEmpty() || includeBreakouts.getSelectionModel().isEmpty() || hasSmartBoard.getSelectionModel().isEmpty() || hasPrinter.getSelectionModel().isEmpty() || dateSelector.getValue() == null) {
      Alert missingField = new Alert(AlertType.ERROR, "Please fill in all fields before submitting data");
      missingField.show();
    } else {
      createNewBooking(clientName.getText(),computersRequired.getSelectionModel().getSelectedItem(), includeBreakouts.getSelectionModel().getSelectedItem(), hasSmartBoard.getSelectionModel().getSelectedItem(), hasPrinter.getSelectionModel().getSelectedItem(),dateSelector.getValue());
    }
    });

    //Add the UI elements to the display
    createBookingLayout.addColumn(0, fieldNames);
    createBookingLayout.add(clientName, 1, 0);
    createBookingLayout.add(computersRequired, 1, 1);
    createBookingLayout.add(includeBreakouts, 1, 2);
    createBookingLayout.add(hasSmartBoard, 1, 3);
    createBookingLayout.add(hasPrinter, 1, 4);
    createBookingLayout.add(dateSelector,1,5);
    createBookingLayout.add(cancel, 0, 6);
    createBookingLayout.add(createBooking, 1, 6);
  }

  /*
    Handles calling require functions and creating booking
   */
  private void createNewBooking(String clientName, Integer computers, String hasBreakoutsString,
      String hasSmartboardString, String hasPrinterString, LocalDate date) {
    //Sets some boolean variables
    boolean hasBreakouts = false;
    boolean hasSmartboard = false;
    boolean hasPrinter = false;

    //Checks if user selected yes in comboBoxes
    if (hasBreakoutsString.equals("Yes")) {
      hasBreakouts = true;
    }
    if (hasSmartboardString.equals("Yes")) {
      hasSmartboard = true;
    }
    if (hasPrinterString.equals("Yes")) {
      hasPrinter = true;
    }

    //Check if name is valid and then creates a booking
    if (clientName.matches("[A-Za-z ]+")) {
      Room checkRooms = new Room(date,clientName);
      checkRooms.findRoom(computers, hasBreakouts, hasSmartboard, hasPrinter);
      displayBookings();
    } else {
      //Displays an error message if name invalid
      Alert invalidName = new Alert(AlertType.ERROR, "There seems to be an invalid character in the name field");
      invalidName.show();
    }
  }

  /*
    Displays the bookings on the screen
   */
  private void displayBookings() {
    //Clears the GridPane
    reportList.getChildren().clear();
    reportList.setPadding(new Insets(10,10,10,10));

    //Sets horizontal gap
    reportList.setHgap(10);

    //Creates the column names
    Text[] comlumnNames = new Text[]{new Text("Booking Num:"), new Text("Client Name:"), new Text("Room Num:"), new Text("Date:"), new Text("Booking canceled"), new Text("Cancel Bookings")};

    //Add columns name to grid
    reportList.addRow(0, comlumnNames);

    //Creates a booking class
    Booking bookings = new Booking();

    //Creates a loop that gets the booking data
    for (int i = 0; i < bookings.getBookings().size(); i++) {

      //Gets the current booking
      JSONObject booking = (JSONObject) bookings.getBookings().get(i);

      //Creates a Text array with the booking details
      Text[] bookingDetails = new Text[] {new Text(booking.get("bookingNum").toString()), new Text(booking.get("client").toString()), new Text(booking.get("roomNum").toString()), new Text(booking.get("date").toString()), new Text(booking.get("isCanceled").toString())};

      //Creates a cancel booking button
      Button cancelBooking = new Button("Cancel Booking");

      //Cancels the booking and refreshes the booking info
      cancelBooking.setOnAction(event -> {
        bookings.cancelBooking(Integer.parseInt(booking.get("bookingNum").toString())-1);
        displayBookings();
      });

      //Adds the row to the gridPane
      reportList.addRow(i+1,bookingDetails);
      reportList.add(cancelBooking,5, i+1);
    }
  }

  /*
    Small function that does stuff with a set
   */
  private void technicalRequirement() {

    //Creates and displays an alert
    Alert dataStructure = new Alert(AlertType.INFORMATION, "This does some data structure stuff for outcome 4 ... hopefully");
    dataStructure.show();

    //Creates a set
    Set<Integer> theSet = new HashSet<>();

    //Loop to add some data to the set
    for (int i = 0; i < 5; i++) {
      theSet.add(i+1);
    }

    //Displays each item in the set to the console
    for (Integer i: theSet) {
      System.out.println(i);
    }

    //Removes the number 3 from set
    theSet.remove(3);
  }

  /*
    Allows the gridPane to be added to the BorderPane
   */
  private GridPane displayBookingGridPane() {
    //Sets horizontal and verticle gaps for the gridPane
    reportList.setHgap(10);
    reportList.setVgap(10);
    reportList.setPadding(new Insets(10,10,10,10));

    //Calls the display bookings class
    displayBookings();

    return reportList;
  }
}