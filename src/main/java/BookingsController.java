import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingsController {

        public static void insertBookings(int bookingType, String description, int slots){
            try{
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Bookings (bookingType, description, slots) VALUES (?, ?, ?)");
                ps.setInt(1, bookingType);
                ps.setString(2, description);
                ps.setInt(3, slots);
                ps.executeUpdate();
                System.out.println("Records successfully added");
            }catch (Exception e){
                System.out.println("Error" + e.getMessage());
            }
    }

        public static void listBookings(){
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Bookings");
                ResultSet results = ps.executeQuery();
                while (results.next()){
                    int bookingID = results.getInt(1);
                    int bookingType = results.getInt(2);
                    String description = results.getString(3);
                    int slots = results.getInt(4);
                    System.out.println(bookingID + " " + bookingType + " " + description + " " + slots);
                }
            }catch (Exception e){
                System.out.println("Error" + e.getMessage());
            }
        }
        public static void deleteBookings(int bookingID){
            try {
                PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Bookings WHERE bookingID = ?");
                ps.setInt(1, bookingID);
                ps.executeUpdate();
            }catch (Exception e){
                System.out.println("Error" + e.getMessage());
            }
        }
        public static void updateBookings(int bookingID, int bookingType, String description, int slots){
            try{
                PreparedStatement ps = Main.db.prepareStatement("UPDATE Bookings SET bookingType = ?, description = ?, slots = ? WHERE bookingID = ?");
                ps.setInt(1, bookingType);
                ps.setString(2, description);
                ps.setInt(3,slots);
                ps.setInt(4, bookingID);
                ps.executeUpdate();
            } catch (Exception e){
                System.out.println("Error" + e.getMessage());
            }
        }
}
