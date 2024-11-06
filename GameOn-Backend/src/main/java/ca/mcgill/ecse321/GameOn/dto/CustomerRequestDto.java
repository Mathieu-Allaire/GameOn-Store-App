package ca.mcgill.ecse321.GameOn.dto;
import java.sql.Date;

public class CustomerRequestDto {
    private String email;
    private String name;
    private String password;
    private int cardNum;
    private Date aCardExpiracyDate;
    private String billingAddress;

    public  String getEmail(){
        return email;
    }

    public  String getName(){
        return name;
    }

    public  String getPassword(){
        return password;
    }

    public  Integer getCardNumber(){
        return cardNum;
    }

    public  Date getExpiracyDate(){
        return aCardExpiracyDate;
    }

    public  String getBillingAddress(){
        return billingAddress;
    }

    
}
