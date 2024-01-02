package ca.mcgill.ecse.biketourplus.javafx.fxml.Gear;

import ca.mcgill.ecse.biketourplus.controller.*;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import ca.mcgill.ecse.biketourplus.javafx.fxml.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import static ca.mcgill.ecse.biketourplus.javafx.fxml.ViewUtils.successful;

public class controller_Gear {

    @FXML
    private Button addGearButton;
    @FXML
    private Button addGearClearButton;
    @FXML
    private TextField addGearNameTextField;
    @FXML
    private TextField addGearPriceTextField;

    @FXML
    private Button addGearSetNameButton;
    @FXML
    private Button addGearSetPriceButton;
    @FXML
    private Button deleteGearButton;
    @FXML
    private ChoiceBox<TOGear> deleteGearChoiceBox;
    @FXML
    private Button updateGearButton;

    @FXML
    private ChoiceBox<TOGear> updateGearChoiceBox;

    @FXML
    private Button updateGearClearButton;


    @FXML
    private Button updateGearSelectOldNameButton;

    @FXML
    private Button updateGearSetNameButton;

    @FXML
    private TextField updateGearSetNameTextField;

    @FXML
    private Button updateGearSetPriceButton;

    @FXML
    private Text errorDisplayAddGearSection;
    @FXML
    private Text errorDisplayUpdateGearSection;
    @FXML
    private Text errorDisplayDeleteGearSection;

    @FXML
    private TextField updateGearSetPriceTextField;
    public void initialize() {
        // Initialize gears
        // the choice boxes listen to the refresh event
        deleteGearChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
            deleteGearChoiceBox.setItems(ViewUtils.getGear());
            // reset the choice
            deleteGearChoiceBox.setValue(null);
        });

        // Initialize gear 2
        // the choice boxes listen to the refresh event
        updateGearChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
            updateGearChoiceBox.setItems(ViewUtils.getGear());
            // reset the choice
            updateGearChoiceBox.setValue(null);
        });

        // let the application be aware of the refreshable node
        BikeTourPlusFxmlView.getInstance().registerRefreshEvent(deleteGearChoiceBox, updateGearChoiceBox);

    }



    // Event Listener on Button[#clear].onAction
    @FXML
    public void clearAddSectionClicked(ActionEvent event) {

        addGearNameTextField.setText("");
        addGearPriceTextField.setText("");
        errorDisplayAddGearSection.setText("");
        displayErrorsAddSection("");


    }

    @FXML
    public void clearUpdateSectionClicked(ActionEvent event) {
        updateGearSetNameTextField.setText(null);
        updateGearSetPriceTextField.setText("");
        updateGearChoiceBox.setValue(null);
        errorDisplayUpdateGearSection.setText("");
        displayErrorsUpdateSection("");

    }


    // Helper method to display what's wrong in the creation of combos
    private void displayErrorsAddSection(String errorsToDisplay)
    {
        errorDisplayAddGearSection.setText(errorsToDisplay);

    }

    // Helper method to display what's wrong in the creation of combos
    private void displayErrorsUpdateSection(String errorsToDisplay)
    {
        errorDisplayUpdateGearSection.setText(errorsToDisplay);
    }

    // Helper method to display what's wrong in the deletion of combos
    private void displayErrorsDeleteSection(String errorsToDisplay)
    {
        errorDisplayDeleteGearSection.setText(errorsToDisplay);
    }

    // To add the gear
    @FXML
    public void addGearClicked(ActionEvent event) {
        String error = "";

        // Name
        String name = addGearNameTextField.getText(); // Get the name
        if (name == null || name.trim().isEmpty()) {    // Check the name
            error += "Please input a valid gear name.\n";
        }

        //Price
        int gearPrice = 0;
        try {

            displayErrorsAddSection(error);
            error = "";
            gearPrice = Integer.parseInt(addGearPriceTextField.getText());


        }
        catch (NumberFormatException e){
            error += "Please input a valid price.";
            displayErrorsAddSection(error);
            error = "";
        }
        if (gearPrice == 0) {
            error += "Please input a valid price.";

            displayErrorsAddSection(error);
            error = "";
        } else {

            // Try creating the Gear
            if (successful(BikeTourPlusFeatureSet5Controller.addGear(name, gearPrice))) {

                // Reset the values
                addGearNameTextField.setText("");
                addGearPriceTextField.setText("");
                error = "";
                displayErrorsAddSection(error);
            }
        }
    }
    // To update the gear

    // Helper method to get the gear's info
    @FXML
    public void selectGearUpdateGearSection(ActionEvent event) {
      // If we have not selected a gear
      if (updateGearChoiceBox.getValue() == null) {
          displayErrorsUpdateSection("Please select a gear to update.");
      } else {
        displayErrorsUpdateSection("");
        TOGear gearToUpdate = updateGearChoiceBox.getValue();
        // get gear info
        updateGearSetNameTextField.setText(gearToUpdate.getNameGear());
        updateGearSetPriceTextField.setText(Integer.toString(updateGearChoiceBox.getValue().getWeeklyPriceGear()));



        updateGearChoiceBox.setValue(gearToUpdate);

        // BikeTourPlusFxmlView.getInstance().refresh();

      }
    }


    @FXML
    public void updateGearClicked(ActionEvent event) {
        BikeTourPlusFxmlView.getInstance().registerRefreshEvent(updateGearChoiceBox);

        // If we have not selected a gear
        if (updateGearChoiceBox.getValue() == null) {
            displayErrorsUpdateSection("Please select a gear to update.");
        } else {
            String error = "";


            // Name
            String oldName = updateGearChoiceBox.getValue().getNameGear();    // Get the old name
            String newName = updateGearSetNameTextField.getText(); // Get the new name, if any
            if (newName == null || newName.trim().isEmpty()) {    // Check the name
                error += "Please input a valid gear name.\n";
            }

            //Price
            int gearPrice = 0;
            try {
                displayErrorsUpdateSection(error);
                gearPrice = Integer.parseInt(updateGearSetPriceTextField.getText());
            } catch (NumberFormatException e) {
                error += "Please input a valid price.";
                displayErrorsUpdateSection(error);
            }

            if (gearPrice == 0) {
                error += "Please input a valid price.";

                displayErrorsAddSection(error);
                error = "";
            } else {
                // Try adding the Gear

                if (successful(BikeTourPlusFeatureSet5Controller.updateGear(oldName, newName, gearPrice))) {

                    // Reset the values
                    updateGearSetPriceTextField.setText("");
                    updateGearSetNameTextField.setText("");
                    updateGearChoiceBox.setValue(null);
                    error = "";
                    displayErrorsAddSection(error);
                }
            }
        }
    }

    // Delete gear
    @FXML
    public void deleteGearClicked(ActionEvent event) {
        String error = "";

        TOGear gear = deleteGearChoiceBox.getValue();
        try {
          displayErrorsDeleteSection("");
            error = BikeTourPlusFeatureSet5Controller.deleteGear(gear.getNameGear());
            deleteGearChoiceBox.setValue(null);
            successful(error);    // To refresh since we can't call successful on deleteGear since it doesn't return a String
        }
        catch (NullPointerException e){
            error = "Please select a gear";
            displayErrorsDeleteSection(error);
        }
    }
}
