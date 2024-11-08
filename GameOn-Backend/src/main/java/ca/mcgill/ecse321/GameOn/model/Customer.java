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

  public boolean setCardNum(int aCardNum)
  {
    boolean wasSet = false;
    cardNum = aCardNum;
    wasSet = true;
    return wasSet;
  }

  public boolean setCardExpiryDate(Date aCardExpiryDate)
  {
    boolean wasSet = false;
    cardExpiryDate = aCardExpiryDate;
    wasSet = true;
    return wasSet;
  }

  public void setCart(Cart aCart) {cart = aCart;}
  public Cart getCart() {return cart;}
  public boolean hasCart(){return cart != null;}

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
  public WishlistLink getCustomerWish(int index)
  {
    WishlistLink aCustomerWish = CustomerWish.get(index);
    return aCustomerWish;
  }

  public List<WishlistLink> getCustomerWish()
  {
    List<WishlistLink> newCustomerWish = Collections.unmodifiableList(CustomerWish);
    return newCustomerWish;
  }

  public int numberOfCustomerWish()
  {
    int number = CustomerWish.size();
    return number;
  }

  public boolean hasCustomerWish()
  {
    boolean has = CustomerWish.size() > 0;
    return has;
  }
  public Cart getCart(){
    return cart;
  }

  public void setCart(Cart aCart){
    cart = aCart;
  }

  public int indexOfCustomerWish(WishlistLink aCustomerWish)
  {
    int index = CustomerWish.indexOf(aCustomerWish);
    return index;
  }
  /* Code from template association_GetMany */
  public Order getCustomerOrder(int index)
  {
    Order aCustomerOrder = customerOrder.get(index);
    return aCustomerOrder;
  }

  public List<Order> getCustomerOrder()
  {
    List<Order> newCustomerOrder = Collections.unmodifiableList(customerOrder);
    return newCustomerOrder;
  }

  public int numberOfCustomerOrder()
  {
    int number = customerOrder.size();
    return number;
  }

  public boolean hasCustomerOrder()
  {
    boolean has = customerOrder.size() > 0;
    return has;
  }

  public int indexOfCustomerOrder(Order aCustomerOrder)
  {
    int index = customerOrder.indexOf(aCustomerOrder);
    return index;
  }
  /* Code from template association_GetMany */
  public Review getCustomerReview(int index)
  {
    Review aCustomerReview = customerReview.get(index);
    return aCustomerReview;
  }

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

  public boolean hasCustomerReview()
  {
    boolean has = customerReview.size() > 0;
    return has;
  }

  public int indexOfCustomerReview(Review aCustomerReview)
  {
    int index = customerReview.indexOf(aCustomerReview);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomerWish()
  {
    return 0;
  }
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

  public boolean removeCustomerWish(WishlistLink aCustomerWish)
  {
    boolean wasRemoved = false;
    //Unable to remove aCustomerWish, as it must always have a CustomerWish
    if (!this.equals(aCustomerWish.getCustomerWish()))
    {
      CustomerWish.remove(aCustomerWish);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCustomerWishAt(WishlistLink aCustomerWish, int index)
  {  
    boolean wasAdded = false;
    if(addCustomerWish(aCustomerWish))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomerWish()) { index = numberOfCustomerWish() - 1; }
      CustomerWish.remove(aCustomerWish);
      CustomerWish.add(index, aCustomerWish);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerWishAt(WishlistLink aCustomerWish, int index)
  {
    boolean wasAdded = false;
    if(CustomerWish.contains(aCustomerWish))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomerWish()) { index = numberOfCustomerWish() - 1; }
      CustomerWish.remove(aCustomerWish);
      CustomerWish.add(index, aCustomerWish);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCustomerWishAt(aCustomerWish, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomerOrder()
  {
    return 0;
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
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCustomerOrderAt(Order aCustomerOrder, int index)
  {  
    boolean wasAdded = false;
    if(addCustomerOrder(aCustomerOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomerOrder()) { index = numberOfCustomerOrder() - 1; }
      customerOrder.remove(aCustomerOrder);
      customerOrder.add(index, aCustomerOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerOrderAt(Order aCustomerOrder, int index)
  {
    boolean wasAdded = false;
    if(customerOrder.contains(aCustomerOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomerOrder()) { index = numberOfCustomerOrder() - 1; }
      customerOrder.remove(aCustomerOrder);
      customerOrder.add(index, aCustomerOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCustomerOrderAt(aCustomerOrder, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomerReview()
  {
    return 0;
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
  public boolean addCustomerReviewAt(Review aCustomerReview, int index)
  {  
    boolean wasAdded = false;
    if(addCustomerReview(aCustomerReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomerReview()) { index = numberOfCustomerReview() - 1; }
      customerReview.remove(aCustomerReview);
      customerReview.add(index, aCustomerReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerReviewAt(Review aCustomerReview, int index)
  {
    boolean wasAdded = false;
    if(customerReview.contains(aCustomerReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomerReview()) { index = numberOfCustomerReview() - 1; }
      customerReview.remove(aCustomerReview);
      customerReview.add(index, aCustomerReview);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCustomerReviewAt(aCustomerReview, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=CustomerWish.size(); i > 0; i--)
    {
      WishlistLink aCustomerWish = CustomerWish.get(i - 1);
      aCustomerWish.delete();
    }
    for(int i=customerOrder.size(); i > 0; i--)
    {
      Order aCustomerOrder = customerOrder.get(i - 1);
      aCustomerOrder.delete();
    }
    for(int i=customerReview.size(); i > 0; i--)
    {
      Review aCustomerReview = customerReview.get(i - 1);
      aCustomerReview.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "cardNum" + ":" + getCardNum()+ "," +
            "billingAddress" + ":" + getBillingAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "cardExpiryDate" + "=" + (getCardExpiryDate() != null ? !getCardExpiryDate().equals(this)  ? getCardExpiryDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}