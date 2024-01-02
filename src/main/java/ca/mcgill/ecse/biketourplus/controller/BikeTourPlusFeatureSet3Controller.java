package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.BookableItem;
import ca.mcgill.ecse.biketourplus.model.BookedItem;
import ca.mcgill.ecse.biketourplus.model.Guide;
import ca.mcgill.ecse.biketourplus.model.Participant;

public class BikeTourPlusFeatureSet3Controller {
   
   private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();
   
   // private constructor to prevent instantiation
   private BikeTourPlusFeatureSet3Controller() {}

   /**
    * This method registers a participant to the system.
    * 
    * @param email - the email address of the participant
    * @param password - the password for the participant
    * @param name - the name of the participant
    * @param emergencyContact - the emergency contact of the participant
    * @param nrWeeks - the number of weeks the participant wants to go on tour
    * @param weekAvailableFrom - the week from which the participant is available
    * @param weekAvailableUntil - the week until which the participant is available
    * @param lodgeRequired - if the participant wishes to stay at a lodge
    * @return - an error message if any
    */
   public static String registerParticipant(String email, String password, String name, String emergencyContact, int nrWeeks, int weekAvailableFrom, int weekAvailableUntil, boolean lodgeRequired) {
	  
      // email empty
      if (email == null || email.isEmpty()) {
         return "Email cannot be empty";
      }
      
      // email has spaces
      if (email.contains(" ")) {
         return "Email must not contain any spaces";
      }
      
      // invalid email format
      if (!validateEmail(email)) {
         return "Invalid email";
      }
      
      // email is the manager's email
      if (email.equals("manager@btp.com")) {
         return "Email cannot be manager@btp.com";
      }
      
      // email is a participant's email
      if (findExistingEmailParticipant(email)) {
         return "Email already linked to a participant account";
      }
      
      // email is another guide's email
      if (findExistingEmailGuide(email)) {
         return "Email already linked to a guide account";
      }
      
      // password empty
      if (password == null || password.isEmpty()) {
         return "Password cannot be empty";
      }
      
      // name empty
      if (name == null || name.isEmpty()) {
         return "Name cannot be empty";
      }
      
      // contact empty
      if (emergencyContact == null || emergencyContact.isEmpty()) {
         return "Emergency contact cannot be empty";
      }
	  
      // number of weeks smaller than 1
      if (nrWeeks < 1) {
         return "Number of weeks must be greater than zero";
      }
      
      // number of weeks higher than number of weeks in biking season
      if (nrWeeks > btp.getNrWeeks()) {
         return "Number of weeks must be less than or equal to the number of biking weeks in the biking season";
      }
	  
      // weekAvailableFrom/Until out of range
      if (weekAvailableFrom < 1 || weekAvailableFrom > btp.getNrWeeks() || weekAvailableUntil < 1 || weekAvailableUntil > btp.getNrWeeks()) {
         return "Available weeks must be within weeks of biking season (1-" + btp.getNrWeeks() + ")";
      }
      
      // weekAvailableFrom greater than weekAvailableUntil
      if (weekAvailableFrom > weekAvailableUntil) {
         return "Week from which one is available must be less than or equal to the week until which one is available";
      }
      
      // number of weeks larger than number of available weeks
      if ((nrWeeks-1) > (weekAvailableUntil-weekAvailableFrom)) {
         return "Number of weeks must be less than or equal to the number of available weeks";
      }
	  
      // register participant if nothing above returns
      // authorization code and refund percentage are to be defined later
      btp.addParticipant(email, password, name, emergencyContact, nrWeeks, weekAvailableFrom, weekAvailableUntil, lodgeRequired, "", 0);
      try {
        BikeTourPlusPersistence.save();
      } catch (RuntimeException e) {
        return e.getMessage();
      }
      return "";
   }

