/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 55 "GameOn.ump"

import jakarta.persistence.*;
@Entity
public class SpecificGame
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SpecificGame Attributes
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private int id;

  //SpecificGame Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SpecificGame(int aId, Game aGame)
  {
    id = aId;
    if (!setGame(aGame))
    {
      throw new RuntimeException("Unable to create SpecificGame due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setGame(Game aNewGame)
  {
    boolean wasSet = false;
    if (aNewGame != null)
    {
      game = aNewGame;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    game = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}