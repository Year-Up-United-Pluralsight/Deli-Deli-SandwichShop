package com.pluralsight.userinterface;

import com.pluralsight.console.ColorCodes;
import com.pluralsight.console.Console;
import com.pluralsight.extraproducts.Chip;
import com.pluralsight.extraproducts.Drink;
import com.pluralsight.filemanager.FileManager;
import com.pluralsight.generics.AddToOptionChoice;
import com.pluralsight.generics.OptionChoice;
import com.pluralsight.sandwich.BLT;
import com.pluralsight.sandwich.Bread;
import com.pluralsight.sandwich.PhillyCheeseSteak;
import com.pluralsight.sandwich.Sandwich;
import com.pluralsight.toppings.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {

    private final Console console = new Console();
    private FileManager fileManager;
    private Sandwich sandwich;
    private final Bread bread = new Bread("Default");
    private final Meat meat = new Meat("Default");
    private final Cheese cheese = new Cheese("Default");
    private final RegularToppings regularToppings = new RegularToppings("Default");
    private final Sauce sauce = new Sauce("Default");
    private final SideSauce sideSauce = new SideSauce("Default");
    private Drink drink = new Drink("");
    private Chip chips = new Chip(" ");
    private BLT bltSandwich = null;
    private PhillyCheeseSteak pcs = null;


//    List<Bread> breadTypes = Bread.getBreadTypes();
//    List<Meat> meatTypes = meat.getMeatTopping();
//    List<Cheese> cheeseTypes = cheese.getCheeseToppings();
//    List<RegularToppings> regularTypes = regularToppings.getRegularToppings();
//    List<Sauce> saucesList = sauce.getSauce();
//    List<SideSauce> sideSaucesList = sideSauce.getSideSauces();
//    List<Drink> drinkList = drink.getDrinkFlavor();
//    List<Chip> chipList = chips.getChips();



    public void init() {
        fileManager = new FileManager();


        userInterface();

    }

    private void userInterface() {

        String welcomeMessage = """
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
                    orderScreen();
                    break;
                default:
                    System.out.println("Quitting...");
                    break;
            }


        } while (choose != 0);


    }

    private void orderScreen() {
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
        do {
            choose = console.promptForInt(orderSelections);
            switch (choose) {
                case 1:
                    orderSandwich();
                    break;
                case 2:
                    orderDefaults();
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
                    int confirmation = console.promptForInt("Press 1 to proceed: ");
                    if (confirmation == 1) {
                        System.out.println("Order Canceled");
                        clearOrder();
                        break;
                    } else if (confirmation > 1 | confirmation < 0) {
                        System.out.println("not a valid number");

                    }


            }


        } while (choose != 0);


    }

    private void orderDefaults() {
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
                   wantToasted = console.promptForInt("This sandwich comes Toasted...Is this what you want. (1 - Yes, 2 - No)");
                   if (wantToasted == 1) {
                       bltSandwich.setToasted(true);

                       break;
                   } else if (wantToasted == 2) {
                       bltSandwich.setToasted(false);

                       break;
                   } else {
                       System.out.println("Not a valid input");

                   }
               }

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

                int wantToasted = console.promptForInt("This sandwich comes Toasted...Is this what you want. (1 - Yes, 2 - No)");
                if(wantToasted == 1){
                    pcs.setToasted(true);
//                    sandwich.setToasted(true);
                } else if (wantToasted == 2) {
                    pcs.setToasted(false);
//                    sandwich.setToasted(false);
                }
                newOrder = false;

            }
        }
    }




    private boolean changeBreadForBLT(BLT bltSandwich) {

        OptionChoice<Bread> breadOptions = new OptionChoice<>();

        Bread selectedBread = breadOptions.selectOption("Adding white Bread", Bread.getBreadTypes(), new Bread("White"));
        if (selectedBread == null) {
            System.out.println("exiting..");
            clearOrder();
            return false;
        } else {
            bltSandwich.replaceBread(selectedBread);


            return true;
        }
    }
    private boolean addOrChangeMeatForBLT(BLT bltSandwich){

        OptionChoice<Meat> meatOptions = new OptionChoice<>();
        Meat selectedMeat = meatOptions.selectOption("Adding Bacon", Meat.getMeatTopping(), new Meat("Bacon"));
        if(selectedMeat == null) {
            System.out.println("exiting...");
            clearOrder();
            return false;
        }

            bltSandwich.replaceMeat(selectedMeat);



        AddToOptionChoice<Meat> addMeat = new AddToOptionChoice<>();
        Meat newlyAddedMeat = addMeat.toCollection("Do you want to " , Meat.getMeatTopping());

        if(newlyAddedMeat != null){
            bltSandwich.addTopping(newlyAddedMeat);

        }

        return true;

    }
    private boolean addOrChangeCheeseForBLT(BLT bltSandwich){
        OptionChoice<Cheese> cheeseOptions = new OptionChoice<>();
        Cheese selectedCheese = cheeseOptions.selectOption("Adding Cheddar Cheese", Cheese.getCheeseToppings(), new Cheese("Cheddar"));
        if(selectedCheese == null){
            System.out.println("exiting...");
            clearOrder();
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
    private boolean addOrChangeRegToppings1ForBLT(BLT bltSandwich){

        OptionChoice<RegularToppings> regularOptions = new OptionChoice<>();
        RegularToppings selectRegular = regularOptions.selectOption("Adding Lettuce", RegularToppings.getRegularToppings(), new RegularToppings("Lettuce"));
        if(selectRegular == null) {
            System.out.println("exiting...");
            clearOrder();
            return false;
        }else {
            bltSandwich.addTopping(selectRegular);

        }



        AddToOptionChoice<RegularToppings> addRegToppings = new AddToOptionChoice<>();
        RegularToppings newlyAddedReg = addRegToppings.toCollection("Do you want to ", RegularToppings.getRegularToppings());
        if(newlyAddedReg != null){
            bltSandwich.addTopping(newlyAddedReg);
        }

        return true;

    }
    private boolean addOrChangeRegToppings2ForBLT(BLT bltSandwich){

        OptionChoice<RegularToppings> secondTopping = new OptionChoice<>();
        RegularToppings selectSecondTopping = secondTopping.selectOption("Adding Tomatoes", RegularToppings.getRegularToppings(), new RegularToppings("Tomato"));
        if(selectSecondTopping == null) {
            System.out.println("exiting...");
            clearOrder();
            return false;
        } else{
            bltSandwich.addTopping(selectSecondTopping);
        }






        AddToOptionChoice<RegularToppings> addSecondRegTopping = new AddToOptionChoice<>();
        RegularToppings newlyAddedSecondRegTopping = addSecondRegTopping.toCollection("Do you want to " , RegularToppings.getRegularToppings());
        if(newlyAddedSecondRegTopping != null){

            bltSandwich.addTopping(newlyAddedSecondRegTopping);

        }

        return true;

    }
    private boolean addOrChangeSauceOptionsForBLT(BLT bltSandwich){

        OptionChoice<Sauce> sauceOption = new OptionChoice<>();
        Sauce selctSauce = sauceOption.selectOption("Adding Ranch", Sauce.getSauce(), new Sauce("Ranch"));
        if(selctSauce == null) {
            System.out.println("exiting...");
            clearOrder();
            return false;
        }

            bltSandwich.replaceSauce(selctSauce);



        AddToOptionChoice<Sauce> addSauce = new AddToOptionChoice<>();
        Sauce newlyAddedSauce = addSauce.toCollection("Do you want to ", Sauce.getSauce());
        if(newlyAddedSauce != null){
            bltSandwich.addSauce(newlyAddedSauce);

        }

        return true;



    }




    private boolean changeBreadForPCS(PhillyCheeseSteak pcs){
        OptionChoice<Bread> breadOptions = new OptionChoice<>();

        Bread selectedBread = breadOptions.selectOption("Adding white Bread", Bread.getBreadTypes(), new Bread("White"));
        if (selectedBread == null) {
            System.out.println("exiting..");
            clearOrder();
            return false;
        } else {
            pcs.replaceBread(selectedBread);
//            sandwich.addBread(selectedBread);

            return true;
        }



    }
    private boolean addOrChangeMeatForPCS(PhillyCheeseSteak pcs){

        OptionChoice<Meat> meatOptions = new OptionChoice<>();
        Meat selectedMeat = meatOptions.selectOption("Adding Steak", Meat.getMeatTopping(), new Meat("Steak"));
        if(selectedMeat == null) {
            System.out.println("exiting...");
            clearOrder();
            return false;
        }

        pcs.replaceMeat(selectedMeat);
//        sandwich.addTopping(selectedMeat);


        AddToOptionChoice<Meat> addMeat = new AddToOptionChoice<>();
        Meat newlyAddedMeat = addMeat.toCollection("Do you want to " , Meat.getMeatTopping());

        if(newlyAddedMeat != null){
            pcs.addTopping(newlyAddedMeat);
//            sandwich.addTopping(newlyAddedMeat);
        }

        return true;

    }
    private boolean addOrChangeCheeseForPCS(PhillyCheeseSteak pcs){

        OptionChoice<Cheese> cheeseOptions = new OptionChoice<>();
        Cheese selectedCheese = cheeseOptions.selectOption("Adding American Cheese", Cheese.getCheeseToppings(), new Cheese("American"));
        if(selectedCheese == null){
            System.out.println("exiting...");
            clearOrder();
            return false;
        } else {
            pcs.replaceCheese(selectedCheese);
//            sandwich.addTopping(selectedCheese);
        }

        AddToOptionChoice<Cheese> addCheese = new AddToOptionChoice<>();
        Cheese newlyAddedCheese = addCheese.toCollection("Do you want to " , Cheese.getCheeseToppings());
        if(newlyAddedCheese != null) {
            pcs.addTopping(newlyAddedCheese);
//            sandwich.addTopping(newlyAddedCheese);
        }

        return  true;



    }
    private boolean addOrChangeRegToppingForPCS(PhillyCheeseSteak pcs){

        OptionChoice<RegularToppings> regularOptions = new OptionChoice<>();
        RegularToppings selectRegular = regularOptions.selectOption("Adding Peppers", RegularToppings.getRegularToppings(), new RegularToppings("Peppers"));
        if(selectRegular == null){
            System.out.println("exiting...");
            clearOrder();
            return false;
        } else {
            pcs.addTopping(selectRegular);
//            sandwich.addTopping(selectRegular);
        }

        AddToOptionChoice<RegularToppings> addRegToppings = new AddToOptionChoice<>();
        RegularToppings newlyAddedReg = addRegToppings.toCollection("Do you want to ", RegularToppings.getRegularToppings());
        if(newlyAddedReg != null){
            pcs.addTopping(newlyAddedReg);
//            sandwich.addTopping(newlyAddedReg);
        }
        return true;



    }
    private boolean addOrChangeSauceForPCS(PhillyCheeseSteak pcs){

        OptionChoice<Sauce> sauceOption = new OptionChoice<>();
        Sauce selctSauce = sauceOption.selectOption("Adding Mayo", Sauce.getSauce(), new Sauce("Mayo"));
        if(selctSauce == null) {
            System.out.println("exiting...");
            clearOrder();
            return false;
        }

        pcs.replaceSauce(selctSauce);
//        sandwich.addSauce(selectSauce);


        AddToOptionChoice<Sauce> addSauce = new AddToOptionChoice<>();
        Sauce newlyAddedSauce = addSauce.toCollection("Do you want to ", Sauce.getSauce());
        if(newlyAddedSauce != null){
            pcs.addSauce(newlyAddedSauce);
//            sandwich.addSauce(newlyAddedSauce);
        }

        return true;



    }






    private void orderSandwich() {


        int size = console.promptForInt("What size sandwich do you want [4, 8, 12]: ");
       Sandwich sandwich = new Sandwich(size);

        addBreadToSandwich(sandwich);
        addMeatToSandwich(sandwich);
        addCheeseToSandwich(sandwich);
        addNormalToppings(sandwich);
        addSauceToSandwich(sandwich);
        toastSandwich(sandwich);
        System.out.println(sandwich);


    }


    private void addBreadToSandwich(Sandwich sandwich) {

        System.out.println("Here is the lists of bread: ");

        int numbering = 1;

        for (Bread b : Bread.getBreadTypes()) {
            System.out.println(numbering + ". " + b);
            numbering++;
        }

        int chosenBread = console.promptForInt("Which bread do you want?: ");
        Bread selectedBread = Bread.getBreadTypes().get(chosenBread - 1);


        sandwich.addBread(selectedBread);


    }

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


            int extra = console.promptForInt("Do you want extra " + m + " " + "(1 for yes): ");

            if (extra == 1) {
                for (Meat meat : Meat.getMeatTopping())
                    meat.setExtra(true);
            }
            sandwich.addTopping(m);


        }
    }

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


            int extraCheese = console.promptForInt("Do you want extra " + c + " cheese?(1 for yes): ");
            if (extraCheese == 1) {
                for (Cheese cheese : Cheese.getCheeseToppings()) {
                    cheese.setExtra(true);
                }
            }
            sandwich.addTopping(c);
        }

    }

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





    private void addSauceToSandwich(Sandwich sandwich) {

        boolean addingSauce = true;

        while (addingSauce) {
            int numbering5 = 1;


            for (Sauce sauces : sauce.getSauce()) {
                System.out.println(numbering5 + " " + sauces);
                numbering5++;
            }

            int addASauce = console.promptForInt("Which Sauce would you like to add?: ");
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
            int addSideSauce = console.promptForInt("Which side sauce do you want to add?: ");

            if (addSideSauce == 0) {
                addingSideSauce = false;
                continue;
            }

            SideSauce ss = SideSauce.getSideSauces().get(addSideSauce - 1);


            if (addSideSauce >= 1) {
                int another = console.promptForInt("Would you like another?: ");
                if (another == 0) {
                    addingSideSauce = false;

                }
            }

            sandwich.addSauce(ss);

        }


    }

    private void toastSandwich(Sandwich sandwich) {

        boolean check = true;

        while (check) {
            int toast = console.promptForInt("Would you like the sandwich toasted?: ");

            if (toast == 1) {
                sandwich.setToasted(true);
                check = false;
            } else if (toast == 2) {
                sandwich.setToasted(false);
                check = false;
            } else {
                System.out.println("Not a valid number");
            }
        }
    }

    private void addDrinkToOrder() {

        boolean addDrink = true;

        while (addDrink) {
            System.out.println("Here are the drinks:");
            int drinkNumber = 1;
            for (Drink d : Drink.getDrinkFlavor()) {
                System.out.println(drinkNumber + " " + d.getName());
                drinkNumber++;
            }
            int chooseDrink = console.promptForInt("Which drink do you want?: ");

            if (chooseDrink == 0) {
                addDrink = false;
            } else if (chooseDrink > Drink.getDrinkFlavor().size()) {
                System.out.println("Invalid..please try again");
                continue;
            } else {
                drink = Drink.getDrinkFlavor().get(chooseDrink - 1);
                addDrink = false;
            }


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


            System.out.println(drink.getName() + " " + drink);


        }


    }

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
                addChip = false;
            } else if (chooseChip > Chip.getChips().size()) {
                System.out.println("Not a valid number please try again");
                continue;
            } else {
                chips = Chip.getChips().get(chooseChip - 1);
                addChip = false;
            }


            System.out.println(chips);


        }


    }


    public void checkOut() {
        double theTotalPrice = 0;

        if (sandwich != null) {
            System.out.println(sandwich);
            theTotalPrice += sandwich.getPrice();
        }

        if(bltSandwich != null && bltSandwich.getBreadType() != null){
            System.out.println(bltSandwich);
            theTotalPrice += bltSandwich.getPrice();
        }


        if (drink != null && drink.getSize() != null) {
            System.out.println(drink);
            theTotalPrice += drink.getPrice("");
        }

        if (!chips.getName().equals(" ")) {
            System.out.println(chips);
            theTotalPrice += chips.getPrice();
        }

        if(drink != null && drink.getSize() != null | !chips.getName().equals(" ") | sandwich != null | bltSandwich != null) {
            System.out.printf("The total price is %.2f\n", theTotalPrice);
        }



        int confirmation;
        boolean checkingOut = true;
        if(drink != null && drink.getSize() != null | !chips.getName().equals(" ") | sandwich != null | bltSandwich != null) {
            while (checkingOut) {
                confirmation = console.promptForInt("Would you like to proceed?( 1 to proceed, 0 to go back): ");
                if (confirmation > 1) {
                    System.out.println("Not a valid number");
                } else if (confirmation == 1) {
                    System.out.println("Thank you for buying at our Deli Deli SandwichShop");
                    fileManager.saveReceipt(sandwich,bltSandwich,chips, drink);
                    clearOrder();
                    checkingOut = false;

                } else {
                    checkingOut = false;
                }
            }
        }





    }




    private void clearOrder(){
        sandwich = null;
        drink = null;
        bltSandwich = null;
        chips = new Chip(" ");



    }





























//                OptionChoice<Bread> breadOptions = new OptionChoice<>();
//
//                Bread selectedBread = breadOptions.selectOption("Adding white Bread", breadTypes, new Bread("White"));
//                if(selectedBread == null){
//                    System.out.println("exiting..");
//                    clearOrder();
//                    return;
//                } else {
//                    bltSandwich.replaceBread(selectedBread);
//                    sandwich.addBread(selectedBread);
//                }
//
//
//                OptionChoice<Meat> meatOptions = new OptionChoice<>();
//                Meat selectedMeat = meatOptions.selectOption("Adding Steak", meatTypes, new Meat("Steak"));
//                if(selectedMeat == null){
//                    System.out.println("exiting...");
//                    clearOrder();
//                    return;
//                } else {
//                    bltSandwich.replaceMeat(selectedMeat);
//                    sandwich.addTopping(selectedMeat);
//                }
//
//                AddToOptionChoice<Meat> addMeat = new AddToOptionChoice<>();
//                Meat newlyAddedMeat = addMeat.toCollection("Do you want to " , meatTypes);
//                if(newlyAddedMeat == null){
//                    System.out.println("exiting...");
//                    clearOrder();
//                    return;
//                } else {
//                    bltSandwich.addTopping(newlyAddedMeat);
//                    sandwich.addTopping(newlyAddedMeat);
//                }
//
//                OptionChoice<Cheese> cheeseOptions = new OptionChoice<>();
//                Cheese selectedCheese = cheeseOptions.selectOption("Adding American Cheese", cheeseTypes, new Cheese("American"));
//                if(selectedCheese == null){
//                    System.out.println("exiting...");
//                    clearOrder();
//                    return;
//                } else {
//                    pcs.replaceCheese(selectedCheese);
//                    sandwich.addTopping(selectedCheese);
//                }
//
//                AddToOptionChoice<Cheese> addCheese = new AddToOptionChoice<>();
//                Cheese newlyAddedCheese = addCheese.toCollection("Do you want to " , cheeseTypes);
//                if(newlyAddedCheese == null){
//                    System.out.println("exiting...");
//                    clearOrder();
//                    return;
//                } else {
//                    pcs.addTopping(newlyAddedCheese);
//                    sandwich.addTopping(newlyAddedCheese);
//                }
//
//                OptionChoice<RegularToppings> regularOptions = new OptionChoice<>();
//                RegularToppings selectRegular = regularOptions.selectOption("Adding Peppers", regularTypes, new RegularToppings("Peppers"));
//                if(selectRegular == null){
//                    System.out.println("exiting...");
//                    clearOrder();
//                    return;
//                } else {
//                    pcs.replaceTopping(selectRegular);
//                    sandwich.addTopping(selectRegular);
//                }
//
//                AddToOptionChoice<RegularToppings> addRegToppings = new AddToOptionChoice<>();
//                RegularToppings newlyAddedReg = addRegToppings.toCollection("Do you want to ", regularTypes);
//                if(newlyAddedReg == null){
//                    System.out.println("exiting...");
//                    clearOrder();
//                    return;
//                } else {
//                    pcs.addTopping(newlyAddedReg);
//                    sandwich.addTopping(newlyAddedReg);
//                }
//
//                AddToOptionChoice<RegularToppings> addSecondRegTopping = new AddToOptionChoice<>();
//                RegularToppings newlyAddedSecondRegTopping = addSecondRegTopping.toCollection("Do you want to " , regularTypes);
//                if(newlyAddedSecondRegTopping == null){
//                    System.out.println("exiting...");
//                    clearOrder();
//                    return;
//                } else {
//                    pcs.addTopping(newlyAddedSecondRegTopping);
//                    sandwich.addTopping(newlyAddedSecondRegTopping);
//                }
//
//                OptionChoice<Sauce> sauceOption = new OptionChoice<>();
//                Sauce selectSauce = sauceOption.selectOption("Adding Mayo", saucesList, new Sauce("Mayo"));
//                if(selectSauce == null){
//                    System.out.println("exiting...");
//                    clearOrder();
//                    return;
//                } else {
//                    pcs.addSauce(selectSauce);
//                    sandwich.addSauce(selectSauce);
//                }
//
//                AddToOptionChoice<Sauce> addSauce = new AddToOptionChoice<>();
//                Sauce newlyAddedSauce = addSauce.toCollection("Do you want to ", saucesList);
//                if(newlyAddedSauce == null){
//                    System.out.println("exiting...");
//                    clearOrder();
//                    return;
//                } else {
//                    pcs.addSauce(newlyAddedSauce);
//                    sandwich.addSauce(newlyAddedSauce);
//                }











//                    int addingBacon = console.promptForInt("Adding Bacon, do you want to change this selection 1 or 2: ");
//                    if (addingBacon == 1) {
//                        int numbering2 = 1;
//                        for (Meat m : meat.getMeatTopping()) {
//                            System.out.println(numbering2 + " " + m);
//                            numbering2++;
//                        }
//                        int chooseBacon = console.promptForInt("Which Meat do you want to add? : ");
//                        Meat newMeat = meatTypes.get(chooseBacon - 1);
//                        bltSandwich.replaceMeat(newMeat);
//                        sandwich.addTopping(newMeat);
//
//                        int addingCheese = console.promptForInt("Adding Cheese, do you want to change this selection 1 or 2: ");
//                        if (addingCheese == 1) {
//                            int numbering3 = 1;
//                            for (Cheese c : cheese.getCheeseToppings()) {
//                                System.out.println(numbering3 + " " + c);
//                                numbering3++;
//                            }
//                            int chooseCheese = console.promptForInt("Which Cheese Topping do you want?: ");
//                            Cheese newCheese = cheeseTypes.get(chooseCheese - 1);
//                            bltSandwich.replaceCheese(newCheese);
//                            sandwich.addTopping(newCheese);
//                        } else if (addingCheese == 2) {
//                            bltSandwich.replaceCheese(new Cheese("Cheddar"));
//                            sandwich.addTopping(new Cheese("Cheddar"));
//                        }
//
//                        int addingRegularToppings = console.promptForInt("Adding RegularTopping, do you want to change this selection 1 or 2: ");
//                        if (addingRegularToppings == 1) {
//                            int numbering4 = 1;
//                            for (RegularToppings reg : regularToppings.getRegularToppings()) {
//                                System.out.println(numbering4 + " " + reg);
//                                numbering4++;
//                            }
//                            int chooseNewTopping = console.promptForInt("Which regular topping do you want?: ");
//                            RegularToppings newTopping = regularTypes.get(chooseNewTopping - 1);
//                            bltSandwich.replaceTopping(newTopping);
//                            sandwich.addTopping(newTopping);
//
//                            int addingSecondTopping = console.promptForInt("Adding a second Topping, Change selection 1 or 2: ");
//                            if (addingSecondTopping == 1) {
//                                int numbering5 = 1;
//                                for (RegularToppings reg2 : regularToppings.getRegularToppings()) {
//                                    System.out.println(numbering5 + " " + reg2);
//                                    numbering5++;
//                                }
//                                int chooseSecondTopping = console.promptForInt("Which new topping do you want?");
//                                RegularToppings secondTopping = regularTypes.get(chooseSecondTopping - 1);
//                                bltSandwich.replaceTopping(secondTopping);
//                                sandwich.addTopping(secondTopping);
//
//                            } else if (addingSecondTopping == 2) {
//                                bltSandwich.replaceTopping(new RegularToppings("Tomato"));
//                                sandwich.addTopping(new RegularToppings("Tomato"));
//                            }
//                            int addingSauce = console.promptForInt("Adding sauce, change this 1 or 2: ");
//                            if (addingSauce == 1) {
//                                int numbering5 = 1;
//                                for (Sauce sauce : sauce.getSauce()) {
//                                    System.out.println(numbering5 + " " + sauce);
//                                    numbering5++;
//                                }
//                                int newSauce = console.promptForInt("Which new sauce do you want? : ");
//                                Sauce changeSauce = saucesList.get(newSauce - 1);
//                                bltSandwich.replaceSauce(changeSauce);
//                                sandwich.addSauce(changeSauce);
//                            } else if (addingSauce == 2) {
//                                bltSandwich.replaceSauce(new Sauce("Ranch"));
//                                sandwich.addSauce(new Sauce("Ranch"));
//                            }
//
//                            int toasted = console.promptForInt("This will come toasted do you want to change that 1 or 2?");
//                            if (toasted == 1) {
//                                bltSandwich.setToasted(false);
//                                sandwich.setToasted(false);
//                                newOrder = false;
//                            } else if (toasted == 2) {
//                                bltSandwich.setToasted(true);
//                                sandwich.setToasted(true);
//                                newOrder = false;
//                            }
//
//
//                        } else if (addingRegularToppings == 2) {
//                            bltSandwich.replaceTopping(new RegularToppings("Lettuce"));
//                            sandwich.addTopping(new RegularToppings("Lettuce"));
//
//
//                        }
//
//                    } else if (addingBacon == 2) {
//                        bltSandwich.replaceMeat(new Meat("Bacon"));
//                        sandwich.addTopping(new Meat("Bacon"));
//
//
//                    }
//                } else if (addingWhiteBread == 2) {
//                    bltSandwich.replaceBread(new Bread("White"));
//                    sandwich.addBread(new Bread("White"));
//
//                }
//            } else if (newSandwich == 2) {
//                sandwich = new Sandwich(8);
//                int addingWhiteBread = console.promptForInt("Adding White bread, do you want to change this selection? 1 or 2");
//                if (addingWhiteBread == 1) {
//                    int numbering = 1;
//                    for (Bread b : breadTypes) {
//                        System.out.println(numbering + " " + b);
//                        numbering++;
//                    }
//                    int chooseNewBread = console.promptForInt("Which new bread do you want");
//                    Bread newBread = breadTypes.get(chooseNewBread - 1);
//                    pcs.replaceBread(newBread);
//                    sandwich.addBread(newBread);
//
//                    int addingBacon = console.promptForInt("Adding Steak, do you want to change this selection 1 or 2: ");
//                    if (addingBacon == 1) {
//                        int numbering2 = 1;
//                        for (Meat m : meat.getMeatTopping()) {
//                            System.out.println(numbering2 + " " + m);
//                            numbering2++;
//                        }
//                        int chooseBacon = console.promptForInt("Which Meat do you want to add? : ");
//                        Meat newMeat = meatTypes.get(chooseBacon - 1);
//                        pcs.replaceMeat(newMeat);
//                        sandwich.addTopping(newMeat);
//
//                        int addingCheese = console.promptForInt("Adding American Cheese, do you want to change this selection 1 or 2: ");
//                        if (addingCheese == 1) {
//                            int numbering3 = 1;
//                            for (Cheese c : cheese.getCheeseToppings()) {
//                                System.out.println(numbering3 + " " + c);
//                                numbering3++;
//                            }
//                            int chooseCheese = console.promptForInt("Which Cheese Topping do you want?: ");
//                            Cheese newCheese = cheeseTypes.get(chooseCheese - 1);
//                            pcs.replaceCheese(newCheese);
//                            sandwich.addTopping(newCheese);
//                        }
//
//                        int addingRegularToppings = console.promptForInt("Adding RegularTopping(Peppers), do you want to change this selection 1 or 2: ");
//                        if (addingRegularToppings == 1) {
//                            int numbering4 = 1;
//                            for (RegularToppings reg : regularToppings.getRegularToppings()) {
//                                System.out.println(numbering4 + " " + reg);
//                                numbering4++;
//                            }
//                            int chooseNewTopping = console.promptForInt("Which regular topping do you want?: ");
//                            RegularToppings newTopping = regularTypes.get(chooseNewTopping - 1);
//                            pcs.replaceTopping(newTopping);
//                            sandwich.addTopping(newTopping);
//
//                            int addingSauce = console.promptForInt("Adding Mayo(Sauce), change this 1 or 2: ");
//                            if (addingSauce == 1) {
//                                int numbering5 = 1;
//                                for (Sauce sauce : sauce.getSauce()) {
//                                    System.out.println(numbering5 + " " + sauce);
//                                    numbering5++;
//                                }
//                                int newSauce = console.promptForInt("Which new sauce do you want? : ");
//                                Sauce changeSauce = saucesList.get(newSauce - 1);
//                                pcs.replaceSauce(changeSauce);
//                                sandwich.addSauce(changeSauce);
//                            }
//
//                            int toasted = console.promptForInt("This will come toasted do you want to change that 1 or 2?");
//                            if (toasted == 1) {
//                                pcs.setToasted(false);
//                                sandwich.setToasted(false);
//                            }
//                        }
//                    }
//                }
//            } else {
//                System.out.println("Not Available");
//            }
//
//        }
//    }
//





    //    private void orderASandwich() {
//
//        try {
//
//            int size = 4;
//
//
//            Sandwich sandwich = new Sandwich(size);
//            System.out.println("Here is the lists of bread: ");
//
//            int numbering = 1;
//
//            for (Bread b : bread.getBreadTypes()) {
//                System.out.println(numbering + ". " + b);
//                numbering++;
//            }
//
//            int chosenBread = console.promptForInt("Which bread do you want?: ");
//            Bread selectedBread = breadTypes.get(chosenBread - 1);
//
//
//            sandwich.addBread(selectedBread);
//
//            int numbering2 = 1;
//
//
//            for (Meat meat : meat.getMeatTopping()) {
//                System.out.println(numbering2 + " " + meat);
//                numbering2++;
//
//            }
//
//            int meaty = console.promptForInt("Which meat do you want?: ");
//            Meat m = meatTypes.get(meaty - 1);
//
//
//            int extra = console.promptForInt("Do you want extra meat(1 for yes): ");
//
//            if (extra == 1) {
//                for (Meat meat : meat.getMeatTopping())
//                    meat.setExtra(true);
//            }
//            sandwich.addTopping(m);
//
//
//            int numbering3 = 1;
//
//
//            for (Cheese cheese : cheese.getCheeseToppings()) {
//                System.out.println(numbering3 + " " + cheese);
//                numbering3++;
//            }
//
//
//            int che = console.promptForInt("Which cheese do you want?: ");
//            Cheese c = cheeseTypes.get(che - 1);
//
//
//            int extraCheese = console.promptForInt("Do you want extra Cheese?(1 for yes): ");
//            if (extraCheese == 1) {
//                for (Cheese cheese : cheese.getCheeseToppings()) {
//                    cheese.setExtra(true);
//                }
//            }
//            sandwich.addTopping(c);
//
//            int numbering4 = 1;
//            for (RegularToppings regular : regularToppings.getRegularToppings()) {
//                System.out.println(numbering4 + " " + regular);
//                numbering4++;
//            }
//
//            boolean normalToppings = true;
//
//            List<RegularToppings> normalT = new ArrayList<>();
//
//            while (normalToppings) {
//                int regular = console.promptForInt("Which regular topping do you want?: ");
//
//                RegularToppings normal = regularTypes.get(regular - 1);
//
//                sandwich.addTopping(normal);
//                normalT.add(normal);
//
//                System.out.println("Would you like another topping? ");
//                int anotherTopping = console.promptForInt("1 for Yes, 2 for No: ");
//
//                if (anotherTopping != 1) {
//                    normalToppings = false;
//                }
//
//            }
//
//
//            System.out.println("Here's your size " + size + " " + selectedBread +
//                    " sandwich with " + m + " and " + c + " cheese" + " with these toppings:  " + normalT);
//
//            System.out.printf(" Your total price is: %.2f \n", sandwich.getPrice());
//
//
//        } catch (Exception e) {
//            System.out.println("Sorry..there was an error processing your order, please try again.");
//        }
//
//    }



}

