/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321.GameOn.model;
import java.util.*;

import jakarta.persistence.*;

// line 2 "GameState.ump"
// line 22 "GameState.ump"
// line 60 "model.ump"
// line 189 "model.ump"

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
  @OneToMany
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
      throw new RuntimeException("Unable to create game due to category. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    wishlistlink = new ArrayList<WishlistLink>();
    reviews = new ArrayList<Review>();
    setGameStatus(GameStatus.Unavailable);
  }

  protected Game() {
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

  public GameStatus getGameStatus()
  {
    return gameStatus;
  }

  public void updateIsInStock() {
    isInStock = this.getQuantity() > 0;
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

  public void setGameStatus(GameStatus aGameStatus)
  {
    this.gameStatus = aGameStatus;
  }

  /* Code from template association_GetOne */
  public Category getCategory()
  {
    return category;
  }

  public List<WishlistLink> getWishlistlink()
  {
    List<WishlistLink> newWishlistlink = Collections.unmodifiableList(wishlistlink);
    return newWishlistlink;
  }

  public boolean hasWishlistlink()
  {
    boolean has = wishlistlink.size() > 0;
    return has;
  }

  public List<Review> getReviews()
  {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public boolean hasReviews()
  {
    boolean has = reviews.size() > 0;
    return has;
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

  /* Code from template association_AddManyToOne */
  public WishlistLink addWishlistlink(Customer aCustomerWish)
  {
    return new WishlistLink(this, aCustomerWish);
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

  /* Code from template association_AddUnidirectionalMany */
  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    reviews.add(aReview);
    wasAdded = true;
    return wasAdded;
  }

}