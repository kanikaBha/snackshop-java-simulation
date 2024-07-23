package SnackShopCoursework;

import java.util.ArrayList;
import java.util.Collections;

/* file: SnackShop.java

        description: class to model each individual snack shop
                     all snack shops will have a name, turnover, registered customer list, available snacks list
                     *contains methods to process files that are passed in to the main method*

*/

public class SnackShop {
    private String shopName;

    private int turnover; // total sales of each shop
    protected ArrayList<Snack> snacks = new ArrayList<>(); // stores records of all snacks sold by a shop

    protected ArrayList<Customer> customers = new ArrayList<>(); // stores records of all customers registered at that shop


    // parameterised SnackShop constructor takes a single argument: shop name
    public SnackShop(String shopName) {
        this.shopName = shopName;
    }

    //accessor and mutator methods
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getTurnover() {
        return turnover;
    }


    // method to retrieve the full Customer object: customers details, by passing in the customers ID as an argument
    public Customer getCustomer(String CustomerID) throws InvalidCustomerException {
        for (Customer c : customers) {
            if (c.getCustomerID().equals(CustomerID)) {
                return c;
            }
        }
        throw new InvalidCustomerException("    -- This customer ID is not registered in this shop: " + getShopName() + " --");
    }

    // method to retrieve the full Snack object: snack details, by passing in the snacks ID as an argument
    public Snack getSnack(String SnackID) throws InvalidSnackException {
        for (Snack s : snacks) {
            if (s.getSnackID().equals(SnackID)) {
                return s;
            }
        }
        throw new InvalidSnackException("   -- This snack ID does not exist in this shop: " + getShopName() + " --");
    }

    // method to populate the customer arraylist for a shop, using the format of the customers.txt file provided; pass in each line of the text file as an array
    public void addCustomer(String c[]) {
        try {
            if (c.length > 3) { // c.length > 3 indicates the customer is either mentioned as a student or a staff
                if ((c[3].toUpperCase()).equals("STAFF")) { // assigns customer as a staff customer object
                    if (c.length == 5) { // the additional field specifies the school that this staff member is a part of- necessary for staff discount calculation
                        StaffCustomer.School schoolEnum = StaffCustomer.School.valueOf(c[4].toUpperCase());
                        StaffCustomer schoolStaff = new StaffCustomer(c[0], c[1], Integer.parseInt(c[2]), schoolEnum);
                        customers.add(schoolStaff);
                        //System.out.println("staff of CMP/MTH/BIO- " + schoolStaff);
                    } else { // the staff must belong to a school in order to determine the correct staff discount; if none is specified, automatically assign their school to 'OTHER'
                        StaffCustomer.School schoolEnum = StaffCustomer.School.OTHER;
                        StaffCustomer otherStaff = new StaffCustomer(c[0], c[1], Integer.parseInt(c[2]), schoolEnum);
                        customers.add(otherStaff);
                        //System.out.println("staff of OTHER- " + otherStaff);
                    }
                } else if ((c[3].toUpperCase()).equals("STUDENT")) { // assigns customer as a student customer object
                    StudentCustomer student = new StudentCustomer(c[0], c[1], Integer.parseInt(c[2]));
                    customers.add(student);
                    //System.out.println("student- " + student);
                }
            } else if (c.length == 3) { // if neither student nor staff is specified; assign customer as member of the public
                Customer memberOfPublic = new Customer(c[0], c[1], Integer.parseInt(c[2]));
                customers.add(memberOfPublic);
                //System.out.println("member of public- " + memberOfPublic);

            } else { // ensure sufficient details provided
                //System.out.println("customer not added. missing fields.");
            }
        } catch (InvalidCustomerException e) {
            System.out.println(e.getMessage());
        }

    }

    // method to populate the snack arraylist for a shop, using the format of the snacks.txt file provided; pass in each line of the text file as an array
    public void addSnack(String s[]) {
        try {
            if (s.length > 3) { // minimum amount of details present for snack object creation
                if (s[0].charAt(0) == 'F') { // assigns snack as food object
                    boolean isHot = s[2].equals("hot"); // if food is specified as hot, then isHot is true-otherwise false i.e. cold
                    Food food = new Food(s[0], s[1], isHot, Integer.parseInt(s[3]));
                    snacks.add(food);
                    //System.out.println("food item- " + food);
                } else if (s[0].charAt(0) == 'D') { // assigns snack as drink object
                    Drink.SugarContent sugarContent = Drink.SugarContent.valueOf(s[2].toUpperCase());
                    Drink drink = new Drink(s[0], s[1], sugarContent, Integer.parseInt(s[3]));
                    snacks.add(drink);
                    //System.out.println("drink item- " + drink);
                } else {
                    throw new InvalidSnackException("   -- Invalid snack ID. Must begin with F or D --");
                }
            } else { // ensure sufficient details provided
                //System.out.println("snack not added. missing fields.");
            }

        } catch (InvalidSnackException e) {
            System.out.println(e.getMessage());
        }
    }


    // method to process a purchase of a snack by passing in 2 arguments: snackID and customerID and simulate the transaction by charging the customer and adding to total sales
    public boolean processPurchase(String CustomerID, String SnackID) throws InsufficientBalanceException, InvalidSnackException, InvalidCustomerException {

        Customer customer = getCustomer(CustomerID); // use getCustomer() method to use the first argument passed in
        Snack snack = getSnack(SnackID); // use getCustomer() method to use the second argument passed in
        int t = customer.chargeAccount(snack); // calls chargeAccount() method to calculate and deduct the correct (possibly surcharged + discounted) price of the snack from customer balance
        turnover += t; // add transaction amount to turnover of shop
        System.out.println("± Purchase successful! Customer: " + customer.getCustomerID() + " was charged: " + t + "p " + " Remaining balance: " + customer.getCustomerBalance() + "p");

        return true;
    }


