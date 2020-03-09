package bookingApp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SeatManager {
    static final int SEATING_CAPACITY = 42;

    public HashMap<Integer, String> getBookings() {
        return bookings;
    }

    private HashMap<Integer, String> bookings = new HashMap<>();

    String addCustomer(int seatNumber, String passenger){
        if (seatNumber > 0 && seatNumber <= SEATING_CAPACITY){
            if (!bookings.containsKey(seatNumber)){
                bookings.put(seatNumber, passenger);
                return "Seat " + seatNumber + " was booked for passenger " + bookings.get(seatNumber);
            }
            else {
                return "Seat is already booked!";
            }
        }
        else {
            return "Seat number not in range!";
        }
    }

    String removeCustomer(String passengerName){
        if (bookings.containsValue(passengerName)){

            for (int seatNo: bookings.keySet()){
                String p = bookings.get(seatNo);
                if (p.equals(passengerName)){
                    bookings.remove(seatNo);
                    return "Passenger " + p + " removed from seat " + seatNo;
                } // end if p == passengerName
            }   // end for
        }// end - if bookings has passengerName
        return "Passenger not found!";
    }

    LinkedHashMap<Integer, String> viewBookings(){
        LinkedHashMap<Integer, String> sortedBookings = new LinkedHashMap();
        String[] names = bookings.values().toArray(new String[0]);  // assign passenger names to array

        String temp;
        for (int j = 0; j < names.length; j++) {

            for (int i = j + 1; i < names.length; i++) {

                if (names[i].compareTo(names[j]) < 0) {

                    temp = names[j];
                    names[j] = names[i];
                    names[i] = temp;
                }
            }
        }

        for (String t : names){
            for (Map.Entry<Integer, String> e: bookings.entrySet()){
                if (e.getValue().equals(t)){
                    sortedBookings.put(e.getKey(), e.getValue());
                }
            }
        }
        return sortedBookings;

    }

}
