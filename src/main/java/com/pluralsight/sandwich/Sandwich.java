package com.pluralsight.sandwich;
import com.pluralsight.javainterfaces.*;
import com.pluralsight.toppings.*;

import java.util.ArrayList;
import java.util.List;


public class Sandwich implements MenuItem {
    private int size;
    private Bread breadType;
    private List<Meat> meats = new ArrayList<>();
    private List<Cheese> cheeses = new ArrayList<>();
    private List<RegularToppings> regularToppings = new ArrayList<>();
    private List<Sauce> sauceList = new ArrayList<>();
    double price;
    private boolean toasted;


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

    public void addBread(Bread bread) {

        this.price += bread.getPrice(size);


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
        return String.format("%s %s %s %s %s %s %s: $%.2f", size, breadType, meats, cheeses, regularToppings, sauceList, toasted ? "Toasted" : "Not Toasted", price);
    }



//    public void checkedOut(){
//        if(this.checkedOut){
//            size = 0;
//            price = 0.0;
//            breadType = null;
//            meats = new ArrayList<>();
//            cheeses = new ArrayList<>();
//            regularToppings = new ArrayList<>();
//            sauceList = new ArrayList<>();
//            toasted = false;
//
//
//        }

    }




