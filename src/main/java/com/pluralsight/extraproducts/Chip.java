package com.pluralsight.extraproducts;

import java.util.ArrayList;
import java.util.List;
import com.pluralsight.javainterfaces.*;

public class Chip implements isCheckedOut, addToCheckOut{

    private String name;
    private double price;
    private boolean checkedOut;
    private boolean addToCheckOut;
    private List<Chip> chips;


    public Chip(String name) {
        this.name = name;
        this.price = getPrice();
        this.chips = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price = 1.50;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Chip> getChips() {


        return chips;
    }

    @Override
    public String toString() {
        return String.format("%s: $%.2f", name , price);
    }

    @Override
    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;

    }

    @Override
    public void addingToCheckOut(boolean addCheckOut) {
        this.addToCheckOut = addCheckOut;
    }
}
