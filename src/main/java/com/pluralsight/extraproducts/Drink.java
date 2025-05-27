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


    private List<Drink> drinkFlavor;


    public Drink(String name) {
        this.name = name;
        this.size = getSize();
        this.price = getPrice("");
        this.drinkFlavor = new ArrayList<>();

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

    public List<Drink> getDrinkFlavor(){
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
