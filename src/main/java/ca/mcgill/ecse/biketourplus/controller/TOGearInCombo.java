/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 31 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
public class TOGearInCombo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOGearInCombo Attributes
  private String nameGear;
  private int quantity;
  private int weeklyPriceGear;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOGearInCombo(String aNameGear, int aQuantity, int aWeeklyPriceGear)
  {
    nameGear = aNameGear;
    quantity = aQuantity;
    weeklyPriceGear = aWeeklyPriceGear;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getNameGear()
  {
    return nameGear;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public int getWeeklyPriceGear()
  {
    return weeklyPriceGear;
  }

  public void delete()
  {}


  @Override
  // line 38 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
   public String toString(){
    return nameGear + " " + weeklyPriceGear + " $   x " + quantity;
  }

}