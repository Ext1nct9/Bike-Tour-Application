package ca.mcgill.ecse.biketourplus.controller;

import java.util.List;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.BookableItem;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.ComboItem;
import ca.mcgill.ecse.biketourplus.model.Gear;

public class BikeTourPlusFeatureSet5Controller {
  // Field

  private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();



  // Constructor

  private BikeTourPlusFeatureSet5Controller() {} // Set the constructor to private to prevent
                                                 // creation of instances of this class



  // Methods


  /**
   * @author Estefania Vazquez
   *
   * This method allows to add a gear with its corresponding name and price.
   * 
   * @param name - the name given to the gear we want to add
   * @param pricePerWeek - the price per week for renting the gear we want to add
   * @return - an error message (if any)
   */
  public static String addGear(String name, int pricePerWeek) {
    String error = "";

    // Empty name
    if (name == null || name.isEmpty()) {
      return "The name must not be empty";
    }
    
    // Repeated name with combo
    if (isComboName(name)) {
      return "A combo with the same name already exists";
    }
      
    // Price is negative
    if (pricePerWeek < 0) {
      return "The price per week must be greater than or equal to 0";
    }
    
    // Try creating a gear
    try {
      btp.addGear(name, pricePerWeek);  
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      error = e.getMessage();
      
      // Repeated name with gear
      if (error.startsWith("Cannot create due to duplicate name.")) {
        error = "A piece of gear with the same name already exists";
      }
    }
    
    return error; 
  }


  /**
   * @author Estefania Vazquez
   *
   * This method allows to update the name and the price per week of a gear that is currently in the
   * list of added gears.
   * 
   * @param oldName - the name with which the gear was added
   * @param newName - the new name we want to give to the gear
   * @param newPricePerWeek - the new price per week we want to give to the gear
   * @return - an error message (if any)
   */
  public static String updateGear(String oldName, String newName, int newPricePerWeek) {    
    // Old name does not exist
    if (existingGear(oldName) == null) {
      return "The piece of gear does not exist";
    }
    
    // New name is empty
    if (newName == null || newName.isEmpty()) {
      return "The name must not be empty";
    }
    
    // New name exists for a combo
    if (isComboName(newName)) {
      return "A combo with the same name already exists";
    }
    
    // New price is negative
    if (newPricePerWeek < 0) {
      return "The price per week must be greater than or equal to 0";
    }

    // Save the gear
    Gear gear = existingGear(oldName);
    
    // New name repeated with another gear
    if (!gear.setName(newName)) {
       return "A piece of gear with the same name already exists";
    }
    
    // If no errors, then we can update the price
    gear.setPricePerWeek(newPricePerWeek);
    try {
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    
    return "";
  }

  
  /**
   * @author Estefania Vazquez
   *
   * This method allows to delete a gear that is currently in the list of added gears.
   * 
   * @param name - the name of the gear that we want to delete
   * @return - an error message (if any)
   */
  public static String deleteGear(String name) {
    String error = "";
    Gear gear = existingGear(name);
    
    // No gear found (nothing happens)
    if (gear == null) {
      return error;
    }
    
    // Gear is found but cannot be deleted from a combo (i.e., would make the combo
    // have less than 2 different combo items)
    if (comboNeedsTheGear(gear)) {
        return "The piece of gear is in a combo and cannot be deleted";
    }
    
    // If a gear with the wanted name exists and we found no issue
    gear.delete();
    try {
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }
    return error;
  }
  
  
  /**
   * @author Estefania Vazquez
   *
   * Helper method to know if the given name already exists for a combo.
   * 
   * @param name - the name that wants to be given to the gear
   * @return - whether or not a combo with that name already exists
   */
  private static boolean isComboName(String name) {
    boolean isComboItem = false;
    BookableItem comboItem = BookableItem.getWithName(name);    // If an item with this name exists 
    
    // If the item was a combo
    if (comboItem instanceof Combo) {   
      isComboItem = true;
    }
    
    return isComboItem;
  }
  
  
  /**
   * @author Estefania Vazquez
   *
   * Helper method to know and find if there is a gear with the given name already existing.
   * 
   * @param name - the name of the gear we want to find
   * @return - the gear with the given name, if found
   */
  private static Gear existingGear(String name) {
    Gear gearFound = null;
    BookableItem itemFound = BookableItem.getWithName(name);
    
    if (itemFound instanceof Gear) {
      gearFound = (Gear) itemFound;
    }
    
    return gearFound;
  }
  
  
  /**
   * @author Estefania Vazquez
   *
   * Helper method to know if a gear can be removed from a combo as to respect the minimum 
   * number of combo items required.
   * 
   * @param gear - the gear we are looking to delete
   * @return - if we can delete a gear or not
   */
  private static boolean comboNeedsTheGear(Gear gear) {
    boolean comboNeedsGear = false;
    
    List<Combo> combos = btp.getCombos();
    for (Combo currentCombo : combos) { // Iterate through all the existing combos
      List<ComboItem> comboItems = currentCombo.getComboItems();
      for (ComboItem currentComboItem : comboItems) {    // Iterate through all the combo items of each combo
        if (currentComboItem.getGear().equals(gear)) { // If we found the given gear in one of the combos) 
          comboNeedsGear = true;
        }
      }
    }
    
    return comboNeedsGear;
  }
}

