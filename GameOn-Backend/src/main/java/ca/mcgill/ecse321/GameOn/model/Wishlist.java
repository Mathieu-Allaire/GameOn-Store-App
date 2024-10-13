/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/
package ca.mcgill.ecse321.GameOn.model;
import jakarta.persistence.*;
import java.util.*;
import java.sql.Date;

// line 82 "GameOn.ump"

@Entity
public class Wishlist
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wishlist Attributes
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private int id;

  //Wishlist Associations

    @OneToMany //Wishlist --> WishList Link
  private List<WishlistLink> wishlistLink;

    @OneToOne
  private Customer wishlistCustomer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wishlist(int aId, Customer aWishlistCustomer)
  {
    id = aId;
    wishlistLink = new ArrayList<WishlistLink>();
    if (aWishlistCustomer == null || aWishlistCustomer.getCustomerWishlist() != null)
    {
      throw new RuntimeException("Unable to create Wishlist due to aWishlistCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    wishlistCustomer = aWishlistCustomer;
  }

  public Wishlist(int aId, int aCardNumForWishlistCustomer, Date aCardExpiryDateForWishlistCustomer, String aBillingAddressForWishlistCustomer)
  {
    id = aId;
    wishlistLink = new ArrayList<WishlistLink>();
    wishlistCustomer = new Customer(aCardNumForWishlistCustomer, aCardExpiryDateForWishlistCustomer, aBillingAddressForWishlistCustomer, this);
  }

  protected Wishlist()
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

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetMany */
  public WishlistLink getWishlistLink(int index)
  {
    WishlistLink aWishlistLink = wishlistLink.get(index);
    return aWishlistLink;
  }

  public List<WishlistLink> getWishlistLink()
  {
    List<WishlistLink> newWishlistLink = Collections.unmodifiableList(wishlistLink);
    return newWishlistLink;
  }

  public int numberOfWishlistLink()
  {
    int number = wishlistLink.size();
    return number;
  }

  public boolean hasWishlistLink()
  {
    boolean has = wishlistLink.size() > 0;
    return has;
  }

  public int indexOfWishlistLink(WishlistLink aWishlistLink)
  {
    int index = wishlistLink.indexOf(aWishlistLink);
    return index;
  }
  /* Code from template association_GetOne */
  public Customer getWishlistCustomer()
  {
    return wishlistCustomer;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWishlistLink()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WishlistLink addWishlistLink(Game aWishlistGames)
  {
    return new WishlistLink(aWishlistGames, this);
  }

  public boolean addWishlistLink(WishlistLink aWishlistLink)
  {
    boolean wasAdded = false;
    if (wishlistLink.contains(aWishlistLink)) { return false; }
    Wishlist existingWishlist = aWishlistLink.getWishlist();
    boolean isNewWishlist = existingWishlist != null && !this.equals(existingWishlist);
    if (isNewWishlist)
    {
      aWishlistLink.setWishlist(this);
    }
    else
    {
      wishlistLink.add(aWishlistLink);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWishlistLink(WishlistLink aWishlistLink)
  {
    boolean wasRemoved = false;
    //Unable to remove aWishlistLink, as it must always have a wishlist
    if (!this.equals(aWishlistLink.getWishlist()))
    {
      wishlistLink.remove(aWishlistLink);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWishlistLinkAt(WishlistLink aWishlistLink, int index)
  {  
    boolean wasAdded = false;
    if(addWishlistLink(aWishlistLink))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWishlistLink()) { index = numberOfWishlistLink() - 1; }
      wishlistLink.remove(aWishlistLink);
      wishlistLink.add(index, aWishlistLink);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWishlistLinkAt(WishlistLink aWishlistLink, int index)
  {
    boolean wasAdded = false;
    if(wishlistLink.contains(aWishlistLink))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWishlistLink()) { index = numberOfWishlistLink() - 1; }
      wishlistLink.remove(aWishlistLink);
      wishlistLink.add(index, aWishlistLink);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWishlistLinkAt(aWishlistLink, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=wishlistLink.size(); i > 0; i--)
    {
      WishlistLink aWishlistLink = wishlistLink.get(i - 1);
      aWishlistLink.delete();
    }
    Customer existingWishlistCustomer = wishlistCustomer;
    wishlistCustomer = null;
    if (existingWishlistCustomer != null)
    {
      existingWishlistCustomer.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "wishlistCustomer = "+(getWishlistCustomer()!=null?Integer.toHexString(System.identityHashCode(getWishlistCustomer())):"null");
  }
}
