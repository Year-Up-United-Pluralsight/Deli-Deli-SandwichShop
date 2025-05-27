package com.pluralsight.sandwich;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bread{

    private final List<Bread> breads;

    private String name;
    private final double price;


    public Bread(String name) {
        this.name = name;
        this.price = getPrice(4);
       this.breads = new ArrayList<>();
    }

    public List<Bread> getBreadTypes() {

//            breads.add(new Bread("Wheat"));
//            breads.add(new Bread("Rye"));
//            breads.add(new Bread("Sourdough"));
//            breads.add(new Bread("White"));


        return breads;
    }


    public double getPrice(int size){
        if(size == 4){
            return 5.50;
        } else if (size == 8) {
            return 7.00;

        } else if (size == 12) {
            return 8.50;
        }

        return price;


    }

    @Override
    public String toString() {
        return name;
    }
}




