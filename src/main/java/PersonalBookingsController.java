import com.sun.org.apache.bcel.internal.generic.Select;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersonalBookingsController {
    public static void listAllUserBookings(){
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM personalBookings");
            ResultSet results = ps.executeQuery();
            while(results.next()){
                int userID = results.getInt(1);
                int bookingID = results.getInt(2);
                System.out.println(userID + " " + bookingID);

            }

        }catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }
    }
    public static void listUserBookings(int userID){
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT bookingID FROM personalBookings WHERE userID = ?");
            ps.setInt(1, userID);
            ResultSet results = ps.executeQuery();
            while (results.next()){
                int bookingID = results.getInt(1);
                System.out.println(bookingID);
            }
        }catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }
    }
    public static void listBookingsUsers(int bookingID){
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT userID FROM personalBookings WHERE bookingID = ?");
            ps.setInt(1, bookingID);
            ResultSet results = ps.executeQuery();
            while (results.next()){
                int userID = results.getInt(1);
                System.out.println(userID);
            }
        }catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }
    }
    public static void insertUserBooking(int userID, int bookingID){
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO personalBookings (userID, bookingID) values (?, ?)");
            ps.setInt(1, userID);
            ps.setInt(2, bookingID);
            ps.executeUpdate();
            System.out.println("Records successfully added");
        }catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }

    }
}
