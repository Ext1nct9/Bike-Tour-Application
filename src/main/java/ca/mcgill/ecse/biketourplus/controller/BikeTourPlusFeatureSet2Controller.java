package ca.mcgill.ecse.biketourplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Participant;

public class BikeTourPlusFeatureSet2Controller {
  
   private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus(); // class variable



   // Constructor

   private BikeTourPlusFeatureSet2Controller() {} // Set the constructor to private to prevent
                                                 // creation of instances of this class
   
   
   
   /**
    * Check that the inputs fit the constraints and then update if all the constraints are met. 
    * 
    * @param startDate - Season start date of BikeTourPlus.
    * @param nrWeeks - Number of weeks in the season.
    * @param priceOfGuidePerWeek - Weekly price of a guide.
    * @return - An error message if an error occurred, otherwise return "".
    */
   public static String updateBikeTourPlus(Date startDate, int nrWeeks, int priceOfGuidePerWeek) {
      if ((startDate.getYear()) < (btp.getStartDate().getYear())) {                   // Check if the year is accurate
        return "The start date cannot be from previous year or earlier";
      }
      if (nrWeeks < 0) {                    // Check if number of weeks is negative.
        return "The number of riding weeks must be greater than or equal to zero";
      }
      if (priceOfGuidePerWeek <0) {         // Check if the price of the guide is negative.
        return "The price of guide per week must be greater than or equal to zero";  
      }      
      btp.setStartDate(startDate);          // Set start date.
      btp.setNrWeeks(nrWeeks);              // If no errors, set number of weeks.
      btp.setPriceOfGuidePerWeek(priceOfGuidePerWeek);  // If no error, set price of guide.
      try {
        BikeTourPlusPersistence.save();
      } catch (RuntimeException e) {
        return e.getMessage();
      }
      return "";
   }
   
   
   /**
    * Check in the participants list if there is a participant with the email, then delete if there is one.
    * 
    * @param email - Participants ID, or email.
    */
   public static void deleteParticipant(String email) {
     Participant ptp = null; // Initialize variable
     for (var part : btp.getParticipants()){
       if (email.equals(part.getEmail())){ // Find participant
         ptp = part;
         
       }
     }
     if (ptp != null){                     // If there is a participant with the email, delete.
       ptp.delete();
       try {
         BikeTourPlusPersistence.save();
       } catch (RuntimeException e) {
         return;
       }
     }
    
   }

   
   /**
    * Check in the combos list if there is a combo with the name, then delete if there is one.
    * 
    * @param name - Name of the combo
    */
   // this method does not need to be implemented by a team with five team members
   public static void deleteCombo(String name) {
     Combo combo = null;      // Initialize
     for (var combo1 : btp.getCombos()) {
       if (name.equals(combo1.getName())) {     // Find participant
         combo = combo1;
       }
     }
     if (combo != null) {                   // Delete if there is a combo with the desired name.
       combo.delete();
       try {
         BikeTourPlusPersistence.save();
       } catch (RuntimeException e) {
         return;
       }
     }
   }

}
