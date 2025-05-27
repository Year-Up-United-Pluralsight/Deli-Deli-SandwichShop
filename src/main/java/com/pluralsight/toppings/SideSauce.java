package com.pluralsight.toppings;

import java.util.ArrayList;
import java.util.List;

public class SideSauce extends Sauce{


    public SideSauce(String name) {
        super(name);
    }


    public static List<SideSauce> getSideSauces(){
        List<SideSauce> sideSaucesList = new ArrayList<>();

            sideSaucesList.add(new SideSauce("au jus"));
            sideSaucesList.add(new SideSauce("sauce"));



        return sideSaucesList;

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
