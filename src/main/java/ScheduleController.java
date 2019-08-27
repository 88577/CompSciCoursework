import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class ScheduleController {
        public static void bookingTiming(int bookingID){
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT day, time FROM Schedule WHERE bookingID = ?");
                ps.setInt(1, bookingID);
                ResultSet results = ps.executeQuery();
                while(results.next()){
                    Date day = results.getDate(1);
                    int time = results.getInt(2);
                    System.out.println(day + " " + time);
                }
            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
        public static void listDaysBookings(String day){
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT bookingID, time FROM Schedule WHERE day = ?");
                ps.setString(1, day);
                ResultSet results = ps.executeQuery();
                while(results.next()){
                    int bookingID = results.getInt(1);
                    int time = results.getInt(2);
                    System.out.println(bookingID + " " + time);
                }
            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
        public static void listTimesBooking(String day, int time){
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT bookingID FROM Schedule WHERE day = ? AND time = ?");
                ps.setString(1, day);
                ps.setInt(2, time);
                ResultSet results = ps.executeQuery();
                while (results.next()){
                    int bookingID = results.getInt(1);
                    System.out.println(bookingID);
                }
            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
        public static void listAllBookings(){
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Schedule");
                ResultSet results = ps.executeQuery();
                while (results.next()){
                    int bookingID = results.getInt(1);
                    String day = results.getString(2);
                    int time = results.getInt(3);
                    System.out.println(bookingID + " " + day + " " + time);
                }
            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
}
