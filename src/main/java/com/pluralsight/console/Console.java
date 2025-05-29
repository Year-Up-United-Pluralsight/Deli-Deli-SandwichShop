package com.pluralsight.console;

import com.pluralsight.sandwich.Bread;

import java.awt.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Console {

   private static final Scanner scanner = new Scanner(System.in);


    public int promptForInt(String prompt) {
        boolean hasResult = false;
        int result = -1;
        while(!hasResult){
            try {

                System.out.print(prompt);
                result = scanner.nextInt();
                scanner.nextLine();
                hasResult = true;

            } catch (Exception e) {
                System.out.println(ColorCodes.RED +"Not a valid option, please try again"+ ColorCodes.RESET);
                scanner.next();


            }
        }

        return result;

    }

    public float promptForFloat(String prompt) {
        boolean hasResult = false;
        float result = -1;

        while (!hasResult) {
            try {
                System.out.print(prompt);
                result = scanner.nextFloat();
                return result;
            } catch (Exception e) {
                System.out.println(ColorCodes.RED + "Not a valid input, please enter a valid float." + ColorCodes.RESET);
                scanner.nextLine();
            }
        }
        return result;
    }


    public double promptForDouble(String prompt) {
        boolean hasResult = false;
        double result = -1;

        while (!hasResult) {
            try {
                System.out.print(prompt);
                result = scanner.nextDouble();
                scanner.nextLine();
                return result;

            } catch (Exception e) {
                System.out.println(ColorCodes.RED +"Not a valid input, please enter a valid double." + ColorCodes.RESET);
                scanner.nextLine();
            }
        }
        return result;
    }

    

    public String promptForString(String prompt){
        System.out.print(prompt);
        return scanner.nextLine().trim();


    }


    public LocalDate promptForDate(String prompt) {
        LocalDate result = null;
        boolean hasResult = false;

        while (!hasResult) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();

                if (!input.isEmpty()) {
                    result = LocalDate.parse(input);
                }
                hasResult = true;
            } catch (DateTimeParseException e) {
                System.out.println(ColorCodes.RED +"Incorrect date format. Please use (YYYY) or leave blank" + ColorCodes.RESET);
            }
        }

        return result;
    }


    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }


    public boolean getBoolean(String prompt) {
        String input;
        do {
            input = getString(prompt + " (y/n): ").toLowerCase();
        } while (!input.equals("y") && !input.equals("n"));
        return input.equals("y");
    }



    public Color getColor(String colorName) {

        try {
            Field field = Color.class.getField(colorName.toUpperCase());
            return (Color) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null; // Or handle the exception as needed
        }
    }



    public String promptForColor(String prompt){
        String result = null;

        boolean hasResult = false;

        while(!hasResult){
            System.out.println(prompt);

            result = scanner.nextLine().trim();

            if(getColor(result) != null){
                hasResult = true;
            } else {
                System.out.println(ColorCodes.RED + "Not a valid Color Name...Please Try Again..." + ColorCodes.RESET);
            }

        }
        return result;
    }








}