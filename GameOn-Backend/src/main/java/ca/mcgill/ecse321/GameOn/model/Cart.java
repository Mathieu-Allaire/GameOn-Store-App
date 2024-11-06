/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321.GameOn.model;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.*;

// line 74 "model.ump"
// line 199 "model.ump"
@Entity
public class Cart
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cart Attributes
  private Date dateAdded;

  //Cart Associations
  @OneToOne
  private Order order;
  @OneToMany
  private List<SpecificGame> specificGame;
  
  @OneToOne
  @Id
  private Customer customer;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cart(Customer aCustomer)
  {
    specificGame = new ArrayList<SpecificGame>();
    boolean didAddCustomer = setCustomer(aCustomer);

    if (!didAddCustomer) { 
      throw new RuntimeException("Unable to create cart due to customer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  protected Cart() {
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDateAdded(Date aDateAdded)
  {
    boolean wasSet = false;
    dateAdded = aDateAdded;
    wasSet = true;
    return wasSet;
  }
  public Customer getCustomer() {
    return customer;
  }
  public boolean setCustomer(Customer aNewCustomer)
{
    boolean wasSet = false;
    if (aNewCustomer == null)
    {
        // Unable to setCustomer to null, as cart must always be associated to a customer
        return wasSet;
    }

    Cart existingCart = aNewCustomer.getCart();
    if (existingCart != null && !equals(existingCart))
    {
        // Unable to setCustomer, the current customer already has a cart, which would be orphaned if it were re-assigned
        return wasSet;
    }

    Customer anOldCustomer = customer;
    customer = aNewCustomer;
    customer.setCart(this);

    if (anOldCustomer != null)
    {
        anOldCustomer.setCart(null);
    }
    wasSet = true;
    return wasSet;
}


  public Date getDateAdded()
  {
    return dateAdded;
  }


  /* Code from template association_GetOne */
  public Order getOrder()
  {
    return order;
  }

  public boolean hasOrder()
  {
    boolean has = order != null;
    return has;
  }
  /* Code from template association_GetMany */
  public SpecificGame getSpecificGame(int index)
  {
    SpecificGame aSpecificGame = specificGame.get(index);
    return aSpecificGame;
  }

  public List<SpecificGame> getSpecificGame()
  {
    List<SpecificGame> newSpecificGame = Collections.unmodifiableList(specificGame);
    return newSpecificGame;
  }

  public int numberOfSpecificGame()
  {
    int number = specificGame.size();
    return number;
  }

  public boolean hasSpecificGame()
  {
    boolean has = specificGame.size() > 0;
    return has;
  }

  public int indexOfSpecificGame(SpecificGame aSpecificGame)
  {
    int index = specificGame.indexOf(aSpecificGame);
    return index;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setOrder(Order aNewOrder)
  {
    boolean wasSet = false;
    if (order != null && !order.equals(aNewOrder) && equals(order.getCart()))
    {
      //Unable to setOrder, as existing order would become an orphan
      return wasSet;
    }

    order = aNewOrder;
    Cart anOldCart = aNewOrder != null ? aNewOrder.getCart() : null;

    if (!this.equals(anOldCart))
    {
      if (anOldCart != null)
      {
        anOldCart.order = null;
      }
      if (order != null)
      {
        order.setCart(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSpecificGame()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addSpecificGame(SpecificGame aSpecificGame)
  {
    boolean wasAdded = false;
    if (specificGame.contains(aSpecificGame)) { return false; }
    specificGame.add(aSpecificGame);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSpecificGame(SpecificGame aSpecificGame)
  {
    boolean wasRemoved = false;
    if (specificGame.contains(aSpecificGame))
    {
      specificGame.remove(aSpecificGame);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSpecificGameAt(SpecificGame aSpecificGame, int index)
  {  
    boolean wasAdded = false;
    if(addSpecificGame(aSpecificGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecificGame()) { index = numberOfSpecificGame() - 1; }
      specificGame.remove(aSpecificGame);
      specificGame.add(index, aSpecificGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSpecificGameAt(SpecificGame aSpecificGame, int index)
  {
    boolean wasAdded = false;
    if(specificGame.contains(aSpecificGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSpecificGame()) { index = numberOfSpecificGame() - 1; }
      specificGame.remove(aSpecificGame);
      specificGame.add(index, aSpecificGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSpecificGameAt(aSpecificGame, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Order existingOrder = order;
    order = null;
    if (existingOrder != null)
    {
      existingOrder.delete();
    }
    specificGame.clear();
    Customer existingCustomer = customer;
    customer = null;
    if (existingCustomer != null)
    {
      existingCustomer.setCart(null);
    }
  }


  public String toString()
  {
    return super.toString() + System.getProperties().getProperty("line.separator") +
            "  " + "dateAdded" + "=" + (getDateAdded() != null ? !getDateAdded().equals(this)  ? getDateAdded().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null");
  }
}