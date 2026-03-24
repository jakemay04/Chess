package ui;

import java.util.Scanner;

public class Repl {

    public void run() {
        System.out.print("Welcome to the Chess Game! Type Help, Login, Register or Quit to continue");

        Scanner scanner = new Scanner(System.in);
        var result = "";

        if (!result.equals("Quit")) {
            System.out.print(">>>");

            String line = scanner.nextLine();

            try {
                result = prelogin_eval(line);
            } catch (Throwable e) {
                System.out.print("Oops! There was an error.");
            }

        }
    }


}
