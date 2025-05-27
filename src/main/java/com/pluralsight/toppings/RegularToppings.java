package com.pluralsight.toppings;

import java.util.ArrayList;

import java.util.List;

public class RegularToppings extends Toppings {

    private final List<RegularToppings> regularToppings = new ArrayList<>();

    public RegularToppings(String name) {
        super(name);
        this. price = getPrice(4);
    }


    public List<RegularToppings> getRegularToppings() {



//            regularToppings.add(new RegularToppings("Lettuce"));
//            regularToppings.add(new RegularToppings("Peppers"));
//            regularToppings.add(new RegularToppings("Onions"));
//            regularToppings.add(new RegularToppings("Tomatoes"));
//            regularToppings.add(new RegularToppings("Jalapenos"));
//            regularToppings.add(new RegularToppings("Cucumbers"));
//            regularToppings.add(new RegularToppings("Pickles"));
//            regularToppings.add(new RegularToppings("Guacamole"));
//            regularToppings.add(new RegularToppings("Mushrooms"));


            return regularToppings;



    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice(int size) {
        return price;
    }

    @Override
    public String toString() {
        return name;
    }
}
