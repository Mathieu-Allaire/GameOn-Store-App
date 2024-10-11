/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/
package ca.mcgill.ecse321.GameOn.model;
import jakarta.persistence.*;
import java.util.*;

// line 70 "GameOn.ump"

@Entity
public class Category
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Category Attributes
  private String name;

  @Id
  @GeneratedValue
  private int id;

  //Category Associations
    
  

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Category(String aName)
  {
    name = aName;
   // id = aId;
    
  }

  protected Category(){

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
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

  public String getName()
  {
    return name;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetMany */
 
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  

 

  /* Code from template association_AddIndexControlFunctions */
  

  

  


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "id" + ":" + getId()+ "]";
  }
}
