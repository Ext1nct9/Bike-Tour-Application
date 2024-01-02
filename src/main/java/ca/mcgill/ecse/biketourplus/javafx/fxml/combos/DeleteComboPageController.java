package ca.mcgill.ecse.biketourplus.javafx.fxml.combos;

import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import ca.mcgill.ecse.biketourplus.controller.TOCombo;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import ca.mcgill.ecse.biketourplus.javafx.fxml.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

public class DeleteComboPageController {
  @FXML
  private ChoiceBox<TOCombo> deleteComboChoiceBox;
  @FXML
  private Button deleteComboButton;
  @FXML
  private Text errorDisplayDeleteSection;
  
  @FXML
  public void initialize() {
    // Initialize combos for delete
    // the choice boxes listen to the refresh event
    deleteComboChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      deleteComboChoiceBox.setItems(ViewUtils.getCombos());
      // reset the choice
      deleteComboChoiceBox.setValue(null);
    });

    // let the application be aware of the refreshable node
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(deleteComboChoiceBox);
  }
  
  
  /** DELETE SECTION */

  // Helper method to display what's wrong in the deletion of combos
  private void displayErrorsDeleteSection(String errorsToDisplay)
  {
    errorDisplayDeleteSection.setText(errorsToDisplay);
  }

  // Delete combo
  @FXML
  public void deleteComboClicked(ActionEvent event) {
    String error = "";

    TOCombo combo = deleteComboChoiceBox.getValue();
    try {
      BikeTourPlusFeatureSet2Controller.deleteCombo(combo.getNameCombo());  // Cannot incorporate inside successful as this method doesn't return a String

      // Reset values (will reset the choice box for delete too)
      BikeTourPlusFxmlView.getInstance().refresh();
    }
    catch (NullPointerException e){
      error = "Please select a combo to delete.";
      displayErrorsDeleteSection(error);
    }
  }
}
