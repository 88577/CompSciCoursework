import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class ScheduleController {
        public static void InsertBookingTiming(int bookingID, String day, int time){
            try {
                boolean running = true;
                PreparedStatement ps1 = Main.db.prepareStatement("INSERT INTO Schedule (bookingID, day, time) VALUES (?, ?, ?)");
                ps1.setInt(1,bookingID);
                ps1.setString(2, day);
                ps1.setInt(3, time);

                PreparedStatement ps2 = Main.db.prepareStatement("SELECT * FROM Schedule");
                ResultSet results = ps2.executeQuery();
                while ((results.next()) && (running == true)){
                    int bookingID2 = results.getInt(1);
                    String day2 = results.getString(2);
                    int time2 = results.getInt(3);

                    if ((day2.equals(day)) && (time2 == time)){
                        if ((CourtController.CheckConflictingBookings(bookingID, bookingID2)) == true){
                            running = false;
                        }
                    }
                }
                if(running == true){
                    System.out.println("Court conflict not found");
                    ps1.executeUpdate();
                    System.out.println("Records successfully added");
                }else {
                    System.out.println("Court conflict found");
                    System.out.println("Records not added");
                }

            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
        public static void ListBookingTiming(int bookingID){
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT day, time FROM Schedule WHERE bookingID = ?");
                ps.setInt(1, bookingID);
                ResultSet results = ps.executeQuery();
                while(results.next()){
                    String day = results.getString(1);
                    int time = results.getInt(2);
                    System.out.println(day + " " + time);
                }
            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
        public static void ListDaysBookings(String day){
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
        public static void ListTimesBooking(String day, int time){
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
        public static void ListAllBookings(){
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
