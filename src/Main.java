import java.util.Scanner;
/*
plans: !! Math.random must show 1 not 1.0 !!; logging in, registering; marking lessons as done if done 100% correctly;lesson creator for admin

Done today:
few practice sets for a lesson (choosing random)
created database
made showing tips from database
 */
public class Main {

    public void work(){
        appManager aM = new appManager();
        Scanner intSc = new Scanner(System.in);
        boolean cont = true;
        boolean subCont = false;
        int op = 0;
        int subOp = 0;
        String lessonName = null;

        System.out.println("Welcome to our German learning app!");
        do {
            System.out.println("");
            System.out.println("Available lessons:");
            System.out.println("[1] Basics 1");
            System.out.println("[2] The");
            System.out.println("[3] Basics 2");
            System.out.println("[4] Phrases");
            System.out.println("Leave the app [5]");
            op = intSc.nextInt();

            switch (op) {
                case 1: {    //Lesson Basics 1
                    lessonName = "Basics 1";
                    subCont = true;
                    do {
                        System.out.println("");
                        System.out.println("Lesson: Basics 1");
                        System.out.println("[1] Tips");
                        System.out.println("[2] Practice");
                        System.out.println("[3] Go back");
                        subOp = intSc.nextInt();

                        switch (subOp) {
                            case 1: {
                                aM.showTips(lessonName);
                                break;
                            }
                            case 2: {
                                aM.practice(lessonName);
                                break;
                            }
                            case 3: {
                                subCont = false;
                                break;
                            }
                        }

                    } while (subCont);
                    break;
                }

                case 2: {    //Lesson 2
                    lessonName = "The";
                    subCont = true;
                    do {
                        System.out.println("");
                        System.out.println("Lesson: The");
                        System.out.println("[1] Tips");
                        System.out.println("[2] Practice");
                        System.out.println("[3] Go back");
                        subOp = intSc.nextInt();

                        switch (subOp) {
                            case 1: {
                                aM.showTips(lessonName);
                                break;
                            }
                            case 2: {
                                aM.practice(lessonName);
                                break;
                            }
                            case 3: {
                                subCont = false;
                                break;
                            }
                        }

                    } while (subCont);
                    break;
                }

                case 5: {
                    System.out.println("Bye!");
                    cont = false;
                    break;
                }

            }

        }while(cont);
        intSc.close();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.work();
    }
}
