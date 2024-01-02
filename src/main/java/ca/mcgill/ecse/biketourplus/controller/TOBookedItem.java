/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 21 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
public class TOBookedItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBookedItem Attributes
  private String nameBI;
  private int quantityBI;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBookedItem(String aNameBI, int aQuantityBI)
  {
    nameBI = aNameBI;
    quantityBI = aQuantityBI;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getNameBI()
  {
    return nameBI;
  }

  public int getQuantityBI()
  {
    return quantityBI;
  }

  public void delete()
  {}


  @Override
  // line 27 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
   public String toString(){
    return nameBI + " " + quantityBI;
  }

}