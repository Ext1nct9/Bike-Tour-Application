/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.model;
import java.util.*;

// line 1 "../../../../../ParticipantStateMachine.ump"
// line 42 "../../../../../BikeTourPlus.ump"
public class Participant extends NamedUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Participant Attributes
  private int nrWeeks;
  private int weekAvailableFrom;
  private int weekAvailableUntil;
  private boolean lodgeRequired;
  private String authorizationCode;
  private int refundedPercentageAmount;

  //Participant State Machines
  public enum Status { NotAssigned, Assigned, Paid, Started, Banned, Cancelled, Finished }
  private Status status;

  //Participant Associations
  private BikeTourPlus bikeTourPlus;
  private BikeTour bikeTour;
  private List<BookedItem> bookedItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Participant(String aEmail, String aPassword, String aName, String aEmergencyContact, int aNrWeeks, int aWeekAvailableFrom, int aWeekAvailableUntil, boolean aLodgeRequired, String aAuthorizationCode, int aRefundedPercentageAmount, BikeTourPlus aBikeTourPlus)
  {
    super(aEmail, aPassword, aName, aEmergencyContact);
    nrWeeks = aNrWeeks;
    weekAvailableFrom = aWeekAvailableFrom;
    weekAvailableUntil = aWeekAvailableUntil;
    lodgeRequired = aLodgeRequired;
    authorizationCode = aAuthorizationCode;
    refundedPercentageAmount = aRefundedPercentageAmount;
    boolean didAddBikeTourPlus = setBikeTourPlus(aBikeTourPlus);
    if (!didAddBikeTourPlus)
    {
      throw new RuntimeException("Unable to create participant due to bikeTourPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    bookedItems = new ArrayList<BookedItem>();
    setStatus(Status.NotAssigned);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNrWeeks(int aNrWeeks)
  {
    boolean wasSet = false;
    nrWeeks = aNrWeeks;
    wasSet = true;
    return wasSet;
  }

  public boolean setWeekAvailableFrom(int aWeekAvailableFrom)
  {
    boolean wasSet = false;
    weekAvailableFrom = aWeekAvailableFrom;
    wasSet = true;
    return wasSet;
  }

  public boolean setWeekAvailableUntil(int aWeekAvailableUntil)
  {
    boolean wasSet = false;
    weekAvailableUntil = aWeekAvailableUntil;
    wasSet = true;
    return wasSet;
  }

  public boolean setLodgeRequired(boolean aLodgeRequired)
  {
    boolean wasSet = false;
    lodgeRequired = aLodgeRequired;
    wasSet = true;
    return wasSet;
  }

  public boolean setAuthorizationCode(String aAuthorizationCode)
  {
    boolean wasSet = false;
    authorizationCode = aAuthorizationCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setRefundedPercentageAmount(int aRefundedPercentageAmount)
  {
    boolean wasSet = false;
    refundedPercentageAmount = aRefundedPercentageAmount;
    wasSet = true;
    return wasSet;
  }

  public int getNrWeeks()
  {
    return nrWeeks;
  }

  public int getWeekAvailableFrom()
  {
    return weekAvailableFrom;
  }

  public int getWeekAvailableUntil()
  {
    return weekAvailableUntil;
  }

  public boolean getLodgeRequired()
  {
    return lodgeRequired;
  }

  public String getAuthorizationCode()
  {
    return authorizationCode;
  }

  public int getRefundedPercentageAmount()
  {
    return refundedPercentageAmount;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isLodgeRequired()
  {
    return lodgeRequired;
  }

  public String getStatusFullName()
  {
    String answer = status.toString();
    return answer;
  }

  public Status getStatus()
  {
    return status;
  }

  public boolean assign(BikeTour bikeTour)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case NotAssigned:
        // line 9 "../../../../../ParticipantStateMachine.ump"
        doAssign(bikeTour);
        setStatus(Status.Assigned);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case NotAssigned:
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      case Assigned:
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 23 "../../../../../ParticipantStateMachine.ump"
        doRefund(50);
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      case Started:
        // line 28 "../../../../../ParticipantStateMachine.ump"
        doRefund(10);
        setStatus(Status.Cancelled);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pay(String authorizationCode)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Assigned:
        if (isAuthorizationCodeValid(authorizationCode))
        {
        // line 15 "../../../../../ParticipantStateMachine.ump"
          doPay(authorizationCode);
          setStatus(Status.Paid);
          wasEventProcessed = true;
          break;
        }
        break;
      case Banned:
        if (isAuthorizationCodeValid(authorizationCode))
        {
        // line 31 "../../../../../ParticipantStateMachine.ump"
          rejectPay(authorizationCode);
          setStatus(Status.Banned);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startTrip(int week)
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Assigned:
        if (hasMatchingStartWeek(week))
        {
          setStatus(Status.Banned);
          wasEventProcessed = true;
          break;
        }
        break;
      case Paid:
        if (hasMatchingStartWeek(week))
        {
          setStatus(Status.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean finishTrip()
  {
    boolean wasEventProcessed = false;
    
    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        setStatus(Status.Finished);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setStatus(Status aStatus)
  {
    status = aStatus;
  }
  /* Code from template association_GetOne */
  public BikeTourPlus getBikeTourPlus()
  {
    return bikeTourPlus;
  }
  /* Code from template association_GetOne */
  public BikeTour getBikeTour()
  {
    return bikeTour;
  }

  public boolean hasBikeTour()
  {
    boolean has = bikeTour != null;
    return has;
  }
  /* Code from template association_GetMany */
  public BookedItem getBookedItem(int index)
  {
    BookedItem aBookedItem = bookedItems.get(index);
    return aBookedItem;
  }

  public List<BookedItem> getBookedItems()
  {
    List<BookedItem> newBookedItems = Collections.unmodifiableList(bookedItems);
    return newBookedItems;
  }

  public int numberOfBookedItems()
  {
    int number = bookedItems.size();
    return number;
  }

  public boolean hasBookedItems()
  {
    boolean has = bookedItems.size() > 0;
    return has;
  }

  public int indexOfBookedItem(BookedItem aBookedItem)
  {
    int index = bookedItems.indexOf(aBookedItem);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBikeTourPlus(BikeTourPlus aBikeTourPlus)
  {
    boolean wasSet = false;
    if (aBikeTourPlus == null)
    {
      return wasSet;
    }

    BikeTourPlus existingBikeTourPlus = bikeTourPlus;
    bikeTourPlus = aBikeTourPlus;
    if (existingBikeTourPlus != null && !existingBikeTourPlus.equals(aBikeTourPlus))
    {
      existingBikeTourPlus.removeParticipant(this);
    }
    bikeTourPlus.addParticipant(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setBikeTour(BikeTour aBikeTour)
  {
    boolean wasSet = false;
    BikeTour existingBikeTour = bikeTour;
    bikeTour = aBikeTour;
    if (existingBikeTour != null && !existingBikeTour.equals(aBikeTour))
    {
      existingBikeTour.removeParticipant(this);
    }
    if (aBikeTour != null)
    {
      aBikeTour.addParticipant(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBookedItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BookedItem addBookedItem(int aQuantity, BikeTourPlus aBikeTourPlus, BookableItem aItem)
  {
    return new BookedItem(aQuantity, aBikeTourPlus, this, aItem);
  }

  public boolean addBookedItem(BookedItem aBookedItem)
  {
    boolean wasAdded = false;
    if (bookedItems.contains(aBookedItem)) { return false; }
    Participant existingParticipant = aBookedItem.getParticipant();
    boolean isNewParticipant = existingParticipant != null && !this.equals(existingParticipant);
    if (isNewParticipant)
    {
      aBookedItem.setParticipant(this);
    }
    else
    {
      bookedItems.add(aBookedItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBookedItem(BookedItem aBookedItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aBookedItem, as it must always have a participant
    if (!this.equals(aBookedItem.getParticipant()))
    {
      bookedItems.remove(aBookedItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBookedItemAt(BookedItem aBookedItem, int index)
  {  
    boolean wasAdded = false;
    if(addBookedItem(aBookedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBookedItems()) { index = numberOfBookedItems() - 1; }
      bookedItems.remove(aBookedItem);
      bookedItems.add(index, aBookedItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBookedItemAt(BookedItem aBookedItem, int index)
  {
    boolean wasAdded = false;
    if(bookedItems.contains(aBookedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBookedItems()) { index = numberOfBookedItems() - 1; }
      bookedItems.remove(aBookedItem);
      bookedItems.add(index, aBookedItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBookedItemAt(aBookedItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    BikeTourPlus placeholderBikeTourPlus = bikeTourPlus;
    this.bikeTourPlus = null;
    if(placeholderBikeTourPlus != null)
    {
      placeholderBikeTourPlus.removeParticipant(this);
    }
    if (bikeTour != null)
    {
      BikeTour placeholderBikeTour = bikeTour;
      this.bikeTour = null;
      placeholderBikeTour.removeParticipant(this);
    }
    for(int i=bookedItems.size(); i > 0; i--)
    {
      BookedItem aBookedItem = bookedItems.get(i - 1);
      aBookedItem.delete();
    }
    super.delete();
  }

  // line 40 "../../../../../ParticipantStateMachine.ump"
   private void doAssign(BikeTour bikeTour){
    setBikeTour(bikeTour);
  }


  /**
   * ADDED
   */
  // line 45 "../../../../../ParticipantStateMachine.ump"
   private void rejectAssign(BikeTour bikeTour){
    throw new RuntimeException("Cannot start tour because the participant is banned");
  }


  /**
   * an authorization code is valid as long as it is not empty or null
   */
  // line 50 "../../../../../ParticipantStateMachine.ump"
   private boolean isAuthorizationCodeValid(String authorizationCode){
    return (authorizationCode.trim().length() > 0 && authorizationCode != null);
  }

  // line 54 "../../../../../ParticipantStateMachine.ump"
   private void doPay(String authorizationCode){
    setStatus(Status.Paid);
  	setAuthorizationCode(authorizationCode);
  }


  /**
   * ADDED
   */
  // line 60 "../../../../../ParticipantStateMachine.ump"
   private void rejectPay(String authorizationCode){
    throw new RuntimeException("Cannot pay for tour because the participant is banned");
  }

  // line 64 "../../../../../ParticipantStateMachine.ump"
   private boolean hasMatchingStartWeek(int week){
    return (week >= getWeekAvailableFrom() && week <= getWeekAvailableUntil());
  }

  // line 68 "../../../../../ParticipantStateMachine.ump"
   private void doRefund(int refundedPercentageAmount){
    setRefundedPercentageAmount(refundedPercentageAmount);
  }


  /**
   * ADDED
   */
  // line 73 "../../../../../ParticipantStateMachine.ump"
   private void rejectRefund(int refundedPercentageAmount){
    throw new RuntimeException("Cannot pay for tour because the participant is banned");
  }


  public String toString()
  {
    return super.toString() + "["+
            "nrWeeks" + ":" + getNrWeeks()+ "," +
            "weekAvailableFrom" + ":" + getWeekAvailableFrom()+ "," +
            "weekAvailableUntil" + ":" + getWeekAvailableUntil()+ "," +
            "lodgeRequired" + ":" + getLodgeRequired()+ "," +
            "authorizationCode" + ":" + getAuthorizationCode()+ "," +
            "refundedPercentageAmount" + ":" + getRefundedPercentageAmount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "bikeTourPlus = "+(getBikeTourPlus()!=null?Integer.toHexString(System.identityHashCode(getBikeTourPlus())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bikeTour = "+(getBikeTour()!=null?Integer.toHexString(System.identityHashCode(getBikeTour())):"null");
  }
}