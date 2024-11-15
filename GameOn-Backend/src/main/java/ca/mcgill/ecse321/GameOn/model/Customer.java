/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.GameOn.model;
import java.sql.Date;
import java.util.*;

// line 31 "GameOn.ump"
import jakarta.persistence.*;
@Entity
public class Customer extends Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private int cardNum;
  private Date cardExpiryDate;
  private String billingAddress;

  //Customer Associations

  @OneToMany
  private List<OrderClass> customerOrderClass;

  @OneToMany(mappedBy = "CustomerWish" ,cascade = CascadeType.ALL, orphanRemoval = true)
  private List<WishlistLink> CustomerWish;

  @OneToMany
  private List<Review> customerReview;

  @OneToOne
  private Cart cart;

 //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(int aCardNum, Date aCardExpiryDate, String aBillingAddress, Cart aCart)
  {
    super();
    cardNum = aCardNum;
    cardExpiryDate = aCardExpiryDate;
    billingAddress = aBillingAddress;
    cart = aCart;
    CustomerWish = new ArrayList<WishlistLink>();
    customerOrderClass = new ArrayList<OrderClass>();
    customerReview = new ArrayList<Review>();
  }

  protected Customer() {
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void setCardNum(int aCardNum)
  {
    cardNum = aCardNum;
  }

  public void setCardExpiryDate(Date aCardExpiryDate)
  {
    cardExpiryDate = aCardExpiryDate;
  }

  

  public boolean setBillingAddress(String aBillingAddress)
  {
    boolean wasSet = false;
    billingAddress = aBillingAddress;
    wasSet = true;
    return wasSet;
  }

  public int getCardNum()
  {
    return cardNum;
  }

  public Date getCardExpiryDate()
  {
    return cardExpiryDate;
  }

  public String getBillingAddress()
  {
    return billingAddress;
  }
  /* Code from template association_GetMany */

  public List<WishlistLink> getCustomerWish()
  {
    List<WishlistLink> newCustomerWish = Collections.unmodifiableList(CustomerWish);
    return newCustomerWish;
  }

  public Cart getCart(){
    return cart;
  }

  public void setCart(Cart aCart){
    cart = aCart;
  }

  
  /* Code from template association_GetMany */
  

  public List<OrderClass> getCustomerOrder()
  {
    List<OrderClass> newCustomerOrderClass = Collections.unmodifiableList(customerOrderClass);
    return newCustomerOrderClass;
  }


  /* Code from template association_GetMany */
  

  public List<Review> getCustomerReview()
  {
    List<Review> newCustomerReview = Collections.unmodifiableList(customerReview);
    return newCustomerReview;
  }

  public int numberOfCustomerReview()
  {
    int number = customerReview.size();
    return number;
  }

  
  /* Code from template association_MinimumNumberOfMethod */
 
  /* Code from template association_AddManyToOne */
  public WishlistLink addCustomerWish(Game aWishlistGames)
  {
    return new WishlistLink(aWishlistGames, this);
  }

  public boolean addCustomerWish(WishlistLink aCustomerWish)
  {
    boolean wasAdded = false;
    if (CustomerWish.contains(aCustomerWish)) { return false; }
    Customer existingCustomerWish = aCustomerWish.getCustomerWish();
    boolean isNewCustomerWish = existingCustomerWish != null && !this.equals(existingCustomerWish);
    if (isNewCustomerWish)
    {
      aCustomerWish.setCustomerWish(this);
    }
    else
    {
      CustomerWish.add(aCustomerWish);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomerWish(WishlistLink aCustomerWish) {
    boolean wasRemoved = false;
    

    // Check if the CustomerWish list contains the wish
    if (this.CustomerWish != null) {
        // Removing the wish from the list
        wasRemoved = this.CustomerWish.remove(aCustomerWish);
    }

    return wasRemoved;
}

  
  /* Code from template association_AddManyToOne */
  public OrderClass addCustomerOrder(int aId, Date aPurchaseDate, Cart aCart)
  {
    return new OrderClass(aPurchaseDate, aCart, this);
  }

  public boolean addCustomerOrder(OrderClass aCustomerOrderClass)
  {
    boolean wasAdded = false;
    if (customerOrderClass.contains(aCustomerOrderClass)) { return false; }
    Customer existingOrderCustomer = aCustomerOrderClass.getOrderCustomer();
    boolean isNewOrderCustomer = existingOrderCustomer != null && !this.equals(existingOrderCustomer);
    if (isNewOrderCustomer)
    {
      aCustomerOrderClass.setOrderCustomer(this);
    }
    else
    {
      customerOrderClass.add(aCustomerOrderClass);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomerOrder(OrderClass aCustomerOrderClass)
  {
    boolean wasRemoved = false;
    //Unable to remove aCustomerOrder, as it must always have a orderCustomer
    if (!this.equals(aCustomerOrderClass.getOrderCustomer()))
    {
      customerOrderClass.remove(aCustomerOrderClass);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  
  /* Code from template association_AddManyToOne */
  public Review addCustomerReview(String aDescription, int aStars, int aLikes, int aDislikes, Manager aManager)
  {
    return new Review(aDescription, aStars, aLikes, aDislikes, this, aManager);
  }
  /* Code from template association_AddIndexControlFunctions */

  public boolean addCustomerReview(Review aCustomerReview)
  {
    boolean wasAdded = false;
    if (customerReview.contains(aCustomerReview)) { return false; }
    Customer existingReviewAuthor = aCustomerReview.getReviewAuthor();
    boolean isNewReviewAuthor = existingReviewAuthor != null && !this.equals(existingReviewAuthor);
    if (isNewReviewAuthor)
    {
      customerReview.add(aCustomerReview);
    }
    wasAdded = true;
    return wasAdded;
  }

}