/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 11 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
public class TOGear
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOGear Attributes
  private String nameGear;
  private int weeklyPriceGear;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOGear(String aNameGear, int aWeeklyPriceGear)
  {
    nameGear = aNameGear;
    weeklyPriceGear = aWeeklyPriceGear;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getNameGear()
  {
    return nameGear;
  }

  public int getWeeklyPriceGear()
  {
    return weeklyPriceGear;
  }

  public void delete()
  {}


  @Override
  // line 17 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
   public String toString(){
    return nameGear + " " + weeklyPriceGear + " $";
  }

}