package SnackShopCoursework;

/* file: StaffCustomer.java

        description: child class of Customer-inherits all allowed properties and methods + overrides relevant methods
                     StaffCustomer objects contain the base properties: ID, name, balance + additional property: school, to determine the school that the staff is a member of

*/

public class StaffCustomer extends Customer {

    protected enum School{CMP, BIO, MTH, OTHER}; // additional property to determine the which school the staff is a member of; contributes to price calculation (enum used for limited categories)

    private School school;

    // parameterised StaffCustomer constructor- takes base class and child class properties: ID, name, school
    public StaffCustomer(String customerID, String customerName, int customerInitialBalance, School school) throws InvalidCustomerException {
        // InvalidCustomerException thrown when invalid/ missing Customer ID + invalid negative balance
        super(customerID, customerName, customerInitialBalance); // calls parent class Customer constructor for base properties
        this.school=school;
    }


    // parameterised StaffCustomer constructor- takes base class and child class properties: ID, name, school; called when initial balance not specified (default=0p)
    public StaffCustomer(String customerID, String customerName, School school) throws InvalidCustomerException {
        // InvalidCustomerException thrown when invalid/ missing Customer ID + negative balance

        super(customerID, customerName);
        this.school=school;
        this.setCustomerBalance(0);
    }


    //accessor and mutator methods
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public int chargeAccount(Snack snack) throws InsufficientBalanceException{  /* overrides method which takes specified snack object as argument to charge customer the price of that snack
                                                                                    - allows for implementation of staff discount where applicable*/

        int price= snack.calculatePrice(); // calls calculatePrice() method to calculate snack price + any relevant surcharges
        double staffDiscount=0.0;

        switch(getSchool()){

            case CMP:
             staffDiscount=10; // staff discount as percentage i.e. 10 = 10%
             break;

            case MTH, BIO:
                staffDiscount=2;
                break;

            case OTHER:
                staffDiscount=0;
                break;

        }

        int staffPrice= (int) Math.ceil(price-(price*staffDiscount/100)); // round up price to nearest penny

        if(staffPrice > getCustomerBalance()){ // ensure staff has sufficient funds for purchase transaction to be successful
            throw new InsufficientBalanceException("    -- Insufficient funds in customer account. Current balance: " + getCustomerBalance() + "p" + " Price: " + staffPrice + "p" + " --" );
        }

        setCustomerBalance(getCustomerBalance() - staffPrice); // deduct calculated price from customer balance

        return staffPrice;

    }

    //Unit Test
    public static void main(String[] args) throws InvalidSnackException, InvalidCustomerException, InsufficientBalanceException {
        System.out.println("StaffCustomer.java Unit Test");
        try {
            StaffCustomer c1 = new StaffCustomer("174454", "a",300, School.CMP); // create valid Customer object
            Food f1 = new Food("F/1583215", "a", false, 300);
            System.out.println(c1.chargeAccount(f1)); //check chargeAccount() method -- should return 270 (10% discount)
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {
            StaffCustomer c2 = new StaffCustomer("174534", "a", School.BIO); // check default balance constructor
            System.out.println(c2);

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {
            StaffCustomer c3 = new StaffCustomer("17434", "a", School.BIO); // check exception inheritance from parent constructor
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

}

    @Override public String toString() { // override toString method-returns complete StaffCustomer object
        return super.toString() + ", customer type: staff" + ", school: " + school;
    }
}
