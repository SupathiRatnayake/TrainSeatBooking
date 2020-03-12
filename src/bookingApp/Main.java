package bookingApp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    private static final int SEATING_CAPACITY = 42;

    @Override
    public void start(Stage primaryStage) throws Exception{
        String pName, choice, message;
        int seatNo;
        LinkedHashMap<Integer, String> ol;
        Scanner in = new Scanner(System.in);
        SeatManager sm = new SeatManager(SEATING_CAPACITY);

        LinkedHashMap<String, String> options = new LinkedHashMap<>();
        options.put("A", "Add customer to seat");
        options.put("E", "Display Empty Seats");
        options.put("D", "Delete customer from seat");
        options.put("F", "Find the seat for a given customers name");
        options.put("S", "Store program data");
        options.put("L", "Load program data");
        options.put("O", "View seats Ordered alphabetically by name");
        options.put("Q", "quit the program");

//        init_map(sm);     // method to fill Dummy data to sm.bookings

        do {

            for (String m : options.keySet()) {
                System.out.println(m + ": " + options.get(m));
            }

            System.out.print("Please select option: ");

            choice = in.nextLine().toUpperCase();

            switch (choice) {
                case "A":
                    System.out.println(options.get("A"));
                    pName = In.getStr("Passenger Name: ");
                    seatNo = In.getInt("Seat Number: ");
                    message = sm.addCustomer(seatNo, pName);
                    System.out.println(message);
                    In._wait();
                    break;

                case "E":
                    System.out.println(options.get("E"));
                    showEmptySeats(sm);
                    break;

                case "D":
                    System.out.println(options.get("D"));
                    pName = In.getStr("Name: ");
                    message = sm.deleteCustomer(pName);
                    System.out.println(message);
                    In._wait();

                    break;

                case "F":
                    pName = In.getStr("Please enter passenger name: ");
                    message = sm.findSeat(pName);
                    System.out.println(message);
                    In._wait();
                    break;

                case "S":
                    System.out.println(options.get("S"));
                    message = sm.storeData();
                    System.out.println(message);
                    break;

                case "L":
                    System.out.println(options.get("L"));
                    message = sm.loadData();
                    System.out.println(message);
                    In._wait();
                    break;

                case "O":
                    ol = sm.getBookingsOrdered();
                    System.out.println(ol);
                    In._wait();
                    break;

                case "Q":
                    System.out.println("Quiting program...");
                    break;

                default:
                    System.out.println("Please enter a valid option!");

            }

        } while (!choice.equals("Q"));

        System.exit(0);
    }

    private void showEmptySeats(SeatManager sm) {
        Stage window;
        Scene sceneSeats;
        BorderPane borderPane;
        ScrollPane sp;
        GridPane grid;
        Button btnSeat;
        int rowIndex,colIndex;
        String styleAvailable, styleBooked;

        grid = new GridPane();
        rowIndex = colIndex = 0;

        styleAvailable = "-fx-background-color: #9f9; -fx-border-color: #595";
        styleBooked = "-fx-background-color: #f00; -fx-text-fill: #ff0; -fx-border-color: #500";

        // Make buttons ans place in Grid FOR SEATING_CAPACITY
        for (int i = 1; i <= SEATING_CAPACITY; i++) {   // make buttons for each and every seat
            btnSeat = new Button();
            btnSeat.setMinWidth(50);
            btnSeat.setMinHeight(50);
            btnSeat.setText(Integer.toString(i));   // set seat number to button text
            btnSeat.setStyle(styleAvailable);

            if (sm.getBookings().containsKey(i)) {
                btnSeat.setDisable(true);   // if seat is already booked, make button non-clickable
                btnSeat.setStyle(styleBooked);
            }

            // When button is clicked handle method is called
            // Alert user to book seat
            // IF user response OK
                // GET name
                // Call SeatManager.addCustomer()
                // Show information
                // disable button, set style to styleBooked
            // END IF
            btnSeat.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Button btn;
                    Alert a1, a2;
                    String message;
                    btn = (Button) event.getSource();

                    // Alert a confirmation to book the seat
                    a1 = new Alert(Alert.AlertType.CONFIRMATION);
                    a1.setContentText("Do you want to book seat No." + btn.getText());
                    Optional<ButtonType> res = a1.showAndWait();


                    if (res.get() == ButtonType.OK) { // IF user clicked OK
                        String name = In.getStr("Please enter name: "); // GET name
                        message = sm.addCustomer(Integer.parseInt(btn.getText()), name);  // PUT entry
                        // Alert booking information
                        a2 = new Alert(Alert.AlertType.INFORMATION);
                        a2.setContentText(message);
                        a2.showAndWait();
                        btn.setDisable(true);
                        btn.setStyle(styleBooked);
                    }

                }
            });

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

        }   // END FOR

        grid.setPadding(new Insets(10));
        sp = new ScrollPane();
        sp.setContent(grid);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        borderPane = new BorderPane();
        borderPane.setCenter(sp);

        sceneSeats = new Scene(borderPane, 300, 400);

        window = new Stage();
        window.setScene(sceneSeats);
        window.setTitle("Denuwara Menike Seat Booking");
        window.showAndWait();

    }

    private static void init_map(SeatManager sm) {
        Random r = new Random();
        String[] d = {"Sam", "Aaron", "Danny", "Matt", "Stacey"};
        for (int i = 1; i < d.length; i++) {

            int n = r.nextInt(42) + 1;
            sm.addCustomer(n, d[i]);

        }
        sm.addCustomer(40, "James");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
