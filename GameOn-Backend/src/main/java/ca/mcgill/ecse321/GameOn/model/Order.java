/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

import jakarta.persistence.*;
import java.sql.Date;
import java.util.*;

// line 49 "GameOn.ump"
public class Order
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private int id;

  private Date purchaseDate;

  //Order Associations
  private Cart cart;
  private List<SpecificGame> orderGames;


    @OneToOne

  private Customer orderCustomer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(int aId, Date aPurchaseDate, Cart aCart, Customer aOrderCustomer)
  {
    id = aId;
    purchaseDate = aPurchaseDate;
    if (aCart == null || aCart.getOrder() != null)
    {
      throw new RuntimeException("Unable to create Order due to aCart. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cart = aCart;
    orderGames = new ArrayList<SpecificGame>();
    boolean didAddOrderCustomer = setOrderCustomer(aOrderCustomer);
    if (!didAddOrderCustomer)
    {
      throw new RuntimeException("Unable to create customerOrder due to orderCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Order(int aId, Date aPurchaseDate, Date aDateAddedForCart, int aIdForCart, Customer aOrderCustomer)
  {
    id = aId;
    purchaseDate = aPurchaseDate;
    cart = new Cart(aDateAddedForCart, aIdForCart, this);
    orderGames = new ArrayList<SpecificGame>();
    boolean didAddOrderCustomer = setOrderCustomer(aOrderCustomer);
    if (!didAddOrderCustomer)
    {
      throw new RuntimeException("Unable to create customerOrder due to orderCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
      existingCart.delete();
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