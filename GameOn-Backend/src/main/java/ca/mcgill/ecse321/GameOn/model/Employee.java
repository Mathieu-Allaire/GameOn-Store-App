/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 1 "EmployeeState.ump"
// line 21 "GameOn.ump"

@Entity
public class Employee extends Staff
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum RequestType { Create, Archive }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private boolean isEmployed;

  //Employee State Machines
  public enum EmployeeStatus { Employed, Unemployed }
  private EmployeeStatus employeeStatus;

  //Employee Associations
    @OneToMany // Employee --> GameRequest
  private List<GameRequest> gameRequest;x

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

  public String getEmployeeStatusFullName()
  {
    String answer = employeeStatus.toString();
    return answer;
  }

  public EmployeeStatus getEmployeeStatus()
  {
    return employeeStatus;
  }

  public boolean kickOut()
  {
    boolean wasEventProcessed = false;
    
    EmployeeStatus aEmployeeStatus = employeeStatus;
    switch (aEmployeeStatus)
    {
      case Employed:
        setEmployeeStatus(EmployeeStatus.Unemployed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean hire()
  {
    boolean wasEventProcessed = false;
    
    EmployeeStatus aEmployeeStatus = employeeStatus;
    switch (aEmployeeStatus)
    {
      case Unemployed:
        setEmployeeStatus(EmployeeStatus.Employed);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setEmployeeStatus(EmployeeStatus aEmployeeStatus)
  {
    employeeStatus = aEmployeeStatus;
  }
  /* Code from template association_GetMany */
  public GameRequest getGameRequest(int index)
  {
    GameRequest aGameRequest = gameRequest.get(index);
    return aGameRequest;
  }

  public List<GameRequest> getGameRequest()
  {
    List<GameRequest> newGameRequest = Collections.unmodifiableList(gameRequest);
    return newGameRequest;
  }

  public int numberOfGameRequest()
  {
    int number = gameRequest.size();
    return number;
  }

  public boolean hasGameRequest()
  {
    boolean has = gameRequest.size() > 0;
    return has;
  }

  public int indexOfGameRequest(GameRequest aGameRequest)
  {
    int index = gameRequest.indexOf(aGameRequest);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameRequest()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public GameRequest addGameRequest(RequestType aRequestType, Game aResquestedGame, Manager aRequestApprover)
  {
    return new GameRequest(aRequestType, this, aResquestedGame, aRequestApprover);
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

  public boolean removeGameRequest(GameRequest aGameRequest)
  {
    boolean wasRemoved = false;
    //Unable to remove aGameRequest, as it must always have a requestCreator
    if (!this.equals(aGameRequest.getRequestCreator()))
    {
      gameRequest.remove(aGameRequest);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameRequestAt(GameRequest aGameRequest, int index)
  {  
    boolean wasAdded = false;
    if(addGameRequest(aGameRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameRequest()) { index = numberOfGameRequest() - 1; }
      gameRequest.remove(aGameRequest);
      gameRequest.add(index, aGameRequest);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameRequestAt(GameRequest aGameRequest, int index)
  {
    boolean wasAdded = false;
    if(gameRequest.contains(aGameRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameRequest()) { index = numberOfGameRequest() - 1; }
      gameRequest.remove(aGameRequest);
      gameRequest.add(index, aGameRequest);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameRequestAt(aGameRequest, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=gameRequest.size(); i > 0; i--)
    {
      GameRequest aGameRequest = gameRequest.get(i - 1);
      aGameRequest.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "isEmployed" + ":" + getIsEmployed()+ "]";
  }
}
