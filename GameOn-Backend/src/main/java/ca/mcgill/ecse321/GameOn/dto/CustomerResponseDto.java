package ca.mcgill.ecse321.GameOn.dto;
import ca.mcgill.ecse321.GameOn.model.Customer;
import ca.mcgill.ecse321.GameOn.model.Person;

public class CustomerResponseDto {
    private String email;
    private String name;
    private String address;
    private int cartId;
    // Jackson needs a default constructor
    protected CustomerResponseDto(){
    }

    public CustomerResponseDto(Person person){
        this.email = person.getEmail();
        this.name = person.getName();
        Customer customer = (Customer) person.getRole(0);
        this.address = customer.getBillingAddress();
        this.cartId = customer.getCart().getId();
    }

    public  String getEmail(){
        return email;
    }

    public  String getName(){
        return name;
    }    

    public String getAddress() {return address;}
    public int getCartId() {return cartId;}
}

