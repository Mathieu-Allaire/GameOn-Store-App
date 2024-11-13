package ca.mcgill.ecse321.GameOn.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

public class CustomerRequestDto {
    @NotBlank(message = "Email address is required.")
    @Email(message = "Invalid email address.")
    private String email;
    @NotBlank(message = "Name is required.")
    private String name;
    @NotBlank(message = "password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;
    @Min(value = 1, message = "Card number must be greater than 0.")
    private Integer cardNum;
    @NotNull(message = "Expiracy date is required.")
    private Date aCardExpiracyDate;
    @NotBlank(message = "BillingAddress is required.")
    private String billingAddress;

    public CustomerRequestDto(String email, String name, String password, Integer cardNum, Date cardExpiracyDate, String billingAddress ){
        this.email = email;
        this.name = name;
        this.password = password;
        this.cardNum = cardNum;
        this.aCardExpiracyDate = cardExpiracyDate;
        this.billingAddress = billingAddress;
    }

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

    public Integer getCardNumber() {
        return cardNum;
    }

    public void setCardNumber(Integer cardNum) {
        this.cardNum = cardNum;
    }

    public Date getExpiracyDate() {
        return aCardExpiracyDate;
    }

    public void setExpiracyDate(Date aCardExpiracyDate) {
        this.aCardExpiracyDate = aCardExpiracyDate;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
}
