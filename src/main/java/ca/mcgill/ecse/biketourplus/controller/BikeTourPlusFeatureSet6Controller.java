package ca.mcgill.ecse.biketourplus.controller;

import java.util.List;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Gear;
import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.model.ComboItem;

public class BikeTourPlusFeatureSet6Controller {

	//Class variable to reference the BikeTourPlus model
	private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();	// class variable: reference BikeTourPlus model

	// Set the constructor to private to prevent instances from being made
	private BikeTourPlusFeatureSet6Controller() {}
	
	
   /**
    * @author Ali Khasawneh
    * This method adds a new combo to the list of combos currently in the system
    * @param name: the name of the combo we want to create (a String)
    * @param discount: the discount we want applied to this combo (an int)
    * @return any errors ecountered
    */
   public static String addCombo(String name, int discount) {
	   
	   //first check if the name inputed is blank
	   if (name == null || name.isEmpty()) {
		   return "The name must not be empty";
	   }
	   
	   //now check if there already is a combo with the same name
	   if (existingComboName(name)) {
		   return "A combo with the same name already exists";
	   }
	   
	   //check if discount entered is valid, i.e is greater than 0 but less than 100 
	   if (discount < 0) {
		   return "Discount must be at least 0";
	   }	   
	   
	   if (discount > 100) {
		   return "Discount must be no more than 100";
	   }
	   
	   //Check if there is a gear with the same name, if so alert the user
	   if (existingGear(name) != null) {
		   return "A piece of gear with the same name already exists";
	   }
	   
	  
	   //if no error is raised, then try adding the combo
	    try {
	      btp.addCombo(name, discount);
	      BikeTourPlusPersistence.save();
	    } catch (RuntimeException exc) {
	      return exc.getMessage();
	    }
	    
	    //if the try/catch block is succesful, then return the combo was added
	    
	    return "";
	  
   }
   
   
   /**
    * @author Ali Khasawneh
    * This method is used to check whether or not the name we want to input is already a name of another combo
    * @param name: the name we want to check
    * @return: return true if the name already exists, return false if the name does not exist
    */
   public static boolean existingComboName(String name) {
	   //get the list of combos
       List<Combo> Combos = btp.getCombos();
       //initialize the boolean exists to false, this is if no match is found, the method returns false
       boolean exists = false;
       //iterate through all the combos 
       for (Combo thisCombo : Combos) {
          //while iterating, if a match is found then update the value of the boolean exists to true
          if (thisCombo.getName().equals(name)) {
             exists = true;
          }
       }
       //return exists, which is true if a match is found and false if a match isn't found
       return exists;
    }
   
   
   
   /**
    * @author Ali Khasawneh
    * This method works to update an already existing combo
    * NOTE: I am working with the assumption that the newDiscount CAN EQUAL the discount of the combo we are updating
    * (in other words, the discount does not necessarily need to change)
    * @param oldName: the previous name of the combo we want to update/rename
    * @param newName: the new name we want to give to said combo
    * @param newDiscount: the new discount we want to apply to this combo
    * @return any errors encountered
    */
   public static String updateCombo(String oldName, String newName, int newDiscount) {
	   
	   //first perform similar checks to what was done for addCombo, starting by checking the validity of the new name
	   if (newName == null || newName.isEmpty()) {
		   return "The name must not be empty";
	   }
	   
	   //check if the oldName actually exists/is the name of an existing combo
	   if (!existingComboName(oldName)) {
		   return "The combo does not exist";
	   }

	   
	   //second we check that the newName is both not equal to the oldName, or a pre-existing combo name
	   if (existingComboName(newName) && !(newName.equals(oldName))) { // Fix by Estefania -> If we give the same current name, it means we don't want to change the name of the combo
		   return "A combo with the same name already exists";
	   }
	   
	   //Once again check if the input discount is valid
	   if (newDiscount > 100) {
		   return "Discount must be no more than 100";
	   } else if (newDiscount < 0) {
		   return "Discount must be at least 0";
	   }   
	   
	   //Check if there is a gear with the same name, if so alert the user
	   if (existingGear(newName) != null) {
		   return "A piece of gear with the same name already exists";
	   }
	  
	   //if no error was raised, then update the relevant fields
	   Combo combo = searchCombo(oldName);
	   combo.setName(newName); 
	   combo.setDiscount(newDiscount);
	   try {
         BikeTourPlusPersistence.save();
       } catch (RuntimeException e) {
         return e.getMessage();
       }
	   
	   //If no errors raised, return that the combo was updated 
	   return "";
   }

   
   
   
   
