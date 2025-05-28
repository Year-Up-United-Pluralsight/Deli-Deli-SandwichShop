package com.pluralsight.filemanager;

import com.pluralsight.extraproducts.Chip;
import com.pluralsight.extraproducts.Drink;
import com.pluralsight.extraproducts.Order;
import com.pluralsight.javainterfaces.isMenuItem;
import com.pluralsight.sandwich.BLT;
import com.pluralsight.sandwich.Sandwich;
import com.pluralsight.toppings.Cheese;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class FileManager {


    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss");


    private LocalDateTime fileName = LocalDateTime.now();

    private String formattedDate = fileName.format(dateTimeFormatter) + ".txt";
    private final Order order = new Order(LocalDateTime.now());

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
    public LocalDateTime getFileName() {
        return fileName;
    }

    public void setFileName(LocalDateTime fileName) {
        this.fileName = fileName;
    }


    public void saveReceipt(List<isMenuItem> menuItemList){

        try {BufferedWriter bw = new BufferedWriter(new FileWriter((formattedDate), true));
            if(menuItemList != null){
                bw.write(menuItemList + "\n");
            }

            if(Order.getTotal() != -1){
                bw.write(String.format("\ntotal amount paid %s", order));

            }

            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
