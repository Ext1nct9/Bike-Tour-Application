/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 42 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
public class TOParticipantsInTour
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOParticipantsInTour Attributes
  private int totalParticipants;
  private String participantEmail;
  private String participantUsername;
  private String emergencyContact;
  private String status;
  private String authCode;
  private int Discount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOParticipantsInTour(int aTotalParticipants, String aParticipantEmail, String aParticipantUsername, String aEmergencyContact, String aStatus, String aAuthCode, int aDiscount)
  {
    totalParticipants = aTotalParticipants;
    participantEmail = aParticipantEmail;
    participantUsername = aParticipantUsername;
    emergencyContact = aEmergencyContact;
    status = aStatus;
    authCode = aAuthCode;
    Discount = aDiscount;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getTotalParticipants()
  {
    return totalParticipants;
  }

  public String getParticipantEmail()
  {
    return participantEmail;
  }

  public String getParticipantUsername()
  {
    return participantUsername;
  }

  public String getEmergencyContact()
  {
    return emergencyContact;
  }

  public String getStatus()
  {
    return status;
  }

  public String getAuthCode()
  {
    return authCode;
  }

  public int getDiscount()
  {
    return Discount;
  }

  public void delete()
  {}


  @Override
  // line 53 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
   public String toString(){
    return participantEmail;
  }

}