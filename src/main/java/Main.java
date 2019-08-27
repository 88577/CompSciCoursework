import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static Connection db = null;
    // Behaves like a global variable
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        openDatabase("courseworkDatabase.db");
        // Opening connection to Database

        ScheduleController.listTimesBooking("1", 12);
        ScheduleController.listAllBookings();

        closeDatabase();
    }

    private static  void openDatabase(String dbFile){
        try{
            Class.forName("org.sqlite.JDBC");
            // Loads the database driver
            SQLiteConfig config = new SQLiteConfig();
            // Does the database settings
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            // Opens database file
            System.out.println("Database connection successfully established.");
        }   catch (Exception exception){
            System.out.println("Database connection error: " + exception.getMessage());
            // Catches any errors and returns error statement
        }
    }

    private static void closeDatabase(){
        try{
            db.close();
            // Closes database
            System.out.println("Disconnected from database successfully.");
        }   catch (Exception exception){
            System.out.println("Database disconnection error:" + exception.getMessage());
        }
    }
}
