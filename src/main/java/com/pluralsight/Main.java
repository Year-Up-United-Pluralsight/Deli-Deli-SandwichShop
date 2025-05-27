package com.pluralsight;


import com.pluralsight.extraproducts.Chip;
import com.pluralsight.extraproducts.Drink;
import com.pluralsight.extraproducts.Order;
import com.pluralsight.sandwich.BLT;
import com.pluralsight.sandwich.Sandwich;
import com.pluralsight.userinterface.UserInterface;

import java.time.LocalDateTime;

public class Main {



    public static void main(String[] args) {


        UserInterface UI = new UserInterface();
        UI.init();


//        Order order = new Order("hey", LocalDateTime.now());
////
////            Drink drink = new Drink("Soda");
////            Chip chip = new Chip("PopCorn");
////            Sandwich sandwich = new Sandwich(8);
////
////            order.addItem(drink);
////            order.addItem(chip);
////            order.addItem(sandwich);

//        System.out.println(order.getTotal());


    }


}