import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class appManager {

    public void showTips(String lessonName){
        try {
            // sqlite driver import
            Class.forName("org.sqlite.JDBC");

            //creating connection with db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");

            //creating statement
            String sql = "SELECT tip from tips INNER JOIN lessons on tips.lessonID = lessons.IDlesson WHERE lessons.lessonName LIKE ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, lessonName);
            ResultSet resultSet = pstmt.executeQuery();

            while(resultSet.next()){
                System.out.println(resultSet.getString("tip"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void doPractice(String lessonName){
        try{
            //creating connection with db
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");

            //TODO: in loop show questions and answers from database and check if user answer is right

        }catch (Exception e){
            e.printStackTrace();
        }
    }
//
//    public void showTips(String lessonName){
//        BufferedReader read = null;
//        try{
//            read = new BufferedReader(new FileReader("resources/lesson "+lessonName+"/tips.txt"));
//            String line = "";
//            while ((line = read.readLine()) != null)
//                System.out.println(line);
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if (read != null){
//                try {
//                    read.close();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
    public void practice(String lessonName){
        Scanner stringSc = new Scanner(System.in);
        BufferedReader read = null;
        double random = (int)(Math.random() * 3 + 1);
        String[] qa = null; //Questions and Answers
        int allQ = 0; //all questions counter
        int correctA = 0; //correct answers counter

        try{
            //Reads file and gets Questions and Answers to String qa by splitting text by ';;'
            read = new BufferedReader(new FileReader("resources/lesson "+lessonName+"/practice " + random +".txt"));
            String line = null;
            while ((line = read.readLine()) != null) {
                qa = line.split(";;");

                //shows questions and checks if user input is right
                for (int i = 0; i < qa.length; i =+ 2){
                    allQ++;
                    System.out.println(qa[i]);
                    String userAnswr = stringSc.nextLine();
                    if (qa[i + 1].equals(userAnswr)){
                        System.out.println("Correct!");
                        correctA++;
                    }else{
                        System.out.println("Wrong.");
                    }
                }
            }
            System.out.println("Your score: " + correctA + "/" + allQ);


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (read != null){
                try {
                    read.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
