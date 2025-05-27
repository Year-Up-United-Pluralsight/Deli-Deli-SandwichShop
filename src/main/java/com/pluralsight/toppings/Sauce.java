package com.pluralsight.toppings;

import java.util.ArrayList;
import java.util.List;

public class Sauce extends Toppings{

    private final List<Sauce> addedSauce = new ArrayList<>();
    public Sauce(String name) {
        super(name);
    }

    public List<Sauce> getSauce(){

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