   /**
    * This method updates a participant's information.
    * 
    * @param email - the email address of the participant
    * @param newPassword - the new password for the participant
    * @param newName - the new name of the participant
    * @param newEmergencyContact - the new emergency contact of the participant
    * @param newNrWeeks - the new number of weeks the participant wants to go on tour
    * @param newWeekAvailableFrom - the new week from which the participant is available
    * @param newWeekAvailableUntil - the new week until which the participant is available
    * @param newLodgeRequired - if the participant wishes to stay at a lodge
    * @return - an error message if any
    */
   public static String updateParticipant(String email, String newPassword, String newName, String newEmergencyContact, int newNrWeeks, int newWeekAvailableFrom, int newWeekAvailableUntil, boolean newLodgeRequired) {
      
      // no email input
      if (email == null || email.isEmpty()) {
         return "Email cannot be empty";
      }
      
      for (Participant p : btp.getParticipants()) {
         if (p.getEmail().equals(email)) { // find corresponding participant
        	
            // new password empty
            if (newPassword == null || newPassword.isEmpty()) {
               return "Password cannot be empty";
            }
          	  
            // new name empty
            if (newName == null || newName.isEmpty()) {
               return "Name cannot be empty";
            }
          	  
            // new emergency contact empty
            if (newEmergencyContact == null || newEmergencyContact.isEmpty()) {
               return "Emergency contact cannot be empty";
            }
          	  
            // number of weeks smaller than 1
            if (newNrWeeks < 1) {
               return "Number of weeks must be greater than zero";
            }

            // number of weeks higher than number of weeks in biking season
            if (newNrWeeks > btp.getNrWeeks()) {
               return "Number of weeks must be less than or equal to the number of biking weeks in the biking season";
            }

            // weekAvailableFrom/Until out of range
            if (newWeekAvailableFrom < 1 || newWeekAvailableFrom > btp.getNrWeeks() || newWeekAvailableUntil < 1 || newWeekAvailableUntil > btp.getNrWeeks()) {
               return "Available weeks must be within weeks of biking season (1-" + btp.getNrWeeks() + ")";
            }

            // weekAvailableFrom greater than weekAvailableUntil
            if (newWeekAvailableFrom > newWeekAvailableUntil) {
               return "Week from which one is available must be less than or equal to the week until which one is available";
            }

            // number of weeks larger than number of available weeks
            if ((newNrWeeks-1) > (newWeekAvailableUntil-newWeekAvailableFrom)) {
               return "Number of weeks must be less than or equal to the number of available weeks";
            }

            // update participant info if nothing above returns
            p.setPassword(newPassword);
            p.setName(newName);
            p.setEmergencyContact(newEmergencyContact);
            p.setNrWeeks(newNrWeeks);
            p.setWeekAvailableFrom(newWeekAvailableFrom);
            p.setWeekAvailableUntil(newWeekAvailableUntil);
            p.setLodgeRequired(newLodgeRequired);
            try {
              BikeTourPlusPersistence.save();
            } catch (RuntimeException e) {
              return e.getMessage();
            }
            return "";
         }
      }
      
      // if code reaches here then participant was not found
      return "The participant account does not exist";
   }

   /**
    * This method adds a piece of gear or combo to a participant.
    * 
    * @param email - the email address of the participant
    * @param bookableItemName - the name of the piece of gear/combo to add
    * @return - an error message if any
    */
   public static String addBookableItemToParticipant(String email, String bookableItemName) {

      // email empty
      if (email == null || email.isEmpty()) {
         return "Email cannot be empty";
      }
      
      // bookableItem does not exist in the system
      BookableItem bookableItem = findBookableItem(bookableItemName);
      if (bookableItem == null) {
         return "The piece of gear or combo does not exist";
      }
	  
      for (Participant p : btp.getParticipants()) {
         if (p.getEmail().equals(email)) { // find corresponding participant
            for (BookedItem b : p.getBookedItems()) {
               if (b.getItem().getName().equals(bookableItem.getName())) { // check if participant already has the item to add
                  b.setQuantity(b.getQuantity()+1); // if so, just add quantity +1
                  try {
                    BikeTourPlusPersistence.save();
                  } catch (RuntimeException e) {
                    return e.getMessage();
                  }
                  return "";
               }
            }
            // if code reaches here then the participant did not already have the item before, so add item
            btp.addBookedItem(1, p, bookableItem);
            try {
              BikeTourPlusPersistence.save();
            } catch (RuntimeException e) {
              return e.getMessage();
            }
            return "";
         }
      }

      // if code reaches here then participant was not found
      return "The participant does not exist";
   }

