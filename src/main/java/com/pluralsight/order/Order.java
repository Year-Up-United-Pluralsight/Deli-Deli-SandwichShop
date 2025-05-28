package com.pluralsight.order;


import com.pluralsight.javainterfaces.MenuItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order  {

    private final String customerName;
    private final LocalDateTime orderTime;
    private final List<MenuItem> menuItems = new ArrayList<>();


    public Order(String customerName, LocalDateTime orderTime) {
        this.customerName = customerName;
        this.orderTime = orderTime;
    }


    public Order(LocalDateTime orderTime){
        this.customerName = getCustomerName();
        this.orderTime = orderTime;
    }




    public void addItem(MenuItem menuItem){

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

        for(MenuItem menuItem : menuItems){
            total += menuItem.getPrice();

        }


        return total;

    }


public List<MenuItem> getMenuItems(){


        return menuItems;


}

public void clearMenuItem(){

       menuItems.clear();

}


}
