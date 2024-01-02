package ca.mcgill.ecse.biketourplus.javafx.fxml.combos;

import static ca.mcgill.ecse.biketourplus.javafx.fxml.ViewUtils.successful;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet6Controller;
import ca.mcgill.ecse.biketourplus.controller.TOCombo;
import ca.mcgill.ecse.biketourplus.controller.TOGear;
import ca.mcgill.ecse.biketourplus.controller.TOGearInCombo;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import ca.mcgill.ecse.biketourplus.javafx.fxml.ViewUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class UpdateComboPageController {
  @FXML
  private ChoiceBox<TOCombo> updateComboChoiceBox;
  @FXML
  private Button selectComboUpdateSectionButton;
  @FXML
  private Button clearUpdateSectionButton;
  @FXML
  private Button doneButton;
  @FXML
  private Button updateNameDiscountButton;
  @FXML
  private TextField updateNameTextField;
  @FXML
  private Button updateNameButton;
  @FXML
  private TextField updateDiscountTextField;
  @FXML
  private Button updateDiscountButton;
  @FXML
  private ChoiceBox<TOGearInCombo> gearToUpdateChoiceBox;
  @FXML
  private Button selectGearToUpdateButton;
  @FXML
  private Spinner<Integer> updateGearQuantitySpinner;
  @FXML
  private ChoiceBox<TOGear> gearToAddChoiceBox;
  @FXML
  private Spinner<Integer> changeAddGearQuantitySpinner;
  @FXML
  private Button addGearUpdateSectionButton;
  @FXML
  private Text errorDisplayUpdateComboSection;

  private TOCombo comboSelectedToUpdate;    // Store the combo selected
  
  
  @FXML
  public void initialize() {
    // Initialize combos for update
    // the choice boxes listen to the refresh event
    updateComboChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      updateComboChoiceBox.setItems(ViewUtils.getCombos());
      // reset the choice
      updateComboChoiceBox.setValue(null);
    });

    // Initialize gear to add in update section
    gearToAddChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      gearToAddChoiceBox.setItems(ViewUtils.getGear());
      gearToAddChoiceBox.setValue(null);
    });
    
    // set something
    gearToUpdateChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      if (comboSelectedToUpdate == null) {
        gearToUpdateChoiceBox.setItems(FXCollections.observableList(new ArrayList<TOGearInCombo>()));
        return;
      }
      gearToUpdateChoiceBox.setItems(ViewUtils.getGearInCombo(comboSelectedToUpdate.getNameCombo()));
      gearToUpdateChoiceBox.setValue(null);
    });

    // let the application be aware of the refreshable node
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(updateComboChoiceBox, gearToUpdateChoiceBox, gearToAddChoiceBox);

    // Initialize spinners
    initializeSpinnerUpdateGearInCombo();
    initializeSpinnerAddGearToCombo();
  }
  
  /** UPDATE SECTION */

  // Helper method to initialize gear to update's spinner
  private void initializeSpinnerUpdateGearInCombo() {
    updateGearQuantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
    updateGearQuantitySpinner.getValueFactory().setValue(0);
  }

  // Helper method to initialize gear to add's spinner
  private void initializeSpinnerAddGearToCombo() {
    changeAddGearQuantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
    changeAddGearQuantitySpinner.getValueFactory().setValue(0);
  }

  // Helper method to reset the values
  private void resetComboValuesDisplayedUpdateSection() {
    updateNameTextField.setText("");
    updateDiscountTextField.setText("");
    updateGearQuantitySpinner.getValueFactory().setValue(0);
    changeAddGearQuantitySpinner.getValueFactory().setValue(0);
    errorDisplayUpdateComboSection.setText("");
    displayErrorsUpdateSection("");
    comboSelectedToUpdate = null;
    BikeTourPlusFxmlView.getInstance().refresh();
  }

  // Event listener for clear button
  @FXML
  public void clearUpdateSectionClicked(ActionEvent event) {
    resetComboValuesDisplayedUpdateSection();
  }

  // Helper method to display what's wrong in the updating of combos
  private void displayErrorsUpdateSection(String errorsToDisplay)
  {
    errorDisplayUpdateComboSection.setText(errorsToDisplay);
  }

  // Helper method to get the combo's info once a combo to update is selected
  @FXML
  public void selectComboUpdateSection(ActionEvent event) {
    // No combo selected
    if (updateComboChoiceBox.getValue() == null) {
      displayErrorsUpdateSection("Please select a combo to update.");
    }

    // Combo selected 
    else {
      // Save combo
      comboSelectedToUpdate = updateComboChoiceBox.getValue();

      // Name
      updateNameTextField.setText(comboSelectedToUpdate.getNameCombo());
      updateDiscountTextField.setText(Integer.toString(comboSelectedToUpdate.getDiscount()));

      
      

      // Refresh 
      BikeTourPlusFxmlView.getInstance().refresh();
      updateComboChoiceBox.setValue(comboSelectedToUpdate);
    }
  }

  // Update the name
  @FXML
  public void updateNameClicked(ActionEvent event) {
    // No combo selected
    if (updateComboChoiceBox.getValue() == null) {
      displayErrorsUpdateSection("Please select a combo to update.");
    }

    // Combo selected
    else {
      // Get data
      TOCombo combo = updateComboChoiceBox.getValue();  // Get the combo to modify
      String oldName = combo.getNameCombo();    // Get the old name
      Integer oldDiscount = combo.getDiscount();    // Get the old discount
      String name = updateNameTextField.getText(); // Get the name

      // Name deleted
      if (name == null || name.trim().isEmpty()) {
        ViewUtils.showError("Please input a valid combo name");
      }

      // Successful change
      else {
        if (!oldName.equals(name)) {
          comboSelectedToUpdate = new TOCombo(name, oldDiscount);  // Because we're going to change the name of the combo, so it won't be able to find the current one anymore
        }
        if (successful(BikeTourPlusFeatureSet6Controller.updateCombo(oldName, name, oldDiscount))) {
          // Reset values only if successful
          resetComboValuesDisplayedUpdateSection();
        }
      }
    }
  }

  // Update the discount
  @FXML
  public void updateDiscountClicked(ActionEvent event) {
    // No combo selected
    if (updateComboChoiceBox.getValue() == null) {
      displayErrorsUpdateSection("Please select a combo to update.");
    }

    // Combo selected
    else {
      TOCombo combo = updateComboChoiceBox.getValue();  // Get the combo to modify
      String name = combo.getNameCombo();    // Get the name

      try {
        Integer discount = Integer.parseInt(updateDiscountTextField.getText());    // Get the new discount 
        if (successful(BikeTourPlusFeatureSet6Controller.updateCombo(name, name, discount))) {
          // Reset values only if successful
          resetComboValuesDisplayedUpdateSection();
        }
      }

      catch (NumberFormatException e) {
        ViewUtils.showError("Please input a valid discount.");
      } 
    }
  }

  // Select the gear to update
  @FXML
  public void selectGearToUpdateClicked(ActionEvent event) {
    // No combo selected
    if (updateComboChoiceBox.getValue() == null) {
      displayErrorsUpdateSection("Please select a combo to update.\n");
    }

    // Combo selected but not gear
    else if (gearToUpdateChoiceBox.getValue() == null) {
      displayErrorsUpdateSection("Please select a gear to update.\n");
    }

    // Combo and gear selected
    else {
      updateGearQuantitySpinner.getValueFactory().setValue(gearToUpdateChoiceBox.getValue().getQuantity()); // Set the value of the spinner to the current quantity of the chosen gear in the combo
    } 
  }

  // Update the gear to update
  @FXML
  public void updateGearToUpdateClicked(ActionEvent event) {
    // No combo selected
    if (updateComboChoiceBox.getValue() == null) {
      displayErrorsUpdateSection("Please select a combo to update.\n");
    }

    // Combo selected but not gear
    else if (gearToUpdateChoiceBox.getValue() == null) {
      displayErrorsUpdateSection("Please select a gear to update.\n");
    }

    // Combo and gear selected
    else {
      TOCombo combo = updateComboChoiceBox.getValue();  // Get the combo to modify
      String comboName = combo.getNameCombo();    // Get the name of the combo
      TOGearInCombo gear = gearToUpdateChoiceBox.getValue();    // Get the gear to modify
      Integer oldGearQty = gear.getQuantity();  // Get the old quantity of gear
      Integer gearQty = updateGearQuantitySpinner.getValueFactory().getValue();    // Get the new value of the gear 

      // Delete gear
      if (gearQty < oldGearQty) {
        for (int i = oldGearQty; i > gearQty; i--) { // Start at the old quantity and remove gear until you get the wanted quantity
          if (successful(BikeTourPlusFeatureSet6Controller.removeGearFromCombo(gear.getNameGear(), comboName))) {

            // Reset values only if successful
            resetComboValuesDisplayedUpdateSection();
          }
        }
      }
      // Add gear (no error possible)
      if (gearQty > oldGearQty) {
        for (int i = oldGearQty; i < gearQty; i++) { // Start at the old quantity and add gear until you get the wanted quantity
          BikeTourPlusFeatureSet6Controller.addGearToCombo(gear.getNameGear(), comboName);
        }
        // Reset values only if successful
        resetComboValuesDisplayedUpdateSection();
      }
    }
  }

  // Add gear to combo
  @FXML
  public void addGearUpdateSectionClicked(ActionEvent event) {
    // No combo selected
    if (updateComboChoiceBox.getValue() == null) {
      displayErrorsUpdateSection("Please select a combo to update.\n");
    }

    // Combo selected but not gear
    else if (gearToAddChoiceBox.getValue() == null) {
      displayErrorsUpdateSection("Please select a gear to add.\n");
    }

    // Combo and gear selected
    else {
      TOCombo combo = updateComboChoiceBox.getValue();  // Get the combo to modify
      String comboName = combo.getNameCombo();    // Get the name of the combo
      TOGear gearToAdd = gearToAddChoiceBox.getValue();   // Get the gear chosen
      Integer gearToAddQty = changeAddGearQuantitySpinner.getValueFactory().getValue();    // Get the value of the gear 
      boolean gearAlreadyInCombo = false;

      // Create a list of the gear currently in the combo
      List<TOGearInCombo> gearInCombo = BikeTourPlusFeatureSet6Controller.getGearInCombo(comboName);
      Integer gearCurrentlyInCombo = 0;
      for (TOGearInCombo gear : gearInCombo) {
        if (gear.getNameGear().equals(gearToAdd.getNameGear())) {
          gearAlreadyInCombo = true;
          gearCurrentlyInCombo = gear.getQuantity();
        }
      }

      // If the gear is already added
      if (gearAlreadyInCombo) {
        if (gearToAddQty > 0) {  // To make sure that the gear has at least one unit 
          if (successful(BikeTourPlusFeatureSet6Controller.addGearToCombo(gearToAdd.getNameGear(), comboName))); {  // Combo is valid
            // Adjust the quantity of the gear
            for (int i = gearCurrentlyInCombo; i < gearToAddQty; i++) { // Start at the quantity of gear currently added to the combo
              successful(BikeTourPlusFeatureSet6Controller.addGearToCombo(gearToAdd.getNameGear(), comboName));
            }
            // Reset values only if successful
            resetComboValuesDisplayedUpdateSection();
          }
        }
        else if (gearToAddQty == 0) {
          displayErrorsUpdateSection("Please set the quantity of the gear to add to be greater than 0.\n");
        }
      }

      // If the gear has not yet been added
      else {
        if (gearToAddQty > 0) {  // To make sure that the gear has at least one unit 
          if (successful(BikeTourPlusFeatureSet6Controller.addGearToCombo(gearToAdd.getNameGear(), comboName))); {  // Combo is valid
            // Adjust the quantity of the gear
            for (int i = 1; i < gearToAddQty; i++) { // Start at 1 since you already added one quantity in the if
              successful(BikeTourPlusFeatureSet6Controller.addGearToCombo(gearToAdd.getNameGear(), comboName));
            }
            // Reset values only if successful
            resetComboValuesDisplayedUpdateSection();
          }
        }
        else if (gearToAddQty == 0) {
          displayErrorsUpdateSection("Please set the quantity of the gear to add to be greater than 0.\n");
        }
      }
    }
  }
}
