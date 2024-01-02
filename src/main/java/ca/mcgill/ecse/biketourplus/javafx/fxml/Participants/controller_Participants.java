package ca.mcgill.ecse.biketourplus.javafx.fxml.Participants;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet3Controller;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusGUIExtraController;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusStateMachineController;
import ca.mcgill.ecse.biketourplus.controller.TOBookedItem;
import ca.mcgill.ecse.biketourplus.controller.TOCombo;
import ca.mcgill.ecse.biketourplus.controller.TOGear;
import ca.mcgill.ecse.biketourplus.controller.TOParticipant;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import ca.mcgill.ecse.biketourplus.javafx.fxml.ViewUtils;
import ca.mcgill.ecse.biketourplus.model.Participant;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.CheckBox;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ChoiceBox;

/**
 * 
 * @author William Zhang
 *
 */
public class controller_Participants {
	@FXML
	private TextField addParticipantNameTextField;
	@FXML
	private TextField ECITextField;
	@FXML
	private Button clearRPSectionButton;
	@FXML
	private Button RPButton;
	@FXML
	private TextField EmailRPTextField1;
	@FXML
	private TextField PasswordRPTextField11;
	@FXML
	private CheckBox LGRPCheckBox;
	@FXML
	private Spinner<Integer> WAFSpinner;
	@FXML
	private Spinner<Integer> WAUSpinner;
	@FXML
	private Spinner<Integer> NWSpinner;
	@FXML
	private ChoiceBox<TOParticipant> UPChoiceBox;
	@FXML
	private Button UPButton;
	@FXML
	private Button clearUPButton;
	@FXML
	private Button selectParticipantUpdateSectionButton;
	@FXML
	private CheckBox LGUPCheckBox;
	@FXML
	private Button AGUPButton;
	@FXML
	private Button ACUPButton;
	@FXML
	private Button RGUPButton3;
	@FXML
	private ChoiceBox<TOGear> AGUPChoiceBox1;
	@FXML
	private ChoiceBox<TOCombo> ACUPChoiceBox2;
	@FXML
	private ChoiceBox<TOBookedItem> RGUPChoiceBox3;
	@FXML
	private TextField NameUPTextField;
	@FXML
	private TextField EmailUPTextField;
	@FXML
	private TextField PasswordUPTextField;
	@FXML
	private TextField ECIUPTextField;
	@FXML
	private Spinner<Integer> WAFUPSpinned;
	@FXML
	private Spinner<Integer> WAUUPSpinner;
	@FXML
	private Spinner<Integer> NWUPSpinner;
	@FXML
	private Label TripStatusLabel;
	@FXML
	private TextField PayTextField;
	@FXML
	private Button PayButton;
	@FXML
	private Button CTButton;
	@FXML
	private Button FTButton;
	@FXML
	private ChoiceBox<TOParticipant> deleteParticipantChoiceBox;
	@FXML
	private Button deleteParticipantButton;
	
