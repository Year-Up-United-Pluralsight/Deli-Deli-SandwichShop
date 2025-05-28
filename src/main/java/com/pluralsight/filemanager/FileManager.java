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
                bw.write("--------------------------------------------\n");
                bw.write("DELI-DELI SANDWICH SHOP\n");
                bw.write("--------------------------------------------\n");
                bw.write("Cashier: IntelliJ\n");

                for(MenuItem item : order.getMenuItems()){
                    bw.write("\n" + item.description() + "\n");
                }



            }

            if(order != null && order.getTotal() != 0){
                bw.write(String.format("\nTotal amount paid $%.2f ordered by -> %s\n", order.getTotal() , order.getCustomerName()));
                bw.write("Thank you for buying at DELI-DELI SANDWICH SHOP");

            }

            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
