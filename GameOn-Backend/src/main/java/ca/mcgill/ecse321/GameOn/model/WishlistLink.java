/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 87 "GameOn.ump"
public class WishlistLink
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WishlistLink Associations

    @ManyToOne //WishList Link --> Game  
  private Game wishlistGames;

    // N/A WishList --> WishList Link
  private Wishlist wishlist;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WishlistLink(Game aWishlistGames, Wishlist aWishlist)
  {
    boolean didAddWishlistGames = setWishlistGames(aWishlistGames);
    if (!didAddWishlistGames)
    {
      throw new RuntimeException("Unable to create wishlistlink due to wishlistGames. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddWishlist = setWishlist(aWishlist);
    if (!didAddWishlist)
    {
      throw new RuntimeException("Unable to create wishlistLink due to wishlist. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Game getWishlistGames()
  {
    return wishlistGames;
  }
  /* Code from template association_GetOne */
  public Wishlist getWishlist()
  {
    return wishlist;
  }
  /* Code from template association_SetOneToMany */
  public boolean setWishlistGames(Game aWishlistGames)
  {
    boolean wasSet = false;
    if (aWishlistGames == null)
    {
      return wasSet;
    }

    Game existingWishlistGames = wishlistGames;
    wishlistGames = aWishlistGames;
    if (existingWishlistGames != null && !existingWishlistGames.equals(aWishlistGames))
    {
      existingWishlistGames.removeWishlistlink(this);
    }
    wishlistGames.addWishlistlink(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setWishlist(Wishlist aWishlist)
  {
    boolean wasSet = false;
    if (aWishlist == null)
    {
      return wasSet;
    }

    Wishlist existingWishlist = wishlist;
    wishlist = aWishlist;
    if (existingWishlist != null && !existingWishlist.equals(aWishlist))
    {
      existingWishlist.removeWishlistLink(this);
    }
    wishlist.addWishlistLink(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Game placeholderWishlistGames = wishlistGames;
    this.wishlistGames = null;
    if(placeholderWishlistGames != null)
    {
      placeholderWishlistGames.removeWishlistlink(this);
    }
    Wishlist placeholderWishlist = wishlist;
    this.wishlist = null;
    if(placeholderWishlist != null)
    {
      placeholderWishlist.removeWishlistLink(this);
    }
  }

}