   /**
    * @author Ali Khasawneh
    * This combo is designed similarly to the existingComboName() method, but here it returns the current combo
    * @param name: the name of the combo we want to find
    * @return the combo with the name entered as an argument
    */
   private static Combo searchCombo(String name) {
	   //Get the list of all the existing gears
	   List<Combo> Combos = btp.getCombos(); 
	   //initiate a variable of type Combo to null, this will be updated if we find the combo (should be the case everytime this method is called)
	   Combo currentCombo = null;
	   //iterate through the combos
	   for (Combo thisCombo : Combos) {
	     //while iterating, once the currentcombo is found by comparing the names, it will update the variable currentCombo
	     if (thisCombo.getName().equals(name)) {  
	       currentCombo = thisCombo; 
	     }
	   }
	   //return the current combo 
	   return currentCombo;
	  }
   
   
   
   /**
    * @author Ali Khasawneh
    * This method adds a gear to a combo
    * @param gearName: the name of the gear we want to add to the combo
    * @param comboName: the name of the combo we want the above gear to be added to
    * @return any errors encountered
    */
   // this method does not need to be implemented by a team with five team members
   public static String addGearToCombo(String gearName, String comboName) {

	   //first we check that the combo in which the gear is to be added exists
	   if (!existingComboName(comboName)) {
		   return "The combo does not exist";
	   }
	   
	   //we then check that the gear we want to add exists or not
	   if (!existingGearName(gearName)) {
		   return "The piece of gear does not exist";
	   }
	   
	   //get the combo and the gear
	   Combo thisCombo = searchCombo(comboName);
	   Gear thisGear = existingGear(gearName);
	   
	   
	   
	   //we now want to check if the gear is already in the combo, if it is then increase the quantity by 1 
	   if (inCombo(thisGear,thisCombo)) {
           for (ComboItem b : thisCombo.getComboItems()) {
               if (b.getGear().equals(thisGear)) {
                   b.setQuantity(b.getQuantity()+1);
                   try {
                     BikeTourPlusPersistence.save();
                   } catch (RuntimeException e) {
                     return e.getMessage();
                   }
                   return "";
               }
           }
       }
	   
       btp.addComboItem(1, thisCombo, thisGear);
       try {
         BikeTourPlusPersistence.save();
       } catch (RuntimeException e) {
         return e.getMessage();
       }

	   
	   
	   //if no errors raised and the gear was added, then return gear item added to combo
      return "";
   }

   /**
    * @author Ali Khasawneh
    * NOTE: This method was taken from BikeTourPlusFeatureSet5Controller as it serves the EXACT same purpose in this controller
    * This method finds the gear with the input name
    * @param name: the name of the gear item we are checking
    * @return null if gear item does not exists. or the gear in question if it does exist
    */
   private static Gear existingGear(String name) {
	    List<Gear> gearList = btp.getGear(); // Get access to all the existing gears
	    Gear gearFound = null;
	    
	    for (Gear gear : gearList) {  // Iterate through the list of all existing gears
	      // If we find the given name of the gear in the list of existing gears, it means it exists
	      if (gear.getName().equals(name)) {  
	        gearFound = gear;   // Save the gear with the wanted name
	      }
	    }
	    
	    return gearFound;
	  }
   
