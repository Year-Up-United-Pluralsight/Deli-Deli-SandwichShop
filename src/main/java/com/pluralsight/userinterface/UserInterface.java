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
        //This is were the User is taken first, They cant see this screen, but they will then be moved to the welcome screen
        fileManager = new FileManager();

        welcomeScreen();

    }


    //Sandwich Shop Start
    private void welcomeScreen() {

        //Start Screen and how the application gets going.

        String welcomeMessage = ColorCodes.FLORAL_WHITE + """
                
                Welcome to Deli-Deli's Sandwich Shop.\s
                Would you like to order?\s
                1. Yes!\s
                0. No, I'll leave (Quit)\s
                Enter here:\s""";

        int choose;
        do {
            choose = console.promptForInt(welcomeMessage);
            if (choose == 1) {
                menuOrders();

            } else {
                System.out.println("Quitting...");
            }


        } while (choose != 0);


    }

    //Menu Orders
    private void menuOrders() {
        //The main menu, After an Order has been created using a Customer Name
        //Everything here can be added to the order, and then checked out.
        //Users can cancel their order as well.
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
        String customerName = console.promptForString("What is your name?: ");
        order = new Order(customerName, LocalDateTime.now());
        while (true) {
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
                    return;
                default:
                    System.out.println("Are you sure you want to cancel the order?(You cannot undo this)");
                    int confirmation = console.promptForInt("Press 1 to confirm cancellation: ");
                    if (confirmation == 1) {
                        System.out.println("\n" + ColorCodes.BOLD + "Order Canceled" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE + "\n");
                        order.clearMenuItem();
                       return;
                    } else if (confirmation > 1 | confirmation < 0) {
                        System.out.println("not a valid number");
                    }
            }
        }
    }


    //The order of making a sandwich
    private void orderSandwich() {

        //This is the start of a Sandwich is the root where all the methods combine into here to form the sandwich.
        int size;
        while (true) {
            size = console.promptForInt("What size sandwich do you want [4, 8, 12]: ");
            if (size == 0) {
                break;
            } else if (size != 4 && size != 8 && size != 12) {
                System.out.println(ColorCodes.RED + size + " was not part of the list..." + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

            } else {

                Sandwich sandwich = new Sandwich(size);

                addBreadToSandwich(sandwich);
                addMeatToSandwich(sandwich);
                addCheeseToSandwich(sandwich);
                addNormalToppings(sandwich);
                addSauceToSandwich(sandwich);
                toastSandwich(sandwich);

                order.addItem(sandwich);
                break;


            }
        }
    }


    // Selects which parts to add to Sandwich Separately
    //Bread
    private void addBreadToSandwich(Sandwich sandwich) {
        //Creating the Bread to start the creation of a sandwich
        //After this method, if the user presses 0 on anything, they can skip the current topping.

        while (true) {
            System.out.println("Here is the lists of bread: ");
            int numbering = 1;
            for (Bread b : Bread.getBreadTypes()) {
                System.out.println(numbering + ". " + b);
                numbering++;
            }
            int chosenBread = console.promptForInt("Which bread do you want?(0 to exit out): ");

                if (chosenBread == 0) {
                    return;
                } else if (chosenBread <= Bread.getBreadTypes().size()) {
                    Bread selectedBread = Bread.getBreadTypes().get(chosenBread - 1);
                    sandwich.setBreadType(selectedBread);
                    System.out.println("\n " + ColorCodes.BOLD + selectedBread + " Bread added \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    break;
                } else {
                    System.out.println(ColorCodes.RED + "Invalid number...please choose a number between 1 and " +
                            Bread.getBreadTypes().size() + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                }
        }
    }

    //Meat
    private void addMeatToSandwich(Sandwich sandwich) {

        //Allows the user to pick a Meat, and add extra of that Meat and allows the user to add more meat on top of that
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
            if (meaty >= 1 && meaty <= Meat.getMeatTopping().size()) {

                Meat m = Meat.getMeatTopping().get(meaty - 1);

                System.out.println("\n " + ColorCodes.BOLD + m + " added  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                int extra = console.promptForInt("Do you want extra " + m + " " + "(1 for yes, 0 for no): ");

                if (extra == 1) {
                    for (Meat meat : Meat.getMeatTopping())
                        meat.setExtra(true);

                    System.out.println(ColorCodes.BOLD + "\nExtra " + m + " added \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                }
                sandwich.addTopping(m);


            } else {
                System.out.println(ColorCodes.RED + "Error...Incorrect number.. Please use a number between 1 and " +
                        Meat.getMeatTopping().size() + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
            }
        }
    }

    //Cheese
    private void addCheeseToSandwich(Sandwich sandwich) {
        //This method allows the user to add Cheese and an Extra of the same Cheese, then allows the user to pick again
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
            } else if (che >=1 && che <= Cheese.getCheeseToppings().size()) {
                Cheese c = Cheese.getCheeseToppings().get(che - 1);

                System.out.println(ColorCodes.BOLD + "\n" + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

                int extraCheese = console.promptForInt("Do you want extra " + c + " cheese?(1 for yes, 0 for no): ");
                if (extraCheese == 1) {
                    for (Cheese cheese : Cheese.getCheeseToppings()) {
                        cheese.setExtra(true);
                    }
                    System.out.println(ColorCodes.BOLD + "\nExtra " + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                }
                sandwich.addTopping(c);
            } else{
                System.out.println(ColorCodes.RED + "Not a valid Input...Please try again" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
            }



        }

    }

    //NormalToppings
    private void addNormalToppings(Sandwich sandwich) {

        //This entire method allows the user to keep adding on toppings as much as they want.
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
            } else if (regular >= 1 && regular <= RegularToppings.getRegularToppings().size()) {

                RegularToppings normal = RegularToppings.getRegularToppings().get(regular - 1);

                sandwich.addTopping(normal);
                normalT.add(normal);

                System.out.println(ColorCodes.BOLD + "\n" + normal + " added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

                System.out.println("Would you like another topping? ");
                int anotherTopping = console.promptForInt("1 for Yes, 0 for No: ");

                if (anotherTopping == 0) {
                    normalToppings = false;

                } else if (anotherTopping != 1) {
                    System.out.println("Not a valid input");

                }

                System.out.println("\n" + ColorCodes.BOLD + normalT + "\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
            } else {
                System.out.println(ColorCodes.RED + "Incorrect number..Please use a number between 1 and " +
                        RegularToppings.getRegularToppings().size() + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
            }
        }
    }

    //Sauce
    private void addSauceToSandwich(Sandwich sandwich) {

        //This entire method allows for the user to choose if they want to add Sauce/Side Sauce to their sandwich
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
            if (addASauce >= 1 && addASauce <= Sauce.getSauce().size()) {
                Sauce s = Sauce.getSauce().get(addASauce - 1);

                sandwich.addSauce(s);
                System.out.println(ColorCodes.BOLD + "\n" + s + " added" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
            } else {
                System.out.println(ColorCodes.RED + "Error..Incorrect number...Please use a number between 1 and " + Sauce.getSauce().size() + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
            }
        }


        boolean addingSideSauce = true;

        while (addingSideSauce) {
            int numbering6 = 1;

            for (SideSauce side : SideSauce.getSideSauces()) {
                System.out.println(numbering6 + " " + side.getName());
            }
            int addSideSauce = console.promptForInt("Which side sauce do you want to add?(0 to skip): ");

            if (addSideSauce == 0) {
                addingSideSauce = false;
                continue;
            }
            if (addSideSauce >= 1 && addSideSauce <= SideSauce.getSideSauces().size()) {

                SideSauce ss = SideSauce.getSideSauces().get(addSideSauce - 1);
                sandwich.addSauce(ss);
                System.out.println(ColorCodes.BOLD + "\n" +  ss + " added" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE + "\n");
                int another = console.promptForInt("Would you like another? (1 for yes 0 for no): ");
                if (another != 1) {
                    addingSideSauce = false;

                } else {
                    System.out.println((ColorCodes.RED + "Error please pick a number between 1 and " +
                            SideSauce.getSideSauces().size() + ColorCodes.RESET + ColorCodes.FLORAL_WHITE));


                }

            }
        }
    }

    //Toasted // Not Toasted
    private void toastSandwich(Sandwich sandwich) {
        //Lets the user toast a sandwich
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
       //Signature Sandwiches for the user to choose from or customize.
        String defaults = """
                 Here are the default orders:
                 1. BLT: 8" White Bread, Bacon, Cheddar, Lettuce, Tomato, Ranch, Toasted.
                
                 2. Philly Cheese Steak: 8" White Bread, Steak, American Cheese, Peppers, Mayo, Toasted
                
                Which pre-made sandwich do you want (1-BLT or 2-Philly Cheese Steak):   \s
                \s""";

        int newSandwich;
        boolean newOrder = true;
        while (newOrder) {
            newSandwich = console.promptForInt(defaults);


            if (newSandwich == 1) {
                //Lists out to the user the option they picked
                System.out.println(ColorCodes.BOLD + "You chose BLT: 8\" White Bread, Bacon, Cheddar, Lettuce, Tomato, Ranch, Toasted \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

                System.out.println(ColorCodes.RED + "\n***NOTE*** Press 0 to cancel out of the process and return to this menu\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                int bltOrder = console.promptForInt("Do you want to customize this sandwich?(1-yes 2-no): ");

                BLT bltSandwich = new BLT();
                if (bltOrder == 1) {
                    //Lists out all the changes that needs to happen to make this sandwich by customizing it.
                    //These are methods
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
                            System.out.println(ColorCodes.RED + "Not a valid input" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

                        }
                    }

                    order.addItem(bltSandwich);
                    newOrder = false;
                } else {

                    order.addItem(bltSandwich);

                    System.out.println("\n" +ColorCodes.BOLD + bltSandwich + ColorCodes.RESET + ColorCodes.FLORAL_WHITE + "\n");
                }


            } else if (newSandwich == 2) {



                System.out.println(ColorCodes.BOLD + "\nPhilly Cheese Steak: 8\" White Bread, Steak, American Cheese, Peppers, Mayo Toasted\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

                System.out.println(ColorCodes.RED + "\n***NOTE*** Press 0 to cancel out of the process and return to this menu\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

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
                        System.out.println(ColorCodes.RED + "Not a valid input" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    }
                    order.addItem(pcs);
                    newOrder = false;
                } else {

                    order.addItem(pcs);

                    System.out.println("\n" +ColorCodes.BOLD + pcs + ColorCodes.RESET + ColorCodes.FLORAL_WHITE + "\n");


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

        // User Chooses if they want to replace existing meat
        if (choice == console.getBoolean("Adding Bacon: Do you want to change this Meat?: ")) {
            //Choice 1: Replace Meat
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


                addMeat = console.getBoolean("Do you want to add more meat?");
            }
        } else {
         //Choice 2: Not Replacing
            while (addMeat) {
                if (console.getBoolean("Do you want to add a different meat?: ")) {
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
                    System.out.println(addedMeats);


                    addMeat = console.getBoolean("Do you want to add more meat?");
                } else {
                    break;
                }
            }
        }
    }



    //Add more Cheese or Change Out Cheese
    private void addOrChangeCheeseForBLT(BLT bltSandwich) {
        boolean choice = true;
        boolean addCheese = true;
        List<Cheese> addedCheese = new ArrayList<>();


        if (choice == console.getBoolean("Adding Cheddar Cheese: Do you want to change this cheese?: ")) {
            // Choice 1: Replace Cheese
            while (addCheese) {
                int numbering3 = 1;


                for (Cheese cheese : Cheese.getCheeseToppings()) {
                    System.out.println(numbering3 + " " + cheese);
                    numbering3++;
                }

                int che = console.promptForInt("What type of cheese do you want? (0 to move on or skip): ");

                if (che == 0) {
                    addCheese = false;
                    continue;
                }


                Cheese c = Cheese.getCheeseToppings().get(che - 1);
                System.out.println("\n " + ColorCodes.BOLD + c + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                int extraCheese = console.promptForInt("Do you want extra " + c + " cheese? (1 for yes, 2 for no): ");
                if (extraCheese == 1) {
                    for (Cheese cheese : Cheese.getCheeseToppings()) {
                        cheese.setExtra(true);
                    }
                    System.out.println(ColorCodes.BOLD + "\nExtra " + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                }


                bltSandwich.replaceCheese(c);
                addedCheese.add(c);
                System.out.println(addedCheese);

                addCheese = console.getBoolean("Do you want to add more cheese?");
            }
        } else {

            while (addCheese) {
                if (console.getBoolean("Do you want to add a different cheese?: ")) {
                    int numbering4 = 1;


                    for (Cheese cheese : Cheese.getCheeseToppings()) {
                        System.out.println(numbering4 + " " + cheese);
                        numbering4++;
                    }

                    int che = console.promptForInt("What type of cheese do you want to add? (0 to move on or skip): ");
                    if (che == 0) {
                        addCheese = false;
                        continue;
                    }


                    Cheese c = Cheese.getCheeseToppings().get(che - 1);
                    System.out.println("\n " + ColorCodes.BOLD + c + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                    int extraCheese = console.promptForInt("Do you want extra " + c + " cheese? (1 for yes, 2 for no): ");
                    if (extraCheese == 1) {
                        for (Cheese cheese : Cheese.getCheeseToppings()) {
                            cheese.setExtra(true);
                        }
                        System.out.println(ColorCodes.BOLD + "\nExtra " + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    }


                    bltSandwich.addTopping(c);
                    addedCheese.add(c); // Add the cheese to the list
                    System.out.println(addedCheese);

                    addCheese = console.getBoolean("Do you want to add more cheese?");
                } else {
                    break;
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
                System.out.println("\n " + ColorCodes.BOLD + s + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                bltSandwich.replaceSauce(s);


                addingSauce = console.getBoolean("Do you want to add more sauce?");
            }


            boolean addingSideSauce = true;
            while (addingSideSauce) {
                int numbering6 = 1;


                for (SideSauce side : SideSauce.getSideSauces()) {
                    System.out.println(numbering6 + " " + side.getName());
                    numbering6++;
                }

                int addSideSauce = console.promptForInt("Which side sauce do you want to add? (0 to skip): ");
                if (addSideSauce == 0) {
                    addingSideSauce = false;
                    continue;
                }

                SideSauce ss = SideSauce.getSideSauces().get(addSideSauce - 1);
                System.out.println("\n " + ColorCodes.BOLD + ss.getName() + " side sauce added \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                int another = console.promptForInt("Would you like another? (1 for yes, 0 for no): ");
                if (another == 0) {
                    addingSideSauce = false;
                }


                bltSandwich.addSauce(ss);
            }

        } else {

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
                System.out.println("\n " + ColorCodes.BOLD + s + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                bltSandwich.addSauce(s);


                addingSauce = console.getBoolean("Do you want to add more sauce?");
            }


            boolean addingSideSauce = true;
            while (addingSideSauce) {
                int numbering6 = 1;


                for (SideSauce side : SideSauce.getSideSauces()) {
                    System.out.println(numbering6 + " " + side.getName());
                    numbering6++;
                }

                int addSideSauce = console.promptForInt("Which side sauce do you want to add? (0 to skip): ");
                if (addSideSauce == 0) {
                    addingSideSauce = false;
                    continue;
                }


                SideSauce ss = SideSauce.getSideSauces().get(addSideSauce - 1);
                System.out.println("\n " + ColorCodes.BOLD + ss.getName() + " side sauce added \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                int another = console.promptForInt("Would you like another? (1 for yes, 0 for no): ");
                if (another == 0) {
                    addingSideSauce = false;
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
        List<Meat> addedMeats = new ArrayList<>();

        // User Chooses if they want to replace existing meat
        if (choice == console.getBoolean("Adding Bacon: Do you want to change this Meat?: ")) {
            //Choice 1: Replace Meat
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


                pcs.replaceMeat(m);
                addedMeats.add(m);
                System.out.println(addedMeats);


                addMeat = console.getBoolean("Do you want to add more meat?");
            }
        } else {
            //Choice 2: Not Replacing
            while (addMeat) {
                if (console.getBoolean("Do you want to add a different meat?: ")) {
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
                    addedMeats.add(m);
                    System.out.println(addedMeats);


                    addMeat = console.getBoolean("Do you want to add more meat?");
                } else {
                    break;
                }
            }
        }
    }

    //Add more Cheese or Change Out Cheese
    private void addOrChangeCheeseForPCS(PhillyCheeseSteak pcs){
        boolean choice = true;
        boolean addCheese = true;
        List<Cheese> addedCheese = new ArrayList<>();


        if (choice == console.getBoolean("Adding Cheddar Cheese: Do you want to change this cheese?: ")) {
            // Choice 1: Replace Cheese
            while (addCheese) {
                int numbering3 = 1;


                for (Cheese cheese : Cheese.getCheeseToppings()) {
                    System.out.println(numbering3 + " " + cheese);
                    numbering3++;
                }

                int che = console.promptForInt("What type of cheese do you want? (0 to move on or skip): ");

                if (che == 0) {
                    addCheese = false;
                    continue;
                }


                Cheese c = Cheese.getCheeseToppings().get(che - 1);
                System.out.println("\n " + ColorCodes.BOLD + c + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                int extraCheese = console.promptForInt("Do you want extra " + c + " cheese? (1 for yes, 2 for no): ");
                if (extraCheese == 1) {
                    for (Cheese cheese : Cheese.getCheeseToppings()) {
                        cheese.setExtra(true);
                    }
                    System.out.println(ColorCodes.BOLD + "\nExtra " + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                }


                pcs.replaceCheese(c);
                addedCheese.add(c);
                System.out.println(addedCheese);

                addCheese = console.getBoolean("Do you want to add more cheese?");
            }
        } else {

            while (addCheese) {
                if (console.getBoolean("Do you want to add a different cheese?: ")) {
                    int numbering4 = 1;


                    for (Cheese cheese : Cheese.getCheeseToppings()) {
                        System.out.println(numbering4 + " " + cheese);
                        numbering4++;
                    }

                    int che = console.promptForInt("What type of cheese do you want to add? (0 to move on or skip): ");
                    if (che == 0) {
                        addCheese = false;
                        continue;
                    }


                    Cheese c = Cheese.getCheeseToppings().get(che - 1);
                    System.out.println("\n " + ColorCodes.BOLD + c + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                    int extraCheese = console.promptForInt("Do you want extra " + c + " cheese? (1 for yes, 2 for no): ");
                    if (extraCheese == 1) {
                        for (Cheese cheese : Cheese.getCheeseToppings()) {
                            cheese.setExtra(true);
                        }
                        System.out.println(ColorCodes.BOLD + "\nExtra " + c + " cheese added\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    }


                    pcs.addTopping(c);
                    addedCheese.add(c); // Add the cheese to the list
                    System.out.println(addedCheese);

                    addCheese = console.getBoolean("Do you want to add more cheese?");
                } else {
                    break;
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
                System.out.println("\n " + ColorCodes.BOLD + s + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                pcs.replaceSauce(s);


                addingSauce = console.getBoolean("Do you want to add more sauce?");
            }


            boolean addingSideSauce = true;
            while (addingSideSauce) {
                int numbering6 = 1;


                for (SideSauce side : SideSauce.getSideSauces()) {
                    System.out.println(numbering6 + " " + side.getName());
                    numbering6++;
                }

                int addSideSauce = console.promptForInt("Which side sauce do you want to add? (0 to skip): ");
                if (addSideSauce == 0) {
                    addingSideSauce = false;
                    continue;
                }

                SideSauce ss = SideSauce.getSideSauces().get(addSideSauce - 1);
                System.out.println("\n " + ColorCodes.BOLD + ss.getName() + " side sauce added \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                int another = console.promptForInt("Would you like another? (1 for yes, 0 for no): ");
                if (another == 0) {
                    addingSideSauce = false;
                }


                pcs.addSauce(ss);
            }

        } else {

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
                System.out.println("\n " + ColorCodes.BOLD + s + " selected  \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                pcs.addSauce(s);


                addingSauce = console.getBoolean("Do you want to add more sauce?");
            }


            boolean addingSideSauce = true;
            while (addingSideSauce) {
                int numbering6 = 1;


                for (SideSauce side : SideSauce.getSideSauces()) {
                    System.out.println(numbering6 + " " + side.getName());
                    numbering6++;
                }

                int addSideSauce = console.promptForInt("Which side sauce do you want to add? (0 to skip): ");
                if (addSideSauce == 0) {
                    addingSideSauce = false;
                    continue;
                }


                SideSauce ss = SideSauce.getSideSauces().get(addSideSauce - 1);
                System.out.println("\n " + ColorCodes.BOLD + ss.getName() + " side sauce added \n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);


                int another = console.promptForInt("Would you like another? (1 for yes, 0 for no): ");
                if (another == 0) {
                    addingSideSauce = false;
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
            //Loops through the drinks
            for (Drink d : Drink.getDrinkFlavor()) {
                System.out.println(drinkNumber + " " + d.getName());
                drinkNumber++;

            }

            //Asks the User to choose which drink they want from the list
            int chooseDrink = console.promptForInt("Which drink do you want? (0 to cancel): " );

            //If they change their mind, 0 will back them out
            if (chooseDrink == 0) {
                break;

                //If they chose an invalid number, They need to try again
            } else if (chooseDrink > Drink.getDrinkFlavor().size()) {

                System.out.println("Invalid..please try again");

            } else {

                //Need to minus one because of how we handle the loop at the start of the method
                Drink drink = Drink.getDrinkFlavor().get(chooseDrink - 1);

                while (true) {

                    //Asks the user to input a string, to find out the size drink they want
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

                        System.out.println(ColorCodes.RED + "Not a valid size" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);
                    }
                }
                    addDrink = false;
                    System.out.println(ColorCodes.BOLD + "\n" + drink.getName() + " " + drink + " "  +  "\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

                    order.addItem(drink);
            }

        }

    }




    //Add Chips
    private void addChipsToOrder() {

        boolean addChip = true;

        while (addChip) {


            System.out.println("Which chip would you like to have?");
            int numberingChip = 1; //Makes sure that the number starts at 1 rather than 0

            //Loops through all the chips and displays them.
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
                Chip chips = Chip.getChips().get(chooseChip - 1); //Because the number starts at 1 rather than 0, we need to minus 1 from chosen chip
                addChip = false;


                //Add to the order
                order.addItem(chips);


                //Out print to the user
                System.out.println("\n" + ColorCodes.BOLD + chips + ColorCodes.RESET +ColorCodes.FLORAL_WHITE + "\n");


            }


        }
    }


    //Check Out
    public void checkOut() {

        // Finds out if there is any order to be checked out
        if(order.getMenuItems() != null && order.getTotal() != 0){
            // Loops through all order items
            for(MenuItem menuItem : order.getMenuItems()){
                //Out prints the items.
                System.out.println("\n" + ColorCodes.BOLD + menuItem.description() + ColorCodes.RESET + ColorCodes.FLORAL_WHITE + "\n");
            }


            //Out prints the total price
           System.out.printf(ColorCodes.BOLD + "\n The total price is %.2f\n" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE , order.getTotal());

        }

        int confirmation;

        boolean checkingOut = true;

        //Finds out if the total isn't 0 and displays it out
        if(order.getMenuItems() != null && order.getTotal() != 0) {
            while (checkingOut) {

                //Asks if the User is sure they want to proceed with the check out
                confirmation = console.promptForInt("Would you like to proceed?( 1 to proceed, 0 to go back): ");
                if (confirmation > 1) {
                    //If they inputted an invalid number, it won't go through
                    System.out.println(ColorCodes.RED + "Not a valid number" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE);

                } else if (confirmation == 1) {
                    //If they chose 1, then saves it and gives a receipt
                    fileManager.saveReceipt(order);
                    order.clearMenuItem();
                    welcomeScreen();
                    return;


                } else {
                    //If they cancel, they can choose to add more to the order.
                    checkingOut = false;
                    System.out.println("\n" + ColorCodes.BOLD + "Cancelling" + ColorCodes.RESET + ColorCodes.FLORAL_WHITE + "\n");
                }

            }

        }

    }


}
