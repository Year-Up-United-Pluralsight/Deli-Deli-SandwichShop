package com.pluralsight.generics;
import com.pluralsight.console.Console;
import java.util.List;


public class OptionChoice<T> {

    private final Console console;


    public OptionChoice() {
        this.console = new Console();
    }


    public T selectOption(String prompt, List<T> options, T original) {

        while (true) {

           int choice = console.promptForInt(prompt + " Change it? (1-Yes, 2-No): ");

            if(choice == 0){
                return null;
            } else if(choice == 2){
                return original;
            } else if (choice == 1) {
                for (int i = 0; i < options.size(); i++) {
                    System.out.println((i + 1) + ". " + options.get(i));
                }


                while (true) {
                    int selection = console.promptForInt("Select the corresponding number(0 to quit): ");

                    if (selection == 0) {
                        int quit = console.promptForInt("Do you want to quit? 1 or 2: ");
                        if (quit == 1) {
                            return null;
                        } else if (quit == 2) {
                            continue;
                        }
                    }
                        if (selection >= 1 && selection <= options.size()) {
                            return options.get(selection - 1);
                        } else {
                        System.out.println("Invalid selection...Will now use the original option");
                            }

                }
            }

            else {
                System.out.println("Invalid option...try again");
            }
        }

    }
}


