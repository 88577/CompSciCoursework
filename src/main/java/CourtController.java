import com.sun.org.apache.bcel.internal.generic.Select;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourtController {
        public static void ListBookedCourts(int bookingID){
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT courtNo FROM Courts WHERE bookingID = ?");
                ps.setInt(1, bookingID);
                ResultSet results = ps.executeQuery();
                while(results.next()){
                    int courtNo = results.getInt(1);
                    System.out.println(courtNo);
                }
            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
}
