package com.pluralsight.userinterface;

import com.pluralsight.console.ColorCodes;
import com.pluralsight.console.Console;
import com.pluralsight.order.Chip;
import com.pluralsight.order.Drink;
import com.pluralsight.order.Order;
import com.pluralsight.filemanager.FileManager;
import com.pluralsight.order.MenuItem;
import com.pluralsight.sandwich.BLT;
import com.pluralsight.sandwich.Bread;
import com.pluralsight.sandwich.PhillyCheeseSteak;
import com.pluralsight.order.Sandwich;
import com.pluralsight.toppings.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {

    //Helps with prompting the user
    private final Console console = new Console();

    //Manages the Receipt
    private FileManager fileManager;

    //Creates an Order
    private Order order = new Order(LocalDateTime.now());


    //initial starting point
    public void init() {
        fileManager = new FileManager();

        welcomeScreen();

    }


    //Sandwich Shop Start
    private void welcomeScreen() {

        String welcomeMessage = ColorCodes.FLORAL_WHITE + """
                
                Welcome to Deli-Deli's Sandwich Shop.\s
                Would you like to order?\s
                1. Yes!\s
                0. No, I'll leave (Quit)\s
                Enter here:\s""";

        int choose;
        do {
            choose = console.promptForInt(welcomeMessage);
            switch (choose) {
                case 1:
                    menuOrders();
                    break;
                default:
                    System.out.println("Quitting...");
                    break;
            }


        } while (choose != 0);


    }

    //Menu Orders
    private void menuOrders() {
        String orderSelections = """ 
                Please select your order\s
                 1. Yes, Let's make a sandwich!\s
                 2. Order from a Menu
                 3. Add Drink
                 4. Add Chips
                 5. Checkout
                 0. Cancel the Order\s
                Enter here:\s""";

        int choose;
        boolean isTrue = true;
        String customerName = console.promptForString("What is your name?: ");
        order = new Order(customerName, LocalDateTime.now());
        while (isTrue) {
            choose = console.promptForInt(orderSelections);
            switch (choose) {
                case 1:
                    orderSandwich();
                    break;
                case 2:
                    signatureSandwiches();
                    break;
                case 3:
                    addDrinkToOrder();
                    break;
                case 4:
                    addChipsToOrder();
                    break;
                case 5:
                    checkOut();
                    break;
                default:
                    System.out.println("Are you sure you want to cancel the order?(You cannot undo this)");
                    int confirmation = console.promptForInt("Press 1 to confirm cancellation: ");
                    if (confirmation == 1) {
                        System.out.println("Order Canceled");
                        order.clearMenuItem();
                        isTrue = false;
                    } else if (confirmation > 1 | confirmation < 0) {
                        System.out.println("not a valid number");
                    }
            }
        }
    }


    //The order of making a sandwich
    private void orderSandwich() {

        int size = console.promptForInt("What size sandwich do you want [4, 8, 12]: ");
        Sandwich sandwich = new Sandwich(size);

        addBreadToSandwich(sandwich);
        addMeatToSandwich(sandwich);
        addCheeseToSandwich(sandwich);
        addNormalToppings(sandwich);
        addSauceToSandwich(sandwich);
        toastSandwich(sandwich);

        order.addItem(sandwich);

//        System.out.println("A sandwich for : " + "\n" + order.getCustomerName());


    }


    // Selects which parts to add to Sandwich Separately
    //Bread
    private void addBreadToSandwich(Sandwich sandwich) {
        System.out.println("Here is the lists of bread: ");
        int numbering = 1;
        for (Bread b : Bread.getBreadTypes()) {
            System.out.println(numbering + ". " + b);
            numbering++;
        }
        int chosenBread = console.promptForInt("Which bread do you want?: ");
        Bread selectedBread = Bread.getBreadTypes().get(chosenBread - 1);
        sandwich.setBreadType(selectedBread);
        System.out.println("\n " + ColorCodes.BOLD + selectedBread + " Bread selected \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
    }

    //Meat
    private void addMeatToSandwich(Sandwich sandwich) {

        boolean addMeat = true;

        while (addMeat) {


            int numbering2 = 1;

            for (Meat meat : Meat.getMeatTopping()) {
                System.out.println(numbering2 + " " + meat);
                numbering2++;

            }


            int meaty = console.promptForInt("Which meat do you want? (0 to move on or skip): ");

            if (meaty == 0) {
                addMeat = false;
                continue;
            }

            Meat m = Meat.getMeatTopping().get(meaty - 1);

            System.out.println("\n " + ColorCodes.BOLD + m + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


            int extra = console.promptForInt("Do you want extra " + m + " " + "(1 for yes, 0 for no): ");

            if (extra == 1) {
                for (Meat meat : Meat.getMeatTopping())
                    meat.setExtra(true);

                System.out.println(ColorCodes.BOLD + "\nExtra " + m + " added \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
            }
            sandwich.addTopping(m);


        }
    }

    //Cheese
    private void addCheeseToSandwich(Sandwich sandwich) {

        boolean addCheese = true;

        while (addCheese) {
            int numbering3 = 1;


            for (Cheese cheese : Cheese.getCheeseToppings()) {
                System.out.println(numbering3 + " " + cheese);
                numbering3++;
            }


            int che = console.promptForInt("Which cheese do you want?: ");
            if (che == 0) {
                addCheese = false;
                continue;
            } else if (che > Cheese.getCheeseToppings().size()) {
                System.out.println("Not a valid input");
            }
            Cheese c = Cheese.getCheeseToppings().get(che - 1);


            int extraCheese = console.promptForInt("Do you want extra " + c + " cheese?(1 for yes, 0 for no): ");
            if (extraCheese == 1) {
                for (Cheese cheese : Cheese.getCheeseToppings()) {
                    cheese.setExtra(true);
                    System.out.println(ColorCodes.BOLD + "\nExtra " + c + " added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                }
            }
            sandwich.addTopping(c);


        }

    }

    //NormalToppings
    private void addNormalToppings(Sandwich sandwich) {


        List<RegularToppings> normalT = new ArrayList<>();
        boolean normalToppings = true;

        while (normalToppings) {
            int numbering4 = 1;
            for (RegularToppings regular : RegularToppings.getRegularToppings()) {
                System.out.println(numbering4 + " " + regular);
                numbering4++;
            }


            int regular = console.promptForInt("Which regular topping do you want?(0 to skip): ");


            if (regular == 0) {
                break;
            } else if (regular < 1 || regular > RegularToppings.getRegularToppings().size()) {
                System.out.println("Not a valid input");
                continue;
            }

            RegularToppings normal = RegularToppings.getRegularToppings().get(regular - 1);

            sandwich.addTopping(normal);
            normalT.add(normal);

            System.out.println("Would you like another topping? ");
            int anotherTopping = console.promptForInt("1 for Yes, 0 for No: ");

            if (anotherTopping == 0) {
                normalToppings = false;

            } else if (anotherTopping != 1) {
                System.out.println("Not a valid input");

            }

            System.out.println(normalT);
        }
    }

    //Sauce
    private void addSauceToSandwich(Sandwich sandwich) {

        boolean addingSauce = true;

        while (addingSauce) {
            int numbering5 = 1;


            for (Sauce sauces : Sauce.getSauce()) {
                System.out.println(numbering5 + " " + sauces);
                numbering5++;
            }

            int addASauce = console.promptForInt("Which Sauce would you like to add? (0 to skip): ");
            if (addASauce == 0) {
                addingSauce = false;
                continue;
            }
            Sauce s = Sauce.getSauce().get(addASauce - 1);

            sandwich.addSauce(s);
        }


        boolean addingSideSauce = true;

        while (addingSideSauce) {
            int numbering6 = 1;

            for (SideSauce side : SideSauce.getSideSauces()) {
                System.out.println(numbering6 + " " + side.getName());
            }
            int addSideSauce = console.promptForInt("Which side sauce do you want to add?(0 to sip): ");

            if (addSideSauce == 0) {
                addingSideSauce = false;
                continue;
            }

            SideSauce ss = SideSauce.getSideSauces().get(addSideSauce - 1);


            if (addSideSauce >= 1) {
                int another = console.promptForInt("Would you like another? (1 for yes 0 for no): ");
                if (another == 0) {
                    addingSideSauce = false;

                }
            }

            sandwich.addSauce(ss);

        }


    }


    //Toasted // Not Toasted
    private void toastSandwich(Sandwich sandwich) {

        boolean check = true;

        while (check) {
            int toast = console.promptForInt("Would you like the sandwich toasted? (1 for yes, 0 for no): ");

            if (toast == 1) {
                sandwich.setToasted(true);
                check = false;
            } else if (toast == 0) {
                sandwich.setToasted(false);
                check = false;
            } else {
                System.out.println("Not a valid number");
            }
        }
    }


    //Signature Sandwiches
    private void signatureSandwiches() {
        String defaults = """
                 Here are the default orders:
                 1. BLT: 8" White Bread, Bacon, Cheddar, Lettuce, Tomato, Ranch, Toasted.
                 2. Philly Cheese Steak: 8" White Bread, Steak, American Cheese, Peppers, Mayo Toasted
                 Do you want pre-made sandwich (1-yes or 0-no):  \s
                \s""";

        int newSandwich;
        boolean newOrder = true;
        while (newOrder) {
            newSandwich = console.promptForInt(defaults);

            if (newSandwich == 1) {

                System.out.println("BLT: 8\" White Bread, Bacon, Cheddar, Lettuce, Tomato, Ranch, Toasted\n");
                System.out.println("***NOTE*** When building a sandwich, press 0 to cancel out of the process");


                int bltOrder = console.promptForInt("Do you want to customize this sandwich?(1-yes 2-no(0-Back)): ");

                BLT bltSandwich = new BLT();
                if (bltOrder == 1) {
                    changeBreadForBLT(bltSandwich);
                    addOrChangeMeatForBLT(bltSandwich);
                    addOrChangeCheeseForBLT(bltSandwich);
                    addOrChangeRegToppings1ForBLT(bltSandwich);
                    addOrChangeRegToppings2ForBLT(bltSandwich);
                    addOrChangeSauceOptionsForBLT(bltSandwich);


                    int wantToasted;

                    while (true) {
                        wantToasted = console.promptForInt("This sandwich comes Toasted...Do you want to remove this?(1 - Yes, 2 - No): ");
                        if (wantToasted == 1) {
                            bltSandwich.setToasted(false);
                            System.out.println(ColorCodes.BOLD + "\nThis sandwich is now toasted\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

                            break;
                        } else if (wantToasted == 2) {
                            bltSandwich.setToasted(true);
                            System.out.println(ColorCodes.BOLD + "\nThis sandwich is not toasted\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                            break;
                        } else {
                            System.out.println(ColorCodes.RED + "Not a valid input" + ColorCodes.RESET);

                        }
                    }

                    order.addItem(bltSandwich);
                    newOrder = false;
                } else {

                    order.addItem(bltSandwich);

                    System.out.println(bltSandwich);
                }


            } else if (newSandwich == 2) {

                System.out.println("Philly Cheese Steak: 8\" White Bread, Steak, American Cheese, Peppers, Mayo Toasted");
                int createPcs = console.promptForInt("Do you want to customize this sandwich?(1-yes 2-no): ");

                PhillyCheeseSteak pcs = new PhillyCheeseSteak();
                if (createPcs == 1) {
                        changeBreadForPCS(pcs);
                        addOrChangeMeatForPCS(pcs);
                        addOrChangeCheeseForPCS(pcs);
                        addOrChangeRegToppingForPCS(pcs);
                        addOrChangeSauceForPCS(pcs);


                    int wantToasted = console.promptForInt("This sandwich comes Toasted...Do you want to remove this?. (1 - Yes, 2 - No): ");
                    if (wantToasted == 1) {
                        pcs.setToasted(true);

                    } else if (wantToasted == 2) {
                        pcs.setToasted(false);

                    } else {
                        System.out.println(ColorCodes.RED + "Not a valid input" + ColorCodes.RESET);
                    }
                    order.addItem(pcs);
                    newOrder = false;
                } else {

                    order.addItem(pcs);

                    System.out.println(pcs);


                }

            } else {
                break;
            }
        }
    }


    //Signature Sandwich BLT Creation
    //Change Out Bread
    private void changeBreadForBLT(BLT bltSandwich) {
        boolean choice = true;
        if (choice == console.getBoolean("Adding White Bread: Do you want to change the bread type?: ")) {
            System.out.println("Here is the lists of bread: ");
            int numbering = 1;
            for (Bread b : Bread.getBreadTypes()) {
                System.out.println(numbering + ". " + b);
                numbering++;
            }
            int chosenBread = console.promptForInt("Which bread do you want?: ");
            Bread selectedBread = Bread.getBreadTypes().get(chosenBread - 1);
            bltSandwich.setBreadType(selectedBread);
            System.out.println("\n " + ColorCodes.BOLD + selectedBread + " Bread selected \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

        }

    }

    //Add more Meat/Change Out Meat
    private void addOrChangeMeatForBLT(BLT bltSandwich) {
        boolean choice = true;
        boolean addMeat = true;
        List<Meat> addedMeats = new ArrayList<>();
        if (choice == console.getBoolean("Adding Bacon: Do you want to change this Meat?: ")) {
            while (addMeat) {
                int numbering2 = 1;

                for (Meat meat : Meat.getMeatTopping()) {
                    System.out.println(numbering2 + " " + meat);
                    numbering2++;

                }

                int meaty = console.promptForInt("Which meat do you want? (0 to move on or skip): ");

                if (meaty == 0) {
                    addMeat = false;
                    continue;
                }

                Meat m = Meat.getMeatTopping().get(meaty - 1);

                System.out.println("\n " + ColorCodes.BOLD + m + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                int extra = console.promptForInt("Do you want extra " + m + " " + "(1 for yes, 0 for no): ");

                if (extra == 1) {
                    for (Meat meat : Meat.getMeatTopping())
                        meat.setExtra(true);

                    System.out.println(ColorCodes.BOLD + "\nExtra " + m + " added \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                }
                bltSandwich.replaceMeat(m);
                addedMeats.add(m);
                System.out.println(addedMeats);

            }
        } else {
            while (addMeat) {
                if (choice == console.getBoolean("Do you want to add different meat?: ")) {
                    int numbering3 = 1;
                    for (Meat meat : Meat.getMeatTopping()) {
                        System.out.println(numbering3 + " " + meat);
                        numbering3++;

                    }

                    int meaty = console.promptForInt("Which meat do you want? (0 to move on or skip): ");
                    if (meaty == 0) {
                            addMeat = false;
                            continue;
                    }

                    Meat m = Meat.getMeatTopping().get(meaty - 1);

                    System.out.println("\n " + ColorCodes.BOLD + m + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    int extra = console.promptForInt("Do you want extra " + m + " " + "(1 for yes, 0 for no): ");

                    if (extra == 1) {
                        for (Meat meat : Meat.getMeatTopping())
                            meat.setExtra(true);

                        System.out.println(ColorCodes.BOLD + "\nExtra " + m + " added \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    }
                    bltSandwich.addTopping(m);
                    addedMeats.add(m);
                } else {
                    break;
                }
            }
        }
    }


    //Add more Cheese or Change Out Cheese
    private void addOrChangeCheeseForBLT(BLT bltSandwich) {
        boolean addCheese = true;
        boolean choice = true;
        if (choice == console.getBoolean("Adding Cheddar Cheese: Do you want to change this? ")) {
            while (addCheese) {
                int numbering3 = 1;


                for (Cheese cheese : Cheese.getCheeseToppings()) {
                    System.out.println(numbering3 + " " + cheese);
                    numbering3++;
                }


                int che;
                try {
                    che = console.promptForInt("What type of cheese do you want?: ");

                    if (che == 0) {
                        addCheese = false;


                    } else if (che >= 1 && che <= Cheese.getCheeseToppings().size()) {
                        Cheese c = Cheese.getCheeseToppings().get(che - 1);
                        bltSandwich.replaceCheese(c);
                        System.out.println(ColorCodes.BOLD + "\n" + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                        int extraCheese = console.promptForInt("Do you want extra cheese?(1-Yes, 2-NO): ");

                            if(extraCheese == 1){
                                for(Cheese cheese: Cheese.getCheeseToppings()){
                                    cheese.setExtra(true);
                                }

                                System.out.println(ColorCodes.BOLD + "\n Extra " + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                            }


                    } else {
                        System.out.println( ColorCodes.BOLD + "\nInvalid Input, Please enter a number between 1 and " + Cheese.getCheeseToppings().size() + "\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter in a number");
                }
            }


        } else if (choice == console.getBoolean("Do you want to add more cheese?: ")) {
            while (addCheese) {
                int numbering4 = 1;

                for (Cheese cheese : Cheese.getCheeseToppings()) {
                    System.out.println(numbering4 + " " + cheese);
                    numbering4++;
                }

                int che;
                try {
                    che = console.promptForInt("What type of cheese do you want to add?: ");

                    if (che == 0) {
                        addCheese = false;


                    } else if (che >= 1 && che <= Cheese.getCheeseToppings().size()) {
                        Cheese c = Cheese.getCheeseToppings().get(che - 1);
                        bltSandwich.addTopping(c);
                        System.out.println(ColorCodes.BOLD + "\n" + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                        int extraCheese = console.promptForInt("Do you want extra cheese?(1-Yes, 2-NO: ");

                        if(extraCheese == 1){
                            for(Cheese cheese: Cheese.getCheeseToppings()){
                                cheese.setExtra(true);
                            }
                            System.out.println(ColorCodes.BOLD + "\nExtra " + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                        }
                    } else {
                        System.out.println( ColorCodes.BOLD + "\nInvalid Input, Please enter a number between 1 and " + Cheese.getCheeseToppings().size() + "\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter in a number");
                }



            }
        }


    }


    //Add more Regular Toppings or Change out Topping
    private void addOrChangeRegToppings1ForBLT(BLT bltSandwich) {
        boolean choice = true;
        if (choice == console.getBoolean("Adding Lettuce, Do you want to change this?: ")) {
            List<RegularToppings> normalT = new ArrayList<>();
            boolean normalToppings = true;

            while (normalToppings) {
                int numbering4 = 1;
                for (RegularToppings regular : RegularToppings.getRegularToppings()) {
                    System.out.println(numbering4 + " " + regular);
                    numbering4++;
                }


                int regular = console.promptForInt("Which regular topping do you want?(0 to skip): ");


                if (regular == 0) {
                    break;
                } else if (regular < 1 || regular > RegularToppings.getRegularToppings().size()) {
                    System.out.println("Not a valid input");
                    continue;
                }

                RegularToppings normal = RegularToppings.getRegularToppings().get(regular - 1);

                bltSandwich.replaceTopping(normal);
                normalT.add(normal);
                System.out.println(normalT);
                normalToppings = false;

            }


        }
    }

    //Add more Regular Toppings or Change out Topping
    private void addOrChangeRegToppings2ForBLT(BLT bltSandwich) {
        boolean choice = true;
        if (choice == console.getBoolean("Adding Tomato, Do you want to change this?: ")) {
            List<RegularToppings> normalT = new ArrayList<>();
            boolean normalToppings = true;

            while (normalToppings) {
                int numbering4 = 1;
                for (RegularToppings regular : RegularToppings.getRegularToppings()) {
                    System.out.println(numbering4 + " " + regular);
                    numbering4++;
                }


                int regular = console.promptForInt("Which regular topping do you want?(0 to skip): ");


                if (regular == 0) {
                    break;
                } else if (regular < 1 || regular > RegularToppings.getRegularToppings().size()) {
                    System.out.println("Not a valid input");
                    continue;
                }

                RegularToppings normal = RegularToppings.getRegularToppings().get(regular - 1);

                bltSandwich.replaceToppingAtIndex1(normal);
                normalT.add(normal);

                System.out.println("Would you like another topping? ");
                int anotherTopping = console.promptForInt("1 for Yes, 0 for No: ");

                if (anotherTopping == 0) {
                    normalToppings = false;

                } else if (anotherTopping != 1) {
                    System.out.println("Not a valid input");

                }

                System.out.println(normalT);


            }
        } else if (choice == console.getBoolean("Do you want to add more toppings?: ")) {
            List<RegularToppings> normalT = new ArrayList<>();
            boolean normalToppings = true;

            while (normalToppings) {
                int numbering5 = 1;
                for (RegularToppings regular : RegularToppings.getRegularToppings()) {
                    System.out.println(numbering5 + " " + regular);
                    numbering5++;
                }


                int regular = console.promptForInt("Which regular topping do you want?(0 to skip): ");


                if (regular == 0) {
                    break;
                } else if (regular < 1 || regular > RegularToppings.getRegularToppings().size()) {
                    System.out.println("Not a valid input");
                    continue;
                }

                RegularToppings normal = RegularToppings.getRegularToppings().get(regular - 1);

                bltSandwich.addTopping(normal);
                normalT.add(normal);

                boolean anotherTopping = true;
                if (anotherTopping == console.getBoolean("Would you like another topping?: ")) {
                    continue;
                } else {
                    normalToppings = false;
                }
                System.out.println(normalT);
            }
        }
    }

    //Add more Sauce or Change out Sauce
    private void addOrChangeSauceOptionsForBLT(BLT bltSandwich) {
        boolean addingSauce = true;
        boolean choice = true;
        if (choice == console.getBoolean("Adding Ranch, do you want to change this?: ")) {
            while (addingSauce) {
                int numbering5 = 1;


                for (Sauce sauces : Sauce.getSauce()) {
                    System.out.println(numbering5 + " " + sauces);
                    numbering5++;
                }

                int addASauce = console.promptForInt("Which Sauce would you like to add? (0 to skip): ");
                if (addASauce == 0) {
                    addingSauce = false;
                    continue;
                }
                Sauce s = Sauce.getSauce().get(addASauce - 1);

                bltSandwich.replaceSauce(s);
            }


            boolean addingSideSauce = true;

            while (addingSideSauce) {
                int numbering6 = 1;

                for (SideSauce side : SideSauce.getSideSauces()) {
                    System.out.println(numbering6 + " " + side.getName());
                }
                int addSideSauce = console.promptForInt("Which side sauce do you want to add?(0 to sip): ");

                if (addSideSauce == 0) {
                    addingSideSauce = false;
                    continue;
                }

                SideSauce ss = SideSauce.getSideSauces().get(addSideSauce - 1);


                if (addSideSauce >= 1) {
                    int another = console.promptForInt("Would you like another? (1 for yes 0 for no): ");
                    if (another == 0) {
                        addingSideSauce = false;

                    }
                }

                bltSandwich.addSauce(ss);

            }


        } else if(choice == console.getBoolean("Do you want to add another sauce?: ")){

            while (addingSauce) {
                int numbering5 = 1;


                for (Sauce sauces : Sauce.getSauce()) {
                    System.out.println(numbering5 + " " + sauces);
                    numbering5++;
                }

                int addASauce = console.promptForInt("Which Sauce would you like to add? (0 to skip): ");
                if (addASauce == 0) {
                    addingSauce = false;
                    continue;
                }
                Sauce s = Sauce.getSauce().get(addASauce - 1);

                bltSandwich.addSauce(s);
            }


            boolean addingSideSauce = true;

            while (addingSideSauce) {
                int numbering6 = 1;

                for (SideSauce side : SideSauce.getSideSauces()) {
                    System.out.println(numbering6 + " " + side.getName());
                }
                int addSideSauce = console.promptForInt("Which side sauce do you want to add?(0 to sip): ");

                if (addSideSauce == 0) {
                    addingSideSauce = false;
                    continue;
                }

                SideSauce ss = SideSauce.getSideSauces().get(addSideSauce - 1);


                if (addSideSauce >= 1) {
                    int another = console.promptForInt("Would you like another? (1 for yes 0 for no): ");
                    if (another == 0) {
                        addingSideSauce = false;

                    }
                }

                bltSandwich.addSauce(ss);

            }



        }
    }




    //Signature Sandwich PhillyCheeseSteak Creation
    //Change Out Bread
    private void changeBreadForPCS(PhillyCheeseSteak pcs){

        boolean choice = true;
        if (choice == console.getBoolean("Adding White Bread: Do you want to change the bread type?: ")) {
            System.out.println("Here is the lists of bread: ");
            int numbering = 1;
            for (Bread b : Bread.getBreadTypes()) {
                System.out.println(numbering + ". " + b);
                numbering++;
            }
            int chosenBread = console.promptForInt("Which bread do you want?: ");
            Bread selectedBread = Bread.getBreadTypes().get(chosenBread - 1);
            pcs.setBreadType(selectedBread);
            System.out.println("\n " + ColorCodes.BOLD + selectedBread + " Bread selected \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

        }



    }

    //Add more Meat/Change Out Meat
    private void addOrChangeMeatForPCS(PhillyCheeseSteak pcs){
        boolean choice = true;
        boolean addMeat = true;
        if (choice == console.getBoolean("Adding Steak: Do you want to change this Meat?: ")) {
            while (addMeat) {
                int numbering2 = 1;

                for (Meat meat : Meat.getMeatTopping()) {
                    System.out.println(numbering2 + " " + meat);
                    numbering2++;

                }

                int meaty = console.promptForInt("Which meat do you want? (0 to move on or skip): ");

                if (meaty == 0) {
                    addMeat = false;
                    continue;
                }

                Meat m = Meat.getMeatTopping().get(meaty - 1);

                System.out.println("\n " + ColorCodes.BOLD + m + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                int extra = console.promptForInt("Do you want extra " + m + " " + "(1 for yes, 0 for no): ");

                if (extra == 1) {
                    for (Meat meat : Meat.getMeatTopping())
                        meat.setExtra(true);

                }
                System.out.println(ColorCodes.BOLD + "\nExtra " + m + " added \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                pcs.addTopping(m);
                addMeat = false;

//                int addMore = console.promptForInt("Do you want to add another meat? (1-yes 2-No): ");
//                if(addMore == 1){
//
//
//                }

            }
        } else {
            while (addMeat) {
                if (choice == console.getBoolean("Do you want to add different meat?: ")) {
                    int numbering3 = 1;
                    for (Meat meat : Meat.getMeatTopping()) {
                        System.out.println(numbering3 + " " + meat);
                        numbering3++;

                    }

                    int meaty = console.promptForInt("Which meat do you want? (0 to move on or skip): ");
                    if (meaty == 0) {
                        addMeat = false;
                        continue;
                    }

                    Meat m = Meat.getMeatTopping().get(meaty - 1);

                    System.out.println("\n " + ColorCodes.BOLD + m + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    int extra = console.promptForInt("Do you want extra " + m + " " + "(1 for yes, 0 for no): ");

                    if (extra == 1) {
                        for (Meat meat : Meat.getMeatTopping())
                            meat.setExtra(true);

                        System.out.println(ColorCodes.BOLD + "\nExtra " + m + " added \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    }
                    pcs.addTopping(m);
                } else {
                    break;
                }
            }
        }
    }

    //Add more Cheese or Change Out Cheese
    private void addOrChangeCheeseForPCS(PhillyCheeseSteak pcs){
        boolean addCheese = true;
        boolean choice = true;
        if (choice == console.getBoolean("Adding Cheddar Cheese: Do you want to change this? ")) {
            while (addCheese) {
                int numbering3 = 1;


                for (Cheese cheese : Cheese.getCheeseToppings()) {
                    System.out.println(numbering3 + " " + cheese);
                    numbering3++;
                }


                int che;
                try {
                    che = console.promptForInt("What type of cheese do you want to add?: ");

                    if (che == 0) {
                        addCheese = false;


                    } else if (che >= 1 && che <= Cheese.getCheeseToppings().size()) {
                        Cheese c = Cheese.getCheeseToppings().get(che - 1);
                        pcs.replaceCheese(c);
                        int extraCheese = console.promptForInt("Do you want extra cheese?: ");

                        if(extraCheese == 1){
                            for(Cheese cheese: Cheese.getCheeseToppings()){
                                cheese.setExtra(true);
                                System.out.println(ColorCodes.BOLD + "\nExtra " + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                            }
                        }

                        System.out.println(ColorCodes.BOLD + "\n" + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    } else {
                        System.out.println( ColorCodes.BOLD + "\nInvalid Input, Please enter a number between 1 and " + Cheese.getCheeseToppings().size() + "\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter in a number");
                }
            }


        } else if (choice == console.getBoolean("Do you want to add more cheese?: ")) {
            while (addCheese) {
                int numbering4 = 1;

                for (Cheese cheese : Cheese.getCheeseToppings()) {
                    System.out.println(numbering4 + " " + cheese);
                    numbering4++;
                }

                int che;
                try {
                    che = console.promptForInt("What type of cheese do you want to add?: ");

                    if (che == 0) {
                        addCheese = false;


                    } else if (che >= 1 && che <= Cheese.getCheeseToppings().size()) {
                        Cheese c = Cheese.getCheeseToppings().get(che - 1);
                        pcs.replaceCheese(c);
                        int extraCheese = console.promptForInt("Do you want extra cheese?: ");

                        if(extraCheese == 1){
                            for(Cheese cheese: Cheese.getCheeseToppings()){
                                cheese.setExtra(true);
                                System.out.println(ColorCodes.BOLD + "\nExtra " + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                            }
                        }

                        System.out.println(ColorCodes.BOLD + "\n" + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    } else {
                        System.out.println( ColorCodes.BOLD + "\nInvalid Input, Please enter a number between 1 and " + Cheese.getCheeseToppings().size() + "\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter in a number");
                }



            }
        }

    }

    //Add more Regular Toppings or Change out Topping
    private void addOrChangeRegToppingForPCS(PhillyCheeseSteak pcs){

        boolean choice = true;
        if (choice == console.getBoolean("Adding Lettuce, Do you want to change this?: ")) {
            List<RegularToppings> normalT = new ArrayList<>();
            boolean normalToppings = true;

            while (normalToppings) {
                int numbering4 = 1;
                for (RegularToppings regular : RegularToppings.getRegularToppings()) {
                    System.out.println(numbering4 + " " + regular);
                    numbering4++;
                }


                int regular = console.promptForInt("Which regular topping do you want?(0 to skip): ");


                if (regular == 0) {
                    break;
                } else if (regular < 1 || regular > RegularToppings.getRegularToppings().size()) {
                    System.out.println("Not a valid input");
                    continue;
                }

                RegularToppings normal = RegularToppings.getRegularToppings().get(regular - 1);

                pcs.replaceToppingAtIndex0(normal);
                normalT.add(normal);

                boolean anotherTopping = true;
                if (anotherTopping == console.getBoolean("Would you like another topping?: ")) {
                    continue;
                } else {
                    normalToppings = false;
                }

                System.out.println(normalT);

            }

        } else if (choice == console.getBoolean("Do you want to add more toppings?: ")) {
            List<RegularToppings> normalT = new ArrayList<>();
            boolean normalToppings = true;

            while (normalToppings) {
                int numbering5 = 1;
                for (RegularToppings regular : RegularToppings.getRegularToppings()) {
                    System.out.println(numbering5 + " " + regular);
                    numbering5++;
                }


                int regular = console.promptForInt("Which regular topping do you want?(0 to skip): ");


                if (regular == 0) {
                    break;
                } else if (regular < 1 || regular > RegularToppings.getRegularToppings().size()) {
                    System.out.println("Not a valid input");
                    continue;
                }

                RegularToppings normal = RegularToppings.getRegularToppings().get(regular - 1);

                pcs.replaceToppingAtIndex1(normal);
                normalT.add(normal);

                boolean anotherTopping = true;
                if (anotherTopping == console.getBoolean("Would you like another topping?: ")) {
                    continue;
                } else {
                    normalToppings = false;
                }
                System.out.println(normalT);

            }
        }


    }

    //Add more Sauce or Change out Sauce
    private void addOrChangeSauceForPCS(PhillyCheeseSteak pcs) {
        boolean addingSauce = true;
        boolean choice = true;
        if (choice == console.getBoolean("Adding Mayo, do you want to change this?: ")) {
            while (addingSauce) {
                int numbering5 = 1;


                for (Sauce sauces : Sauce.getSauce()) {
                    System.out.println(numbering5 + " " + sauces);
                    numbering5++;
                }

                int addASauce = console.promptForInt("Which Sauce would you like to add? (0 to skip): ");
                if (addASauce == 0) {
                    addingSauce = false;
                    continue;
                }
                Sauce s = Sauce.getSauce().get(addASauce - 1);

                pcs.addSauce(s);
            }


            boolean addingSideSauce = true;

            while (addingSideSauce) {
                int numbering6 = 1;

                for (SideSauce side : SideSauce.getSideSauces()) {
                    System.out.println(numbering6 + " " + side.getName());
                }
                int addSideSauce = console.promptForInt("Which side sauce do you want to add?(0 to sip): ");

                if (addSideSauce == 0) {
                    addingSideSauce = false;
                    continue;
                }

                SideSauce ss = SideSauce.getSideSauces().get(addSideSauce - 1);


                if (addSideSauce >= 1) {
                    int another = console.promptForInt("Would you like another? (1 for yes 0 for no): ");
                    if (another == 0) {
                        addingSideSauce = false;

                    }
                }

                pcs.addSauce(ss);

            }


        } else if(choice == console.getBoolean("Do you want to add another sauce?: ")){

            while (addingSauce) {
                int numbering5 = 1;


                for (Sauce sauces : Sauce.getSauce()) {
                    System.out.println(numbering5 + " " + sauces);
                    numbering5++;
                }

                int addASauce = console.promptForInt("Which Sauce would you like to add? (0 to skip): ");
                if (addASauce == 0) {
                    addingSauce = false;
                    continue;
                }
                Sauce s = Sauce.getSauce().get(addASauce - 1);

                pcs.addSauce(s);
            }


            boolean addingSideSauce = true;

            while (addingSideSauce) {
                int numbering6 = 1;

                for (SideSauce side : SideSauce.getSideSauces()) {
                    System.out.println(numbering6 + " " + side.getName());
                }
                int addSideSauce = console.promptForInt("Which side sauce do you want to add?(0 to sip): ");

                if (addSideSauce == 0) {
                    addingSideSauce = false;
                    continue;
                }

                SideSauce ss = SideSauce.getSideSauces().get(addSideSauce - 1);


                if (addSideSauce >= 1) {
                    int another = console.promptForInt("Would you like another? (1 for yes 0 for no): ");
                    if (another == 0) {
                        addingSideSauce = false;

                    }
                }

                pcs.addSauce(ss);

            }



        }
    }



    //Adds extra items
    //Add Drinks
    private void addDrinkToOrder() {
        boolean addDrink = true;
        while (addDrink) {
            System.out.println("Here are the drinks:");
            int drinkNumber = 1;
            for (Drink d : Drink.getDrinkFlavor()) {

                System.out.println(drinkNumber + " " + d.getName());
                drinkNumber++;

            }

            int chooseDrink = console.promptForInt("Which drink do you want? (0 to cancel): " );

            if (chooseDrink == 0) {
                break;

            } else if (chooseDrink > Drink.getDrinkFlavor().size()) {

                System.out.println("Invalid..please try again");

            } else {

                Drink drink = Drink.getDrinkFlavor().get(chooseDrink - 1);

                while (true) {

                    String size = console.promptForString("What size Drink do you want?(Small, Medium, Large):  ");

                    if (size.equalsIgnoreCase("Small")) {

                        drink.setSize("small");

                        break;

                    } else if (size.equalsIgnoreCase("Medium")) {

                        drink.setSize("medium");

                        break;

                    } else if (size.equalsIgnoreCase("Large")) {

                        drink.setSize("large");

                        break;

                    } else {

                        System.out.println(ColorCodes.RED + "Not a valid size" + ColorCodes.RESET);
                    }
                }
                    addDrink = false;
                    System.out.println(drink.getName() + " " + drink);
                    drink.getPrice();
                    order.addItem(drink);
            }

        }

    }




    //Add Chips
    private void addChipsToOrder() {

        boolean addChip = true;

        while (addChip) {

            System.out.println("Which chip would you like to have?");
            int numberingChip = 1;

            for (Chip c : Chip.getChips()) {
                System.out.println(numberingChip + " " + c.getName());
                numberingChip++;
            }

            int chooseChip = console.promptForInt("Select the chip you want: ");

            if (chooseChip == 0) {
                break;

            } else if (chooseChip > Chip.getChips().size()) {
                System.out.println("Not a valid number please try again");

            } else {
                Chip chips = Chip.getChips().get(chooseChip - 1);
                addChip = false;


                order.addItem(chips);


                System.out.println(chips);


            }


        }
    }


    //Check Out
    public void checkOut() {

        if(order.getMenuItems() != null && order.getTotal() != 0){

            for(MenuItem menuItem : order.getMenuItems()){

                System.out.println(menuItem.description());
            }

           System.out.printf("The total price is %.2f\n" , order.getTotal());

        }

        int confirmation;

        boolean checkingOut = true;

        if(order.getMenuItems() != null && order.getTotal() != 0) {
            while (checkingOut) {

                confirmation = console.promptForInt("Would you like to proceed?( 1 to proceed, 0 to go back): ");
                if (confirmation > 1) {

                    System.out.println(ColorCodes.RED + "Not a valid number" + ColorCodes.RESET);

                } else if (confirmation == 1) {

                    fileManager.saveReceipt(order);
                    order.clearMenuItem();
                    checkingOut = false;
                    welcomeScreen();

                } else {
                    checkingOut = false;
                }

            }

        }

    }


}
