import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;

public class userManager {

    public String hash(String password){
        String pswrdH = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            pswrdH = DatatypeConverter.printHexBinary(digest).toUpperCase(); //hashed password
        }catch (Exception e){
            e.printStackTrace();
        }
        return pswrdH;
    }

    public boolean isRegistered(String login){
        boolean isRegistered = true;
        try {
            // sqlite driver import
            Class.forName("org.sqlite.JDBC");

            //connecting with db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");

            //creating, executing query
            String sql = "SELECT * FROM users WHERE login LIKE ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, login);
            ResultSet resultSet = pstmt.executeQuery();

            //if no results were found user is not registered
            if (!resultSet.next()){
                isRegistered = false;
            }

            //closing connection and statement
            if(connection != null){
                pstmt.close();
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isRegistered;
    }

    public void register(String login, String password){
        try {
            //creating connection with db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");

            //creating, executing query (inserts user data to db)
            String sql = "INSERT INTO USERS (login, password) VALUES (?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, login);
            pstmt.setString(2, hash(password));
            pstmt.executeUpdate();

            //closing connection and statement
            if(connection != null){
                pstmt.close();
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean logIn(String login, String password){
        boolean logIn = false;
        try {
            //creating connection with db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");

            //creating, executing query
            String sql = "SELECT * FROM users WHERE login LIKE ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, login);
            ResultSet resultSet =  pstmt.executeQuery();

            //checks if there is user with that login and if there is checks if password matches with password from db
            if (resultSet.next()){
                String db_pass = resultSet.getString("password");
                if(db_pass.equalsIgnoreCase(hash(password))){
                    logIn = true;
                }else {
                    logIn = false;
                }
            }else {
                logIn = false;
            }

            //closing connection and statement
            if(connection != null){
                pstmt.close();
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return logIn;
    }

    public int getUserID(String login){
        int userID = 0;
        try{
            //connecting with db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");

            //creating, executing query
            String sql = "SELECT * FROM users WHERE login LIKE ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, login);
            ResultSet resultSet = pstmt.executeQuery();

            //gets user's id to int userID
            while (resultSet.next()){
                userID = resultSet.getInt("IDuser");
            }

            //closing connection and statement
            if(connection != null){
                pstmt.close();
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return userID;
    }
}
