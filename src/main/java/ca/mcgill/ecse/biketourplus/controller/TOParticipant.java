/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 64 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
public class TOParticipant
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOParticipant Attributes
  private String participantEmail;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOParticipant(String aParticipantEmail)
  {
    participantEmail = aParticipantEmail;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getParticipantEmail()
  {
    return participantEmail;
  }

  public void delete()
  {}


  @Override
  // line 69 "../../../../../BikeTourPlusAdditionalTransferObjects.ump"
   public String toString(){
    return participantEmail;
  }

}