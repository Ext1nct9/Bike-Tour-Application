package ca.mcgill.ecse.biketourplus.controller;

import java.util.ArrayList;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTour;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Guide;
import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.model.Participant.Status;

public class BikeTourPlusStateMachineController {

  private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus(); // class variable:
                                                                               // reference
                                                                               // BikeTourPlus model

  /**
   * Constructor: prevent instantiation of this class
   * 
   * @author Lin Wei Li
   */
  private BikeTourPlusStateMachineController() {
    // Private constructor to prevent instantiation
  }

  /**
   * Start the creation process of bike tours. Fails if there are no guides in the system. Creates
   * bike tours using the least number of guides possible by checking if there is already an 
   * existing suitable bike tour, then creating one by matching the guide's availabilities with 
   * the participant's.
   * 
   * 
   * @return - an error message, if any
   * 
   * @author William Zhang
   */
  public static String createBikeTours() {
    int tourid = 1;
    // Counter for available weeks for guide check
    int counter = 0;

    // Arraylist containing (Strings) booleans of weekly availabilities for a guide
    ArrayList<String> availableWeeks = new ArrayList<String>();
    for (int i = 0; i < btp.getNrWeeks(); i++) {
      availableWeeks.add("True");
    }
    // Check if there are guides in the system.
    if (!btp.hasGuides()) {
      return "Cannot Create Bike Tours since there are no guides in the system.";
    } else {

      // Guide loop
      for (int l = 0; l < btp.getGuides().size(); l++) {
        Guide guide = btp.getGuide(l);

        // Set guide availabilities grid
        for (int i = 0; i < btp.getNrWeeks(); i++) {
          availableWeeks.set(i, "True");
        }
        for (var bt : guide.getBikeTours()) {
          for (int j = bt.getStartWeek() - 1; j < (bt.getEndWeek()); j++) {
            availableWeeks.set(j, "False");
          }
        }

        // Go through each participant and see if they can be assigned to a tour of that guide
        for (int k = 0; k < btp.getParticipants().size(); k++) {
          Participant part = btp.getParticipant(k);

          // Check if participant has a bike tour assigned
          if (!part.hasBikeTour()) {

            // Check if the constraints are met for existing bike tours and add participant if
            // possible
            for (var bt : guide.getBikeTours()) {
              if (part.getWeekAvailableFrom() <= bt.getStartWeek()
                  && part.getWeekAvailableUntil() >= bt.getEndWeek()) {
                if (part.getNrWeeks() == bt.getEndWeek() - bt.getStartWeek() + 1) {
                  bt.addParticipant(part);
                  part.assign(bt);
                  try {
                    BikeTourPlusPersistence.save();
                  } catch (RuntimeException e) {
                    return e.getMessage();
                  }
                }
              }
            }
          }
          // If participant has not been assigned to an existing bike tour of the specified guide,
          // check if the guide is available for one slot where the partcipant is available.
          if (!part.hasBikeTour()) {

            // Iterate through each possible 'clump' of weeks
            for (int m = part.getWeekAvailableFrom() - 1; m < part.getWeekAvailableUntil()
                - part.getNrWeeks() + 1; m++) {

              // Reset counter
              counter = 0;

              // For a clump of weeks, check if guide is available for all the weeks
              for (int n = m; n < m + part.getNrWeeks(); n++) {
                if (availableWeeks.get(n).equals("True")) {
                  counter++;

                  // Add bike tour if guide is available for a clump of weeks where participant is
                  // available too
                  if (counter == part.getNrWeeks()) {
                    BikeTour newBikeTour = btp.addBikeTour(tourid, m + 1, n + 1, guide);
                    newBikeTour.addParticipant(part);

                    // Change participant assignment state
                    part.assign(newBikeTour);

                    // Disable availabilities in the arraylist of the guide
                    for (int j =
                        newBikeTour.getStartWeek() - 1; j < (newBikeTour.getEndWeek()); j++) {
                      availableWeeks.set(j, "False");
                    }

                    // Increment tourid
                    tourid++;

                    // Stop participant for loop (for the clump of weeks)
                    m = part.getWeekAvailableUntil() - part.getNrWeeks();
                    try {
                      BikeTourPlusPersistence.save();
                    } catch (RuntimeException e) {
                      return e.getMessage();
                    }
                  }
                }
              }
            }
          }
        }
      }

      // Check if all participants have been assigned to a bike tour, and return error message if
      // not.
      for (var participant : btp.getParticipants()) {
        if (!participant.hasBikeTour()) {
          return "At least one participant could not be assigned to their bike tour";
        }
      }
    }
    return "";
  }

