package SnackShopCoursework;

/* file: Customer.java

    description: base class for Customer-extends to 2 child classes: StaffCustomer and StudentCustomer into which some, but not all customers will be categorised
                 distinction is made to provide certain university member benefits
                 all customers must have ID, name, balance
 */

public class Customer {

    private String customerID;
    private String customerName;
    private int customerBalance;


    // parameterised Customer constructor- takes all class Customer properties: ID, name, initial balance
    public Customer(String customerID, String customerName, int customerInitialBalance) throws InvalidCustomerException {
        // InvalidCustomerException thrown when invalid/ missing Customer ID + negative balance

        this.customerID= customerID;
        this.customerName = customerName;
        this.customerBalance = customerInitialBalance;

        if (customerID == null || customerID.isEmpty()) { // customer ID is required
            throw new InvalidCustomerException("    -- Customer not added. No customerID available. CustomerID is required --");
        }

        else if (validCustomerID() == false) { // calls method to check customer ID format validity
            throw new InvalidCustomerException("    -- Customer not added. Invalid customer ID. A valid customer ID should consist of a 6 digit alphanumeric identifier --");
        }

        else if (customerInitialBalance < 0) { // ensures initial balance for all customers is positive
            throw new InvalidCustomerException("    -- Customer not added. Invalid customer balance. Balance cannot be less than 0 pence --");
        }

    }

    // parameterised Customer constructor- takes ID, name; called when initial balance is not specified; default initial balance set to 0 pence
    public Customer(String customerID, String customerName) throws InvalidCustomerException  {

        this.customerID = customerID;
        this.customerName = customerName;
        this.customerBalance = 0;
        if (validCustomerID() == false) { // calls method to check customer ID format validity
            throw new InvalidCustomerException("    -- Customer not added. Invalid customer ID. A valid customer ID should consist of a 6 digit alphanumeric identifier --");
        }

    }


// accessor and mutator methods
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(int customerBalance) {
        this.customerBalance = customerBalance;
    }

    public boolean validCustomerID() { //checks the format of the CustomerID - returns true or false; checks length and character pattern

        if (getCustomerID().length() != 6) {
            return false;
        }

        for (int i = 0; i < getCustomerID().length(); i++) {
            char c = getCustomerID().charAt(i);
            if (!Character.isLetterOrDigit(c)) { // check all characters are letters or digits in ID
                return false;
            }
        }

        return true;
    }

    public int addFunds(int amount) { //method for customers to add funds and update their balance
        if (amount > 0) { // ensures amount being deposited is positive
            customerBalance= getCustomerBalance() + amount;
        }
        else {
            System.out.println("    -- Customer balance not changed. Amount entered must be above 0p --");
        }

        return amount;
    }

    public int chargeAccount(Snack snack) throws InsufficientBalanceException { // method which takes specified snack object as argument to charge customer the price of that snack

        int price= snack.calculatePrice(); // calls calculatePrice() method to calculate snack price + any relevant surcharges

        if(getCustomerBalance()<price){ // ensures customer has sufficient funds to be charged the price of the snack before the transaction (minimum balance allowed for regular customers = 0p )
            throw new InsufficientBalanceException("    -- Insufficient funds in customer account. Current balance: " + customerBalance + "p"+ " Price: " + price + "p" +" --");
        }

        customerBalance= getCustomerBalance()-price; //deduct calculated price from customer balance
        return price;

    }


//Unit Test
    public static void main(String[] args) throws InvalidSnackException, InvalidCustomerException, InsufficientBalanceException {
        System.out.println("Customer.java Unit Test");
        try {
            Customer c1 = new Customer("174454", "a",300); // create valid Customer object
            System.out.println(c1.addFunds(50)); // check addFunds() method
            System.out.println(c1.getCustomerBalance()); // check funds addition - should be 350
            Food f1 = new Food("F/1583215", "a", true, 300);
            System.out.println(c1.chargeAccount(f1)); //check chargeAccount() method -- should return 330
            System.out.println(c1.getCustomerBalance()); // check funds addition -- should become 20 (balance: 350, price: 330)
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {
            Customer c2 = new Customer("17454", "a",300); // create invalid Customer object
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {
            Customer c3 = new Customer("174564", "a"); // check default balance constructor
            System.out.println(c3); // check funds addition
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {
            Customer c4 = new Customer("175454", "a",350); // create valid Customer object
            Drink d1 = new Drink("D/1583215", "a", Drink.SugarContent.HIGH, 300);
            System.out.println(c4.chargeAccount(d1)); //check chargeAccount() method -- should return 324
            System.out.println(c4.chargeAccount(d1)); // check InsufficientBalance exception

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
}

    public String toString(){ //override toString method-returns complete Customer object
        return "customer ID: " + customerID + ", customer name: " + customerName + ", customer balance: " + customerBalance;
    }



}



