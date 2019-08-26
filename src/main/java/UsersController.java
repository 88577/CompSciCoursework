import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class UsersController {

        public static void insertUsers(int UserID, String firstName, String lastName, String password, String email, boolean admin) {
            try{
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (UserID, firstName, lastName, password, email, admin) values (?, ?, ?, ?, ?, ?)");
                ps.setInt(1, UserID);
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, password);
                ps.setString(5, email);
                ps.setBoolean(6, admin);
                ps.executeUpdate();
                System.out.println("Records successfully added");
            }   catch (Exception exception){
                System.out.println("Error: " + exception.getMessage());
            }
        }
        public static void listUsers(){
            try{
                PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, firstName, lastName, password, email, admin FROM Users");
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
                System.out.println("Database error: " + exception.getMessage());
            }

        }
        public static void deleteUser(int userID){
            try {
                PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE UserID = ?");
                ps.setInt(1, userID);
                ps.executeUpdate();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        public static void updateUser(String firstName, String lastName, String password, String email, boolean admin, int userID){
            try {
                PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET firstName = ?, lastName = ?, password = ?, email = ?, admin = ? WHERE userID = ?");
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, password);
                ps.setString(4, email);
                ps.setBoolean(5, admin);
                ps.setInt(6, userID);
                ps.executeUpdate();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
}


