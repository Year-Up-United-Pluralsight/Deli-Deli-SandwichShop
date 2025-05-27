package com.pluralsight.filemanager;

import com.pluralsight.extraproducts.Chip;
import com.pluralsight.extraproducts.Drink;
import com.pluralsight.sandwich.BLT;
import com.pluralsight.sandwich.Sandwich;
import com.pluralsight.toppings.Cheese;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FileManager {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss");


    private LocalDateTime fileName = LocalDateTime.now();

    private String formattedDate = fileName.format(dateTimeFormatter) + ".txt";


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


    public void saveReceipt(Sandwich sandwich,BLT bltSandwich, Chip chip, Drink drink){

        try {BufferedWriter bw = new BufferedWriter(new FileWriter((formattedDate), true));

            double total = 0;
            if(sandwich != null) {
                bw.write(sandwich + "\n");
                total += sandwich.getPrice();
            }

            if(bltSandwich != null){
                bw.write(bltSandwich + "\n");
                total += bltSandwich.getPrice();
            }


            if(!chip.getName().equals(" ")){
                bw.write(chip + "\n");
                total += chip.getPrice();
            }

            if(drink != null && drink.getSize() != null) {
                bw.write(drink + "\n");
                total += drink.getPrice(" ");

            }


            if(total != -1){
                bw.write(String.format("\ntotal amount paid: $%.2f",total));
            }

            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
