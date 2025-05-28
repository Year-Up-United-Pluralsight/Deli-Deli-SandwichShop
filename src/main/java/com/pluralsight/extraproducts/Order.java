package com.pluralsight.extraproducts;


import com.pluralsight.javainterfaces.isMenuItem;
import com.pluralsight.userinterface.UserInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order  {

    private static String customerName;
    private static LocalDateTime orderTime;
    private static  List<isMenuItem> menuItems = new ArrayList<>();


    public Order(String customerName, LocalDateTime orderTime) {
        Order.customerName = customerName;
        Order.orderTime = orderTime;
    }


    public Order(LocalDateTime orderTime){
        Order.customerName = getCustomerName();
        Order.orderTime = orderTime;
    }




    public static void addItem(isMenuItem menuItem){

        menuItems.add(menuItem);


    }


    public LocalDateTime getOrderTime(){

        return orderTime;

    }


    public String getCustomerName(){


        return customerName;
    }

    public static double getTotal(){
        double total = 0;

        for(isMenuItem menuItem : menuItems){
            total += menuItem.getPrice();

        }


        return total;

    }


public static List<isMenuItem> getMenuItems(){


        return menuItems;


}

public static void clearMenuItem(){

       menuItems.clear();

}




}
