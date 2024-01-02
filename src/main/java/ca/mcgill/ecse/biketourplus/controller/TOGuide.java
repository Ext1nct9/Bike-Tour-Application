/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 74 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
public class TOGuide
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOGuide Attributes
  private String name;
  private String email;
  private String password;
  private String emergencyContact;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOGuide(String aName, String aEmail, String aPassword, String aEmergencyContact)
  {
    name = aName;
    email = aEmail;
    password = aPassword;
    emergencyContact = aEmergencyContact;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmergencyContact(String aEmergencyContact)
  {
    boolean wasSet = false;
    emergencyContact = aEmergencyContact;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public String getEmergencyContact()
  {
    return emergencyContact;
  }

  public void delete()
  {}


  @Override
  // line 81 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
   public String toString(){
    return email;
  }

}