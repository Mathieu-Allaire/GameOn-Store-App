/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/
package ca.mcgill.ecse321.GameOn.model;
import jakarta.persistence.*;
import java.util.*;

// line 12 "GameOn.ump"
@Entity
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes

  @Id
  private String email;
  private String name;
  private String password;

  //Person Associations

    @OneToMany(fetch = FetchType.EAGER) // Person --> Role
    
  private List<Role> roles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aEmail, String aName, String aPassword, Role... allRoles)
  {
    email = aEmail;
    name = aName;
    password = aPassword;
    roles = new ArrayList<Role>();
    boolean didAddRoles = setRoles(allRoles);
    if (!didAddRoles)
    {
      throw new RuntimeException("Unable to create Person, must have 1 to 3 roles. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  protected Person() {}

  //------------------------
  // INTERFACE
  //------------------------

  public String getEncryptedPassword(String aPassword){
    String encryptedPassword = "";
        for (int i = aPassword.length() - 1; i >= 0; i--) {
            encryptedPassword += aPassword.charAt(i);
        }

        // encryptedPassword to ascii multiplied by two as a string seperated by commas
        String asciiEncryptedPassword = "";
        for (int i = 0; i < encryptedPassword.length(); i++) {
            asciiEncryptedPassword += (int) encryptedPassword.charAt(i) * 2;
            if (i != encryptedPassword.length() - 1) {
                asciiEncryptedPassword += ",";
            }
        }

        return asciiEncryptedPassword;

  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  /**
   * not sure how to put unique here
   */
  public String getEmail()
  {
    return email;
  }

  public String getName()
  {
    return name;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetMany */
  public Role getRole(int index)
  {
    Role aRole = roles.get(index);
    return aRole;
  }

  public int numberOfRoles()
  {
    int number = roles.size();
    return number;
  }

  public boolean hasRoles()
  {
    boolean has = roles.size() > 0;
    return has;
  }

  /* Code from template association_SetUnidirectionalMN */
  public boolean setRoles(Role... newRoles)
  {
    boolean wasSet = false;
    ArrayList<Role> verifiedRoles = new ArrayList<Role>();
    for (Role aRole : newRoles)
    {
      if (verifiedRoles.contains(aRole))
      {
        continue;
      }
      verifiedRoles.add(aRole);
    }

    if (verifiedRoles.size() != newRoles.length)
    {
      return wasSet;
    }

    roles.clear();
    roles.addAll(verifiedRoles);
    wasSet = true;
    return wasSet;
  }
}
