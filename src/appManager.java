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
        Scanner intSc = new Scanner(System.in);
        BufferedReader read = null;
        String[] qa = null; //Questions and Answers

        try{
            read = new BufferedReader(new FileReader("resources/lesson "+lessonName+"/practice.txt"));
            String line = null;
            while ((line = read.readLine()) != null) {
                qa = line.split(";;");    //Get Questions and Answers
            }
//            for (int i = 1; i == qa.length; i =+2){ //Makes answer a number
//                int answr = Integer.parseInt(qa[i]);
//            }

            for (int i = 0; i < qa.length; i =+ 2){     //Work in progress TODO: Practice
                System.out.println(qa[i]);
                int userAnswr = intSc.nextInt();
                if (userAnswr == Integer.parseInt(qa[i+1])){

                }
            }
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
        intSc.close();
    }

}
