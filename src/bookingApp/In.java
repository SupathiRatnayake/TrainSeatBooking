package bookingApp;

import java.util.Scanner;

public class In {
    private static Scanner in = new Scanner(System.in);

    public static int getInt(String hint){
        int n;
        System.out.print(hint);
        while (!in.hasNextInt()){
            System.out.println("Please enter integers only");
            System.out.print(hint);
            in.next();
        }
        n = in.nextInt();
        in.nextLine();
        return n;
    }

    public static int getInt(String hint, int min, int max){
        int n;
        n = getInt(hint);
        while (n < min || n > max){
            System.out.println("Number must be between " + min + " and " + max);
            n = getInt(hint);
        }
        return n;
    }

    public static String getStr(String hint){
        System.out.print(hint);
        return in.nextLine();
    }


    public static char getOption(String hint, char[] options){
        int i = 0;
        char choice;
        try {

            choice = getStr(hint).charAt(0);

            for (char op: options){
                if (choice == op){
                    return choice;
                }
                i++;
            }
            if (i == options.length){
                System.out.println("Please enter option in.. ");
                System.out.println(options);
                choice = getOption(hint, options);
            }
        }
        catch (StringIndexOutOfBoundsException e){
            System.out.println("Please enter option in.. ");
            System.out.println(options);
            choice = getOption(hint, options);
        }
        return choice;
    }

    public static void showAndWait(){
        getStr("Press enter to return to main menu");
    }

}
