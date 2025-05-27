package com.pluralsight.toppings;

import java.util.ArrayList;
import java.util.List;

public class Sauce extends Toppings{


    public Sauce(String name) {
        super(name);
    }

    public static List<Sauce> getSauce(){

        List<Sauce> addedSauce = new ArrayList<>();

            addedSauce.add(new Sauce("Mayo"));
            addedSauce.add(new Sauce("Mustard"));
            addedSauce.add(new Sauce("Ketchup"));
            addedSauce.add(new Sauce("Ranch"));
            addedSauce.add(new Sauce("Thousand Islands"));
            addedSauce.add(new Sauce("Vinaigrette"));



        return addedSauce;

    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice(int size) {
        return 0;
    }


    @Override
    public String toString() {
        return  name;
    }
}
