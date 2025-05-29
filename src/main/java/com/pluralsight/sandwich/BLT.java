package com.pluralsight.sandwich;

import com.pluralsight.javainterfaces.*;
import com.pluralsight.order.MenuItem;
import com.pluralsight.order.Sandwich;
import com.pluralsight.toppings.*;

public class BLT extends Sandwich implements customizeToppings, MenuItem {

    private int size;



    public BLT() {
        super(8);
        super.addBread(new Bread("White"));
        super.addTopping(new Meat("Bacon"));
        super.addTopping(new Cheese("Cheddar"));
        super.addTopping(new RegularToppings("Tomato"));
        super.addTopping(new RegularToppings("Lettuce"));
        super.addSauce(new Sauce("Ranch"));
        super.setToasted(true);


    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void removeTopping(Toppings topping) {
        super.getRegularToppings().removeFirst();
    }

    @Override
    public void replaceTopping(RegularToppings replacedTopping) {
        super.getRegularToppings().replaceAll(regularToppings -> replacedTopping);
    }

    @Override
    public void replaceBread(Bread replacedBread) {

        super.getBreadType().add(replacedBread); // add the new bread

    }

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

    public void replaceToasted(boolean toasted) {

        super.setToasted(toasted);
    }

}
