package com.pluralsight.filemanager;

import com.pluralsight.order.Order;
import com.pluralsight.order.MenuItem;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FileManager {


    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss");
    private final LocalDateTime localDateTime = LocalDateTime.now();
    private final String fileName = localDateTime.format(dateTimeFormatter) + ".txt";
    private final DateTimeFormatter getDateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd - hh:mm");
    private final LocalDateTime todaySDate = LocalDateTime.now();
    private final String today = todaySDate.format(getDateTime);


    //This will save the file to a receipts folder inside of SRC


    public void saveReceipt(Order order){

        File folder = new File("src/receipts");
        if (!folder.exists()) {
            folder.mkdir();
        }

        String pathToFolder = "src/receipts/" + fileName;

        try {BufferedWriter bw = new BufferedWriter(new FileWriter((pathToFolder), true));
            if(order != null){
                bw.write("--------------------------------------------\n");
                bw.write("DELI-DELI SANDWICH SHOP\n");
                bw.write("--------------------------------------------\n");
                bw.write("Cashier: DELI-DELI-MANAGER\n");

                for(MenuItem item : order.getMenuItems()){
                    bw.write("\n" + item.description());
                }
                bw.write(String.format("\nTOTAL: $%.2f\n", order.getTotal()));



            }

            if(order != null && order.getTotal() != 0){
                bw.write(String.format("\nOrdered by -> %s" ,order.getCustomerName()));
                bw.write("\nThank you for buying at DELI-DELI SANDWICH SHOP\n");

                bw.write(String.format("\nTODAY'S DATE: %s\n" , today));

            }

            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
