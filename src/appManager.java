import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;

public class appManager {

    public Connection connect(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db.db");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public void showTips(String lessonName){
        try {
            // sqlite driver import
            Class.forName("org.sqlite.JDBC");

            //connecting with db
            Connection connection = connect();

            //creating, executing query
            String sql = "SELECT tip from tips INNER JOIN lessons on tips.lessonID = lessons.IDlesson WHERE lessons.lessonName LIKE ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, lessonName);
            ResultSet resultSet = pstmt.executeQuery();

            //print tip
            while(resultSet.next()){
                System.out.println(resultSet.getString("tip"));
            }

            //closing connection and statement
            if(connection != null){
                connection.close();
                pstmt.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getLessonID(String lessonName){
        int lessonID = 0;
        try {
            //connecting with db
            Connection connection = connect();

            //creating executing query
            String sql = "SELECT * FROM lessons WHERE lessonName LIKE ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, lessonName);
            ResultSet resultSet = pstmt.executeQuery();

            //assign ID of lesson to lessonID
            while (resultSet.next()){
                lessonID = resultSet.getInt("IDlesson");
            }

            //closing connection and statement
            if(connection != null){
                connection.close();
                pstmt.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lessonID;
    }

    public String getLessonName(int lessonID){

        String lessonName = "";
        try {
            //connecting with db
            Connection connection = connect();

            //creating, executing query
            String sql = "SELECT * FROM lessons WHERE IDlesson = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, lessonID);
            ResultSet resultSet = pstmt.executeQuery();

            //gets lesson name to string lessonName
            while (resultSet.next()){
                lessonName = resultSet.getString("lessonName");
            }

            //closing connection and statement
            if(connection != null){
                connection.close();
                pstmt.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return lessonName;
    }

    //I think, I shouldn't have done this so big :/
    public void practice(String lessonName, String login, Scanner scan){
        try{
            //connecting with db
            Connection connection = connect();

            //===getting questions
            //creating, executing query (gets first 3 random questions for that lesson from db)
            //if you want more questions per lesson change this limit number and numbers in other places where it's needed (it's marked by !!)
            String sql = "SELECT * FROM questions INNER JOIN lessons ON lessons.IDlesson = questions.lessonID WHERE lessonName LIKE ? ORDER BY RANDOM() LIMIT 3";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, lessonName);
            ResultSet resultSet = pstmt.executeQuery();

            ArrayList<String> questionList = new ArrayList<>();
            ArrayList<Integer> idList = new ArrayList<>();

            //getting questions to questionList and IDquestions to idList
            for (int i = 0; resultSet.next(); i++){
                questionList.add(resultSet.getString("question"));
                idList.add(resultSet.getInt("IDquestion"));
            }

            //===getting answers
            //!! if you want more questions just add another case
            //HashMaps hold answers and value isCorrect from db
            HashMap<String, Integer> firstQ = new HashMap<>();
            HashMap<String, Integer> secondQ = new HashMap<>();
            HashMap<String, Integer> thirdQ = new HashMap<>();

            for (int i = 0; i < questionList.size(); i++){

                sql = "SELECT * FROM answers WHERE questionID = ?";
                pstmt = connection.prepareStatement(sql);
                switch (i){

                    case 0: {
                        pstmt.setInt(1, idList.get(i)); //first IDquestion in idList
                        resultSet = pstmt.executeQuery();
                        //gets answers and value of isCorrect to firstQ map
                        while (resultSet.next()) {
                            firstQ.put(resultSet.getString("answer"), resultSet.getInt("isCorrect"));
                        }
                        break;
                    }

                    case 1: {
                        pstmt.setInt(1, idList.get(1)); //second IDquestion in idList
                        resultSet = pstmt.executeQuery();
                        //gets answers and value of isCorrect to secondQ map
                        while (resultSet.next()) {
                            secondQ.put(resultSet.getString("answer"), resultSet.getInt("isCorrect"));
                        }
                        break;
                    }

                    case 2: {
                        pstmt.setInt(1, idList.get(2)); //2 - third IDquestion in idList
                        resultSet = pstmt.executeQuery();
                        //gets answers and value of isCorrect to firstQ map
                        while (resultSet.next()) {
                            thirdQ.put(resultSet.getString("answer"), resultSet.getInt("isCorrect"));
                        }
                        break;
                    }
                }
            }

            //===Communication with user
            //usrAnswr and answrIndex allow to "index" a map (Helps check if user answer is correct).
            int usrAnswr = 0;
            String[] answrIndex = {"","","",""};
            float allA = 0;   //counts all answers
            float crrctA = 0; //counts correct answers

            for (int i = 0; i < questionList.size(); i++){

                switch (i) {

                    case 0: {
                        //first question
                        System.out.println(questionList.get(i));
                        int index = 1; //numbers answers for user
                        int k = 0;
                        for (Map.Entry<String, Integer> entry : firstQ.entrySet()) { //iterates through answers for the first question
                            System.out.println("[" + index + "] " + entry.getKey()); //prints answers with index
                            answrIndex[k] = entry.getKey(); //puts answer in answrIndex on k place
                            k++;
                            index++;
                        }
                        usrAnswr = scan.nextInt();
                        if (firstQ.get(answrIndex[usrAnswr - 1]) == 1) { //checks if answer is correct
                            System.out.println("Correct!");
                            allA++;
                            crrctA++;
                        } else {
                            System.out.println("Wrong.");
                            allA++;
                        }
                        break;
                    }

                    case 1: {
                        //second question
                        System.out.println(questionList.get(i));
                        int index = 1; //numbers answers for user
                        int k = 0;
                        for (Map.Entry<String, Integer> entry : secondQ.entrySet()) { //iterates through answers for the second question
                            System.out.println("[" + index + "] " + entry.getKey()); //prints answers with index
                            answrIndex[k] = entry.getKey(); //puts answer in answrIndex on k place
                            k++;
                            index++;
                        }
                        usrAnswr = scan.nextInt();
                        if (secondQ.get(answrIndex[usrAnswr - 1]) == 1) { //checks if answer is correct
                            System.out.println("Correct!");
                            allA++;
                            crrctA++;
                        } else {
                            System.out.println("Wrong.");
                            allA++;
                        }
                        break;
                    }

                    case 2: {
                        //third question
                        System.out.println(questionList.get(i));
                        int index = 1; //numbers answers for user
                        int k = 0;
                        for (Map.Entry<String, Integer> entry : thirdQ.entrySet()) { //iterates through answers for the third question
                            System.out.println("[" + index + "] " + entry.getKey()); //prints answers with index
                            answrIndex[k] = entry.getKey(); //puts answer in answrIndex on k place
                            k++;
                            index++;
                        }
                        usrAnswr = scan.nextInt();
                        if (thirdQ.get(answrIndex[usrAnswr - 1]) == 1) { //checks if answer is correct
                            System.out.println("Correct!");
                            allA++;
                            crrctA++;
                        } else {
                            System.out.println("Wrong.");
                            allA++;
                        }
                        break;
                    }
                }
            }
            //===PRINT USER'S SCORE
            float percentage = 0;
            if (allA != 0){ //in case of dividing by zero
                percentage = crrctA / allA * 100;
                System.out.println("Your score: " + new DecimalFormat("#").format(crrctA) + "/" + new DecimalFormat("#").format(allA) + " (" + percentage + "%)");
            }else{
                System.out.println("Something went wrong");
            }

            //===INSERT USER'S SCORE TO DB
            //gets user's ID
            userManager uM = new userManager();
            int userID = uM.getUserID(login);

            //gets id of lesson
            int lessonID = getLessonID(lessonName);

            //check if it was user's first try. If it was insert values, if it wasn't update values
            sql = "SELECT * FROM percentages WHERE userID = ? AND lessonID = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, lessonID);
            resultSet = pstmt.executeQuery();

            if (!resultSet.next()){
                sql = "INSERT INTO percentages VALUES (?, ?, ?)";
                pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, userID);
                pstmt.setInt(2, lessonID);
                pstmt.setFloat(3, percentage);
                pstmt.executeUpdate();
            }else if (resultSet.getFloat("percentage") < percentage){
                sql = "UPDATE percentages SET percentage = ? WHERE userID = ? AND lessonID = ?";
                pstmt = connection.prepareStatement(sql);
                pstmt.setFloat(1, percentage);
                pstmt.setInt(2, userID);
                pstmt.setInt(3, lessonID);
                pstmt.executeUpdate();
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

    public float getPercentage(int userID, int lessonID){
       float percentage = 0;
       try {
           //connecting with db
           Connection connection = connect();

           //Creating, executing query
           String sql = "SELECT * FROM percentages WHERE userID = ? AND lessonID = ?";
           PreparedStatement pstmt = connection.prepareStatement(sql);
           pstmt.setInt(1, userID);
           pstmt.setInt(2, lessonID);
           ResultSet resultSet = pstmt.executeQuery();

           //assign user's percentage to float percentage
           while (resultSet.next()){
               percentage = resultSet.getFloat("percentage");
           }

           //closing connection and statement
           if(connection != null){
               pstmt.close();
               connection.close();
           }
       }catch (Exception e){
           e.printStackTrace();
       }

       return percentage;
    }

    public void printLessons(String login){
        userManager uM = new userManager();
        try{
            //connecting with db
            Connection connection = connect();

            //Creating, executing query
            String sql = "SELECT * FROM lessons";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            //print results
            System.out.println("Available lessons: ");
            for (int i = 3; resultSet.next(); i++){
                //[1] Basics 1 (50%); rounds to int numbers
                System.out.println("[" + i + "] " + resultSet.getString("lessonName") + " (" + new DecimalFormat("#").format(getPercentage(uM.getUserID(login), resultSet.getInt("IDlesson"))) + "%)");
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

}
