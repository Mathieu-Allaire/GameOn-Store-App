/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321.GameOn.model;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.*;

// line 48 "model.ump"
// line 179 "model.ump"

@Entity
@Table(name = "\"order\"")
public class Order
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private Date purchaseDate;

  //Order Associations
  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart;
  @OneToMany
  private List<SpecificGame> orderGames;
  @ManyToOne
  private Customer orderCustomer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(Date aPurchaseDate, Cart aCart, Customer aOrderCustomer)
  {
    purchaseDate = aPurchaseDate;
    boolean didAddCart = setCart(aCart);
    if (!didAddCart)
    {
      throw new RuntimeException("Unable to create order due to cart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orderGames = new ArrayList<SpecificGame>();
    boolean didAddOrderCustomer = setOrderCustomer(aOrderCustomer);
    if (!didAddOrderCustomer)
    {
      throw new RuntimeException("Unable to create customerOrder due to orderCustomer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  protected Order() {
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setPurchaseDate(Date aPurchaseDate)
  {
    boolean wasSet = false;
    purchaseDate = aPurchaseDate;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public Date getPurchaseDate()
  {
    return purchaseDate;
  }
  /* Code from template association_GetOne */
  public Cart getCart()
  {
    return cart;
  }
  /* Code from template association_GetMany */
  public SpecificGame getOrderGame(int index)
  {
    SpecificGame aOrderGame = orderGames.get(index);
    return aOrderGame;
  }

  public List<SpecificGame> getOrderGames()
  {
    List<SpecificGame> newOrderGames = Collections.unmodifiableList(orderGames);
    return newOrderGames;
  }

  public int numberOfOrderGames()
  {
    int number = orderGames.size();
    return number;
  }

  public boolean hasOrderGames()
  {
    boolean has = orderGames.size() > 0;
    return has;
  }

  public int indexOfOrderGame(SpecificGame aOrderGame)
  {
    int index = orderGames.indexOf(aOrderGame);
    return index;
  }
  /* Code from template association_GetOne */
  public Customer getOrderCustomer()
  {
    return orderCustomer;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setCart(Cart aNewCart)
  {
    boolean wasSet = false;
    if (aNewCart == null)
    {
      //Unable to setCart to null, as order must always be associated to a cart
      return wasSet;
    }
    
    Order existingOrder = aNewCart.getOrder();
    if (existingOrder != null && !equals(existingOrder))
    {
      //Unable to setCart, the current cart already has a order, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Cart anOldCart = cart;
    cart = aNewCart;
    cart.setOrder(this);

    if (anOldCart != null)
    {
      anOldCart.setOrder(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrderGames()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addOrderGame(SpecificGame aOrderGame)
  {
    boolean wasAdded = false;
    if (orderGames.contains(aOrderGame)) { return false; }
    orderGames.add(aOrderGame);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrderGame(SpecificGame aOrderGame)
  {
    boolean wasRemoved = false;
    if (orderGames.contains(aOrderGame))
    {
      orderGames.remove(aOrderGame);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderGameAt(SpecificGame aOrderGame, int index)
  {  
    boolean wasAdded = false;
    if(addOrderGame(aOrderGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderGames()) { index = numberOfOrderGames() - 1; }
      orderGames.remove(aOrderGame);
      orderGames.add(index, aOrderGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderGameAt(SpecificGame aOrderGame, int index)
  {
    boolean wasAdded = false;
    if(orderGames.contains(aOrderGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderGames()) { index = numberOfOrderGames() - 1; }
      orderGames.remove(aOrderGame);
      orderGames.add(index, aOrderGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderGameAt(aOrderGame, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setOrderCustomer(Customer aOrderCustomer)
  {
    boolean wasSet = false;
    if (aOrderCustomer == null)
    {
      return wasSet;
    }

    Customer existingOrderCustomer = orderCustomer;
    orderCustomer = aOrderCustomer;
    if (existingOrderCustomer != null && !existingOrderCustomer.equals(aOrderCustomer))
    {
      existingOrderCustomer.removeCustomerOrder(this);
    }
    orderCustomer.addCustomerOrder(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Cart existingCart = cart;
    cart = null;
    if (existingCart != null)
    {
      existingCart.setOrder(null);
    }
    orderGames.clear();
    Customer placeholderOrderCustomer = orderCustomer;
    this.orderCustomer = null;
    if(placeholderOrderCustomer != null)
    {
      placeholderOrderCustomer.removeCustomerOrder(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "orderCustomer = "+(getOrderCustomer()!=null?Integer.toHexString(System.identityHashCode(getOrderCustomer())):"null");
  }
}