    // method to return the largest base price of a snack in each shop
    public int findLargestBasePrice() {
        int maxItem = 0;
        for (Snack s : snacks) {
            if (s.getSnackBasePrice() > maxItem) {
                maxItem = s.getSnackBasePrice(); /* iterate through all snacks + reassign each snack price as the max if it is larger than the previous in the snack collection
                                                    until there is no higher base price found */
            }
        }
        return maxItem;
    }


    // method to calculate and return median customer balance
    public int calculateMedianCustomerBalance() {
        ArrayList<Integer> balances = new ArrayList<>();

        for (Customer c : customers) {
            balances.add(c.getCustomerBalance()); // populate balance arraylist with all customer balances
        }

        Collections.sort(balances); // values need to be in ascending order to calculate median


        int median;
        int size = balances.size();
        if (size % 2 == 0) { // if even number of balances
            median = (balances.get(size / 2) + balances.get((size / 2) - 1)) / 2;
        } else { // if odd number of balances
            median = balances.get(size / 2);
        }
        return median;
    }


    // method to count and return the number of customers with a negative balance
    public int countNegativeAccounts() {
        int nAcc = 0;
        for (Customer c : customers) {
            if (c.getCustomerBalance() < 0) {
                nAcc++;
            }
        }
        return nAcc;
    }

    // method to process transactions listed in transactions.txt file; pass in each line of the file as an arraylist- easier than an array to manipulate each index of an arraylist
    public void processTransactions(ArrayList<String> tDetails) { // transaction details
        try {
            String action = tDetails.get(0); // follows format of txt file; first index specifies the type of transaction: add customer/ purchase/ add funds
            if (action.equals("PURCHASE")) { // purchase snack
                processPurchase(tDetails.get(1), tDetails.get(2)); // use processPurchase() method + pass in customerID, snackId
            } else if (action.equals("ADD_FUNDS")) { // add funds to customer account
                Customer c = getCustomer(tDetails.get(1)); // getCustomer() method to retrieve customer object
                c.addFunds(Integer.parseInt(tDetails.get(2)));
                System.out.println("± Funds successfully added to " + c.getCustomerID() + " updated balance: " + c.getCustomerBalance());
            } else if (action.equals("NEW_CUSTOMER")) { // add a new customer to customer list of that shop
                boolean duplicate = false; // assume there are no duplicate customer IDs
                for (Customer c : customers) { // check for duplicates
                    if (c.getCustomerID().equals(tDetails.get(1))) {
                        duplicate = true;
                        System.out.println("    -- Customer not added. This customer ID already exists in " + getShopName() + " --"); // if duplicate present; reject transaction
                    }
                }
                if (duplicate == false) { // no duplicate present
                    tDetails.remove(0); // remove 0th index i.e. action
                    int balance = Integer.parseInt(tDetails.get(tDetails.size() - 1)); // store specified initial balance of customer to be added
                    tDetails.remove(tDetails.size() - 1);     // re-position the details of the customer to match the format of the addCustomer() function
                    tDetails.add(2, String.valueOf(balance));         // format: ID, name, balance, (student/staff/public),(school)
                    String[] t = tDetails.toArray(new String[0]);  // read as an array of strings in addCustomer()
                    addCustomer(t);
                    System.out.println("± Customer successfully added!");
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//Unit Test
    public static void main(String[] args) throws InvalidCustomerException, InvalidSnackException {
        System.out.println("SnackShop.java Unit Test");

        SnackShop s = new SnackShop("SHOP");

        ArrayList<Customer> customers1 = s.customers;
        ArrayList<Snack> snacks1 = s.snacks;

            // populate customer + snack arraylists
            Customer c1 = new Customer("175454", "a",400); //create valid customer objects
            Customer c2 = new Customer("174454", "a",200);
            Customer c3 = new StudentCustomer("174464", "a",-100);

            customers1.add(c1);
            customers1.add(c2);
            customers1.add(c3);

            Food f1 = new Food("F/1583215", "a", true, 300); // create valid food object
            Drink f2 = new Drink("D/1583215", "a", Drink.SugarContent.HIGH, 100);


        snacks1.add(f1);
        snacks1.add(f2);

            try {
                System.out.println(s.getCustomer("174454")); // check getCustomer() method
                System.out.println(s.getSnack("F/1583215")); // check getSnack() method
                System.out.println(s.calculateMedianCustomerBalance()); // check medianCustomerBalance() method - should return 200
                System.out.println(s.countNegativeAccounts()); // check countNegativeAccounts() method - should return 1
                System.out.println(s.findLargestBasePrice()); // check findLargestBasePrice() method - should return 300
                s.processPurchase("175454", "F/1583215");  // check processPurchase() method
                System.out.println(s.getCustomer("175454").getCustomerBalance()); // should return 70 (400-330)
                System.out.println(s.getTurnover()); // should return 330
                s.processPurchase("175454", "F/1583215"); // check exception handling
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }



    }

    public String toString() { //override toString method-returns name of SnackShop object
        return "shop name: " + shopName + ", turnover: " + turnover;
    }

}