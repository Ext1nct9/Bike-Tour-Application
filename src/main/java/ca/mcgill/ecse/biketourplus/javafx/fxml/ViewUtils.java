package ca.mcgill.ecse.biketourplus.javafx.fxml;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet3Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet4Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet5Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet6Controller;
import ca.mcgill.ecse.biketourplus.controller.TOCombo;
import ca.mcgill.ecse.biketourplus.controller.TOGear;
import ca.mcgill.ecse.biketourplus.controller.TOGearInCombo;
import ca.mcgill.ecse.biketourplus.controller.TOGuide;
import ca.mcgill.ecse.biketourplus.controller.TOParticipant;
import ca.mcgill.ecse.biketourplus.controller.TOParticipantsInTour;
import ca.mcgill.ecse.biketourplus.controller.TOBikeTour;
import ca.mcgill.ecse.biketourplus.controller.TOBikeTourStart;
import ca.mcgill.ecse.biketourplus.controller.TOBookedItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewUtils {
  /** Calls the controller and shows an error, if applicable. */
  public static boolean callController(String result) {
    if (result.isEmpty()) {
      BikeTourPlusFxmlView.getInstance().refresh();
      return true;
    }
    showError(result);
    return false;
  }

  /** Calls the controller and returns true on success. This method is included for readability. */
  public static boolean successful(String controllerResult) {
    return callController(controllerResult);
  }

  /**
   * Creates a popup window.
   *
   * @param title: title of the popup window
   * @param message: message to display
   */
  public static void makePopupWindow(String title, String message) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Text text = new Text(message);
    Button okButton = new Button("OK");
    okButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10; // inner padding/spacing
    int outerPadding = 100; // outer padding
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(text, okButton);
    Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
    dialog.setScene(dialogScene);
    dialog.setTitle(title);
    dialog.show();
  }

  public static void showError(String message) {
    makePopupWindow("Error", message);
  }

  
  public static ObservableList<TOGear> getGear() {
    List<TOGear> gear = BikeTourPlusFeatureSet6Controller.getGear();
    // as javafx works with observable list, we need to convert the java.util.List to
    // javafx.collections.observableList
    return FXCollections.observableList(gear);
  }
  
  public static ObservableList<TOBikeTour> getTour() {
	    List<TOBikeTour> tours = BikeTourPlusFeatureSet1Controller.getTour();
	    // as javafx works with observable list, we need to convert the java.util.List to
	    // javafx.collections.observableList
	    return FXCollections.observableList(tours);
	  }
  
  public static ObservableList<TOParticipantsInTour> getParticipants(int ID){
	  List<TOParticipantsInTour> participantsInTour = BikeTourPlusFeatureSet1Controller.getParticipants(ID);
	  return FXCollections.observableList(participantsInTour);
  }
  
  public static ObservableList<TOCombo> getCombos() {
    List<TOCombo> combos = BikeTourPlusFeatureSet6Controller.getCombos();
    // as javafx works with observable list, we need to convert the java.util.List to
    // javafx.collections.observableList
    return FXCollections.observableList(combos);
  }
  
  public static ObservableList<TOGearInCombo> getGearInCombo(String comboName) {
    List<TOGearInCombo> gearInCombo = BikeTourPlusFeatureSet6Controller.getGearInCombo(comboName);
    // as javafx works with observable list, we need to convert the java.util.List to
    // javafx.collections.observableList
    return FXCollections.observableList(gearInCombo);
  }
	
  public static ObservableList<TOGuide> getGuide() {
    List<TOGuide> guides = BikeTourPlusFeatureSet4Controller.getGuides();
    return FXCollections.observableList(guides);
  }
  
  public static ObservableList<TOParticipant> getParticipant() {
    List<TOParticipant> participant = BikeTourPlusFeatureSet6Controller.getParticipant();
    // as javafx works with observable list, we need to convert the java.util.List to
    // javafx.collections.observableList
    return FXCollections.observableList(participant);
  }
  
  public static ObservableList<TOBookedItem> getParticipantBookedItems(String email) {
    List<TOBookedItem>  pItems = BikeTourPlusFeatureSet6Controller.getParticipantBookedItems(email);
    // as javafx works with observable list, we need to convert the java.util.List to
    // javafx.collections.observableList
    if (pItems !=null) {
    return FXCollections.observableList(pItems);
    }
    return null;
  }
  
}