   /**
    * @author Ali Khasawneh
    * NOTE: This is the exact same method as existingGearCombo() but this time iterates through the gears not combos
    * This method is used to check whether or not the name we want to input is already a name of another gear
    * @param name: the name we want to check
    * @return: return true if the name already exists, return false if the name does not exist
    */
   public static boolean existingGearName(String name) {
	   //get the list of gears
       List<Gear> Gears = btp.getGear();
       //initialize the boolean exists to false, this is if no match is found, the method returns false
       boolean exists = false;
       //iterate through all the combos 
       for (Gear thisGear : Gears) {
          //while iterating, if a match is found then update the value of the boolean exists to true
          if (thisGear.getName().equals(name)) {
             exists = true;
          }
       }
       //return exists, which is true if a match is found and false if a match isn't found
       return exists;
    }
   
   
   /**
    * @author Ali Khasawneh
    * This method checks if a certain gear is already in the combo
    * @param gear: the gear we want to look for
    * @param combo: the combo in which we are looking for the above gear
    * @return a Gear object if the gear is in the combo, null otherwise
    */
   public static boolean inCombo(Gear gear, Combo combo) {
	   boolean isIn = false;
	   //get the list of all the comboItems in the combo
	   List<ComboItem> comboItems = combo.getComboItems();
	   //Initialize the currentGear item to null, this will be changed if a gear item is found in the combo
	   //Iterate through all the items in the combo
	   for (ComboItem currentItem : comboItems) {
		   //if the gear we are looking for is found, update the value of currentGear
		   if (currentItem.getGear().equals(gear)) {
			   isIn = true;
		   }
	   }
	   //return the gear
	   return isIn;
   }
   
   
   /**
    * @author Ali Khasawneh
    * This method removes a gear item from a certain combo, provided the gear item and the combo exist and the gear is in the combo
    * @param gearName: the name of the gear we want to remove from the combo
    * @param comboName: the name of the combo we want the gear removed from
    */
   public static String removeGearFromCombo(String gearName, String comboName) {
	   //first we check that the combo and the gear both exist
	   

	   //first we check that the combo in which the gear is to be added exists
	   if (!existingComboName(comboName)) {
		   return "The combo does not exist";
	   }
	   
	   //we then check that the gear we want to add exists or not
	   if (!existingGearName(gearName)) {
		   return "Gear does not exist";
	   }
	  
	   //get the combo and the gear and the comboItem
	   Combo thisCombo = searchCombo(comboName);
	   Gear thisGear = existingGear(gearName);
	   
	   //check that there are at least 2 pieces of gear in the combo
	   if (thisCombo.getComboItems().size() < 2) {
		   return "A combo must have at least two pieces of gear";
	   }
	   if (existingComboName(comboName) && existingGearName(gearName)) {
		   //we then check that the gear is indeed in the combo
		   if (inCombo(thisGear,thisCombo)) {
			   for (ComboItem b : thisCombo.getComboItems()) {
                   if (b.getGear().equals(thisGear)) {
                	   //Decrease quantity by 1 
                       b.setQuantity(b.getQuantity()-1);
                       //Check again if the combo size is as required 
                       if (b.getQuantity() < 1) {
                           if (thisCombo.getComboItems().size() <= 2) {
                               b.setQuantity(b.getQuantity()+1);
                               return "A combo must have at least two pieces of gear";
                           }
                           btp.removeComboItem(b);
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
		   }
	   }
	   return "";
   }
   
   
   // ADDED by Estefania Vazquez to make the UI simpler
   
   public static List<TOGear> getGear() {
     return btp.getGear().stream().map(gear -> new TOGear(gear.getName(), gear.getPricePerWeek())).toList();
   }
   
   public static List<TOCombo> getCombos() {
     return btp.getCombos().stream().map(combo -> new TOCombo(combo.getName(), combo.getDiscount())).toList();
   }
	
   public static List<TOParticipant> getParticipant() {
     return btp.getParticipants().stream().map(participant -> new TOParticipant(participant.getEmail())).toList();
   }
   
   public static List<TOGearInCombo> getGearInCombo(String comboName) {
     Combo comboWanted = searchCombo(comboName);    // Point to the combo that we want
     int indexOfComboWanted = btp.indexOfCombo(comboWanted);    // Get the index of the combo to be able to access it from the btp
     return btp.getCombo(indexOfComboWanted).getComboItems().stream().map(item -> new TOGearInCombo(item.getGear().getName(), item.getQuantity(), item.getGear().getPricePerWeek())).toList();
   }
   
   public static List<TOBookedItem> getParticipantBookedItems(String email) {
     Participant part = (Participant) Participant.getWithEmail(email);
     if (part != null) {
     return part.getBookedItems().stream().map(bookeditem -> new TOBookedItem(bookeditem.getItem().getName(), bookeditem.getQuantity())).toList();
     }
    return null;
   }
   
   
}
