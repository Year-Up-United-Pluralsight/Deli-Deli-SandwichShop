package com.pluralsight.generics;
import com.pluralsight.console.Console;
import java.util.List;




public class AddToOptionChoice<T> {

    private final Console console;



    public AddToOptionChoice() {
        this.console = new Console();
    }


    public T toCollection(String prompt, List<T> options) {


        while (true) {

            int choice = console.promptForInt(prompt + "add another? (1-Yes, 2-No): ");

            if (choice == 0) {
                return null;
            } else if (choice == 1) {
                for (int i = 0; i < options.size(); i++) {
                    System.out.println((i + 1) + " " + options.get(i));

                }
            } else if (choice == 2) {
                System.out.println("Not adding");
                break;
            } else {
                System.out.println("Invalid selection");
                continue;


            }


            while (true) {
                int selection = console.promptForInt("Select the corresponding number: ");

                if (selection == 0) {
                    int quit = console.promptForInt("Do you want to quit?: ");
                    if (quit == 1) {
                        return null;
                    } else if (quit == 2) {
                        continue;
                    }
                }

                if (selection >= 1 && selection <= options.size()) {
                    return options.get(selection - 1);
                } else {
                    System.out.println("Invalid Selection....");

                }
            }


        }

      return null;
    }
}





