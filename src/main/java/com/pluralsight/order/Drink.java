package com.pluralsight.order;

import java.util.ArrayList;
import java.util.List;

import com.pluralsight.javainterfaces.*;

public class Drink implements drinkChooseSize, MenuItem{

    private String size;
    private  String name;
    private double price;



    public Drink(String name) {
        this.name = name;
        this.size = getSize();



    }

    public Drink(){
        this.name = getName();
        this.size = getSize();
    }

    public static List<Drink> getDrinkFlavor(){
        List<Drink> drinkFlavor = new ArrayList<>();

        drinkFlavor.add((new Drink("Grape")));
        drinkFlavor.add(new Drink("Lemon"));
        drinkFlavor.add((new Drink("Mango")));
        drinkFlavor.add((new Drink("pineapple")));


        return drinkFlavor;

    }


    @Override
    public void setSize(String size) {
        if (size.equalsIgnoreCase("small") || size.equalsIgnoreCase("medium") || size.equalsIgnoreCase("large")) {
            this.size = size.toLowerCase();

        }
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
//        this.price = getPrice();
        return String.format( "A size %s %s flavor drink: $%.2f", size,name,getPrice());
    }


    @Override
    public double getPrice() {
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
    public String description() {
       return toString();
    }


}
