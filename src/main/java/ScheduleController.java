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
}
