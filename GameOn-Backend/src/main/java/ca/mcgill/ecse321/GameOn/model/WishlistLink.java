/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/
package ca.mcgill.ecse321.GameOn.model;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;
// line 87 "GameOn.ump"
@Entity
public class WishlistLink{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //WishlistLink Associations
    @EmbeddedId
    private Key key;

      //WishlistLink Associations
    private Game wishlistGames;
    private Customer CustomerWish;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public WishlistLink(Game aWishlistGames, Customer aCustomerWish)
    {
      boolean didAddWishlistGames = setWishlistGames(aWishlistGames);
      if (!didAddWishlistGames)
      {
        throw new RuntimeException("Unable to create wishlistlink due to wishlistGames. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
      }
      boolean didAddCustomerWish = setCustomerWish(aCustomerWish);
      if (!didAddCustomerWish)
      {
        throw new RuntimeException("Unable to create CustomerWish due to CustomerWish. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
      }
    }

    protected WishlistLink() {}

    //------------------------
    // INTERFACE
    //------------------------
    /* Code from template association_GetOne */
    public Game getWishlistGames()
    {
      return wishlistGames;
    }
    /* Code from template association_GetOne */
    public Customer getCustomerWish()
    {
      return CustomerWish;
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
    public boolean setCustomerWish(Customer aCustomerWish)
    {
      boolean wasSet = false;
      if (aCustomerWish == null)
      {
        return wasSet;
      }

      Customer existingCustomerWish = CustomerWish;
      CustomerWish = aCustomerWish;
      if (existingCustomerWish != null && !existingCustomerWish.equals(aCustomerWish))
      {
        existingCustomerWish.removeCustomerWish(this);
      }
      CustomerWish.addCustomerWish(this);
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
      Customer placeholderCustomerWish = CustomerWish;
      this.CustomerWish = null;
      if(placeholderCustomerWish != null)
      {
        placeholderCustomerWish.removeCustomerWish(this);
      }
    }

    public WishlistLink(Key key){
      this.key = key;
    }

    public Key getKey(){
      return key;
    }

    public void setKey(Key key){
      this.key = key;
    }

    @Embeddable
    public static class Key implements Serializable{

      @ManyToOne
      private Game wishlistGames;

      @ManyToOne
      private Customer customerWishlist;

      public Key() {
        super();
      }
      
      public Key(Game wishlistGames, Customer customerWishlist) {
        this.wishlistGames = wishlistGames;
        this.customerWishlist = customerWishlist;
      }

      public Game getWishlistGames() {
        return wishlistGames;
      }

      public Customer getCustomer(){
        return customerWishlist;
      }
    
      @Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Key)) {
				return false;
			}
			Key that = (Key) obj;
			return this.getCustomer().getId() == that.getCustomer().getId()
					&& this.getWishlistGames().getName() == that.getWishlistGames().getName();
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.getCustomer().getId(), this.getWishlistGames().getName());
    }
  }
}