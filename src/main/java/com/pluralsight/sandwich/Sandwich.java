package com.pluralsight.sandwich;
import com.pluralsight.javainterfaces.*;
import com.pluralsight.toppings.*;

import java.util.ArrayList;
import java.util.List;


public class Sandwich implements isToasted, isCheckedOut, addToCheckOut {
    private int size;
    private List<Bread> breadType = new ArrayList<>();
    private List<Meat> meats = new ArrayList<>();
    private List<Cheese> cheeses = new ArrayList<>();
    private List<RegularToppings> regularToppings = new ArrayList<>();
    private List<Sauce> sauceList = new ArrayList<>();
    double price;
    private boolean toasted;
    private boolean addToCheckOut;
    private boolean checkedOut;



    public int getSize() {
        return size;
    }

    public List<Bread> getBreadType() {
        return breadType;
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

    public boolean isAddToCheckOut() {
        return addToCheckOut;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public Sandwich(int size) {
        this.size = size;

    }


    public void addSauce(Sauce sauce) {

        sauceList.add(sauce);

    }

    public void addBread(Bread bread) {

        breadType.add(bread);
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
    public void setToasted(boolean toasted) {
        this.toasted = toasted;
    }

    @Override
    public String toString() {
        String toastedString = toasted ? "Toasted" : "Not Toasted";
        return String.format("%s %s %s %s %s %s %s: $%.2f", size, breadType, meats, cheeses, regularToppings, sauceList, toastedString, price);
    }


    @Override
    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
        if(checkedOut){
            checkedOut();
        }

    }

    public void checkedOut(){
        if(this.checkedOut){
            size = 0;
            price = 0.0;
            breadType = new ArrayList<>();
            meats = new ArrayList<>();
            cheeses = new ArrayList<>();
            regularToppings = new ArrayList<>();
            sauceList = new ArrayList<>();
            toasted = false;


        }

    }

    @Override
    public void addingToCheckOut(boolean addCheckOut) {
        this.addToCheckOut = addCheckOut;
    }
}


