/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


package ca.mcgill.ecse321.GameOn.model;
// line 27 "GameOn.ump"
import jakarta.persistence.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Staff extends Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Staff()
  {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}