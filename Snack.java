package SnackShopCoursework;

/* file: Snack.java

    description: abstract base class to model each Snack in a shop-extends to 2 child classes: Food and Drink into which all snacks must be separated
                 all snacks must have ID, name, base price
 */

public abstract class Snack {
    private String snackID;
    private String snackName;
    private int snackBasePrice;


    // parameterised Snack constructor- takes all class Snack properties: ID, name, base price
    public Snack(String snackID, String snackName, int snackBaseprice) throws InvalidSnackException {
        //InvalidSnackException thrown when invalid/ missing snack ID + invalid negative snack base price
        this.snackID = snackID;
        this.snackBasePrice = snackBaseprice;

        if (validSnackID() == false) { //calls method that checks SnackID format validity
            throw new InvalidSnackException("   -- Snack not recorded. Invalid snackID. A valid snack ID consists of a single letter (F or D) followed by a single forward slash (‘/’) and then 7 numeric characters --");
        }

        if (snackID == null || snackID.isEmpty()) { //SnackID required
            throw new InvalidSnackException("   -- Snack not recorded. No snackID provided. SnackID is required --");
        }

        if (snackBaseprice < 0) { //ensures snack base price is always positive
            throw new InvalidSnackException("   -- Snack not recorded. Invalid price. Valid price must be positive --");
        }

        this.snackName = snackName;
    }

    //accessor and mutator methods
    public String getSnackID() {
        return snackID;
    }

    public void setSnackID(String snackID) {
        this.snackID = snackID;
    }

    public String getSnackName() {
        return snackName;
    }

    public void setSnackName(String snackName) {
        this.snackName = snackName;
    }

    public int getSnackBasePrice() {
        return snackBasePrice;
    }

    public void setSnackBasePrice(int snackBasePrice) {
        this.snackBasePrice = snackBasePrice;
    }

    public abstract int calculatePrice(); // method to calculate price of snacks-abstract because snack can either be food or drink; each having different respective surcharges added to base price

    public boolean validSnackID() { //checks the format of the SnackID - returns true or false; checks length and character pattern

        if (getSnackID().length() != 9) {
            return false;
        }

        char firstIDChar = getSnackID().charAt(0);
        if (firstIDChar != 'F' && firstIDChar != 'D') { //SnackID must begin with either F or D to separate into food and drink
            return false;
        }

        if (getSnackID().charAt(1) != '/') { //second character must be '/'
            return false;
        }

        for (int i = 2; i < getSnackID().length(); i++) { // ensure last 7 characters are all digits
            char c = getSnackID().charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;

    }

    public String toString() { //override toString method-returns complete Snack object
        return "snack ID: " + snackID + ", snack name: " + snackName + ", snack base price: " + snackBasePrice;
    }
}

