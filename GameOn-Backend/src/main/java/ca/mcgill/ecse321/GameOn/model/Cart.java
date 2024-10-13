/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.GameOn.model;
import java.sql.Date;
import java.util.*;

import jakarta.persistence.*;

// line 75 "GameOn.ump"
@Entity
public class Cart
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cart Attributes
  private Date dateAdded;

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private int id;

  //Cart Associations
  @OneToOne(mappedBy = "cart")
  private Order order;
  @OneToMany
  private List<SpecificGame> specificGame;

  //------------------------
  // CONSTRUCTOR
  //------------------------


  public Cart(Date aDateAdded, int aId, Order aOrder)
  {
    dateAdded = aDateAdded;
    id = aId;
    if (aOrder == null || aOrder.getCart() != null)
    {
      throw new RuntimeException("Unable to create Cart due to aOrder. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }

    order = aOrder;

    specificGame = new ArrayList<SpecificGame>();
  }

  public Cart(Date aDateAdded, int aId, int aIdForOrder, Date aPurchaseDateForOrder, Customer aOrderCustomerForOrder)
  {
    dateAdded = aDateAdded;
    id = aId;
    order = new Order(aIdForOrder, aPurchaseDateForOrder, this, aOrderCustomerForOrder);
    specificGame = new ArrayList<SpecificGame>();
  }

  public Cart(Date aDateAdded, int aId)
  {
    dateAdded = aDateAdded;
    id = aId;
    specificGame = new ArrayList<SpecificGame>();
  }

  protected Cart()
  {

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

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public Date getDateAdded()
  {
    return dateAdded;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Order getOrder()
  {
    return order;
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
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dateAdded" + "=" + (getDateAdded() != null ? !getDateAdded().equals(this)  ? getDateAdded().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null");
  }
}
