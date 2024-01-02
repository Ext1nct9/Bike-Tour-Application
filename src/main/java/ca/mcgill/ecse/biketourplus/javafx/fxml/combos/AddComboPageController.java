package ca.mcgill.ecse.biketourplus.javafx.fxml.combos;

import static ca.mcgill.ecse.biketourplus.javafx.fxml.ViewUtils.successful;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet6Controller;
import ca.mcgill.ecse.biketourplus.controller.TOGear;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import ca.mcgill.ecse.biketourplus.javafx.fxml.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddComboPageController {
  @FXML
  private TextField addComboNameTextField;
  @FXML
  private TextField addDiscountTextField;
  @FXML
  private ChoiceBox<TOGear> gear1ChoiceBox;
  @FXML
  private ChoiceBox<TOGear> gear2ChoiceBox;
  @FXML
  private Spinner<Integer> changeGear1QuantitySpinner;
  @FXML
  private Spinner<Integer> changeGear2QuantitySpinner;
  @FXML
  private Button clearAddSectionButton;
  @FXML
  private Button addComboButton;
  @FXML
  private Text errorDisplayAddComboSection;
  
  
  @FXML
  public void initialize() {
    // Initialize gear 1
    // the choice boxes listen to the refresh event
    gear1ChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      gear1ChoiceBox.setItems(ViewUtils.getGear());
      // reset the choice
      gear1ChoiceBox.setValue(null);
    });

    // Initialize gear 2 
    // the choice boxes listen to the refresh event
    gear2ChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      gear2ChoiceBox.setItems(ViewUtils.getGear());
      // reset the choice
      gear2ChoiceBox.setValue(null);
    });

    // let the application be aware of the refreshable node
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(gear1ChoiceBox, gear2ChoiceBox);

    // Initialize spinners
    initializeSpinnerGear1();
    initializeSpinnerGear2();
  }



  /** ADD COMBO SECTION */

  // Helper method to initialize gear 1's spinner
  private void initializeSpinnerGear1() {
    changeGear1QuantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
    changeGear1QuantitySpinner.getValueFactory().setValue(0);
  }

  // Helper method to initialize gear 2's spinner
  private void initializeSpinnerGear2() {
    changeGear2QuantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
    changeGear2QuantitySpinner.getValueFactory().setValue(0);
  }

  // Helper method to reset the values entered in add section
  private void resetValuesEnteredAddSection() {
    addComboNameTextField.setText("");
    addDiscountTextField.setText("");
    changeGear2QuantitySpinner.getValueFactory().setValue(0);
    changeGear1QuantitySpinner.getValueFactory().setValue(0);
    errorDisplayAddComboSection.setText("");
    displayErrorsAddSection("");
  }

  // Event listener for clear button
  @FXML
  public void clearAddSectionClicked(ActionEvent event) {
    resetValuesEnteredAddSection();
  }

  // Helper method to display what's wrong in the creation of combos
  private void displayErrorsAddSection(String errorsToDisplay)
  {
    errorDisplayAddComboSection.setText(errorsToDisplay);
  }

  // To add the combo
  @FXML
  public void addComboClicked(ActionEvent event) {
    String error = "";


    // Name
    String name = addComboNameTextField.getText(); // Get the name
    if (name == null || name.trim().isEmpty()) {    // Check the name
      error += "Please input a valid combo name.\n";
    }


    // Gear

    // Gear 1
    TOGear gear1 = gear1ChoiceBox.getValue();   // Get the gear chosen
    if (gear1 == null) {    // If didn't select a gear 1
      error += "Please select Gear 1.\n";
    }
    Integer gear1Qty = changeGear1QuantitySpinner.getValueFactory().getValue();    // Get the value of the gear 1
    if (gear1 != null && gear1Qty < 1) {   // If gear quantity is set to 0
      error += "Please set the quantity of Gear 1 to be greater than 0.\n"; 
    }

    // Gear 2
    TOGear gear2 = gear2ChoiceBox.getValue();   // Get the gear chosen
    if (gear2 == null) {    // If didn't select a gear 2
      error += "Please select Gear 2.\n";
    }
    Integer gear2Qty = changeGear2QuantitySpinner.getValueFactory().getValue();    // Get the value of the gear 2
    if (gear2 != null && gear2Qty < 1) {   // If gear quantity is set to 0
      error += "Please set the quantity of Gear 2 to be greater than 0.\n"; 
    }

    if (gear1 != null && gear2 != null && gear1.toString().equals(gear2.toString())) {   // If selected the same gear to create the combo (need at least to different gear to create a combo)
      error += "Please choose different types of gear.\n"; 
    }


    // Discount
    try {
      displayErrorsAddSection(error);
      int discount = Integer.parseInt(addDiscountTextField.getText());   // Get the discount
      // Try creating the Combo
      if (gear1Qty > 0 && gear2Qty > 0 && !(gear1.toString().equals(gear2.toString()))) {  // To make sure that both gears have at least one unit and that they aren't the same gear (isn't handled by the controller)
        if (successful(BikeTourPlusFeatureSet6Controller.addCombo(name, discount)) 
            && successful(BikeTourPlusFeatureSet6Controller.addGearToCombo(gear1.getNameGear(), name)) 
            && successful(BikeTourPlusFeatureSet6Controller.addGearToCombo(gear2.getNameGear(), name))); {  // Combo is valid
              // Adjust the quantity of each gear

              // Gear 1
              for (int i = 1; i < gear1Qty; i++) { // Start at 1 since you already added one quantity in the if
                BikeTourPlusFeatureSet6Controller.addGearToCombo(gear1.getNameGear(), name);
              }

              // Gear 2
              for (int i = 1; i < gear2Qty; i++) { // Start at 1 since you already added one quantity in the if
                BikeTourPlusFeatureSet6Controller.addGearToCombo(gear2.getNameGear(), name);
              }

              // Reset the values
              resetValuesEnteredAddSection();
            }
      }
    }
    catch (NumberFormatException e) {
      error += "Please input a valid discount number.";
      displayErrorsAddSection(error);
    }
  }
}
