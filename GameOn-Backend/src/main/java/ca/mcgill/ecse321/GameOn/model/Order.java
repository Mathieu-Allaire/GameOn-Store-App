/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse321.GameOn.model;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.*;

// line 48 "model.ump"
// line 179 "model.ump"

@Entity
@Table(name = "\"order\"")
public class Order
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private Date purchaseDate;

  //Order Associations
  @OneToOne
  private Cart cart;
  @OneToMany
  private List<SpecificGame> orderGames;
  @ManyToOne
  private Customer orderCustomer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(Date aPurchaseDate, Cart aCart, Customer aOrderCustomer)
  {
    purchaseDate = aPurchaseDate;
    boolean didAddCart = setCart(aCart);
    if (!didAddCart)
    {
      throw new RuntimeException("Unable to create order due to cart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orderGames = new ArrayList<SpecificGame>();
    boolean didAddOrderCustomer = setOrderCustomer(aOrderCustomer);
    if (!didAddOrderCustomer)
    {
      throw new RuntimeException("Unable to create customerOrder due to orderCustomer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  protected Order() {
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getId()
  {
    return id;
  }

  public Date getPurchaseDate()
  {
    return purchaseDate;
  }
  /* Code from template association_GetOne */
  public Cart getCart()
  {
    return cart;
  }

  /* Code from template association_GetOne */
  public Customer getOrderCustomer()
  {
    return orderCustomer;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setCart(Cart aNewCart)
  {
    boolean wasSet = false;
    if (aNewCart == null)
    {
      //Unable to setCart to null, as order must always be associated to a cart
      return wasSet;
    }
    
    Order existingOrder = aNewCart.getOrder();
    if (existingOrder != null && !equals(existingOrder))
    {
      //Unable to setCart, the current cart already has a order, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Cart anOldCart = cart;
    cart = aNewCart;
    cart.setOrder(this);

    if (anOldCart != null)
    {
      anOldCart.setOrder(null);
    }
    wasSet = true;
    return wasSet;
  }

  public List<SpecificGame> getOrderGames()
  {
    List<SpecificGame> newOrderGames = Collections.unmodifiableList(orderGames);
    return newOrderGames;
  }

  /* Code from template association_SetOneToMany */
  public boolean setOrderCustomer(Customer aOrderCustomer)
  {
    boolean wasSet = false;
    if (aOrderCustomer == null)
    {
      return wasSet;
    }

    Customer existingOrderCustomer = orderCustomer;
    orderCustomer = aOrderCustomer;
    if (existingOrderCustomer != null && !existingOrderCustomer.equals(aOrderCustomer))
    {
      existingOrderCustomer.removeCustomerOrder(this);
    }
    orderCustomer.addCustomerOrder(this);
    wasSet = true;
    return wasSet;
  }

}