import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class adminManager {

    public void addLesson(String lessonName){
        try{
            //connecting with db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");

            //creating, executing query
            String sql = "SELECT * FROM lessons WHERE lessonName like ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, lessonName);
            ResultSet resultSet = pstmt.executeQuery();

            //checks if there is already lesson named like that. If there is not, adds it
            if (!resultSet.next()){
                sql = "INSERT INTO lessons(lessonName) VALUES (?)";
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, lessonName);
                pstmt.executeUpdate();
                System.out.println("Successfully added a new lesson!");
            }else if (resultSet.next()){
                System.out.println("Lesson named like that already exists!");
            }

            //closing connection and statement
            if(connection != null){
                pstmt.close();
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printLessonsQuestions(){
        userManager uM = new userManager();
        try{
            //connecting with db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");

            //Creating, executing query
            String sql = "SELECT * FROM lessons";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            //counts how many questions are there for a lesson
            sql = "SELECT COUNT(lessonID) FROM questions WHERE lessonID = ?";
            pstmt = connection.prepareStatement(sql);

            //print results
            System.out.println("Available lessons: ");
            for (int i = 1; resultSet.next(); i++){
                //[1] Basics 1 (5 questions)
                pstmt.setInt(1, i);
                ResultSet rs = pstmt.executeQuery();
                System.out.println("[" + i + "] " + resultSet.getString("lessonName") + " (" + rs.getInt("COUNT(lessonID)") + " questions)");
            }

            //closing connection and statement
            if(connection != null){
                pstmt.close();
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printLessonsTip(){
        userManager uM = new userManager();
        try{
            //connecting with db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");

            //Creating, executing query
            String sql = "SELECT * FROM lessons LEFT JOIN tips ON IDlesson = lessonID";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            //print results
            for (int i = 1; resultSet.next(); i++){
                String hasTip = null;
                if (resultSet.getString("tip") == null){
                    hasTip = "No tip!";
                }else {
                    hasTip = "Has a tip";
                }
                //[1] Basics 1
                System.out.println("[" + i + "] " + resultSet.getString("lessonName") + " (" + hasTip + ")");
            }

            //closing connection and statement
            if(connection != null){
                pstmt.close();
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addTip(int lessonID, String tip){
        try {
            //connecting with db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");

            //creating, executing query
            String sql = "SELECT * FROM tips WHERE lessonID like ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, lessonID);
            ResultSet resultSet = pstmt.executeQuery();

            //checks if chosen lesson has a tip. If not, adds it
            if (!resultSet.next()){
                sql = "INSERT INTO tips(lessonID, tip) VALUES (?, ?)";
                pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, lessonID);
                pstmt.setString(2, tip);
                pstmt.executeUpdate();
                System.out.println("Successfully added a tip!");
            }else {
                System.out.println("This lesson already has a tip!");
            }

            //closing connection and statement
            if(connection != null){
                pstmt.close();
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addQuestion(int lessonID, String question, HashMap<String, Integer> QandA){
        try {
            //connecting with db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");

            //insert the new question
            String sql = "INSERT INTO questions(lessonID, question) VALUES (?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, lessonID);
            pstmt.setString(2, question);
            pstmt.executeUpdate();

            //get id of the new question
            sql = "SELECT * FROM questions WHERE question LIKE ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, question);
            ResultSet resultSet = pstmt.executeQuery();

            int qID = 0;    //id of the new question
            while (resultSet.next()){
                qID = resultSet.getInt("IDquestion");
            }

            //adds answers from map to db
            sql = "INSERT INTO answers(questionID, answer, isCorrect) VALUES (?, ?, ?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, qID);
            for (Map.Entry<String, Integer> entry : QandA.entrySet()) {
                pstmt.setString(2, entry.getKey());
                pstmt.setInt(3,entry.getValue());
                pstmt.executeUpdate();
            }

            System.out.println("Question added successfully!");

            //closing connection and statement
            if(connection != null){
                pstmt.close();
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
