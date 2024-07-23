package SnackShopCoursework;

/* file: StudentCustomer.java

        description: child class of Customer-inherits all allowed properties and methods + overrides relevant methods
                     StudentCustomer objects contain the base properties: ID, name, balance + no additional properties

*/

public class StudentCustomer extends Customer{

    // parameterised StudentCustomer constructor- takes base class and child class properties (no additional properties specified): ID, name; called when initial balance not specified (default=0p)
    public StudentCustomer(String customerID, String customerName) throws InvalidCustomerException {
        // InvalidCustomerException thrown when invalid/ missing Customer ID
        super(customerID, customerName); // calls parent class Customer constructor for base properties when only 2 arguments passed in-sets default balance to 0
    }

    // parameterised StudentCustomer constructor- takes all base class and child class properties (no additional properties specified): ID, name, balance
    public StudentCustomer(String customerID, String customerName, int studentCustomerBalance) throws InvalidCustomerException{
        // InvalidCustomerException thrown when invalid/ missing Customer ID- but for students the negative balance exception is redefined using overdraft allowance
        super(customerID, customerName); // calls parent class Customer constructor for base properties- 3 arguments passed in but different requirements for third parameter: balance
        this.setCustomerBalance(studentCustomerBalance);

        if(studentCustomerBalance < overdraft){ // allows a specified overdraft for students
            throw new InvalidCustomerException("    -- Customer not added. Invalid balance. Customer balance cannot be less than negative £5 --");
        }
    }

    private static final int overdraft= -500; // -500 overdraft discount for students
    private static double getDiscountAmount(){ // method to return a flat discount of 5% for all students
        return 5.0;
    }


    public int chargeAccount(Snack snack) throws InsufficientBalanceException{ /* overrides method which takes specified snack object as argument to charge customer the price of that snack
                                                                                    - allows for implementation of allowed overdraft and student discount*/

        int price= snack.calculatePrice(); // calls calculatePrice() method to calculate snack price + any relevant surcharges
        double studentDiscount=getDiscountAmount();

        int studentPrice= (int)Math.ceil(price-(price*studentDiscount/100)); // calculates price that students will be charged after relevant food surcharges, student discount; rounded up to nearest penny

        if(getCustomerBalance()-studentPrice < overdraft){ // accounts for overdraft allowance when checking for sufficient funds for transaction
            throw new InsufficientBalanceException("    -- Insufficient funds in customer account. Current balance: " + getCustomerBalance() + " Price: " + studentPrice + " --");
        }

        setCustomerBalance(getCustomerBalance() - studentPrice); // deduct calculated price from customer balance
        return studentPrice;

    }

//Unit Test
    public static void main(String[] args) throws InvalidSnackException, InvalidCustomerException, InsufficientBalanceException {
        System.out.println("StudentCustomer.java Unit Test");
        try {
            StudentCustomer c1 = new StudentCustomer("174454", "a",500); // create valid Customer object
            Food f1 = new Food("F/1583215", "a", false, 200);
            System.out.println(c1.chargeAccount(f1)); //check chargeAccount() method -- should return 190 (5% discount)
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {
            StudentCustomer c2 = new StudentCustomer("174534", "a"); // check default balance constructor
            System.out.println(c2);

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {
            StudentCustomer c3 = new StudentCustomer("17434", "a"); // check exception inheritance from parent constructor
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {
            StudentCustomer c4 = new StudentCustomer("174434", "a", 20);
            Food f1 = new Food("F/1583215", "a", false, 200);
            System.out.println(c4.chargeAccount(f1)); //check if overdraft is accounted for, should return 190 + should not throw exception
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {
            StudentCustomer c4 = new StudentCustomer("174434", "a", -350); // check exception inheritance from parent constructor
            Food f1 = new Food("F/1583215", "a", false, 200);
            System.out.println(c4.chargeAccount(f1)); //check insufficient balance exception with overdraft accounted for
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public String toString() { //override toString method-returns complete StudentCustomer object
        return super.toString() + ", customer type: Student";
    }
}

