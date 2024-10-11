/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/
package ca.mcgill.ecse321.GameOn.model;
import jakarta.persistence.*;
import java.util.*;

// line 2 "GameState.ump"
// line 61 "GameOn.ump"

@Entity
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private String picture;
  @Id
  private String name;
  private String description;
  private int price;
  private int quantity;

  private boolean isInStock;

  //Game State Machines
  public enum GameStatus { Available, OutOfStock, Unavailable }
  private GameStatus gameStatus;

  //Game Associations

    @ManyToOne
  private Category category;

    @OneToMany
  private List<WishlistLink> wishlistlink;

    @OneToMany //Game --> Review
  private List<Review> reviews;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(String aPicture, String aName, String aDescription, int aPrice, int aQuantity, Category aCategory)
  {
    picture = aPicture;
    name = aName;
    description = aDescription;
    price = aPrice;
    quantity = aQuantity;
    boolean didAddCategory = setCategory(aCategory);
    if (!didAddCategory)
    {
      throw new RuntimeException("Unable to create game due to category. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    wishlistlink = new ArrayList<WishlistLink>();
    reviews = new ArrayList<Review>();
    setGameStatus(GameStatus.Available);
  }

  protected Game(){

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPicture(String aPicture)
  {
    boolean wasSet = false;
    picture = aPicture;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(int aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public String getPicture()
  {
    return picture;
  }

  /**
   * Pk
   */
  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public int getPrice()
  {
    return price;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public String getGameStatusFullName()
  {
    String answer = gameStatus.toString();
    return answer;
  }

  public GameStatus getGameStatus()
  {
    return gameStatus;
  }

  private boolean __autotransition7__()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case Available:
        if (!isInStock)
        {
          setGameStatus(GameStatus.OutOfStock);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean setUnavailable()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case Available:
        setGameStatus(GameStatus.Unavailable);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition8__()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case OutOfStock:
        if (isInStock)
        {
          setGameStatus(GameStatus.Available);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean setAvailable()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case Unavailable:
        setGameStatus(GameStatus.Available);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setGameStatus(GameStatus aGameStatus)
  {
    gameStatus = aGameStatus;

    // entry actions and do activities
    switch(gameStatus)
    {
      case Available:
        __autotransition7__();
        break;
      case OutOfStock:
        __autotransition8__();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Category getCategory()
  {
    return category;
  }
  /* Code from template association_GetMany */
  public WishlistLink getWishlistlink(int index)
  {
    WishlistLink aWishlistlink = wishlistlink.get(index);
    return aWishlistlink;
  }

  public List<WishlistLink> getWishlistlink()
  {
    List<WishlistLink> newWishlistlink = Collections.unmodifiableList(wishlistlink);
    return newWishlistlink;
  }

  public int numberOfWishlistlink()
  {
    int number = wishlistlink.size();
    return number;
  }

  public boolean hasWishlistlink()
  {
    boolean has = wishlistlink.size() > 0;
    return has;
  }

  public int indexOfWishlistlink(WishlistLink aWishlistlink)
  {
    int index = wishlistlink.indexOf(aWishlistlink);
    return index;
  }
  /* Code from template association_GetMany */
  public Review getReview(int index)
  {
    Review aReview = reviews.get(index);
    return aReview;
  }

  public List<Review> getReviews()
  {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews()
  {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews()
  {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Review aReview)
  {
    int index = reviews.indexOf(aReview);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCategory(Category aCategory)
  {
    boolean wasSet = false;
    if (aCategory == null)
    {
      return wasSet;
    }

    Category existingCategory = category;
    category = aCategory;
    if (existingCategory != null && !existingCategory.equals(aCategory))
    {
      existingCategory.removeGame(this);
    }
    category.addGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWishlistlink()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WishlistLink addWishlistlink(Wishlist aWishlist)
  {
    return new WishlistLink(this, aWishlist);
  }

  public boolean addWishlistlink(WishlistLink aWishlistlink)
  {
    boolean wasAdded = false;
    if (wishlistlink.contains(aWishlistlink)) { return false; }
    Game existingWishlistGames = aWishlistlink.getWishlistGames();
    boolean isNewWishlistGames = existingWishlistGames != null && !this.equals(existingWishlistGames);
    if (isNewWishlistGames)
    {
      aWishlistlink.setWishlistGames(this);
    }
    else
    {
      wishlistlink.add(aWishlistlink);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWishlistlink(WishlistLink aWishlistlink)
  {
    boolean wasRemoved = false;
    //Unable to remove aWishlistlink, as it must always have a wishlistGames
    if (!this.equals(aWishlistlink.getWishlistGames()))
    {
      wishlistlink.remove(aWishlistlink);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWishlistlinkAt(WishlistLink aWishlistlink, int index)
  {  
    boolean wasAdded = false;
    if(addWishlistlink(aWishlistlink))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWishlistlink()) { index = numberOfWishlistlink() - 1; }
      wishlistlink.remove(aWishlistlink);
      wishlistlink.add(index, aWishlistlink);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWishlistlinkAt(WishlistLink aWishlistlink, int index)
  {
    boolean wasAdded = false;
    if(wishlistlink.contains(aWishlistlink))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWishlistlink()) { index = numberOfWishlistlink() - 1; }
      wishlistlink.remove(aWishlistlink);
      wishlistlink.add(index, aWishlistlink);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWishlistlinkAt(aWishlistlink, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    reviews.add(aReview);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview)
  {
    boolean wasRemoved = false;
    if (reviews.contains(aReview))
    {
      reviews.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Review aReview, int index)
  {  
    boolean wasAdded = false;
    if(addReview(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Review aReview, int index)
  {
    boolean wasAdded = false;
    if(reviews.contains(aReview))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Category placeholderCategory = category;
    this.category = null;
    if(placeholderCategory != null)
    {
      placeholderCategory.removeGame(this);
    }
    for(int i=wishlistlink.size(); i > 0; i--)
    {
      WishlistLink aWishlistlink = wishlistlink.get(i - 1);
      aWishlistlink.delete();
    }
    reviews.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "picture" + ":" + getPicture()+ "," +
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "price" + ":" + getPrice()+ "," +
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "category = "+(getCategory()!=null?Integer.toHexString(System.identityHashCode(getCategory())):"null");
  }
}
