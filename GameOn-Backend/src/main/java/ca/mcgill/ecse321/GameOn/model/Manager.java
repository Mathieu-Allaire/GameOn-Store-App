/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 44 "GameOn.ump"

@Entity
public class Manager extends Staff
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum RequestType { Create, Archive }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
    @OneToMany //Manager --> Category (No need, why do we have this association?)
  private List<Category> categories;

    // N/A GameRequest --> Manager (don't need this, only one manager)    
  private List<GameRequest> gameRequest;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager()
  {
    super();
    categories = new ArrayList<Category>();
    gameRequest = new ArrayList<GameRequest>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Category getCategory(int index)
  {
    Category aCategory = categories.get(index);
    return aCategory;
  }

  public List<Category> getCategories()
  {
    List<Category> newCategories = Collections.unmodifiableList(categories);
    return newCategories;
  }

  public int numberOfCategories()
  {
    int number = categories.size();
    return number;
  }

  public boolean hasCategories()
  {
    boolean has = categories.size() > 0;
    return has;
  }

  public int indexOfCategory(Category aCategory)
  {
    int index = categories.indexOf(aCategory);
    return index;
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
  public static int minimumNumberOfCategories()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCategory(Category aCategory)
  {
    boolean wasAdded = false;
    if (categories.contains(aCategory)) { return false; }
    categories.add(aCategory);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCategory(Category aCategory)
  {
    boolean wasRemoved = false;
    if (categories.contains(aCategory))
    {
      categories.remove(aCategory);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCategoryAt(Category aCategory, int index)
  {  
    boolean wasAdded = false;
    if(addCategory(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCategoryAt(Category aCategory, int index)
  {
    boolean wasAdded = false;
    if(categories.contains(aCategory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCategories()) { index = numberOfCategories() - 1; }
      categories.remove(aCategory);
      categories.add(index, aCategory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCategoryAt(aCategory, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameRequest()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public GameRequest addGameRequest(RequestType aRequestType, Employee aRequestCreator, Game aResquestedGame)
  {
    return new GameRequest(aRequestType, aRequestCreator, aResquestedGame, this);
  }

  public boolean addGameRequest(GameRequest aGameRequest)
  {
    boolean wasAdded = false;
    if (gameRequest.contains(aGameRequest)) { return false; }
    Manager existingRequestApprover = aGameRequest.getRequestApprover();
    boolean isNewRequestApprover = existingRequestApprover != null && !this.equals(existingRequestApprover);
    if (isNewRequestApprover)
    {
      aGameRequest.setRequestApprover(this);
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
    //Unable to remove aGameRequest, as it must always have a requestApprover
    if (!this.equals(aGameRequest.getRequestApprover()))
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
    categories.clear();
    for(int i=gameRequest.size(); i > 0; i--)
    {
      GameRequest aGameRequest = gameRequest.get(i - 1);
      aGameRequest.delete();
    }
    super.delete();
  }

}
