import java.util.Scanner;
/*
Plans: Users; marking lessons as done if done correctly; few practice sets for a lesson
 */
public class Main {

    public void work(){
        appManager aM = new appManager();
        Scanner intSc = new Scanner(System.in);
        boolean cont = true;
        boolean subCont = true;
        int op = 0;
        int subOp = 0;
        String lessonName = null;

        System.out.println("Welcome to our German learning app!");
        do {
            System.out.println("Available lessons:");
            System.out.println("[1] Basics 1");
            System.out.println("[2] The");
            System.out.println("[3] Basics 2");
            System.out.println("[4] Phrases");
            op = intSc.nextInt();

            switch (op){
                case 1:{    //Lesson 1
                    lessonName = "Basics 1";
                    do {
                        System.out.println("Lesson: Basics 1");
                        System.out.println("[1] Tips");
                        System.out.println("[2] Practice");
                        System.out.println("[3] Go back");
                        subOp = intSc.nextInt();

                        switch (subOp){
                            case 1:{
                                aM.tips(lessonName);
                                break;
                            }
                            case 2:{
                                //TODO READ FILE WITH PRACTICE, USING METHOD
                                break;
                            }
                            case 3:{
                                subCont = false;
                                break;
                            }
                        }

                    }while (subCont);
                    break;
                }
                case 2:{    //Lesson 2
                    lessonName = "The ";
                    do {
                        System.out.println("Lesson: The");
                        System.out.println("[1] Tips");
                        System.out.println("[2] Practice");
                        System.out.println("[3] Go back");
                        subOp = intSc.nextInt();

                        switch (subOp){
                            case 1:{
                                aM.tips(lessonName);
                                break;
                            }
                            case 2:{
                                //TODO READ FILE WITH PRACTICE, USING METHOD
                                break;
                            }
                            case 3:{
                                subCont = false;
                                break;
                            }
                        }

                    }while (subCont);
                    break;
                }
            }

        }while(cont);
        intSc.close();
    }

    public static void main(String[] args) {

    }
}
