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

    public WishlistLink(){
    }

    public WishlistLink(Key key){
      this.key = key;
    }

    public Key getKey(){
      return key;
    }

    @Embeddable
    public static class Key implements Serializable{

      @ManyToOne //WishList Link --> Game  
      @JoinColumn(name = "game_id", nullable = false)
      private Game wishlistGames;
      @ManyToOne
      @JoinColumn(name = "wishlist_id", nullable = false)
      private Wishlist wishlist;

      public Key() {
        super();
      }
      
      public Key(Game wishlistGames, Wishlist wishlist) {
        this.wishlistGames = wishlistGames;
        this.wishlist = wishlist;
      }

      public Game getWishlistGames() {
        return wishlistGames;
      }

      public Wishlist getWishList(){
        return wishlist;
      }

      public Wishlist getWishlist()
      {
        return this.getWishlist();
      }
    
      @Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Key)) {
				return false;
			}
			Key that = (Key) obj;
			return this.getWishList().getId() == that.getWishList().getId()
					&& this.getWishlistGames().getName() == that.getWishlistGames().getName();
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.getWishList().getId(), this.getWishlistGames().getName());
    }
  
  }
    
}
