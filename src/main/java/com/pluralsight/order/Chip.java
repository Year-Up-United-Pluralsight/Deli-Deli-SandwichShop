package com.pluralsight.order;

import java.util.ArrayList;
import java.util.List;

public class Chip implements MenuItem {

    private String name;
    private double price;
    private boolean checkedOut;
    private boolean addToCheckOut;



    public Chip(String name) {
        this.name = name;
        this.price = getPrice();
    }

    public Chip(){
        this.name =getName();
        this.price = getPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price = 1.50;
    }

    @Override
    public String description() {
        return toString();
        //System.out.println("These are bags of Chips");
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static List<Chip> getChips() {
        List<Chip> chips = new ArrayList<>();

            chips.add((new Chip("Lays")));
            chips.add((new Chip("Fritos")));
            chips.add((new Chip("Doritos")));
            chips.add((new Chip("Cheetos")));



        return chips;
    }

    @Override
    public String toString() {
        return String.format("A bag of %s: $%.2f", name , price);
    }


}
