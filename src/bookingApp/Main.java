package bookingApp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    SeatManager sm = new SeatManager();

    @Override
    public void start(Stage primaryStage) throws Exception{
//        AnchorPane root = new AnchorPane();
        BorderPane root = new BorderPane();

        primaryStage.setTitle("Train Seat Booking");
        primaryStage.setScene(new Scene(root, 300, 600));


        GridPane grid = new GridPane();

        grid.setPadding(new Insets(20));

        int rowIndex = 0;
        int colIndex = 0;
        for (int i = 1; i <= SeatManager.SEATING_CAPACITY; i++) {   // make buttons for each and every seat
            Button btnSeat = new Button();
            btnSeat.setText(Integer.toString(i));   // set seat number to button text
            if (sm.getBookings().containsKey(i)) {
                btnSeat.setDisable(true);   // if seat is already booked, make button non-clickable
            }

            btnSeat.setMinWidth(50);
            btnSeat.setMinHeight(50);

//            btnSeat.setOnAction(this::bookSeat);    // when user click - bookSeat method is called

            grid.getColumnConstraints().add(new ColumnConstraints(50));     // set column width

            // code below arranges the buttons - like in a train compartment

            grid.add(btnSeat, colIndex, rowIndex);
            if (i % 4 == 0) {
                rowIndex++;
            }
            colIndex = i % 4;
            if (colIndex >= 2){
                colIndex++;
            }

        }

        Button btnAdmin = new Button();
        btnAdmin.setText("Admin Tools");
        btnAdmin.setOnAction(this::alert);

        root.setCenter(grid);
        root.setTop(btnAdmin);
        primaryStage.show();
    }

    @FXML
    private void alert(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Do you want to use Admin Tools?");
        Optional<ButtonType> res = a.showAndWait();
        if (res.get() == ButtonType.OK){
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setContentText("You're too stupid to use Admin Tools!");
            a2.showAndWait();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
