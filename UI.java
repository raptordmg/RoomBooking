package RaptorDMG;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UI extends Application {
    public void start(Stage stage) {
        BorderPane mainScreen = new BorderPane();
        FlowPane menuBar = setButtons();

        mainScreen.setTop(menuBar);

        Scene scene = new Scene(mainScreen,900,700);
        stage.setTitle("RoomBooking");
        stage.setScene(scene);
        stage.show();
    }

    private FlowPane setButtons() {
        Button[] menuButtons = new Button[] {
                new Button("Create Booking"),
                new Button("About"),
                new Button("Help")
        };

        FlowPane menuBar = new FlowPane();
        menuBar.setPadding(new Insets(10,10,10,10));
        menuBar.setStyle("-fx-background-color: #97CEF9;");
        menuBar.setHgap(10);
        menuBar.setVgap(5);
        menuBar.getChildren().addAll(menuButtons);

        menuButtons[0].setOnAction(event -> createBooking());

        return menuBar;
    }

    private void createBooking() {
        GridPane bookingLayout = new GridPane();
        
    }
}