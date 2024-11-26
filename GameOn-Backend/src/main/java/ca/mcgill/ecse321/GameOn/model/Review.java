/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/
package ca.mcgill.ecse321.GameOn.model;


// line 91 "GameOn.ump"
import jakarta.persistence.*;
@Entity
public class Review
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private int id;

  private String description;
  private int stars;
  private int likes;
  private int dislikes;
  private String reply;


  //Review Associations
    @ManyToOne //Review --> Customer
  private Customer reviewAuthor;

    @ManyToOne //Review --> Manager
  private Manager manager;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(String aDescription, int aStars, int aLikes, int aDislikes, Customer aReviewAuthor, Manager aManager)
  {
    description = aDescription;
    stars = aStars;
    likes = aLikes;
    dislikes = aDislikes;
    reply = null;

    boolean didAddReviewAuthor = setReviewAuthor(aReviewAuthor);
    if (!didAddReviewAuthor)
    {
      throw new RuntimeException("Unable to create customerReview due to reviewAuthor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setManager(aManager))
    {
      throw new RuntimeException("Unable to create Review due to aManager. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  protected Review()
  {
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

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setStars(int aStars)
  {
    boolean wasSet = false;
    stars = aStars;
    wasSet = true;
    return wasSet;
  }

  public boolean setLikes(int aLikes)
  {
    boolean wasSet = false;
    likes = aLikes;
    wasSet = true;
    return wasSet;
  }

  public boolean setDislikes(int aDislikes)
  {
    boolean wasSet = false;
    dislikes = aDislikes;
    wasSet = true;
    return wasSet;
  }

  public boolean setReply(String aReply)
  {
    boolean wasSet = false;
    reply = aReply;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getDescription()
  {
    return description;
  }

  public int getStars()
  {
    return stars;
  }

  public int getLikes()
  {
    return likes;
  }

  public int getDislikes()
  {
    return dislikes;
  }
  /* Code from template association_GetOne */
  public Customer getReviewAuthor()
  {
    return reviewAuthor;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
  }
  public String getReply()
  {
    return reply;
  }
  /* Code from template association_SetOneToMany */
  public boolean setReviewAuthor(Customer aReviewAuthor)
  {
    boolean wasSet = false;
    if (aReviewAuthor == null)
    {
      return wasSet;
    }

    reviewAuthor = aReviewAuthor;
    reviewAuthor.addCustomerReview(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setManager(Manager aNewManager)
  {
    boolean wasSet = false;
    if (aNewManager != null)
    {
      manager = aNewManager;
      wasSet = true;
    }
    return wasSet;
  }

}
