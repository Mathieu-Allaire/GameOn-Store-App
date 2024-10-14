/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/
package ca.mcgill.ecse321.GameOn.model;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.*;

// line 72 "model.ump"
// line 200 "model.ump"
@Entity
public class Cart {
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cart Attributes
  private Date dateAdded;

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int id;

  //Cart Associations
  @OneToOne
  private Order order;
  @OneToMany
  private List<SpecificGame> specificGame;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cart(Date aDateAdded, Order aOrder)
  {
    dateAdded = aDateAdded;
    if (!setOrder(aOrder))
    {
      throw new RuntimeException("Unable to create Cart due to aOrder. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    specificGame = new ArrayList<SpecificGame>();
  }

  protected Cart(){

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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setOrder(Order aNewOrder)
  {
    boolean wasSet = false;
    if (aNewOrder != null)
    {
      order = aNewOrder;
      wasSet = true;
    }
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
    order = null;
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