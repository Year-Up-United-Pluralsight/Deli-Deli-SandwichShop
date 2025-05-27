package com.pluralsight.extraproducts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pluralsight.javainterfaces.*;

public class Drink implements chooseSize, isCheckedOut, addToCheckOut{

    private String size;
    private  String name;
    private boolean checkedOut;
    private boolean addToCheckout;
    private double price;



    public Drink(String name) {
        this.name = name;
        this.size = getSize();
        this.price = getPrice("");


    }

    public double getPrice(String size) {

        if(size != null) {
            switch (size.toLowerCase()) {
                case "small":
                    return 2.00;
                case "medium":
                    return 2.50;
                case "large":
                    return 3.00;
            }
        }
        return price;
    }

    @Override
    public void setSize(String size) {
        if (size.equalsIgnoreCase("small") || size.equalsIgnoreCase("medium") || size.equalsIgnoreCase("large")) {
            this.size = size.toLowerCase();

        }
    }

    public static List<Drink> getDrinkFlavor(){
        List<Drink> drinkFlavor = new ArrayList<>();

            drinkFlavor.add((new Drink("Grape")));
            drinkFlavor.add(new Drink("Lemon"));
            drinkFlavor.add((new Drink("Mango")));
            drinkFlavor.add((new Drink("pineapple")));


        return drinkFlavor;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        this.price = getPrice(size);
        return String.format( "A size %s %s flavor drink: $%.2f", size,name,price);
    }

    @Override
    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
        if(checkedOut){
            drinkFlavor = new ArrayList<>();
            price = 0.0;
            size = "";
            name = "";
        }


    }

    @Override
    public void addingToCheckOut(boolean addCheckOut) {
        this.addToCheckout = addCheckOut;
    }
}