   /**
    * This method removes a piece of gear or combo to a participant.
    * 
    * @param email - the email address of the participant
    * @param bookableItemName - the name of the piece of gear/combo to remove
    * @return - an error message if any
    */
   public static String removeBookableItemFromParticipant(String email, String bookableItemName) {

      // email empty
      if (email == null || email.isEmpty()) {
         return "Email cannot be empty";
      }

      for (Participant p : btp.getParticipants()) {
         if (p.getEmail().equals(email)) { // find corresponding participant
            for (BookedItem b : p.getBookedItems()) {
               if (b.getItem().getName().equals(bookableItemName)) { // find corresponding item to remove
                  b.setQuantity(b.getQuantity()-1); // reduce quantity by 1
                  if (b.getQuantity() < 1) { // check if quantity reaches 0
                     btp.removeBookedItem(b); // if so, remove bookableItem
                     b.delete();
                     try {
                       BikeTourPlusPersistence.save();
                     } catch (RuntimeException e) {
                       return e.getMessage();
                     }
                  }
                  return "";
               } 
            }
            // if code reaches here then the bookable item was not found
            return "The piece of gear or combo does not exist";
         }
      }

      // if code reaches here then participant was not found
      return "The participant does not exist";
   }
   
   
   
   /**
    * This helper method verifies the validity of the email.
    * 
    * @param email - the email address
    * @return - true if the email is valid, false if not
    */
   private static boolean validateEmail(String email) {
      boolean isValid = false;
      // check if '@' is in the email and if it is the only one
      if (email.indexOf("@") > 0 && email.indexOf("@") == email.lastIndexOf("@")) {
         // check if the last dot is located after '@' and if there is something before/after that dot
         if (email.indexOf("@") < email.lastIndexOf(".")-1 && email.lastIndexOf(".") < email.length()-1) {
            isValid = true; // email is valid if above conditions are met
         }
      }
      return isValid;
   }
   
   /**
    * This helper method finds if an email is already linked to a participant.
    * 
    * @param email - the email address
    * @return - true if the email is already linked to a participant, false if not
    */
   private static boolean findExistingEmailParticipant(String email) {
      boolean found = false;
      for (Participant p : btp.getParticipants()) { // iterate through the list of participants
         if (p.getEmail().equals(email)) { // find the corresponding participant
            found = true; // email is already used for a participant
            break;
         }
      }
      return found;
   }
   
   /**
    * This helper method finds if an email is already linked to a guide.
    * 
    * @param email - the email address
    * @return - true if the email is already linked to a guide, false if not
    */
   private static boolean findExistingEmailGuide(String email) {
      boolean found = false;
      for (Guide g : btp.getGuides()) { // iterate through the list of guides
         if (g.getEmail().equals(email)) { // find the corresponding guide
            found = true; // email is already used for a guide
            break;
         }
      }
      return found;
   }
   
   /**
    * This helper method finds if a bookableItem exists in the system.
    * 
    * @param bookableItemName - the name of the bookableItem
    * @return - the bookableItem object if found, returns null otherwise
    */
   private static BookableItem findBookableItem(String bookableItemName) {
      for (BookableItem b : btp.getGear()) {
         if (b.getName().equals(bookableItemName)) { // find the corresponding gear name
            return b;
         }
      }
      for (BookableItem b : btp.getCombos()) {
         if (b.getName().equals(bookableItemName)) { // find the corresponding combo name
            return b;
         }
      }
      return null; // null if item was not found
   }

}
		
