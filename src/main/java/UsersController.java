import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class UsersController{

        public static void UserCheck(){
            // Selects every email and password to compare with user's login details
            try{
                PreparedStatement ps = Main.db.prepareStatement("SELECT (email, password) FROM Users");
                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    String email = results.getString(1);
                    String password = results.getString(2);
                    System.out.println(email + " " + password);
                }
                } catch (Exception exception){
                    System.out.println("Error: " + exception.getMessage());
                }
            }
        public static void InsertUsers(String firstName, String lastName, String password, String email, boolean admin) {
            // Inserts all new user data into the table Users
            try{
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (firstName, lastName, password, email, admin) values (?, ?, ?, ?, ?)");
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, password);
                ps.setString(4, email);
                ps.setBoolean(5, admin);
                ps.executeUpdate();
                System.out.println("Records successfully added");
            }   catch (Exception exception){
                System.out.println("Error: " + exception.getMessage());
            }
        }
        public static void ListAllUsers(){
            // Selects all users for use by admin
            try{
                PreparedStatement ps = Main.db.prepareStatement("SELECT (userID, firstName, lastName, password, email, admin) FROM Users");
                ResultSet results = ps.executeQuery();
                while(results.next()){
                    int userID = results.getInt(1);
                    String firstName = results.getString(2);
                    String lastName = results.getString(3);
                    String password = results.getString(4);
                    String email = results.getString(5);
                    boolean admin = results.getBoolean(6);
                    System.out.println(userID + " " + firstName + " " + lastName + " " + password + " " + email + " " + admin);
                }
            }   catch (Exception exception){
                System.out.println("Error " + exception.getMessage());
            }
        }
    public static void ListUsers(int userID){
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT (userID, firstName, lastName, password, email, admin) FROM Users WHERE userID = ?");
            ps.setInt(1, userID);
            ResultSet results = ps.executeQuery();
            while(results.next()){
                int UserID = results.getInt(1);
                String firstName = results.getString(2);
                String lastName = results.getString(3);
                String password = results.getString(4);
                String email = results.getString(5);
                boolean admin = results.getBoolean(6);
                System.out.println(UserID + " " + firstName + " " + lastName + " " + password + " " + email + " " + admin);
            }
        }   catch (Exception exception){
            System.out.println("Error " + exception.getMessage());
        }
    }
        public static void DeleteUser(int userID){
            try {
                PreparedStatement ps1 = Main.db.prepareStatement("DELETE FROM personalBookings WHERE userID = ?");
                ps1.setInt(1, userID);
                ps1.executeUpdate();
                PreparedStatement ps2 = Main.db.prepareStatement("DELETE FROM Users WHERE userID = ?");
                ps2.setInt(1, userID);
                ps2.executeUpdate();
                System.out.println("Records removed successfully");
            } catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
        public static void UpdateUser(int userID, String firstName, String lastName, String password, String email, boolean admin){
            try {
                PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET firstName = ?, lastName = ?, password = ?, email = ?, admin = ? WHERE userID = ?");
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, password);
                ps.setString(4, email);
                ps.setBoolean(5, admin);
                ps.setInt(6, userID);
                ps.executeUpdate();
                System.out.println("Records successfully updated");
            }catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }

}


