package SnackShopCoursework;

/* file: Food.java

    description: child class of Snack-inherits all allowed properties and methods + overrides abstract methods
                 Food objects contain the base properties: ID, name, base price + additional property: hotFood, to determine if food item is served hot or cold
 */


public class Food extends Snack{

    private boolean hotFood; //additional snack property to categorise into hot and cold food; contributes to price calculation

    //parameterised Food constructor- takes all base class and child class properties: ID, name, base price, hot food (true/false)
    public Food(String snackID, String snackName, boolean hotFood, int snackBasePrice) throws InvalidSnackException {
        //InvalidSnackException thrown when invalid/ missing snack ID + invalid negative snack base price (handled in base class constructor)
        super(snackID, snackName, snackBasePrice); //calls parent class Snack constructor for base properties

        if(snackID.charAt(0)!='F'){ //ensures SnackID correctly interprets snack as food
            throw new InvalidSnackException("   -- Snack not added due to invalid snackID. Valid food snack ID must begin with \'F\' --");
        }

        this.hotFood=hotFood;
    }
//accessor and mutator methods
    public boolean isHotFood() {
        return hotFood;
    }

    public void setHotFood(boolean hotFood) {
        this.hotFood=hotFood;
    }


    public static final double hotFoodSurchargePt=10.00; // 10% surcharge added to base price of all hot foot items

    @Override
    public int calculatePrice() { /*override abstract method in Snack class for calculating price of food item after adding surcharge to specific snack base prices
                                    if not hot food, base price remains unchanged*/
        if(isHotFood()==true){
            int newPrice= (int)Math.ceil((getSnackBasePrice()+(getSnackBasePrice()*hotFoodSurchargePt/100))); // round up surcharged price to nearest penny
            return newPrice;
        }
        else{
            return getSnackBasePrice(); // price remains as original base price
        }

    }

//Unit Test
     public static void main(String[] args) throws InvalidSnackException {
        System.out.println("Food.java Unit Test");
        try {

            Food f1 = new Food("F/1583215", "a", true, 300); // create valid Food object
            System.out.println(f1.calculatePrice()); //check the calculated price with surcharge - should be 330 (300*1.1)

            Food f2 = new Food("D/1583215", "a", true, 300); // create invalid Food object-Drink
        }
        catch(InvalidSnackException e){
            System.out.println (e.getMessage());
        }

        try {
            Food f3 = new Food("null", "a", true, 10); // create invalid Food object-null ID
            }
        catch(InvalidSnackException e){
                System.out.println (e.getMessage());
        }

         try {
             Food f4 = new Food("F/1583215", "a", true, -20); // create invalid Food object-negative price
         }
         catch(InvalidSnackException e){
             System.out.println (e.getMessage());
         }

         try {
             Food f5 = new Food("F/1515", "a", true, 40); // create invalid Food object
         }
         catch(InvalidSnackException e){
             System.out.println (e.getMessage());
         }

    }

    @Override
    public String toString() { //override toString method-returns complete Snack-Food object
        return super.toString() + ", hot food? " + hotFood;
    }
}
