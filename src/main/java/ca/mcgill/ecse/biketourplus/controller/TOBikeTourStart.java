/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.0.6441.414d09714 modeling language!*/
package ca.mcgill.ecse.biketourplus.controller;



// line 40 "model.ump"
public class TOBikeTourStart
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBikeTourStart Attributes
  private Integer startWeek;
  private Integer endWeek;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBikeTourStart(Integer aStartWeek, Integer aEndWeek)
  {
    startWeek = aStartWeek;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartWeek(Integer aStartWeek)
  {
    boolean wasSet = false;
    startWeek = aStartWeek;
    wasSet = true;
    return wasSet;
  }
  
  public boolean setEndWeek(Integer aEndWeek)
  {
    boolean wasSet = false;
    startWeek = aEndWeek;
    wasSet = true;
    return wasSet;
  }

  public Integer getStartWeek()
  {
    return startWeek;
  }

  public Integer getEndWeek() {
	  return endWeek;
  }
  
  public void delete()
  {}


  @Override
  // line 44 "model.ump"
   public String toString(){
    return "" + startWeek + endWeek;
  }

}


