package com.pluralsight.extraproducts;


import com.pluralsight.javainterfaces.isMenuItem;
import com.pluralsight.sandwich.BLT;
import com.pluralsight.sandwich.Sandwich;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order  {

    private static String customerName;
    private static LocalDateTime orderTime;
    private final static List<isMenuItem> menuItems = new ArrayList<>();


    public Order(String customerName, LocalDateTime orderTime) {
        this.customerName = customerName;
        this.orderTime = orderTime;
    }


    public void addItem(isMenuItem menuItem){
        menuItems.add(menuItem);


    }


    public LocalDateTime getOrderTime(){

        return orderTime;

    }


    public String getCustomerName(){


        return customerName;
    }

    public double getTotal(){
        double total = 0;

        for(isMenuItem menuItem : menuItems){
            total += menuItem.getPrice();

        }


        return total;

    }






}
//private final  List<Sandwich> sandwichList;
//private final  List<BLT> bltList;
//private final  List<Drink> drinkList;
//private final  List<Chip> chipList;
//
//
//    public Order() {
//        sandwichList = new ArrayList<>();
//        bltList  = new ArrayList<>();
//        drinkList = new ArrayList<>();
//        chipList = new ArrayList<>();
//    }
//
//
//    public  void addBltToOrder(BLT bltSandwich){
//
//        if(bltSandwich != null){
//            bltList.add(bltSandwich);
//        }
//    }
//
//    public  List<BLT> getBltList(){
//        return bltList;
//
//
//    }
//
//
//
//    public  void addSandwichToOrder(Sandwich sandwich){
//        sandwichList.add(sandwich);
//
//    }
//
//    public  void addDrinkToOrder(Drink drink){
//        drinkList.add(drink);
//    }
//
//    public void addChipToOrder(Chip chip){
//        chipList.add(chip);
//
//    }
//
//
//    public  List<Sandwich> getSandwichList(){
//
//        return sandwichList;
//
//    }
//
//
//    public List<Drink> getDrinkList(){
//
//        return drinkList;
//    }
//
//    public  List<Chip> getChipList(){
//
//        return chipList;
//    }
//
//    public void checkOut(){
//
//        for (Sandwich sandwich : sandwichList) {
//            sandwich.setCheckedOut(true);
//        }
//
//
//        for (Drink drink : drinkList) {
//            drink.setCheckedOut(true);
//
//        }
//
//        for (Chip chip : chipList) {
//            chip.setCheckedOut(true);
//        }
//
//        System.out.println("Thank you for buying at our Deli Deli SandwichShop");
//
//
//
//
//    }
//
//
//
//    public double getPrice(){
//        double price = 0;
//
//
//        for (Sandwich sandwich : sandwichList) {
//
//            price += sandwich.getPrice();
//        }
//
////        for (Drink drink : drinkList) {
////            price += drink.getPrice();
////        }
//        for(isMenuItem )
//
//
//
//        for (Chip chip : chipList) {
//            price += chip.getPrice();
//        }
//
//        System.out.printf("The total Price is:$%.2f\n " , price);
//
//
//
//        return price;
//
//
//    }
//
//    public void clearOrder() {
//        sandwichList.clear();
//        bltList.clear();
//        drinkList.clear();
//        chipList.clear();
//    }
//
//}
