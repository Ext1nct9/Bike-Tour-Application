/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 15 "../../../../../BikeTourPlusTransferObjects.ump"
public class TOParticipantCost
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOParticipantCost Attributes
  private String participantEmail;
  private String participantName;
  private String status;
  private int totalCostForBookableItems;
  private int totalCostForBikingTour;
  private String authorizationCode;
  private int refundedPercentageAmount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOParticipantCost(String aParticipantEmail, String aParticipantName, String aStatus, int aTotalCostForBookableItems, int aTotalCostForBikingTour, String aAuthorizationCode, int aRefundedPercentageAmount)
  {
    participantEmail = aParticipantEmail;
    participantName = aParticipantName;
    status = aStatus;
    totalCostForBookableItems = aTotalCostForBookableItems;
    totalCostForBikingTour = aTotalCostForBikingTour;
    authorizationCode = aAuthorizationCode;
    refundedPercentageAmount = aRefundedPercentageAmount;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getParticipantEmail()
  {
    return participantEmail;
  }

  public String getParticipantName()
  {
    return participantName;
  }

  public String getStatus()
  {
    return status;
  }

  public int getTotalCostForBookableItems()
  {
    return totalCostForBookableItems;
  }

  public int getTotalCostForBikingTour()
  {
    return totalCostForBikingTour;
  }

  public String getAuthorizationCode()
  {
    return authorizationCode;
  }

  public int getRefundedPercentageAmount()
  {
    return refundedPercentageAmount;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "participantEmail" + ":" + getParticipantEmail()+ "," +
            "participantName" + ":" + getParticipantName()+ "," +
            "status" + ":" + getStatus()+ "," +
            "totalCostForBookableItems" + ":" + getTotalCostForBookableItems()+ "," +
            "totalCostForBikingTour" + ":" + getTotalCostForBikingTour()+ "," +
            "authorizationCode" + ":" + getAuthorizationCode()+ "," +
            "refundedPercentageAmount" + ":" + getRefundedPercentageAmount()+ "]";
  }
}