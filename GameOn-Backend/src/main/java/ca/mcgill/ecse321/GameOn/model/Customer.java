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
  private List<Order> customerOrder;

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
    customerOrder = new ArrayList<Order>();
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
  

  public List<Order> getCustomerOrder()
  {
    List<Order> newCustomerOrder = Collections.unmodifiableList(customerOrder);
    return newCustomerOrder;
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
  public Order addCustomerOrder(int aId, Date aPurchaseDate, Cart aCart)
  {
    return new Order(aPurchaseDate, aCart, this);
  }

  public boolean addCustomerOrder(Order aCustomerOrder)
  {
    boolean wasAdded = false;
    if (customerOrder.contains(aCustomerOrder)) { return false; }
    Customer existingOrderCustomer = aCustomerOrder.getOrderCustomer();
    boolean isNewOrderCustomer = existingOrderCustomer != null && !this.equals(existingOrderCustomer);
    if (isNewOrderCustomer)
    {
      aCustomerOrder.setOrderCustomer(this);
    }
    else
    {
      customerOrder.add(aCustomerOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomerOrder(Order aCustomerOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aCustomerOrder, as it must always have a orderCustomer
    if (!this.equals(aCustomerOrder.getOrderCustomer()))
    {
      customerOrder.remove(aCustomerOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  
  /* Code from template association_AddManyToOne */
  public Review addCustomerReview(String aDescription, int aStars, int aLikes, int aDislikes, Manager aManager)
  {
    return new Review(aDescription, aStars, aLikes, aDislikes, this, aManager);
  }

  public boolean addCustomerReview(Review aCustomerReview)
  {
    boolean wasAdded = false;
    if (customerReview.contains(aCustomerReview)) { return false; }
    Customer existingReviewAuthor = aCustomerReview.getReviewAuthor();
    boolean isNewReviewAuthor = existingReviewAuthor != null && !this.equals(existingReviewAuthor);
    if (isNewReviewAuthor)
    {
      aCustomerReview.setReviewAuthor(this);
    }
    else
    {
      customerReview.add(aCustomerReview);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomerReview(Review aCustomerReview)
  {
    boolean wasRemoved = false;
    //Unable to remove aCustomerReview, as it must always have a reviewAuthor
    if (!this.equals(aCustomerReview.getReviewAuthor()))
    {
      customerReview.remove(aCustomerReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  
  

}