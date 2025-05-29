package com.pluralsight.toppings;

import java.util.ArrayList;
import java.util.List;



public class Meat extends Toppings implements isExtra  {

    private boolean extra;


    public Meat(String name) {
        super(name);
        this.price = getPrice(4);


    }

    public static List<Meat> getMeatTopping() {
        List<Meat> meatTopping = new ArrayList<>();

            meatTopping.add(new Meat("Steak"));
            meatTopping.add(new Meat("Ham"));
            meatTopping.add(new Meat("Salami"));
            meatTopping.add(new Meat("Roast Beef"));
            meatTopping.add(new Meat("Chicken"));
            meatTopping.add(new Meat("Bacon"));


        return meatTopping;
    }


    @Override
    public void setExtra(boolean extra) {
        this.extra = extra;
    }

    @Override
    public String getName() {
        return this.name;

    }

    @Override
    public double getPrice(int size) {


        if (!extra && size == 4) {
            return 1.00;
        } else if (!extra && size == 8) {
            return 2.00;
        } else if (!extra && size == 12) {
            return 3.00;
        }else if (extra && size == 4) {
            return 1.00 + .50;
        } else if (extra && size == 8) {
            return 2.00 + 1.00;
        } else if (extra && size == 12) {
            return 3.00 + 1.50;
        }




        return price;

    }


    @Override
    public String toString() {
        return name;
    }
}
