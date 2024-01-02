package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;

import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.*;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTour;
import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.model.BookedItem;
import ca.mcgill.ecse.biketourplus.model.BookableItem;
import ca.mcgill.ecse.biketourplus.model.Gear;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.ComboItem;

public class BikeTourPlusFeatureSet1Controller {

	private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();	// class variable: reference BikeTourPlus model

	/**
	 * Constructor: prevent instantiation of this class
	 */
	private BikeTourPlusFeatureSet1Controller() {
		// Private constructor to prevent instantiation
	}

	/**
	 * Check that the given password fulfills all constraints,
	 * Then attempt to change the btp's manager's password to given password
	 * 
	 * @param password - new password to be set for the manager
	 * @return - an error message if an error occurred
	 * 
	 * Assumption: only 1 error needs to be returned even if multiple constraints are not met
	 */

	public static String updateManager(String password) {
		// Check password constraints //
		// password must be at least four characters long
		if (password.length() < 4) {
			// password cannot be empty
			if (password.length() == 0) return "Password cannot be empty";
			return "Password must be at least four characters long";
		}
		
		// password must contain a special character out of !#$
		if (password.indexOf('!') == -1 && password.indexOf('#') == -1 && password.indexOf('$') == -1) {
			return "Password must contain one character out of !#$";
		}
		
		// password must contain an upper case character
		// password must contain a lower case character
		boolean hasUpper = false;
		boolean hasLower = false;
		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) hasUpper = true;
			else if (Character.isLowerCase(c)) hasLower = true;
		}
		if (!hasUpper) return "Password must contain one upper-case character";
		if (!hasLower) return "Password must contain one lower-case character";
		
		// Attempt to set manager password //
		// Set the manager's new password and check if successfully set
		if (btp.getManager().setPassword(password)) {
		    try {
		       BikeTourPlusPersistence.save();
		     } catch (RuntimeException e) {
		       return e.getMessage();
		     }
			// success
			return "";
		}
		// fail
		return "An error occurred while trying to set the manager's new password";
	}

	/**
	 * Get all the data describing a BikeTour found through the given id
	 * Including data about the guide and the participants linked to the target BikeTour
	 * 
	 * @param id - the id of the target BikeTour
	 * @return - a TOBikeTour object holding all data concerning the target BikeTour
	 */
	public static TOBikeTour getBikeTour(int id) {
		// Get all BikeTour data //
		BikeTour tour = btp.getBikeTour(id-1);		// store reference to target tour
		//check that tour was set
		if (tour == null) return null;

		// id
		// NA: already given

		// startWeek
		int startWeek = tour.getStartWeek();

		// endWeek
		int endWeek = tour.getEndWeek();

		// guideEmail
		String guideEmail = tour.getGuide().getEmail();

		// guideName
		String guideName = tour.getGuide().getName();

		// totalCostForGuide
		int totalCostForGuide = btp.getPriceOfGuidePerWeek() * (endWeek - startWeek+1);

		// participantCosts
		List<TOParticipantCost> participantCosts = new ArrayList<TOParticipantCost>();		// list of TOParticipantCost

		//travel association from BikeTour to Participants
		for (Participant participant : tour.getParticipants()) {
			// Get all participant data //
			boolean requiresLodge = participant.getLodgeRequired();		// boolean to store if participant requiresLodge (for discount)

			// participantEmail
			String participantEmail = participant.getEmail();

			// participantName
			String participantName = participant.getName();
			
			// status
			String status = participant.getStatusFullName();

			// totalCostForBookableItems
			int totalCostForBookableItems = 0;			// keep track of sum of the totalCostForBookableItems
			
			//travel association from Participant to BookedItems
			for (BookedItem bookedItem : participant.getBookedItems()) {
				int qty = bookedItem.getQuantity();				// qty of the BookableItem
				
				//travel association from BookedItem to BookableItem
				BookableItem item = bookedItem.getItem();
				int bookableItemCostPerWeek = 0;				// weekly cost of BookableItem
				
				//check if BookedItem is a Gear or a Combo
				if (item instanceof Gear) {
					// gear
					bookableItemCostPerWeek = ((Gear) item).getPricePerWeek();
				} else if (item instanceof Combo) {
					// combo
					//travel association from Combo to ComboItem
					for (ComboItem comboItem : ((Combo) item).getComboItems()) {
						bookableItemCostPerWeek += comboItem.getGear().getPricePerWeek() * comboItem.getQuantity();
					}
					//check discount
					if (requiresLodge) {
					    bookableItemCostPerWeek *= (100 - ((Combo) item).getDiscount())/100.;
					}
				}
				
				//calculate total cost
				totalCostForBookableItems += qty * bookableItemCostPerWeek * (endWeek - startWeek+1);
			}

			// totalCostForBikingTour
			int totalCostForBikingTour = totalCostForBookableItems + totalCostForGuide;
			
			// authorizationCode
			String authorizationCode = participant.getAuthorizationCode();
			
			// refundedPercentageAmount
			int refundedPercentageAmount = participant.getRefundedPercentageAmount();

			// Build participant TO //
			participantCosts.add(new TOParticipantCost(participantEmail, participantName, status, totalCostForBookableItems, totalCostForBikingTour, authorizationCode, refundedPercentageAmount));
		}

		// Build bikeTour TO //
		TOParticipantCost[] arrParticipantCosts = new TOParticipantCost[participantCosts.size()]; // transform ArrayList to array
		arrParticipantCosts = participantCosts.toArray(arrParticipantCosts);

		return new TOBikeTour(id, startWeek, endWeek, guideEmail, guideName, totalCostForGuide, arrParticipantCosts);
		  
	}
	public static List<TOBikeTour> getTour() {
		return btp.getBikeTours().stream().map(startDate -> new TOBikeTour(startDate.getId(), startDate.getStartWeek(), startDate.getEndWeek(), startDate.getGuide().getEmail(), startDate.getGuide().getName(), startDate.getBikeTourPlus().getPriceOfGuidePerWeek())).toList();
	}
	
	public static List<TOBikeTourStart> getStart(){
		return btp.getBikeTours().stream().map(givenDate -> new TOBikeTourStart(givenDate.getStartWeek(), givenDate.getEndWeek())).toList();
	}
	public static List<TOParticipantsInTour> getParticipants(int tourID){
      BikeTour tour = btp.getBikeTour(tourID-1);
      int sizeOfTour = tour.getParticipants().size();
      return tour.getParticipants().stream().map(participants -> new TOParticipantsInTour(sizeOfTour , participants.getEmail(), participants.getName(), participants.getEmergencyContact(), (participants.getStatusFullName()), participants.getAuthorizationCode(), participants.getRefundedPercentageAmount())).toList();


    }
}




















