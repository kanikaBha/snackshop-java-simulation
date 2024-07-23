/*package SnackShopCoursework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class main {
    SnackShop shop = new SnackShop("UEA shop");

        try {
        BufferedReader brC = new BufferedReader(new FileReader("/Users/kanikabhatia/IdeaProjects/JavaBasics/src/main/java/SnackShopCoursework/customers.txt"));
        String customerFileLine = brC.readLine();
        while (customerFileLine != null) {
            String c[] = customerFileLine.split("#");
            shop.addCustomer(c);
            customerFileLine = brC.readLine();
        }

        BufferedReader brS = new BufferedReader(new FileReader("/Users/kanikabhatia/IdeaProjects/JavaBasics/src/main/java/SnackShopCoursework/snacks.txt"));
        String snackFileLine = brS.readLine();
        while (snackFileLine != null) {
            String s[] = snackFileLine.split("@");
            shop.addSnack(s);
            snackFileLine = brS.readLine();
        }

    } catch (
    IOException e) {
        System.out.println(e.getMessage());
    }
}
}*/
