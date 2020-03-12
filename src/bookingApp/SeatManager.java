package bookingApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class SeatManager {
    private int numberOfSeats;  // number of seats in compartment
    private HashMap<Integer, String> bookings; // store int seatNo as key, String name as value

    public SeatManager(int numberOfSeats) {
        // SET numberOfSeats to this.numberOfSeats
        // initialize this.bookings as new HashMap
        this.numberOfSeats = numberOfSeats;
        this.bookings = new HashMap<>();
    }

    public HashMap<Integer, String> getBookings() {
        return bookings;    // send HashMap bookings
    }

    String addCustomer(int seatNumber, String passenger){
        // IF seatNumber is in valid range
        if (seatNumber > 0 && seatNumber <= numberOfSeats){
            // IF bookings does not have a record already
            if (!bookings.containsKey(seatNumber)){
                bookings.put(seatNumber, passenger);    // PUT seatNumber as key, passenger as value
                // return information message
                return "Seat " + seatNumber + " was booked for passenger " + bookings.get(seatNumber);
            }
            else {
                //IF bookings have a record
                return "Seat is already booked!";
            }
        }
        else {
            // IF seat number is not in valid range
            return "Seat number not in range!";
        }
    }

    String deleteCustomer(String passengerName){

        // IF bookings have passengerName in values
        if (bookings.containsValue(passengerName)){

            for (int seatNo: bookings.keySet()){
                String p = bookings.get(seatNo);
                if (p.equals(passengerName)){
                    bookings.remove(seatNo);
                    return "Passenger " + p + " removed from seat " + seatNo;
                } // END IF
            }   // END FOR
        }
        // IF bookings does not have passengerName in values
        return "Passenger not found!";
    }



    LinkedHashMap<Integer, String> getBookingsOrdered(){
        // Initialize new LinkedHashMap as it keeps insertion order
        LinkedHashMap<Integer, String> sortedBookings = new LinkedHashMap();
        String[] names = bookings.values().toArray(new String[0]);  // assign passenger names to array

        String temp;    // variable to store a String value temporarily
        for (int j = 0; j < names.length; j++) {

            for (int i = j + 1; i < names.length; i++) {

                if (names[i].compareTo(names[j]) < 0) {
                    // swapping happens here
                    temp = names[j];
                    names[j] = names[i];
                    names[i] = temp;
                }   //END IF
            }   // END inner FOR
        }   // END outer FOR

        // FOREACH String name in names
            // FOREACH entry in bookings
                // IF entry.value equals name
                    // PUT entry into sortedBookings
                // END IF
            // END inner FOREACH
        // END outer FOREACH
        for (String t : names){
            for (Map.Entry<Integer, String> e: bookings.entrySet()){
                if (e.getValue().equals(t)){
                    sortedBookings.put(e.getKey(), e.getValue());
                }
            }
        }
        return sortedBookings;  // return alphabetically ordered LinkedHashMap

    }

    public String findSeat(String pName) {
        if (bookings.containsValue(pName)){
            for (int key: bookings.keySet()){
                if (bookings.get(key).equals(pName)){
                    return "Passenger: " + pName + "\t-\tSeat No. " + key;
                }
            }
        }
        return "Passenger name not found in bookings!";
    }


    public String storeData() {

        String record;

        record = bookings.toString();
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter("bookings.txt");
            fWriter.write(record);
            System.out.println("Writing successful");
            fWriter.close();

            return "Written to file successfully!";
        }
        catch (IOException e) {
            return e.getMessage();
        }

    }

    public String loadData(){
        File file = new File("bookings.txt");

        String text = "";

        Scanner in;
        try {
            in = new Scanner(file);
            while (in.hasNextLine()){
                text = text.concat(in.nextLine()); // concatenate each line to text
            }


            text = text.substring(1, text.length() - 1);    // removing "{" and "}" from both end

            String[] textList = text.split(", "); // make String array splitting by ", "


            for (String s : textList) {

                String[] entry = s.split("=");
                System.out.println(entry[0]);
                System.out.println(entry[1]);
                bookings.put(Integer.parseInt(entry[0]), entry[1]);
            }
            return "Data successfully loaded from file!";

        }
        catch (FileNotFoundException e) {
            return  e.getMessage();
        }


    }

}
