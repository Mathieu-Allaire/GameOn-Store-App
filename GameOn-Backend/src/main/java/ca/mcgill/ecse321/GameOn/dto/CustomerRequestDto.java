package ca.mcgill.ecse321.GameOn.dto;

public class CustomerRequestDto {
    private String email;
    private String name;
    private String password;
    private String cardNum;
    private String aCardExpiracyDate;
    private String billingAddress;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNumber() {
        return cardNum;
    }

    public void setCardNumber(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getExpiracyDate() {
        return aCardExpiracyDate;
    }

    public void setExpiracyDate(String aCardExpiracyDate) {
        this.aCardExpiracyDate = aCardExpiracyDate;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
}