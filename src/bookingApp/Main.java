package bookingApp;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Scanner;

public class Main extends Application {

    SeatManager sm = new SeatManager();
    Stage window;
    Scene sc1, sc2;
    BorderPane bp1, bp2;
    GridPane grid1, grid2;
    Label lbl1, lbl2;
    TextField txt;
    PasswordField pw;
    Button btnAdmin;
    boolean sessionIsOn = false;




    @Override
    public void start(Stage primaryStage) throws Exception{
        this.window = primaryStage;

        // Scene sc1

        // BorderPane
        bp1 = new BorderPane();

        // ScrollPane to contain button grid
        ScrollPane sp = new ScrollPane();


        grid1 = new GridPane();     // make grid pane

        grid1.setPadding(new Insets(10));    // grid pane settings


        // init row and column indexes
        int rowIndex = 0;
        int colIndex = 0;

        // make buttons for number of seats
        for (int i = 1; i <= SeatManager.SEATING_CAPACITY; i++) {   // make buttons for each and every seat
            Button btnSeat = new Button();
            btnSeat.setText(Integer.toString(i));   // set seat number to button text
            if (sm.getBookings().containsKey(i)) {
                btnSeat.setDisable(true);   // if seat is already booked, make button non-clickable
            }

            btnSeat.setMinWidth(50);
            btnSeat.setMinHeight(50);

//            btnSeat.setOnAction(this::bookSeat);    // when user click - bookSeat method is called

            grid1.getColumnConstraints().add(new ColumnConstraints(50));     // set column width

            // code below arranges the buttons - like in a train compartment

            grid1.add(btnSeat, colIndex, rowIndex);
            if (i % 4 == 0) {
                rowIndex++;
            }
            colIndex = i % 4;
            if (colIndex >= 2){
                colIndex++;
            }

        }   // seat representing button creation process ends


        btnAdmin = new Button(); // button to access login scene
        btnAdmin.setPadding(new Insets(10));
        btnAdmin.setText("Admin Tools");
        btnAdmin.setOnAction(e -> window.setScene(sc2));

        sp.setContent(grid1);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        bp1.setCenter(sp);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20));
        hBox.getChildren().add(btnAdmin);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        bp1.setTop(hBox);
        sc1 = new Scene(bp1, 300, 600);    // setting scene 1
        // Scene sc1 done

        // Scene sc2


        Button btnLogin, btnBack;
        lbl1 = new Label("Username");
        lbl1.setPadding(new Insets(20));
        lbl2 = new Label("Password");
        lbl2.setPadding(new Insets(20));
        txt = new TextField();
        pw = new PasswordField();
        btnLogin = new Button("Login");
        btnLogin.setPadding(new Insets(20));
        btnLogin.setOnAction(this::login);
        btnBack = new Button("Back");
        btnBack.setOnAction(e -> window.setScene(sc1));
        btnBack.setPadding(new Insets(20));
        grid2 = new GridPane();
        grid2.setPadding(new Insets(20));
        grid2.getRowConstraints().add(new RowConstraints(50));     // set row height
//        grid1.getColumnConstraints().add(new ColumnConstraints(100));     // set column width

        grid2.add(lbl1, 0, 0);
        grid2.add(txt, 1, 0);
        grid2.add(lbl2, 0, 1);
        grid2.add(pw, 1, 1);
        grid2.add(btnBack, 0, 2);
        grid2.add(btnLogin, 1, 2);

        bp2 = new BorderPane();
        bp2.setCenter(grid2);

        sc2 = new Scene(bp2, 300, 600);

        // Scene sc2 done


        // window settings
        window.setTitle("Train Seat Booking");
        window.setScene(sc1);
        window.setMaxWidth(300);
        window.show();
    }

    @FXML
    private void login(ActionEvent actionEvent) {
        String username, password, message;
        Alert a;
        username = "admin";
        password = "admin123";


        if (txt.getText().equals(username) && pw.getText().equals(password)){
            message = "Login Successful!";
            a = new Alert(Alert.AlertType.INFORMATION);
            sessionIsOn = true;
            btnAdmin.setText("Log out");
            btnAdmin.setOnAction(e ->{
                Alert a2;
                a2 = new Alert(Alert.AlertType.CONFIRMATION);
                a2.setContentText("Do you want to log out?");
                Optional<ButtonType> res = a2.showAndWait();
                if (res.get() == ButtonType.OK){
                    sessionIsOn = false;
                    btnAdmin.setText("Admin Tools");
                    btnAdmin.setOnAction(e2 -> window.setScene(sc2));
                }
            });
            window.setScene(sc1);

        }
        else {
            message = "Username and Password does not match!";
            a = new Alert(Alert.AlertType.ERROR);
        }

        txt.clear();
        pw.clear();

        a.setContentText(message);
        a.showAndWait();

        if (sessionIsOn){
            runAdmin();
        }

    }

    private void runAdmin() {
        String line = In.getStr("Nigga what da ya want?");
        System.out.println("Fuck off jack ass! U'll never get what u want");
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
