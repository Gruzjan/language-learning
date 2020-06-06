import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class appManager {

    public void tips(String lessonName){
        BufferedReader read = null;
        try{
            read = new BufferedReader(new FileReader("resources/lesson "+lessonName+"/tips.txt"));
            String line = "";
            while ((line = read.readLine()) != null)
                System.out.println(line);
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


    public void practice(String lessonName){
        Scanner stringSc = new Scanner(System.in);
        BufferedReader read = null;
        String[] qa = null; //Questions and Answers
        int allQ = 0; //all questions counter
        int correctA = 0; //correct answers counter

        try{
            //Reads file and gets Questions and Answers to String qa by splitting text by ';;'
            read = new BufferedReader(new FileReader("resources/lesson "+lessonName+"/practice.txt"));
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
