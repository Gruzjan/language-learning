import java.util.HashMap;
import java.util.Scanner;
/*
TODO: lesson creator, modifier for admin
 */

public class Main {

    public void work(){
        userManager uM = new userManager();
        appManager aM = new appManager();
        adminManager adminM = new adminManager();
        Scanner intScan = new Scanner(System.in);
        Scanner stringScan = new Scanner(System.in);
        boolean cont = true;
        boolean subCont = false;
        int op = 0;
        int subOp = 0;
        boolean inMenu = true;
        boolean loggedIn = false;
        String lessonName = null;
        String login = null; //current user

        System.out.println("Welcome to our German learning app!");
        do {
            if (inMenu){
                System.out.println("[1] Register");
                System.out.println("[2] Log in");
                op = intScan.nextInt();

                switch (op){

                    //registering
                    case 1:{
                        do {
                            System.out.println("Enter login:");
                            login = stringScan.nextLine();
                            if(uM.isRegistered(login)){
                                System.out.println("This login is already taken. Please try again.");
                            }else {
                                System.out.println("Enter password:");
                                String pass = stringScan.nextLine();
                                System.out.println("Confirm your password:");
                                String passCnfrm = stringScan.nextLine();
                                if (pass.equals(passCnfrm)){    //checks if passwords match
                                    uM.register(login, pass);
                                    System.out.println("Register successful");
                                    op = 0;
                                }else{
                                    System.out.println("Passwords don't match. Please try again.");
                                }
                            }
                        }while (op == 1);
                        break;
                    }

                    //logging in
                    case 2:{
                        System.out.println("Please enter your login: ");
                        login = stringScan.nextLine();
                        System.out.println("Please enter your password: ");
                        String password = stringScan.nextLine();

                        if (uM.logIn(login, password)){
                            loggedIn = true;
                            inMenu = false;
                            op = 0;
                            System.out.println("Log in successful!");
                        }else {
                            System.out.println("Something went wrong. Please try again.");
                        }
                        break;
                    }
                }
            }

            //===Admin Panel
            if (loggedIn && login.equals("admin")){
                System.out.println("[1] Log out");
                System.out.println("[2] Close the app");
                System.out.println("[3] Add a new lesson");
                System.out.println("[4] Add a tip");
                System.out.println("[5] Add a question");
                op = intScan.nextInt();

                switch (op){
                    //logging out
                    case 1:{
                        System.out.println("Logging out!");
                        inMenu = true;
                        break;
                    }

                    //closing the app
                    case 2:{
                        System.out.println("Bye!");
                        cont = false;
                        break;
                    }

                    //creating lesson
                    case 3:{
                        System.out.println("Enter new lesson's name:");
                        String newLesson = stringScan.nextLine();
                        adminM.addLesson(newLesson);
                        break;
                    }

                    //adding a tip to lesson
                    case 4:{
                        System.out.println("Choose a lesson:");
                        adminM.printLessonsTip();
                        op = intScan.nextInt();
                        System.out.println("Adding to: " + aM.getLessonName(op));
                        System.out.println("Enter a tip: ");
                        String tip = stringScan.nextLine();
                        adminM.addTip(op, tip);
                        break;
                    }

                    //adding a question to lesson
                    case 5:{
                        System.out.println("Choose a lesson:");
                        adminM.printLessonsQuestions();
                        op = intScan.nextInt();

                        HashMap<String, Integer> QandA = new HashMap<>();

                        System.out.println("Adding to: " + aM.getLessonName(op));
                        System.out.println("Enter a question: ");
                        String question = stringScan.nextLine();

                        for (int i = 1; i < 5; i++){
                            System.out.println("Enter the answer number " + i);
                            String answr = stringScan.nextLine();
                            System.out.println("Is it correct? (0 for no; 1 for yes)");
                            int crrct = intScan.nextInt();
                            QandA.put(answr, crrct);
                        }

                        adminM.addQuestion(op, question, QandA);

                        break;
                    }
                }

                //===User's Panel
            }else if (loggedIn){
                System.out.println("[1] Log out");
                System.out.println("[2] Close the app");
                aM.printLessons(login);
                op = stringScan.nextInt();

                if (op == 1){
                    System.out.println("Logging out!");
                    inMenu = true;
                }else if (op == 2){
                    System.out.println("Bye!");
                    cont = false;
                }else {
                    lessonName = aM.getLessonName(op - 2);
                    subCont = true;

                    do {
                        System.out.println("Lesson: " + lessonName);
                        System.out.println("[1] Tips");
                        System.out.println("[2] Practice");
                        System.out.println("[3] Go back");
                        subOp = stringScan.nextInt();

                        switch (subOp) {
                            case 1: {
                                aM.showTips(lessonName);
                                break;
                            }
                            case 2: {
                                aM.practice(lessonName, login, intScan);
                                break;
                            }
                            case 3: {
                                subCont = false;
                                break;
                            }
                        }
                    }while (subCont);
                }

            }
            
        }while(cont);
        intScan.close();
        stringScan.close();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.work();
    }
}
