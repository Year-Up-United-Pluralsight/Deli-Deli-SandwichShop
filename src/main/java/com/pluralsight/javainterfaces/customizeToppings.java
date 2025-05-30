package com.pluralsight.javainterfaces;
import com.pluralsight.sandwich.Bread;
import com.pluralsight.toppings.*;

public interface customizeToppings {
    void addTopping(Toppings topping);
    void removeTopping(Toppings topping);
    void replaceTopping(RegularToppings replacedTopping);
    void replaceBread(Bread replacedBread);
    void removeSauce(Sauce sauce);
    void replaceSauce(Sauce replacedSauce);
    void addSauce(Sauce sauce);
    void removeMeat(Meat meat);
    void replaceMeat(Meat replacedMeat);
    void removeCheese(Cheese cheese);
    void replaceCheese(Cheese replacedCheese);
    void replaceToasted(boolean toasted);
    void replaceToppingAtIndex0(RegularToppings replacedTopping);
    void replaceToppingAtIndex1(RegularToppings regularToppings);
}

//This class will help in the future to add and remove toppings and sauce.