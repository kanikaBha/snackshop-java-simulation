package SnackShopCoursework;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/* file: Simulation.java

        description: class to simulate running a snack shop
                     methods to set up and run transactions of a shop based on the information provided in files passed in to the main method
                     *contains main method (creates one SnackShop: UEA shop)

*/


public class Simulation {


    // method to return shop with name set to the argument passed in + files with snack and customer passed in to be parsed and processed under shopName shop
        public static SnackShop initaliseShop(String shopName, File snackFile, File customerFile){
            SnackShop shop = new SnackShop(shopName); // set name of the shop to shopName argument



            try {
                BufferedReader brS = new BufferedReader(new FileReader(snackFile)); // snackFile argument file reading
                String snackFileLine = brS.readLine();
                while (snackFileLine != null) {
                    String snackDetails[] = snackFileLine.split("@"); // split each line by '@' into an array of strings
                    shop.addSnack(snackDetails); // use addSnack() method to add each line containing snack details to the snack arraylist of shopName
                    snackFileLine = brS.readLine();
                }


                BufferedReader brC = new BufferedReader(new FileReader(customerFile)); // customerFile argument file reading
                String customerFileLine = brC.readLine();
                while (customerFileLine != null) {
                    String customerDetails[] = customerFileLine.split("#"); // split each line by '#' into an array of strings
                    shop.addCustomer(customerDetails); // use addCustomer() method to add each line containing customer details to the customer arraylist of shopName
                    customerFileLine = brC.readLine();
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            return shop;

        }

    // method to simulate the shopping process after shopName has been created using initialiseShop() method; now with customers and snacks present-file with transactions is parsed and processed
        public static void simulateShopping(SnackShop shop, File transactionFile){
            try{
                BufferedReader brT = new BufferedReader(new FileReader(transactionFile)); // transactionFile argument file reading
                String transactionFileLine = brT.readLine();
                while (transactionFileLine != null) {
                    ArrayList<String> transactionDetails = new ArrayList<>(); // create arraylist to store transaction details inside
                    String[] split=transactionFileLine.split(","); // split each line by ',' into parts
                    for(String detail : split){ // each part is added to tDetails arraylist
                        transactionDetails.add(detail);
                    }
                        shop.processTransactions(transactionDetails); // each line is processed individually before moving onto the next-using the processTransactions() method

                    transactionFileLine = brT.readLine();
                }
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }

        }


        public static void main(String[] args){ // main method
            // pass in all text files
            File snackFile = new File("snacks.txt");
            File customerFile= new File("customers.txt");
            File transactionFile= new File("transactions.txt");
            // use static methods to create one instance (UEA) of the snack shop + process transactions as listed in transactionFile using the customers and snacks provided
            SnackShop shop= initaliseShop("UEA shop", snackFile, customerFile);
            simulateShopping(shop, transactionFile);
            System.out.println(shop.getShopName() + " Turnover: " + shop.getTurnover()); // display turnover of shopName
            System.out.println(shop.getShopName() + " Largest base price: " + shop.findLargestBasePrice()); // display the largest base price of shopName
            System.out.println(shop.getShopName() + " Number of negative balances: " + shop.countNegativeAccounts()); // display number of negative balances registered with shopName
            System.out.println(shop.getShopName() + " Median customer balance: " + shop.calculateMedianCustomerBalance()); // display the median customer balance of shopName


        }

    }



