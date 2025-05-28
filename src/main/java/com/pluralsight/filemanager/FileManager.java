package com.pluralsight.filemanager;

import com.pluralsight.order.Order;
import com.pluralsight.javainterfaces.MenuItem;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FileManager {


    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss");
    private final LocalDateTime fileName = LocalDateTime.now();
    private final String formattedDate = fileName.format(dateTimeFormatter) + ".txt";




    public void saveReceipt(Order order){

        try {BufferedWriter bw = new BufferedWriter(new FileWriter((formattedDate), true));
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
                bw.write("\nThank you for buying at DELI-DELI SANDWICH SHOP");

            }

            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
