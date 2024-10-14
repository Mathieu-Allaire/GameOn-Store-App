/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321.GameOn.model;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.*;

// line 46 "model.ump"
// line 185 "model.ump"
@Entity
@Table(name = "\"order\"")
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
  @OneToMany
  private List<SpecificGame> orderGames;

  @OneToMany
  private Customer orderCustomer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(int aId, Date aPurchaseDate, Customer aOrderCustomer)
  {
    id = aId;
    purchaseDate = aPurchaseDate;
    orderGames = new ArrayList<SpecificGame>();
    boolean didAddOrderCustomer = setOrderCustomer(aOrderCustomer);
    if (!didAddOrderCustomer)
    {
      throw new RuntimeException("Unable to create customerOrder due to orderCustomer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  protected Order(){

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
            "  " + "orderCustomer = "+(getOrderCustomer()!=null?Integer.toHexString(System.identityHashCode(getOrderCustomer())):"null");
  }
}


