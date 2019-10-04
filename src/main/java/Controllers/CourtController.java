package Controllers;

import Server.Main;

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
        public static boolean CheckConflictingBookings(int bookingID1, int bookingID2){
            try {
                PreparedStatement ps1 = Main.db.prepareStatement("SELECT courtNo FROM Courts WHERE bookingID = ?");
                ps1.setInt(1, bookingID1);
                ResultSet results1 = ps1.executeQuery();

                PreparedStatement ps2 = Main.db.prepareStatement("SELECT courtNo FROM Courts WHERE bookingID = ?");
                ps2.setInt(1, bookingID2);
                ResultSet results2 = ps2.executeQuery();

                while(results1.next()){
                    while(results2.next()){
                        if (results1.getInt(1) == results2.getInt(1)){
                            System.out.println("Court booking conflict found");
                            return true;
                        }
                    }
                }
                System.out.println("Court booking conflict not found");
            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
            return false;
        }
        public static void ListAllCourtBookings(){
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Courts");
                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    int bookingID = results.getInt(1);
                    int courtNo = results.getInt(2);
                    System.out.println(bookingID + " " + courtNo);
                }
            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
        public static void InsertCourtBookings(int bookingID, int courtNo){
            try {
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Courts (bookingID, courtNo) VALUES (?, ?)");
                ps.setInt(1, bookingID);
                ps.setInt(2, courtNo);
                ps.executeUpdate();
                System.out.println("Records successfully added");
            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
        public static void DeleteCourtBookings(int bookingID){
            try {
                PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Courts WHERE bookingID = ?");
                ps.setInt(1, bookingID);
                ps.executeUpdate();
                System.out.println("Records removed successfully");
            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
}
