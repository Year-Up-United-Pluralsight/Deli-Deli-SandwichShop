package com.pluralsight.filemanager;

import com.pluralsight.extraproducts.Chip;
import com.pluralsight.extraproducts.Drink;
import com.pluralsight.extraproducts.Order;
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
    private final Order order = new Order("Name", LocalDateTime.now());

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


    public void saveReceipt(List<Sandwich> sandwich, List<BLT> bltSandwich, List<Chip> chip, List<Drink> drink){

        try {BufferedWriter bw = new BufferedWriter(new FileWriter((formattedDate), true));

//            double total = 0;
//            if(!order.){
//                bw.write(sandwich + "\n");
//
//
//            }
//
//            if(order.getBltList() != null){
//                bw.write(bltSandwich + "\n");
//
//            }
//
//            if(order.getChipList() != null){
//                bw.write(chip + "\n");
//
//            }
//            if(order.getDrinkList() != null){
//                bw.write(drink + "\n");
//
//            }
//
//            if(order.getPrice() != -1){
                bw.write(String.format("\ntotal amount paid: $%.2f", order.);

//            }

            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