	private TOParticipant participantSelectedToUpdate;

	
	public void initialize() {
      UPChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e->{
      UPChoiceBox.setItems(ViewUtils.getParticipant());
      UPChoiceBox.setValue(null);
      });
      AGUPChoiceBox1.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e->{
      AGUPChoiceBox1.setItems(ViewUtils.getGear());
      AGUPChoiceBox1.setValue(null);
      });
      ACUPChoiceBox2.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e->{
      ACUPChoiceBox2.setItems(ViewUtils.getCombos());
      ACUPChoiceBox2.setValue(null);
      });
      RGUPChoiceBox3.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e->{
      RGUPChoiceBox3.setValue(null);
      });
 
      deleteParticipantChoiceBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e->{
      deleteParticipantChoiceBox.setItems(ViewUtils.getParticipant());
      deleteParticipantChoiceBox.setValue(null);
      });
      WAFSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,BikeTourPlusGUIExtraController.getNumWeeks()));
      WAFSpinner.getValueFactory().setValue(0);
      WAUSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,BikeTourPlusGUIExtraController.getNumWeeks()));
      WAUSpinner.getValueFactory().setValue(0);
      NWSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,BikeTourPlusGUIExtraController.getNumWeeks()));
      NWSpinner.getValueFactory().setValue(0);
      WAFUPSpinned.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,BikeTourPlusGUIExtraController.getNumWeeks()));
      WAFUPSpinned.getValueFactory().setValue(0);
      WAUUPSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,BikeTourPlusGUIExtraController.getNumWeeks()));
      WAUUPSpinner.getValueFactory().setValue(0);
      NWUPSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,BikeTourPlusGUIExtraController.getNumWeeks()));
      NWUPSpinner.getValueFactory().setValue(0);
      BikeTourPlusFxmlView.getInstance().registerRefreshEvent(deleteParticipantChoiceBox,UPChoiceBox,RGUPChoiceBox3, ACUPChoiceBox2, AGUPChoiceBox1);
}
	// Event Listener on Button[#clearRPSectionButton].onAction
	@FXML
	public void ClearRPClicked(ActionEvent event) {
	  addParticipantNameTextField.clear();
      ECITextField.clear();
      EmailRPTextField1.clear();
      PasswordRPTextField11.clear();
      WAFSpinner.getValueFactory().setValue(0);
      WAUSpinner.getValueFactory().setValue(0);
      LGRPCheckBox.setSelected(false);
      NWSpinner.getValueFactory().setValue(0);
	}
	// Event Listener on Button[#RPButton].onAction
	@FXML
	public void RPButtonClicked(ActionEvent event) {
	  String name = addParticipantNameTextField.getText();
      String eci = ECITextField.getText();
      String email = EmailRPTextField1.getText();
      String password = PasswordRPTextField11.getText();
      int waf = WAFSpinner.getValueFactory().getValue();
      int wau = WAUSpinner.getValueFactory().getValue();
      int nrWeeks = NWSpinner.getValueFactory().getValue();
      boolean lodge = LGRPCheckBox.isSelected();
      if (name==null||eci==null||email==null||password==null||waf==0||wau==0||nrWeeks==0) {
        ViewUtils.makePopupWindow("Error", "All required fields must be filled");
        return;
      }
      String errmsg = BikeTourPlusFeatureSet3Controller.registerParticipant(email, password, name, eci, nrWeeks, waf, wau, lodge);
      if (!hasErrors(errmsg, "Participant registered successfully")) {
      addParticipantNameTextField.clear();
      ECITextField.clear();
      EmailRPTextField1.clear();
      PasswordRPTextField11.clear();
      WAFSpinner.getValueFactory().setValue(0);
      WAUSpinner.getValueFactory().setValue(0);
      LGRPCheckBox.setSelected(false);
      NWSpinner.getValueFactory().setValue(0);
      BikeTourPlusFxmlView.getInstance().refresh();
      }
	}
	// Event Listener on Button[#UPButton].onAction
	@FXML
	public void UpdateParticipantClicked(ActionEvent event) {
	  String nameUP = NameUPTextField.getText();
      String eciUP = ECIUPTextField.getText();
      String emailUP = EmailUPTextField.getText();
      String passwordUP = PasswordUPTextField.getText();
      int wafUP = WAFUPSpinned.getValueFactory().getValue();
      int wauUP = WAUUPSpinner.getValueFactory().getValue();
      int nrWeeksUP = NWUPSpinner.getValueFactory().getValue();
      boolean lodgeUP = LGUPCheckBox.isSelected();
      if (nameUP==null||eciUP==null||emailUP==null||passwordUP==null||wafUP==0||wauUP==0||nrWeeksUP==0) {
        ViewUtils.makePopupWindow("Error", "All required fields must be filled");
        return;
      }
      
      String errmsg = BikeTourPlusFeatureSet3Controller.updateParticipant(emailUP, passwordUP, nameUP, eciUP, nrWeeksUP , wafUP, wauUP, lodgeUP);

      hasErrors(errmsg, "Participant updated successfully");
      NameUPTextField.clear();
      ECIUPTextField.clear();
      EmailUPTextField.clear();
      PasswordUPTextField.clear();
      WAFUPSpinned.getValueFactory().setValue(0);
      WAUUPSpinner.getValueFactory().setValue(0);
      LGUPCheckBox.setSelected(false);
      UPChoiceBox.setValue(null);
      TripStatusLabel.setText(null);
      NWUPSpinner.getValueFactory().setValue(0);
      AGUPChoiceBox1.setValue(null);
      ACUPChoiceBox2.setValue(null);
      RGUPChoiceBox3.setValue(null);
	}
	// Event Listener on Button[#clearUPButton].onAction
	@FXML
	public void clearUpdateParticipantClicked(ActionEvent event) {
	  NameUPTextField.clear();
      ECIUPTextField.clear();
      EmailUPTextField.clear();
      PasswordUPTextField.clear();
      WAFUPSpinned.getValueFactory().setValue(0);
      WAUUPSpinner.getValueFactory().setValue(0);
      LGUPCheckBox.setSelected(false);
      TripStatusLabel.setText(null);
      UPChoiceBox.setValue(null);
      NWUPSpinner.getValueFactory().setValue(0);
      AGUPChoiceBox1.setValue(null);
      ACUPChoiceBox2.setValue(null);
      RGUPChoiceBox3.setValue(null);
      
	}
	public void selectParticipantUpdateSection(ActionEvent event) {
	  participantSelectedToUpdate = UPChoiceBox.getValue();
      NameUPTextField.clear();
      ECIUPTextField.clear();
      EmailUPTextField.clear();
      PasswordUPTextField.clear();
      WAFUPSpinned.getValueFactory().setValue(0);
      WAUUPSpinner.getValueFactory().setValue(0);
      LGUPCheckBox.setSelected(false);
      NWUPSpinner.getValueFactory().setValue(0);
      TripStatusLabel.setText(null);
      AGUPChoiceBox1.setValue(null);
      ACUPChoiceBox2.setValue(null);
      RGUPChoiceBox3.setValue(null);
     
      if (UPChoiceBox.getValue() != null) {
        Participant participant2 = (Participant) Participant.getWithEmail(participantSelectedToUpdate.getParticipantEmail());
        NameUPTextField.appendText(participant2.getName());
        ECIUPTextField.appendText(participant2.getEmergencyContact());
        EmailUPTextField.appendText(participant2.getEmail());
        PasswordUPTextField.appendText(participant2.getPassword());
        if (participant2.getStatus()!= null) {
          TripStatusLabel.setText(participant2.getStatusFullName());
        }
        WAFUPSpinned.getValueFactory().setValue(participant2.getWeekAvailableFrom());
        WAUUPSpinner.getValueFactory().setValue(participant2.getWeekAvailableUntil());
        NWUPSpinner.getValueFactory().setValue(participant2.getNrWeeks());
        LGUPCheckBox.setSelected(participant2.getLodgeRequired());
        UPChoiceBox.setValue(participantSelectedToUpdate);
        RGUPChoiceBox3.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e->{
          if (ViewUtils.getParticipantBookedItems(participant2.getEmail()) != null) {
              RGUPChoiceBox3.setItems(ViewUtils.getParticipantBookedItems(participant2.getEmail()));
          }
          RGUPChoiceBox3.setValue(null);
        });
      BikeTourPlusFxmlView.getInstance().refresh();
      }
	}
	// Event Listener on Button[#AGUPButton].onAction
    @FXML
    public void AddGearUPClicked(ActionEvent event) {
      participantSelectedToUpdate = UPChoiceBox.getValue();
      if(participantSelectedToUpdate == null) return;
      String errmsg = BikeTourPlusFeatureSet3Controller.addBookableItemToParticipant(participantSelectedToUpdate.toString(), AGUPChoiceBox1.getValue().getNameGear());
      hasErrors(errmsg, "Gear added successfully");
      BikeTourPlusFxmlView.getInstance().refresh();
      UPChoiceBox.setValue(participantSelectedToUpdate);
    }
    // Event Listener on Button[#ACUPButton].onAction
    @FXML
    public void AddComboUPClicked(ActionEvent event) {
      participantSelectedToUpdate = UPChoiceBox.getValue();
      if(participantSelectedToUpdate == null) return;
      String errmsg = BikeTourPlusFeatureSet3Controller.addBookableItemToParticipant(participantSelectedToUpdate.toString(), ACUPChoiceBox2.getValue().getNameCombo());
      hasErrors(errmsg, "Combo added successfully");
      BikeTourPlusFxmlView.getInstance().refresh();
      UPChoiceBox.setValue(participantSelectedToUpdate);
    }
    // Event Listener on Button[#RGUPButton].onAction
    @FXML
    public void RemoveGearUPClicked(ActionEvent event) {
      participantSelectedToUpdate = UPChoiceBox.getValue();
      if(participantSelectedToUpdate == null) return;
      String errmsg = BikeTourPlusFeatureSet3Controller.removeBookableItemFromParticipant(participantSelectedToUpdate.toString(), RGUPChoiceBox3.getValue().getNameBI());
      hasErrors(errmsg, "Gear removed successfully");
      BikeTourPlusFxmlView.getInstance().refresh();
      UPChoiceBox.setValue(participantSelectedToUpdate);
    }
    // Event Listener on Button[#deleteParticipantButton].onAction
    @FXML
    public void deleteParticipantClicked(ActionEvent event) {
      if(deleteParticipantChoiceBox.getValue() == null) return;
      BikeTourPlusFeatureSet2Controller.deleteParticipant(deleteParticipantChoiceBox.getValue().getParticipantEmail());
      ViewUtils.makePopupWindow("Success","Participant deleted" );
      deleteParticipantChoiceBox.setValue(null);
      BikeTourPlusFxmlView.getInstance().refresh();
      
    }
    public void PayButtonClicked(ActionEvent event) {
      participantSelectedToUpdate = UPChoiceBox.getValue();
      if(participantSelectedToUpdate == null) return;
      String errmsg = "";
      Participant participant = (Participant) Participant.getWithEmail(participantSelectedToUpdate.toString());
      if (participant.getStatus() != null){
        if (PayTextField.getText() != null) {
          errmsg = BikeTourPlusStateMachineController.confirmPayForParticipant(participantSelectedToUpdate.toString(),PayTextField.getText());
        }
        else {
          errmsg = "Code is empty";
        }
      }
      else {
        errmsg = "Status is null";
      }
      hasErrors(errmsg, "Paid");
      BikeTourPlusFxmlView.getInstance().refresh();
      UPChoiceBox.setValue(participantSelectedToUpdate);
    }
    public void CTClicked(ActionEvent event) {
      participantSelectedToUpdate = UPChoiceBox.getValue();
      if(participantSelectedToUpdate == null) return;
      String errmsg = "";
      Participant participant = (Participant) Participant.getWithEmail(participantSelectedToUpdate.toString());
      if (participant.getStatus() != null) {
        if (!participant.getStatusFullName().equals("Finished")||!participant.getStatusFullName().equals("Banned")||!participant.getStatusFullName().equals("Cancelled")) {
          errmsg = BikeTourPlusStateMachineController.cancelTripForParticipant(participantSelectedToUpdate.toString());
        }
        else {
        errmsg = "Cannot cancel trip for a participant with status Finished, Banned or Cancelled";
        }
      }
      else {
        errmsg = "Status is null";
      }
     
      hasErrors(errmsg, "Cancelled Trip");
      BikeTourPlusFxmlView.getInstance().refresh();
      UPChoiceBox.setValue(participantSelectedToUpdate);
    }
    public void FTClicked(ActionEvent event) {
      participantSelectedToUpdate = UPChoiceBox.getValue();
      if(participantSelectedToUpdate == null) return;
      String errmsg = "";
      Participant participant = (Participant) Participant.getWithEmail(participantSelectedToUpdate.toString());
      if (participant.getStatus() != null) {
        if (participant.getStatusFullName().equals("Started")) {
          errmsg = BikeTourPlusStateMachineController.finishTourForParticipant(participantSelectedToUpdate.toString());
        }
        else {
          errmsg = "Cannot finish trip for a participant who has not started a trip";
        }
      }
      else {
        errmsg = "Status is null";
      }
      hasErrors(errmsg, "Finished Trip");
      BikeTourPlusFxmlView.getInstance().refresh();
      UPChoiceBox.setValue(participantSelectedToUpdate);
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
