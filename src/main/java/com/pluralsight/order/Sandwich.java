package com.pluralsight.order;
import com.pluralsight.sandwich.Bread;
import com.pluralsight.toppings.*;

import java.util.ArrayList;
import java.util.List;


public class Sandwich implements MenuItem {
    private final int size;
    private Bread breadType;
    private final List<Meat> meats = new ArrayList<>();
    private final List<Cheese> cheeses = new ArrayList<>();
    private final List<RegularToppings> regularToppings = new ArrayList<>();
    private final List<Sauce> sauceList = new ArrayList<>();
    double price;
    private boolean toasted;

    //Everything here is what a single Sandwich can have

    public Sandwich(int size) {
        this.size = size;

    }

    public int getSize() {
        return size;
    }

    public void setBreadType(Bread breadType) {
        this.breadType = breadType;
        this.price += breadType.getPrice(size);

    }

    public List<Meat> getMeats() {
        return meats;
    }

    public List<Cheese> getCheeses() {
        return cheeses;
    }

    public List<RegularToppings> getRegularToppings() {
        return regularToppings;
    }

    public List<Sauce> getSauceList() {
        return sauceList;
    }

    public boolean isToasted() {
        return toasted;
    }


    public void addSauce(Sauce sauce) {

        sauceList.add(sauce);

    }

    public void replaceBread(Bread replacedBread){

        if(breadType != null){
            price -= breadType.getPrice(size);
        }
        this.breadType = replacedBread;
        price += replacedBread.getPrice(size);

    }


public void addTopping(Toppings topping) {
    if (topping instanceof Meat) {
            meats.add((Meat) topping);

        } else if (topping instanceof Cheese) {
            cheeses.add((Cheese) topping);

        } else if (topping instanceof RegularToppings) {
            regularToppings.add((RegularToppings) topping);

        }

        this.price += topping.getPrice(size);


    }

    public double getPrice() {

        return price;


    }

    @Override
    public String description() {
        return toString();
    }


    public void setToasted(boolean toasted) {
        this.toasted = toasted;
    }

    @Override
    public String toString() {
        return String.format("A size %s\"  %s  with %s %s %s %s %s: $%.2f", size, breadType, meats, cheeses, regularToppings, sauceList, toasted ? "Toasted" : "Not Toasted", price);
    }

    public void clear(){
        Sandwich sandwich = null;
    }


}




