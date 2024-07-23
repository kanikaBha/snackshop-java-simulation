package SnackShopCoursework;

/* file: Drink.java

    description: child class of Snack-inherits all allowed properties and methods + overrides abstract methods
                 Drink objects contain the base properties: ID, name, base price + additional property: sugarContent, to determine sugar content of the drink item
 */

public class Drink extends Snack{

    protected enum SugarContent{NONE, LOW, HIGH}; // additional Snack property to determine sugar content of drink; contributes to price calculation (enum used for limited categories)

    SugarContent theSugarContent;


    // parameterised Drink constructor- takes all base class and child class properties: ID, name, base price, sugar content (none/low/high)
    public Drink(String snackID, String snackName, SugarContent theSugarContent, int snackBasePrice) throws InvalidSnackException{
        //InvalidSnackException thrown when invalid/ missing snack ID + invalid negative snack base price (handled in base class constructor)
        super(snackID, snackName, snackBasePrice); // calls parent class Snack constructor for base properties

        if(snackID.charAt(0)!='D'){ //ensures SnackID correctly interprets snack as drink
            throw new InvalidSnackException("   -- Snack not added. Invalid snackID. Valid drink snack ID must begin with \'D\' --");
        }

        this.theSugarContent=theSugarContent;
    }

    //parameterised Drink constructor- takes all base class properties; called when sugar content not specified-default set to NONE
    public Drink(String snackID, String snackName, int snackBasePrice) throws InvalidSnackException{
        super(snackID, snackName, snackBasePrice); //calls parent class Snack constructor for base properties

        if(snackID.charAt(0)!='D'){ //ensures SnackID correctly interprets snack as drink
            throw new InvalidSnackException("   -- Snack not added. Invalid snackID. Valid drink snack ID must begin with \'D\' --");
        }

        this.theSugarContent=SugarContent.NONE;
    }


    //accessor and mutator methods
    public SugarContent getTheSugarContent() {
        return theSugarContent;
    }

    public void setTheSugarContent(SugarContent theSugarContent) {
        this.theSugarContent = theSugarContent;
    }

    public int calculatePrice(){ /*override abstract method in Snack class for calculating price of food item after adding surcharge to specific snack base prices
                                    surcharge calculated according to sugar content (base price unchanged if sugar content is zero) */
        int sugarTax=0;

       switch(getTheSugarContent()){

           case HIGH:
               sugarTax=24; // sugar tax price in pence i.e. 24 = 24p
               break;

           case LOW:
               sugarTax=18;
               break;

           case NONE:
               sugarTax=0;
       }

       int newPrice=getSnackBasePrice()+sugarTax; // returns new drink price; higher the sugar content: higher the surcharge
       return newPrice;
    }


    //Unit Test
    public static void main(String[] args) throws InvalidSnackException {
        System.out.println("Drink.java Unit Test");
        try {

            Drink d1 = new Drink("D/7154984", "a", SugarContent.HIGH, 300); // create valid Drink object
            System.out.println(d1.calculatePrice()); // check calculated price - should be 324 (300 + 24)

            Drink d2 = new Drink("D/71544", "a", SugarContent.HIGH, 300); // create invalid Drink object
        }
        catch(InvalidSnackException e){
            System.out.println (e.getMessage());
        }

        try {
            Drink d3 = new Drink("F/7154445", "a", SugarContent.HIGH, 300); // create invalid Drink object-Food
            }
        catch(InvalidSnackException e){
                System.out.println (e.getMessage());
        }

         try {
             Drink d4 = new Drink("D/7154444", "a", 300); // test default sugar content constructor
             System.out.println(d4);

         }
         catch(InvalidSnackException e){
             System.out.println (e.getMessage());
         }

         try {
             Food d5 = new Food("D/7154444", "a", true, -10); // create invalid Food object-negative price
         }
         catch(InvalidSnackException e){
             System.out.println (e.getMessage());
         }

    }


    @Override
    public String toString() { //override toString method-returns complete Snack-Drink object
        return super.toString() + ", sugar content: " + theSugarContent;
    }
}
