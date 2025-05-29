package com.pluralsight.toppings;

import java.util.ArrayList;
import java.util.List;

public class Cheese extends Toppings implements isExtra  {

    private boolean extra ;


    public Cheese(String name) {
        super(name);
        this.price = getPrice(4);
    }



    public static List<Cheese> getCheeseToppings(){
        List<Cheese> cheeseToppings = new ArrayList<>();

            cheeseToppings.add(new Cheese("American"));
            cheeseToppings.add(new Cheese("Provolone"));
            cheeseToppings.add(new Cheese("Cheddar"));
            cheeseToppings.add(new Cheese("Swiss"));


        return cheeseToppings;

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
        if(extra && size == 4){
            return .75  + .30;
        } else if(extra && size == 8){
            return 1.50 + .60;
        } else if(extra && size == 12) {
            return 2.25 + .90;
        }

        else if(!extra && size == 4){
            return .75;
        } else if(!extra && size == 8){
            return 1.50;
        } else if(!extra && size == 12) {
            return 2.25;
        }


        return price;
    }

    @Override
    public String toString() {
            return name;

    }
}
