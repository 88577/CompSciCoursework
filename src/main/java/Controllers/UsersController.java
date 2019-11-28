package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("UserController/")
public class UsersController{
        @POST
        // Inserting data
        @Path("InsertUser")
        // Defines the API path for method
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String InsertUser(
                // Inserts a new user into the database
                @FormDataParam("userID") Integer userID,
                @FormDataParam("firstName") String firstName,
                @FormDataParam("lastName") String lastName,
                @FormDataParam("password") String password,
                @FormDataParam("email") String email,
                @FormDataParam("admin") Boolean admin)
                // Retrieves all form data inputted by user
                {
            try{
                if(userID == null || firstName == null || lastName == null || password == null || email == null || admin == null){
                    // Check that no value is null
                    throw new Exception("One or more form data parameters are missing from the HTTP request");
                }
                System.out.println("InsertUser/new userID=" + userID);
                // Output to console to monitor API call

                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (userID, firstName, lastName, password, email, admin) values (?, ?, ?, ?, ?, ?)");

                ps.setInt(1, userID);
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, password);
                ps.setString(5, email);
                ps.setBoolean(6, admin);
                ps.execute();
                return "{\"status\": \"OK\"}";

            }   catch (Exception exception){
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
            }
        }
        @GET
        // Retrieving data
        @Path("ListAllUsers")
        // Defines the API path for method
        @Produces(MediaType.APPLICATION_JSON)
        public String ListAllUsers(){
            // Lists all users, and corresponding user details from Users table
            System.out.println("UserController/ListAllUsers");
            // Output to console to monitor API call
            JSONArray list = new JSONArray();
            // Instantiates a JSON array to hold JSON objects for each user
            try{
                PreparedStatement ps = Main.db.prepareStatement("SELECT userID, firstName, lastName, password, email, admin FROM Users");
                ResultSet results = ps.executeQuery();
                while(results.next()){
                    JSONObject item = new JSONObject();
                    // Instantiates a JSON object to hold attributes for one user
                    item.put("userID", results.getInt(1));
                    item.put("firstName", results.getString(2));
                    item.put("lastName", results.getString(3));
                    item.put("password", results.getString(4));
                    item.put("email", results.getString(5));
                    item.put("admin", results.getBoolean(6));
                    list.add(item);
                    // Puts all values into JSON object
                }
                return list.toString();
            }   catch (Exception exception){
                System.out.println("Error " + exception.getMessage());
                return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
            }
        }
        @GET
        // Retrieving data
        @Path("ListUser/{userID}")
        // Defines the API path for method
        @Produces(MediaType.APPLICATION_JSON)
        public String ListUser(@PathParam("userID") Integer userID) throws Exception {
            // Lists all user details for one specific user
            if(userID == null){
                // Check for whether userID exists
                throw new Exception("User ID is missing from HTTP request");
            }
            System.out.println("UserController/ListUser" + userID);
            // Output to console to monitor API call
            JSONObject item = new JSONObject();
            // Instantiates a JSON object to hold user's attributes
            try{
                PreparedStatement ps = Main.db.prepareStatement("SELECT firstName, lastName, password, email, admin FROM Users WHERE userID = ?");
                ps.setInt(1, userID);
                ResultSet results = ps.executeQuery();
                if(results.next()){
                    item.put("userID", userID);
                    item.put("firstName", results.getString(1));
                    item.put("lastName", results.getString(2));
                    item.put("password", results.getString(3));
                    item.put("email", results.getString(4));
                    item.put("admin", results.getBoolean(5));
                    // Puts all values into JSON object
                }
                return item.toString();
                // Returns values as a String
            }   catch (Exception exception){
                System.out.println("Database error " + exception.getMessage());
                return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
            }
        }
        @POST
        // Changing data in the database
        @Path("DeleteUser")
        // Defines the API path for method
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String DeleteUser(@FormDataParam("userID") Integer userID){
            // Deletes a user from the Users table
            try {
                if(userID == null){
                    // Check if the userID is null
                    throw new Exception("One or more form data parameters are missing in the HTTP request");
                }
                System.out.println("UserController/DeleteUser userID=" + userID);

                PreparedStatement ps1 = Main.db.prepareStatement("DELETE FROM personalBookings WHERE userID = ?");
                ps1.setInt(1, userID);
                ps1.execute();
                PreparedStatement ps2 = Main.db.prepareStatement("DELETE FROM Users WHERE userID = ?");
                ps2.setInt(1, userID);
                ps2.execute();
                return "{\"status\": \"OK\"}";

            } catch (Exception e){
                System.out.println("Database error: " + e.getMessage());
                return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
            }
        }
        @POST
        @Path("UpdateUser")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String UpdateUser(
                @FormDataParam("userID") Integer userID,
                @FormDataParam("firstName") String firstName,
                @FormDataParam("lastName") String lastName,
                @FormDataParam("password") String password,
                @FormDataParam("email") String email,
                @FormDataParam("admin") Boolean admin) {
            try {
                if(userID == null || firstName == null || lastName == null || password == null || email == null || admin == null){
                    throw new Exception("One or more form data parameters are missing in the HTTP request");
                }
                System.out.println("UserController/UpdateUser userID=" + userID);
                PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET firstName = ?, lastName = ?, password = ?, email = ?, admin = ? WHERE userID = ?");
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, password);
                ps.setString(4, email);
                ps.setBoolean(5, admin);
                ps.setInt(6, userID);
                ps.execute();
                return "{\"status\": \"OK\"}";

            }catch (Exception e){
                System.out.println("Database error: " + e.getMessage());
                return "{\"error\": \"Unable to update item, please see server console for more info\"}";
            }
        }

}


