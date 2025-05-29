package com.pluralsight.sandwich;
import com.pluralsight.javainterfaces.*;
import com.pluralsight.order.Sandwich;
import com.pluralsight.toppings.*;

import java.util.List;


public class PhillyCheeseSteak extends Sandwich implements customizeToppings {

    public PhillyCheeseSteak() {
        super(8);
        super.setBreadType(new Bread("White"));
        super.addTopping(new Meat("Steak"));
        super.addTopping(new Cheese("American"));
        super.addTopping(new RegularToppings("Peppers"));
        super.addSauce(new Sauce("Mayo"));
        super.setToasted(true);
    }


    @Override
    public void removeTopping(Toppings topping) {

    }

    @Override
    public void replaceTopping(RegularToppings replacedTopping) {

    }
//
//    @Override
//    public void replaceBread(Bread replacedBread) {
//        super.clear().replaceAll(bread -> replacedBread);
//
//    }

    @Override
    public void removeSauce(Sauce sauce) {

        super.getSauceList().remove(sauce);
    }

    @Override
    public void replaceSauce(Sauce replacedSauce) {
        super.getSauceList().replaceAll(sauce -> replacedSauce);
    }

    @Override
    public void removeMeat(Meat meat) {
        super.getMeats().remove(meat);
    }

    @Override
    public void removeCheese(Cheese cheese) {
        super.getCheeses().remove(cheese);
    }

    @Override
    public void replaceMeat(Meat replacedMeat) {
        super.getMeats().replaceAll(meat -> replacedMeat);
    }

    @Override
    public void replaceCheese(Cheese replacedCheese) {
        super.getCheeses().replaceAll(cheese -> replacedCheese);
    }


    @Override
    public void replaceToasted(boolean toasted) {

        super.setToasted(toasted);
    }

    @Override
    public void replaceToppingAtIndex0(RegularToppings replacedTopping) {
        List<RegularToppings> toppings = super.getRegularToppings();
        if (toppings.size() == 1) {
            toppings.set(0, replacedTopping); // Replace the topping at index 0
        } else {
            System.out.println("No toppings to replace at index 0.");
        }
    }

    @Override
    public void replaceToppingAtIndex1(RegularToppings replacedTopping) {
        List<RegularToppings> toppings = super.getRegularToppings();
        if (toppings.size() == 2) {
            toppings.set(1, replacedTopping); // Replace the topping at index 1
        } else {
            System.out.println("No topping to replace at index 1.");
        }
    }
}