  /**
   * Attempt to confirm a participant's payment. Fails is given authorizationCode is invalid. Fails
   * if no participant is found with the given email. Fails if the target participant is not in a
   * valid state. Updates target participant's state and sets their authorizationCode if successful.
   * 
   * @param participantEmail - Email identifying participant
   * @param authorizationCode - Code for payment confirmation
   * @return - an error message, if any
   * 
   * @author Lin Wei Li
   */
  public static String confirmPayForParticipant(String participantEmail, String authorizationCode) {

    // Invalid authorization code
    if (authorizationCode.trim().length() == 0)
      return "Invalid authorization code";

    // Participant with email address <participantEmail> does not exist
    Participant targetParticipant = null;
    for (Participant participant : btp.getParticipants()) {
      if (participant.getEmail().equals(participantEmail)) {
        targetParticipant = participant;
        break;
      }
    }
    if (targetParticipant == null)
      return "Participant with email address " + participantEmail + " does not exist";

    // The participant has not been assigned to their tour
    if (targetParticipant.getStatus().equals(Status.NotAssigned))
      return "The participant has not been assigned to their tour";

    // The participant has already paid for their tour
    if (targetParticipant.getStatus().equals(Status.Paid)
        || targetParticipant.getStatus().equals(Status.Started)
        || targetParticipant.getStatus().equals(Status.Finished)) {
      return "The participant has already paid for their tour";
    }

    // Cannot pay for tour because the participant is banned
    if (targetParticipant.getStatus().equals(Status.Banned))
      return "Cannot pay for tour because the participant is banned";

    // Cannot pay for tour because the participant has cancelled their tour
    if (targetParticipant.getStatus().equals(Status.Cancelled))
      return "Cannot pay for tour because the participant has cancelled their tour";

    // No errors
    targetParticipant.pay(authorizationCode); // call event in SM
                                                                  // (authorizationCode set in
                                                                  // transition action)
    try {
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * Attempt to start all Tours with a given startWeek. Fails if the target participant is not in a
   * valid state. Updates the state of each participants assigned to all Tours which start on the
   * given week.
   * 
   * @param startWeek - an int denoting the startWeek of the Tours that need to be started
   * @return - concatenated error messages, if any
   * 
   * @author Lin Wei Li
   */
  public static String startAllToursForWeek(int startWeek) {

    String errors = ""; // keep track of all error messages (concatenate)

    // Loop through all tours
    for (BikeTour tour : btp.getBikeTours()) {

      // Check tour startWeek
      if (tour.getStartWeek() == startWeek) {

        // Loop through all participants
        for (Participant participant : tour.getParticipants()) {

          // Cannot start tour because the participant has already started their tour
          if (participant.getStatus().equals(Status.Started))
            errors += "Cannot start tour because the participant has already started their tour";

          // Cannot start tour because the participant is banned
          else if (participant.getStatus().equals(Status.Banned))
            errors += "Cannot start tour because the participant is banned";

          // Cannot start tour because the participant has cancelled their tour
          else if (participant.getStatus().equals(Status.Cancelled))
            errors += "Cannot start tour because the participant has cancelled their tour";

          // Cannot start tour because the participant has finished their tour
          else if (participant.getStatus().equals(Status.Finished))
            errors += "Cannot start tour because the participant has finished their tour";

          // No errors
          else
            participant.startTrip(startWeek);

        }

      }

    }
    try {
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return errors;
  }

  /**
   * Attempt to finish a participant's Tour. Fails if no participant is found with the given email.
   * Fails if the target participant is not in a valid state. Updates the target participant's state
   * if successful.
   * 
   * @param participantEmail - Email identifying participant
   * @return - an error message, if any
   * 
   * @author Lin Wei Li
   */
  public static String finishTourForParticipant(String participantEmail) {

    // Participant with email address <participantEmail> does not exist
    Participant targetParticipant = null;
    for (Participant participant : btp.getParticipants()) {
      if (participant.getEmail().equals(participantEmail)) {
        targetParticipant = participant;
        break;
      }
    }
    if (targetParticipant == null)
      return "Participant with email address " + participantEmail + " does not exist";

    // Cannot finish a tour for a participant who has not started their tour
    if (targetParticipant.getStatus().equals(Status.NotAssigned)
        || targetParticipant.getStatus().equals(Status.Assigned)
        || targetParticipant.getStatus().equals(Status.Paid)) {
      return "Cannot finish a tour for a participant who has not started their tour";
    }

    // Cannot finish tour because the participant is banned
    if (targetParticipant.getStatus().equals(Status.Banned))
      return "Cannot finish tour because the participant is banned";

    // Cannot finish tour because the participant has cancelled their tour
    if (targetParticipant.getStatus().equals(Status.Cancelled))
      return "Cannot finish tour because the participant has cancelled their tour";

    // Cannot finish tour because the participant has already finished their tour
    if (targetParticipant.getStatus().equals(Status.Finished))
      return "Cannot finish tour because the participant has already finished their tour";

    // No error
    targetParticipant.finishTrip();
    try {
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }

  /**
   * Attempt to cancel a participant's Tour. Fails if no participant is found with the given email.
   * Fails if the target participant is not in a valid state. Updates the target participant's state
   * if successful.
   * 
   * @param participantEmail - Email identifying participant
   * @return - an error message, if any
   * 
   * @author Lin Wei Li
   */
  public static String cancelTripForParticipant(String participantEmail) {

    // Participant with email address <participantEmail> does not exist
    Participant targetParticipant = null;
    for (Participant participant : btp.getParticipants()) {
      if (participant.getEmail().equals(participantEmail)) {
        targetParticipant = participant;
        break;
      }
    }
    if (targetParticipant == null)
      return "Participant with email address " + participantEmail + " does not exist";

    // Cannot cancel tour because the participant is banned
    if (targetParticipant.getStatus().equals(Status.Banned))
      return "Cannot cancel tour because the participant is banned";

    // Cannot cancel tour because the participant has already cancelled their tour
    if (targetParticipant.getStatus().equals(Status.Cancelled))
      return "Cannot cancel tour because the participant has already cancelled their tour";

    // Cannot cancel tour because the participant has finished their tour
    if (targetParticipant.getStatus().equals(Status.Finished))
      return "Cannot cancel tour because the participant has finished their tour";

    // No error
    targetParticipant.cancel();
    try {
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return "";
  }
}
