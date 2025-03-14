/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


package ca.mcgill.ecse321.GameOn.model;
// line 40 "GameOn.ump"
import jakarta.persistence.*;
@Entity
public class GameRequest
{
  //------------------------
  // MEMBER VARIABLES
  //------------------------
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  //GameRequest Attributes
  private RequestType requestType;

  //GameRequest Associations

  @ManyToOne
  private Employee requestCreator;

  @OneToOne // GameRequest --> Game
  private Game resquestedGame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameRequest(RequestType aRequestType, Employee aRequestCreator, Game aResquestedGame)
  {
    requestType = aRequestType;
    boolean didAddRequestCreator = setRequestCreator(aRequestCreator);
    if (!didAddRequestCreator)
    {
      throw new RuntimeException("Unable to create gameRequest due to requestCreator. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setResquestedGame(aResquestedGame))
    {
      throw new RuntimeException("Unable to create GameRequest due to aResquestedGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  protected GameRequest()
  {
  }

  //------------------------
  // INTERFACE
  //------------------------
  

  public boolean setRequestType(RequestType aRequestType)
  {
    boolean wasSet = false;
    requestType = aRequestType;
    wasSet = true;
    return wasSet;
  }

  public int getId() {
    return id;
  }

  public RequestType getRequestType()
  {
    return requestType;
  }
  /* Code from template association_GetOne */
  public Employee getRequestCreator()
  {
    return requestCreator;
  }
  /* Code from template association_GetOne */
  public Game getRequestedGame()
  {
    return resquestedGame;
  }
  /* Code from template association_SetOneToMany */
  public boolean setRequestCreator(Employee aRequestCreator)
  {
    boolean wasSet = false;
    if (aRequestCreator == null)
    {
      return wasSet;
    }
    requestCreator = aRequestCreator;
    requestCreator.addGameRequest(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setResquestedGame(Game aNewResquestedGame)
  {
    boolean wasSet = false;
    if (aNewResquestedGame != null)
    {
      resquestedGame = aNewResquestedGame;
      wasSet = true;
    }
    return wasSet;
  }
}
