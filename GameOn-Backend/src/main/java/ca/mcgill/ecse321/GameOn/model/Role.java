/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/
package ca.mcgill.ecse321.GameOn.model;
import jakarta.persistence.*;
// line 5 "GameOn.ump"

@MappedSuperclass
public abstract class Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  public Role()
  {}

     // Getters and setters
  public Long getId() {
      return id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {}

}