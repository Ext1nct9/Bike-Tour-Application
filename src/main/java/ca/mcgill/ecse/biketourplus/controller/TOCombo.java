/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 1 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
public class TOCombo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOCombo Attributes
  private String nameCombo;
  private int discount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOCombo(String aNameCombo, int aDiscount)
  {
    nameCombo = aNameCombo;
    discount = aDiscount;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getNameCombo()
  {
    return nameCombo;
  }

  public int getDiscount()
  {
    return discount;
  }

  public void delete()
  {}


  @Override
  // line 7 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
   public String toString(){
    return nameCombo + " | Discount: " + discount + " %";
  }

}