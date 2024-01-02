package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Guide;
import ca.mcgill.ecse.biketourplus.model.Participant;

import java.util.List;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
public class BikeTourPlusFeatureSet4Controller {

  private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();

  private BikeTourPlusFeatureSet4Controller() {
    // Private constructor to prevent instantiation
  }

  /**
   * @author Faiza Chowdhury
   *     <p>This method will add a guide to the BikeTourPlus Application with the provided
   *     information. It checks that all the provided information fits required formats.
   * @param email - email address of the guide
   * @param password - the password provided
   * @param name - the name of the guide
   * @param emergencyContact - emergency contact information of the guide
   * @return error message (if encountered)
   */
  public static String registerGuide(
      String email, String password, String name, String emergencyContact) {
    String error = "";

    // check if email is empty

    if (email == null || email.isEmpty()) {
      return "Email cannot be empty";
    }

    // check if password is empty

    if (password == null || password.isEmpty()) {
      return "Password cannot be empty";
    }

    // check if name is empty

    if (name == null || name.isEmpty()) {
      return "Name cannot be empty";
    }

    // check if emergency contact info is empty

    if (emergencyContact == null || emergencyContact.isEmpty()) {
      return "Emergency contact cannot be empty";
    }

    // check if guide with this email already exists in the system

    Guide temp = findGuide(email);
    if (!(temp == null)) {
      return "Email already linked to a guide account";
    }

    // check if email is linked to any participant

    List<Participant> Participants =
        btp.getParticipants(); // Get access to all the existing participants
    Participant found = null;
    for (Participant current :
        Participants) { // Iterate through the list of all existing participants
      // If we find the given name of the participant in the list, it is stored in the variable
      // 'found'
      if (current.getEmail().equals(email)) {
        found = current;
      }
    }
    if (!(found == null)) {
      return "Email already linked to a participant account";
    }

    // check if email entered is the manager's

    if (email.equals("manager@btp.com")) {
      return "Email cannot be manager@btp.com";
    }

    // check if email contains any spaces
    if (email.contains(" ")) {
      return "Email must not contain any spaces";
    }

    // checking if email is in an acceptable format i.e "abc@def.ghi"

    if (!((email.contains("@"))
        && (email.indexOf("@") > 0)
        && (email.indexOf("@") == email.lastIndexOf("@"))
        && (email.indexOf("@") < email.lastIndexOf(".") - 1)
        && (email.lastIndexOf(".") < email.length() - 1))) {
      return "Invalid email";
    }

    // after everything has been verified to be in the correct format, the guide can be added to the
    // system

    try {
      btp.addGuide(email, password, name, emergencyContact);
      BikeTourPlusPersistence.save();  
    } catch (RuntimeException e) {
      error = e.getMessage();
    }
    return error;
  }

  /**
   * @author Faiza Chowdhury
   *     <p>This method allows information to be updated for a guide with the given email address
   * @param email - the email address of the guide, this is used to find the guide in the system
   * @param newPassword - the new password that the user wants
   * @param newName - new name that the user wants
   * @param newEmergencyContact - new emergency contact info that tha user wants
   * @return error message (if encountered any)
   */
  public static String updateGuide(
      String email, String newPassword, String newName, String newEmergencyContact) {
    String error = "";

    // check if email is empty

    if (email == null || email.isEmpty()) {
      return "Email cannot be empty";
    }

    // check if password is empty

    if (newPassword == null || newPassword.isEmpty()) {
      return "Password cannot be empty";
    }

    // check if name is empty

    if (newName == null || newName.isEmpty()) {
      return "Name cannot be empty";
    }

    // check if emergency contact info is empty

    if (newEmergencyContact == null || newEmergencyContact.isEmpty()) {
      return "Emergency contact cannot be empty";
    }

    // try to find the guide with the entered email

    Guide current = findGuide(email);

    if (current == null) {
      return "The guide account does not exist";
    } else {

      // Update the values (if no error arises)

      try {
        current.setName(newName);
        current.setPassword(newPassword);
        current.setEmergencyContact(newEmergencyContact);
        BikeTourPlusPersistence.save();
      } catch (RuntimeException e) {
        error = e.getMessage();
      }
      return error;
    }
  }

  /**
   * @author Faiza Chowdhury
   *     <p>This method deletes a guide from the system
   * @param email - the email address of the guide that user wants to be deleted
   */
  public static void deleteGuide(String email) {
    // try to find the guide with the entered email

    Guide current = findGuide(email);

    // If a guide with the entered email exists it will be deleted

    if (current != null) {
      current.delete();
      try {
        BikeTourPlusPersistence.save();
      } catch (RuntimeException e) {
        return;
      }
    }
  }

  /**
   * @author Faiza Chowdhury
   *     <p>This helper method finds a guide with the given email in the system (if they exist), and
   *     returns the Guide Object
   * @param email - email address of the Guide that we want
   * @return Guide- The Guide object we were looking for
   */
  private static Guide findGuide(String email) {
    List<Guide> Guides = btp.getGuides(); // Get access to all the existing guides
    Guide found = null;
    for (Guide currentGuide : Guides) { // Iterate through the list of all existing guides
      // If we find the given name of the guide in the list, it is stored in the variable 'found'
      if (currentGuide.getEmail().equals(email)) {
        found = currentGuide;
      }
    }
    return found;
  }
  
  /**
   * @author Qin Xuan Xu
   * Method used by ViewUtils
   */
  public static List<TOGuide> getGuides() {
    return btp.getGuides().stream().map(guide -> new TOGuide(guide.getName(), guide.getEmail(), guide.getPassword(), guide.getEmergencyContact())).toList();
  }
}
