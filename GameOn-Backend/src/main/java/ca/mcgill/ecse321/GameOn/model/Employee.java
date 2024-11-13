/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.GameOn.model;
import java.util.*;
import jakarta.persistence.*;
// line 1 "EmployeeState.ump"
// line 21 "GameOn.ump"

@Entity
public class Employee extends Staff
{


  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private boolean isEmployed;

  //Employee State Machines
  public enum EmployeeStatus { Employed, Unemployed }
  private EmployeeStatus employeeStatus;

  //Employee Associations
    //@OneToMany // Employee --> GameRequest
  @OneToMany(mappedBy = "requestCreator", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<GameRequest> gameRequest;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(boolean aIsEmployed)
  {
    super();
    isEmployed = aIsEmployed;
    gameRequest = new ArrayList<GameRequest>();
    setEmployeeStatus(EmployeeStatus.Employed);
  }

  protected Employee(){
    
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsEmployed(boolean aIsEmployed)
  {
    boolean wasSet = false;
    isEmployed = aIsEmployed;
    wasSet = true;
    return wasSet;
  }

  /**
   * 
   */
  public boolean getIsEmployed()
  {
    return isEmployed;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsEmployed()
  {
    return isEmployed;
  }

  public EmployeeStatus getEmployeeStatus()
  {
    return employeeStatus;
  }

  private void setEmployeeStatus(EmployeeStatus aEmployeeStatus)
  {
    employeeStatus = aEmployeeStatus;
  }


  public List<GameRequest> getGameRequest()
  {
    List<GameRequest> newGameRequest = Collections.unmodifiableList(gameRequest);
    return newGameRequest;
  }

  /* Code from template association_AddManyToOne */
  public GameRequest addGameRequest(RequestType aRequestType, Game aResquestedGame)
  {
    return new GameRequest(aRequestType, this, aResquestedGame);
  }

  public boolean addGameRequest(GameRequest aGameRequest)
  {
    boolean wasAdded = false;
    if (gameRequest.contains(aGameRequest)) { return false; }
    Employee existingRequestCreator = aGameRequest.getRequestCreator();
    boolean isNewRequestCreator = existingRequestCreator != null && !this.equals(existingRequestCreator);
    if (isNewRequestCreator)
    {
      aGameRequest.setRequestCreator(this);
    }
    else
    {
      gameRequest.add(aGameRequest);
    }
    wasAdded = true;
    return wasAdded;
  }
}
