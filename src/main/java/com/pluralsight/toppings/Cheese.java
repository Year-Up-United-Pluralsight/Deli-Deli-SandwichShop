package com.pluralsight.toppings;

import java.util.ArrayList;
import java.util.List;
import com.pluralsight.javainterfaces.isExtra;

public class Cheese extends Toppings implements isExtra  {

    private boolean extra ;
    private final List<Cheese> cheeseToppings;

    public Cheese(String name) {
        super(name);
        this.price = getPrice(4);
        this.cheeseToppings = new ArrayList<>();
    }



    public List<Cheese> getCheeseToppings(){

//        if(cheeseToppings != null){
//            cheeseToppings.add(new Cheese("American"));
//            cheeseToppings.add(new Cheese("Provolone"));
//            cheeseToppings.add(new Cheese("Cheddar"));
//            cheeseToppings.add(new Cheese("Swiss"));
//        }

        return this.cheeseToppings;

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
