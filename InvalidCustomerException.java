package SnackShopCoursework;

public class InvalidCustomerException extends Exception{

    public InvalidCustomerException(String message){
        super(message);
    }
    void showError(){
        System.out.println("invalid customer");
    }
}
