package com.pluralsight.filemanager;

import com.pluralsight.extraproducts.Order;
import com.pluralsight.javainterfaces.MenuItem;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class FileManager {


    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss");
    private final LocalDateTime fileName = LocalDateTime.now();
    private final String formattedDate = fileName.format(dateTimeFormatter) + ".txt";




    public void saveReceipt(Order order){

        try {BufferedWriter bw = new BufferedWriter(new FileWriter((formattedDate), true));
            if(order != null){
                for(MenuItem item : order.getMenuItems()){
                    bw.write(item.description() + "\n");
                }



            }

            if(order != null && order.getTotal() != 0){
                bw.write(String.format("\ntotal amount paid $%.2f ordered by -> %s", order.getTotal() , order.getCustomerName()));

            }

            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
