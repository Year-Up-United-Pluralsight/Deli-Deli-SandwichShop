package com.pluralsight.toppings;

public abstract class Toppings {

    protected String name;
    protected double price;


    public Toppings(String name) {
        this.name = name;
        this.price = price;
    }


    public abstract String getName();
    public abstract double getPrice(int size);



}
