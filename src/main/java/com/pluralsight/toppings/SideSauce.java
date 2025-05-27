package com.pluralsight.toppings;

import java.util.ArrayList;
import java.util.List;

public class SideSauce extends Sauce{

    List<SideSauce> sideSauces = new ArrayList<>();
    public SideSauce(String name) {
        super(name);
    }


    public List<SideSauce> getSideSauces(){
        return sideSauces;

    }


    @Override
    public List<Sauce> getSauce() {
        return super.getSauce();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getPrice(int size) {
        return super.getPrice(size);
    }
}
