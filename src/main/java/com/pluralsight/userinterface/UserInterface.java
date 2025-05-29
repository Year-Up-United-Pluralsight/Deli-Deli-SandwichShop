package com.pluralsight.userinterface;

import com.pluralsight.console.ColorCodes;
import com.pluralsight.console.Console;
import com.pluralsight.order.Chip;
import com.pluralsight.order.Drink;
import com.pluralsight.order.Order;
import com.pluralsight.filemanager.FileManager;
import com.pluralsight.generics.AddToOptionChoice;
import com.pluralsight.generics.OptionChoice;
import com.pluralsight.javainterfaces.MenuItem;
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
                Enter here:\s""" ;

        int choose;
        do {
            choose = console.promptForInt(welcomeMessage);
            switch (choose) {
                case 1:
                    menuOrders();
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Quitting...");
                    break;
            }


        } while (choose != 0);


    }

    //Menu Orders
    private void menuOrders() {
        String orderSelections =  """ 
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

        System.out.println("A sandwich for : " + "\n" + sandwich);


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
        sandwich.getBreadType(selectedBread);
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


            int extra = console.promptForInt("Do you want extra " + m + " " + "(1 for yes, 0 for no): ");

            if (extra == 1) {
                for (Meat meat : Meat.getMeatTopping())
                    meat.setExtra(true);
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
            } else if(che >Cheese.getCheeseToppings().size()){
                System.out.println("Not a valid input");
            }
            Cheese c = Cheese.getCheeseToppings().get(che - 1);


            int extraCheese = console.promptForInt("Do you want extra " + c + " cheese?(1 for yes, 0 for no): ");
            if (extraCheese == 1) {
                for (Cheese cheese : Cheese.getCheeseToppings()) {
                    cheese.setExtra(true);
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
                \s
                 Do you want pre-made sandwich 1 or 2:  \s
                \s""";

        int newSandwich;
        boolean newOrder = true;
        while (newOrder) {
            newSandwich = console.promptForInt(defaults);

            if (newSandwich == 1) {
                BLT bltSandwich = new BLT(8);
                if (!changeBreadForBLT(bltSandwich)) {
                    return;
                }
                if (!addOrChangeMeatForBLT(bltSandwich)) {
                    return;
                }
                if (!addOrChangeCheeseForBLT(bltSandwich)) {
                    return;
                }
                if (!addOrChangeRegToppings1ForBLT(bltSandwich)) {
                    return;
                }
                if(!addOrChangeRegToppings2ForBLT(bltSandwich)) {
                    return;
                }
                if(!addOrChangeSauceOptionsForBLT(bltSandwich)) {
                    return;
                }

                int wantToasted;

                while (true) {
                    wantToasted = console.promptForInt("This sandwich comes Toasted...Is this what you want. (1 - Yes, 2 - No): " + ColorCodes.RESET);
                    if (wantToasted == 1) {
                        bltSandwich.setToasted(true);

                        break;
                    } else if (wantToasted == 2) {
                        bltSandwich.setToasted(false);

                        break;
                    } else {
                        System.out.println(ColorCodes.RED + "Not a valid input" + ColorCodes.RESET);

                    }
                }

                order.addItem(bltSandwich);
                newOrder = false;


            } else if (newSandwich == 2) {
                PhillyCheeseSteak pcs = new PhillyCheeseSteak();
                if(!changeBreadForPCS(pcs)) {
                    return;
                }
                if(!addOrChangeMeatForPCS(pcs)) {
                    return;
                }
                if(!addOrChangeCheeseForPCS(pcs)) {
                    return;
                }
                if(!addOrChangeRegToppingForPCS(pcs)) {
                    return;
                }
                if(!addOrChangeSauceForPCS(pcs)) {
                    return;
                }

                int wantToasted = console.promptForInt("This sandwich comes Toasted...Is this what you want. (1 - Yes, 2 - No): ");
                if(wantToasted == 1){
                    pcs.setToasted(true);

                } else if (wantToasted == 2) {
                    pcs.setToasted(false);

                }
                order.addItem(pcs);
                newOrder = false;

            }
        }
    }


    //Signature Sandwich BLT Creation
    //Change Out Bread
    private boolean changeBreadForBLT(BLT bltSandwich) {

        OptionChoice<Bread> breadOptions = new OptionChoice<>();

        Bread selectedBread = breadOptions.selectOption(ColorCodes.SNOW + "Adding white Bread", Bread.getBreadTypes(), new Bread("White" + ColorCodes.RESET));
        if (selectedBread == null) {
            System.out.println("exiting..");
            bltSandwich.clear();
            return false;
        } else {
            bltSandwich.replaceBread(selectedBread);


            return true;
        }
    }

    //Add more Meat/Change Out Meat
    private boolean addOrChangeMeatForBLT(BLT bltSandwich){

        OptionChoice<Meat> meatOptions = new OptionChoice<>();
        Meat selectedMeat = meatOptions.selectOption(ColorCodes.BRIGHT_BLUE + "Adding Bacon", Meat.getMeatTopping(), new Meat("Bacon" + ColorCodes.RESET));
        if(selectedMeat == null) {
            System.out.println("exiting...");
            bltSandwich.clear();
            return false;
        }

            bltSandwich.replaceMeat(selectedMeat);



        AddToOptionChoice<Meat> addMeat = new AddToOptionChoice<>();
        Meat newlyAddedMeat = addMeat.toCollection(ColorCodes.ORANGE + "Do you want to " , Meat.getMeatTopping());

        if(newlyAddedMeat != null){
            bltSandwich.addTopping(newlyAddedMeat);

        }

        return true;

    }

    //Add more Cheese or Change Out Cheese
    private boolean addOrChangeCheeseForBLT(BLT bltSandwich){
        OptionChoice<Cheese> cheeseOptions = new OptionChoice<>();
        Cheese selectedCheese = cheeseOptions.selectOption(ColorCodes.RESET + ColorCodes.BRIGHT_YELLOW + "Adding Cheddar Cheese", Cheese.getCheeseToppings(), new Cheese("Cheddar" + ColorCodes.RESET));
        if(selectedCheese == null){
            System.out.println("exiting...");
            bltSandwich.clear();
            return false;
        } else {
            bltSandwich.replaceCheese(selectedCheese);

        }

        AddToOptionChoice<Cheese> addCheese = new AddToOptionChoice<>();
        Cheese newlyAddedCheese = addCheese.toCollection("Do you want to " , Cheese.getCheeseToppings());
        if(newlyAddedCheese != null) {
            bltSandwich.addTopping(newlyAddedCheese);

        }

        return  true;



    }

    //Add more Regular Toppings or Change out Topping
    private boolean addOrChangeRegToppings1ForBLT(BLT bltSandwich){

        OptionChoice<RegularToppings> regularOptions = new OptionChoice<>();
        RegularToppings selectRegular = regularOptions.selectOption(ColorCodes.BRIGHT_GREEN + "Adding Lettuce", RegularToppings.getRegularToppings(), new RegularToppings("Lettuce" + ColorCodes.RESET));
        if(selectRegular == null) {
            System.out.println("exiting...");
            bltSandwich.clear();
            return false;
        }else {
            bltSandwich.addTopping(selectRegular);

        }



        AddToOptionChoice<RegularToppings> addRegToppings = new AddToOptionChoice<>();
        RegularToppings newlyAddedReg = addRegToppings.toCollection(ColorCodes.LAVENDER +"Do you want to " , RegularToppings.getRegularToppings());
        if(newlyAddedReg != null){
            bltSandwich.addTopping(newlyAddedReg);
        }

        return true;

    }

    //Add more Regular Toppings or Change out Topping
    private boolean addOrChangeRegToppings2ForBLT(BLT bltSandwich){

        OptionChoice<RegularToppings> secondTopping = new OptionChoice<>();
        RegularToppings selectSecondTopping = secondTopping.selectOption(ColorCodes.RESET + ColorCodes.IVORY + "Adding Tomatoes", RegularToppings.getRegularToppings(), new RegularToppings("Tomato" + ColorCodes.RESET));
        if(selectSecondTopping == null) {
            System.out.println("exiting...");
            bltSandwich.clear();
            return false;
        } else{
            bltSandwich.addTopping(selectSecondTopping);
        }


        AddToOptionChoice<RegularToppings> addSecondRegTopping = new AddToOptionChoice<>();
        RegularToppings newlyAddedSecondRegTopping = addSecondRegTopping.toCollection(ColorCodes.MINT + "Do you want to " , RegularToppings.getRegularToppings());
        if(newlyAddedSecondRegTopping != null){

            bltSandwich.addTopping(newlyAddedSecondRegTopping);

        }

        return true;

    }

    //Add more Sauce or Change out Sauce
    private boolean addOrChangeSauceOptionsForBLT(BLT bltSandwich){

        OptionChoice<Sauce> sauceOption = new OptionChoice<>();
        Sauce selctSauce = sauceOption.selectOption(ColorCodes.PINK + "Adding Ranch", Sauce.getSauce(), new Sauce("Ranch" + ColorCodes.RESET));
        if(selctSauce == null) {
            System.out.println("exiting...");
            bltSandwich.clear();
            return false;
        }

            bltSandwich.replaceSauce(selctSauce);



        AddToOptionChoice<Sauce> addSauce = new AddToOptionChoice<>();
        Sauce newlyAddedSauce = addSauce.toCollection(ColorCodes.TEAL + "Do you want to " , Sauce.getSauce());
        if(newlyAddedSauce != null){
            bltSandwich.addSauce(newlyAddedSauce);

        }

        return true;



    }


    //Signature Sandwich PhillyCheeseSteak Creation
    //Change Out Bread
    private boolean changeBreadForPCS(PhillyCheeseSteak pcs){
        OptionChoice<Bread> breadOptions = new OptionChoice<>();

        Bread selectedBread = breadOptions.selectOption("Adding white Bread", Bread.getBreadTypes(), new Bread("White"));
        if (selectedBread == null) {
            System.out.println("exiting..");
            pcs.clear();
            return false;
        } else {
            pcs.replaceBread(selectedBread);


            return true;
        }



    }

    //Add more Meat/Change Out Meat
    private boolean addOrChangeMeatForPCS(PhillyCheeseSteak pcs){

        OptionChoice<Meat> meatOptions = new OptionChoice<>();
        Meat selectedMeat = meatOptions.selectOption("Adding Steak", Meat.getMeatTopping(), new Meat("Steak"));
        if(selectedMeat == null) {
            System.out.println("exiting...");
            pcs.clear();
            return false;
        }

        pcs.replaceMeat(selectedMeat);



        AddToOptionChoice<Meat> addMeat = new AddToOptionChoice<>();
        Meat newlyAddedMeat = addMeat.toCollection("Do you want to " , Meat.getMeatTopping());

        if(newlyAddedMeat != null){
            pcs.addTopping(newlyAddedMeat);

        }

        return true;

    }

    //Add more Cheese or Change Out Cheese
    private boolean addOrChangeCheeseForPCS(PhillyCheeseSteak pcs){

        OptionChoice<Cheese> cheeseOptions = new OptionChoice<>();
        Cheese selectedCheese = cheeseOptions.selectOption( "Adding American Cheese", Cheese.getCheeseToppings(), new Cheese("American" ));
        if(selectedCheese == null){
            System.out.println("exiting...");
            pcs.clear();
            return false;
        } else {
            pcs.replaceCheese(selectedCheese);

        }

        AddToOptionChoice<Cheese> addCheese = new AddToOptionChoice<>();
        Cheese newlyAddedCheese = addCheese.toCollection( "Do you want to " , Cheese.getCheeseToppings());
        if(newlyAddedCheese != null) {
            pcs.addTopping(newlyAddedCheese);

        }

        return  true;



    }

    //Add more Regular Toppings or Change out Topping
    private boolean addOrChangeRegToppingForPCS(PhillyCheeseSteak pcs){

        OptionChoice<RegularToppings> regularOptions = new OptionChoice<>();
        RegularToppings selectRegular = regularOptions.selectOption("Adding Peppers", RegularToppings.getRegularToppings(), new RegularToppings("Peppers" ));
        if(selectRegular == null){
            System.out.println("exiting...");
            pcs.clear();
            return false;
        } else {
            pcs.addTopping(selectRegular);

        }

        AddToOptionChoice<RegularToppings> addRegToppings = new AddToOptionChoice<>();
        RegularToppings newlyAddedReg = addRegToppings.toCollection("Do you want to ", RegularToppings.getRegularToppings());
        if(newlyAddedReg != null){
            pcs.addTopping(newlyAddedReg);

        }
        return true;



    }

    //Add more Sauce or Change out Sauce
    private boolean addOrChangeSauceForPCS(PhillyCheeseSteak pcs){

        OptionChoice<Sauce> sauceOption = new OptionChoice<>();
        Sauce selctSauce = sauceOption.selectOption("Adding Mayo", Sauce.getSauce(), new Sauce("Mayo" ));
        if(selctSauce == null) {
            System.out.println("exiting...");
            pcs.clear();
            return false;
        }

        pcs.replaceSauce(selctSauce);



        AddToOptionChoice<Sauce> addSauce = new AddToOptionChoice<>();
        Sauce newlyAddedSauce = addSauce.toCollection("Do you want to ", Sauce.getSauce());
        if(newlyAddedSauce != null){
            pcs.addSauce(newlyAddedSauce);
        }

        return true;



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

           System.out.printf("The total price is %.2f\n +" , order.getTotal());

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

                } else {
                    checkingOut = false;
                }

            }

        }

    }


}
