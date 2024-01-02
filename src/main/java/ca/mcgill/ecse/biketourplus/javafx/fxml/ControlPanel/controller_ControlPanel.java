package ca.mcgill.ecse.biketourplus.javafx.fxml.ControlPanel;

import java.time.LocalDate;
import java.sql.Date;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusGUIExtraController;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusStateMachineController;
import ca.mcgill.ecse.biketourplus.javafx.fxml.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class controller_ControlPanel {
  
  // Update Manager Password
  @FXML
  private TextField txt_NewPassword;
  
  // Update General Information
  @FXML
  private DatePicker date_StartDate;
  @FXML
  private Spinner<Integer> spin_NumWeeks;
  @FXML
  private Spinner<Integer> spin_GuidePrice;
  
  // Start BikeTours for week
  @FXML
  private Spinner<Integer> spin_WeekNo;
  
  /**
   * Initialize Control Panel
   */
  public void initialize() {
    
    // General information
    LocalDate initStartDate = BikeTourPlusGUIExtraController.getStartDate().toLocalDate();
    date_StartDate.setValue(initStartDate);
    
    int initNumWeeks = BikeTourPlusGUIExtraController.getNumWeeks();
    spin_NumWeeks.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 52));
    spin_NumWeeks.getValueFactory().setValue(initNumWeeks);
    
    int initGuidePrice = BikeTourPlusGUIExtraController.getGuidePrice();
    spin_GuidePrice.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
    spin_GuidePrice.getValueFactory().setValue(initGuidePrice);
    
    // Start all Tours for Week
    spin_WeekNo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, initNumWeeks));
    spin_WeekNo.getValueFactory().setValue(0);
    
  }
  
  /**
   * Update manager password.
   */
  public void updateManagerPassword(ActionEvent e) {
    
    // Get new password
    String newPassword = txt_NewPassword.getText();
    
    // Set new password
    String errMsg = BikeTourPlusFeatureSet1Controller.updateManager(newPassword);
    
    // Check for errors
    hasErrors(errMsg, "Password updated successfully");
    
    // Reset field
    txt_NewPassword.setText("");
    
  }
  
  /**
   * Update general information.
   */
  public void updateGeneralInformation(ActionEvent e) {
    
    // Check that all information was filled
    if(Date.valueOf(date_StartDate.getValue())      == null ||
       spin_NumWeeks.getValueFactory().getValue()   == null ||
       spin_GuidePrice.getValueFactory().getValue() == null ) {         // empty fields
      ViewUtils.makePopupWindow("Error", "All required fields must be filled");
      return;
    }
    
    // Get new information
    Date newStartDate = Date.valueOf(date_StartDate.getValue());
    int newNumWeeks = spin_NumWeeks.getValueFactory().getValue();
    int newGuidePrice = spin_GuidePrice.getValueFactory().getValue();
    
    // Set new information
    String errMsg = BikeTourPlusFeatureSet2Controller.updateBikeTourPlus(newStartDate, newNumWeeks, newGuidePrice);
    
    // Check for errors
    if(!hasErrors(errMsg, "General information updated succesfully")) {     // no errors
      spin_WeekNo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, newNumWeeks));
      spin_WeekNo.getValueFactory().setValue(0);
    }
    
  }
  
  /**
   * Initiate BikeTour creation process.
   */
  public void createBikeTours(ActionEvent e) {
    
    // Prevent if tours already created
    if(BikeTourPlusGUIExtraController.toursAlreadyCreated()) {
      ViewUtils.showError("Tours have already been created");;
      return;
    }
    
    // Create BikeTours
    String errMsg = BikeTourPlusStateMachineController.createBikeTours();
    
    // Check for errors
    hasErrors(errMsg, "BikeTours succesfully created");
    
  }
  
  /**
   * Start all trips for given week.
   */
  public void startTrips(ActionEvent e) {
    
    // Check that all information was filled
    if(spin_WeekNo.getValueFactory().getValue() == null) {          // empty field
      ViewUtils.makePopupWindow("Error", "All required fields must be filled");
      return;
    }
    
    // Get week number
    int startWeekNo = spin_WeekNo.getValueFactory().getValue();
    
    // Start Tours
    String errMsg = BikeTourPlusStateMachineController.startAllToursForWeek(startWeekNo);
    
    // Check for errors
    hasErrors(errMsg, "All Bike Tours started for Week " + Integer.toString(startWeekNo));
    
  }
  
  /**
   * Helper method: check for errors and create popup
   * 
   * @param errMsg - error message to check and potentially display
   * @param successMsg - message to display in case of success
   * 
   * @return errorFound - true for error, false for success
   */
  private boolean hasErrors(String errMsg, String successMsg) {
    if(errMsg.trim().length() == 0) {    // no error
      ViewUtils.makePopupWindow("Success", successMsg);
      return false;
    } else {        // error found
      ViewUtils.makePopupWindow("Error", errMsg);
      return true;
    }
  }
  
}
