package ca.mcgill.ecse.biketourplus.features;


import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusStateMachineController;
import ca.mcgill.ecse.biketourplus.model.BikeTour;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Gear;
import ca.mcgill.ecse.biketourplus.model.Guide;
import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.sql.Date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;

public class BikeToursStepDefinitions {

  private BikeTourPlus btp;
  private String error;
  private int errorCntr;

  /**
   * @author William Zhang
   * 
   *         This was taken directly from 'AddAndUpdateGearStepDefinitions' since it is the same
   *         step definition
   * 
   * @param dataTable
   */
  @Given("the following BikeTourPlus system exists:")
  public void the_following_bike_tour_plus_system_exists(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      Date startDate = Date.valueOf(row.get("startDate")); // Extract data from the Cucumber data
                                                           // table
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      int priceOfGuidePerWeek = Integer.parseInt(row.get("priceOfGuidePerWeek"));
      btp = BikeTourPlusApplication.getBikeTourPlus(); // Instantiate btp
      btp.setStartDate(startDate); // Setters
      btp.setNrWeeks(nrWeeks);
      btp.setPriceOfGuidePerWeek(priceOfGuidePerWeek);

      error = ""; // error counter
      errorCntr = 0;
    }
  }

  /**
   * @author Faiza Chowdhury
   *
   *         This method adds guides to the current btp instance
   *
   * @param dataTable - information of the guides to be added
   */
  @Given("the following guides exist in the system:")
  public void the_following_guides_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var guide : rows) {
      // add guides to the system by extracting data from the data table

      btp.addGuide(guide.get("email"), guide.get("password"), guide.get("name"),
          guide.get("emergencyContact"));
    }
  }

  /**
   * @author Faiza Chowdhury
   *
   *         This method adds participants to the current btp instance
   *
   * @param dataTable - information of the participants to be added
   */
  @Given("the following participants exist in the system:")
  public void the_following_participants_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (var participant : rows) {
      boolean lodgeRequired = Boolean.valueOf(participant.get("lodgeRequired"));
      // add participants to the system by extracting data from the data table
      // authorization code is initially null and refund percentage is initially 0
      btp.addParticipant(participant.get("email"), participant.get("password"),
          participant.get("name"), participant.get("emergencyContact"),
          Integer.parseInt(participant.get("nrWeeks")),
          Integer.parseInt(participant.get("weeksAvailableFrom")),
          Integer.parseInt(participant.get("weeksAvailableUntil")), lodgeRequired, null, 0);
    }
  }

  /**
   * @author Faiza Chowdhury
   *
   *         This method calls the required controller when the scenario described is encountered
   */
  @When("the administrator attempts to initiate the bike tour creation process")
  public void the_administrator_attempts_to_initiate_the_bike_tour_creation_process() {
    // call required controller method
    callController(BikeTourPlusStateMachineController.createBikeTours());
  }

  /**
   * @author Faiza Chowdhury
   *
   *         This method checks if the required bike tours are currently in the system
   *
   * @param dataTable - information of the bike tours we expect in the system
   */
  @Then("the following bike tours shall exist in the system:")
  public void the_following_bike_tours_shall_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    // get a list of all the bike tours currently in the system and a list of their id's
    List<BikeTour> actualTours = btp.getBikeTours();
    List<Integer> idList = new ArrayList<>();
    for (var t : actualTours) {
      idList.add(t.getId());
    }
    // the list of bike tours expected to be in the system
    List<Map<String, String>> rows = dataTable.asMaps();

    // check using the id's if each tour in the data table is in the system

    for (var tour : rows) {
      int id = Integer.parseInt(tour.get("id"));
      assertTrue(idList.contains(id));
    }

  }

  /**
   * @author Estefania Vazquez
   * 
   *         This method gets the state that a participant with a given email is in.
   * 
   * @param string - the email of the participant
   * @param string2 - the current state of the participant
   */
  @Then("the participant with email {string} shall be marked as {string}")
  public void the_participant_with_email_shall_be_marked_as(String string, String string2) {
    Participant wantedParticipant = null; // To store the wanted participant
    List<Participant> participants = btp.getParticipants(); // Obtain list of all participants
    // Iterate through all the participants
    for (Participant participant : participants) {
      // If we found the participant with the email we're looking for
      if (participant.getEmail().equals(string)) {
        wantedParticipant = participant;
        break;
      }
    }
    String participantState = wantedParticipant.getStatus().toString(); // Get the state of
                                                                               // the participant
                                                                               // (passed as enum)
    assertEquals(string2, participantState);
  }

  /**
   * @author Faiza Chowdhury
   *
   *         This method checks if the number of bike tours in system is correct
   *
   * @param string - the number of bike tours expected
   */
  @Then("the number of bike tours shall be {string}")
  public void the_number_of_bike_tours_shall_be(String string) {
    btp = BikeTourPlusApplication.getBikeTourPlus();

    // get the list of bike tours in the system and check the size of the list

    List tours = btp.getBikeTours();
    assertEquals(Integer.parseInt(string), tours.size());
  }

  /**
   * @author Ali Khasawneh
   * 
   *         This method verifies if there is an error.
   * 
   * @param string the error message
   */
  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String string) {
    assertTrue(error.contains(string));
  }

  /**
   * @author Estefania Vazquez
   * 
   *         This was taken directly from 'AddAndUpdateGearStepDefinitions' since it is the same
   *         step definition
   * 
   *         This method adds pieces of gear to *this* BikeTourPlus.
   * 
   * @param dataTable - the data table containing the elements to test
   */
  @Given("the following pieces of gear exist in the system:")
  public void the_following_pieces_of_gear_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name"); // Extract name of gear from Cucumber data table
      int pricePerWeek = Integer.parseInt(row.get("pricePerWeek")); // Extract price per week for
                                                                    // gear from Cucumber data table
      btp.addGear(name, pricePerWeek); // Add extracted data to the current btp
    }
  }

  /**
   * @author Ali Khasawneh
   * 
   *         Finds participant with certain email and sets their state to onCancel
   * 
   * @param string email of the participant
   */
  @Given("the participant with email {string} has cancelled their tour")
  public void the_participant_with_email_has_cancelled_their_tour(String string) {
    List<Participant> participants = btp.getParticipants();
    for (Participant participant : participants) {
      // find the participant
      if (participant.getEmail().equals(string)) {
        // cancel their tour
        participant.cancel();
      }
    }
  }

  /**
   * @author Qin Xuan Xu
   * 
   *         This method adds the given combos to the system
   *
   * @param dataTable - the data table containing the elements to test
   */
  @Given("the following combos exist in the system:")
  public void the_following_combos_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int discount = Integer.parseInt(row.get("discount"));
      String items = row.get("items");
      String quantity = row.get("quantity");
      String[] itemArray = items.split(",");
      String[] quantityArray = quantity.split(",");
      Combo combo = new Combo(name, discount, btp);

      for (int i = 0; i < itemArray.length; i++) {
        int q = Integer.parseInt(quantityArray[i]);
        for (var gear : btp.getGear()) {
          if (itemArray[i].equals(gear.getName())) {
            combo.addComboItem(q, btp, gear);
            break;
          }
        }
      }
    }
  }

  /**
   * @author Estefania Vazquez
   * 
   *         This method adds requested pieces of gears to the participants who requested them in
   *         *this* BikeTourPlus.
   * 
   * @param dataTable - the data table containing the elements to test
   */
  @Given("the following participants request the following pieces of gear:")
  public void the_following_participants_request_the_following_pieces_of_gear(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email"); // Extract email from Cucumber data table
      String gearRequested = row.get("gear"); // Extract gear name from Cucumber data table
      int quantityRequested = Integer.parseInt(row.get("quantity")); // Extract quantity of gear
                                                                     // from Cucumber data table

      List<Participant> participants = btp.getParticipants(); // Obtain list of all participants
      // Iterate through all the participants
      for (Participant participant : participants) {
        // If we found the participant with the email we're looking for
        if (participant.getEmail().equals(email)) {
          List<Gear> gearOffered = btp.getGear(); // Obtain the list of all the gear offered
          // Iterate through all the gear offered
          for (Gear gear : gearOffered) {
            // If the gear requested is the gear offered
            if (gear.getName().equals(gearRequested)) {
              participant.addBookedItem(quantityRequested, btp, gear);
              break;
            }
          }
          break;
        }
      }
    }
  }

  /**
   * @author Qin Xuan Xu
   * 
   *         This method assigns combos to the given participants
   * 
   * @param dataTable
   */
  @Given("the following participants request the following combos:")
  public void the_following_participants_request_the_following_combos(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String gear = row.get("gear");
      int quantity = Integer.parseInt(row.get("quantity"));

      for (Participant p : btp.getParticipants()) {
        if (p.getEmail().equals(email)) { // find the corresponding participant
          for (Gear g : btp.getGear()) {
            if (g.getName().equals(gear)) { // find the corresponding gear
              p.addBookedItem(quantity, btp, g);
              break;
            }
          }
          break;
        }
      }
    }
  }

  /**
   * @author Ali Khasawneh
   * 
   *         Finds participant associated with a given email and sets state to Banned
   * 
   * @param string email of participant
   */
  @Given("the participant with email {string} is banned")
  public void the_participant_with_email_is_banned(String string) {
    List<Participant> participants = btp.getParticipants();
    for (Participant participant : participants) {
      // find the participant
      if (participant.getEmail().equals(string)) {
        // flag as banned
        participant.assign(new BikeTour(10, 1, 2, new Guide("email1", "password", "name1", "emergencycontact", btp), btp));
        participant.startTrip(participant.getWeekAvailableFrom());
      }
    }
  }

  /**
   * @author Estefania Vazquez
   * 
   *         This method sets the state of a participant with a specific email to Started
   * 
   * @param string - the email of the participant
   */
  @Given("the participant with email {string} has started their tour")
  public void the_participant_with_email_has_started_their_tour(String string) {
    List<Participant> participants = btp.getParticipants(); // Obtain list of all participants
    // Iterate through all the participants
    for (Participant participant : participants) {
      // If we found the participant with the email wwe're looking for
      if (participant.getEmail().equals(string)) {
        participant.assign(new BikeTour(20, 1, 2, new Guide("email2", "password", "name2", "emergencycontact", btp), btp));
        participant.pay("yes");
        participant.startTrip(participant.getWeekAvailableFrom());
        break;
      }
    }
  }

  /**
   * @author Ali Khasawneh
   * 
   *         Finds participant associated with a given email and sets state to paid
   * 
   * @param string email of participant
   */
  @Given("the participant with email {string} has paid for their tour")
  public void the_participant_with_email_has_paid_for_their_tour(String string) {
    List<Participant> participants = btp.getParticipants();
    String participantState = null;
    for (Participant participant : participants) {
      // find the participant with required email
      if (participant.getEmail().equals(string)) {
        // if the participant has paid for his tour, we set his state to paid.
        participantState = participant.getStatus().toString();
        participant.pay("yes");
        break;
      }
    }
  }

  /**
   * @author Faiza Chowdhury
   * 
   *         This method adds required bike tours to the current btp instance
   * 
   * @param dataTable - the information of the bike tours that are expected to be in the system
   */
  @Given("the following bike tours exist in the system:")
  public void the_following_bike_tours_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    btp = BikeTourPlusApplication.getBikeTourPlus();
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var tour : rows) {

      // extract info from the data table

      int id = Integer.parseInt(tour.get("id"));
      int startWeek = Integer.parseInt(tour.get("startWeek"));
      int endWeek = Integer.parseInt(tour.get("endWeek"));

      // use the guide's email to find the Guide object

      String guideEmail = tour.get("guide");
      Guide guide = (Guide) User.getWithEmail(guideEmail);

      // create a bike tour with the info extracted

      BikeTour current = btp.addBikeTour(id, startWeek, endWeek, guide);

      // create an array from the participants list, and add them to the bike tour that was created

      String[] participants = tour.get("participants").split(",");

      for (String email : participants) {
        Participant p = (Participant) User.getWithEmail(email);
        current.addParticipant(p);
        p.assign(current);
      }
    }
  }

  /**
   * @author Estefania Vazquez
   * 
   *         This method sets a participant with a given email on the "Finished" state.
   * 
   * @param string - the email of the participant
   */
  @Given("the participant with email {string} has finished their tour")
  public void the_participant_with_email_has_finished_their_tour(String string) {
    List<Participant> participants = btp.getParticipants(); // Obtain list of all participants
    // Iterate through all the participants
    for (Participant participant : participants) {
      // If we found the participant with the email wwe're looking for
      if (participant.getEmail().equals(string)) {
        participant.assign(new BikeTour(30, 1, 2, new Guide("email3", "password", "name3", "emergencycontact", btp), btp));
        participant.pay("yes");
        participant.startTrip(participant.getWeekAvailableFrom());
        participant.finishTrip(); // Set participant on Finished state
        break;
      }
    }
  }

  /**
   * @author Ali Khasawneh
   * 
   *         This method calls the controller to try and cancel the tour for a participant.
   * 
   * @param string email of participant
   */
  @When("the manager attempts to cancel the tour for email {string}")
  public void the_manager_attempts_to_cancel_the_tour_for_email(String string) {
    // Call the controller and cancel the trip for the participant String
    callController(BikeTourPlusStateMachineController.cancelTripForParticipant(string));
  }

  /**
   * @author Faiza Chowdhury
   * 
   *         This method calls the required controller when the scenario described is encountered
   * @param string - email of the participant
   */
  @When("the manager attempts to finish the tour for the participant with email {string}")
  public void the_manager_attempts_to_finish_the_tour_for_the_participant_with_email(
      String string) {

    // call required controller method
    callController(BikeTourPlusStateMachineController.finishTourForParticipant(string));
  }

  /**
   * @author Faiza Chowdhury
   * 
   *         This method calls the required controller when the scenario described is encountered
   * 
   * @param string - the week number that the tours will be started for
   */
  @When("the manager attempts to start the tours for week {string}")
  public void the_manager_attempts_to_start_the_tours_for_week(String string) {

    // call required controller method
    callController(
        BikeTourPlusStateMachineController.startAllToursForWeek(Integer.parseInt(string)));
  }

  /**
   * @author Estefania Vazquez
   * 
   *         This method calls the controller to try and confirm payment for a participant with a
   *         given email with a specific authorization code.
   * 
   * @param string - the email of the participant we want to confirm payment
   * @param string2 - the authorization code to confirm payment
   */
  @When("the manager attempts to confirm payment for email {string} using authorization code {string}")
  public void the_manager_attempts_to_confirm_payment_for_email_using_authorization_code(
      String string, String string2) {
    callController(BikeTourPlusStateMachineController.confirmPayForParticipant(string, string2));
  }

  /**
   * @author Ali Khasawneh
   * 
   *         This method verifies that no participant with the given email exists.
   * 
   * @param string email of participant
   */
  @Then("a participant account shall not exist with email {string}")
  public void a_participant_account_shall_not_exist_with_email(String string) {
    List<Participant> participants = btp.getParticipants();
    for (Participant participant : participants) {
      assertFalse(participant.getEmail().equals(string));
    }
  }

  /*
   * @author Ali Khasawneh
   * 
   * This method makes sure the expected number of participants is the same as the outcome.
   * 
   * @param string number of participants
   */
  @Then("the number of participants shall be {string}")
  public void the_number_of_participants_shall_be(String string) {
    assertEquals(Integer.parseInt(string), btp.numberOfParticipants());

  }

  /**
   * @author Estefania Vazquez
   * 
   *         This method compares the expected refund percentage value that a participant with a
   *         given email has with the actual refund percentage value they have.
   * 
   * @param string - the email of the participant we want to add a refund to
   * @param string2 - the refund percentage value alloted
   */
  @Then("a participant account shall exist with email {string} and a refund of {string} percent")
  public void a_participant_account_shall_exist_with_email_and_a_refund_of_percent(String string,
      String string2) {
    Participant wantedParticipant = null; // To store the wanted participant
    List<Participant> participants = btp.getParticipants(); // Obtain list of all participants
    // Iterate through all the participants
    for (Participant participant : participants) {
      // If we found the participant with the email we're looking for
      if (participant.getEmail().equals(string)) {
        wantedParticipant = participant;
      }
    }
    int refundPercentage = wantedParticipant.getRefundedPercentageAmount(); // Get the refund
                                                                            // percentage of the
                                                                            // participant
    assertEquals(Integer.parseInt(string2), refundPercentage);
  }

  /**
   * @author Estefania Vazquez
   * 
   *         This method compares the expected authorization code that a participant with a given
   *         email has with the actual authorization code they have.
   * 
   * @param string - the email of the participant we want to add a confirmation code for
   * @param string2 - the authorization code
   */
  @Then("a participant account shall exist with email {string} and authorization code {string}")
  public void a_participant_account_shall_exist_with_email_and_authorization_code(String string,
      String string2) {
    Participant wantedParticipant = null; // To store the wanted participant
    List<Participant> participants = btp.getParticipants(); // Obtain list of all participants
    // Iterate through all the participants
    for (Participant participant : participants) {
      // If we found the participant with the email we're looking for
      if (participant.getEmail().equals(string)) {
        wantedParticipant = participant;
      }
    }
    String authorizationCode = wantedParticipant.getAuthorizationCode(); // Get authorization code
                                                                         // of the participant
    assertEquals(string2, authorizationCode);
  }


  /**
   * @author 20001LastOrder (but imported by Estefania Vazquez)
   * 
   *         It was taken from: https://github.com/F2022-ECSE223/ecse223-tutorials/wiki/Tutorial-06
   * 
   *         This method calls controller and sets error and counter.
   * 
   * @param result
   */
  private void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
      errorCntr += 1;
    }
  }
}
