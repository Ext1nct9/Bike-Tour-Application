package ca.mcgill.ecse.biketourplus.javafx.fxml.Guides;

import javafx.fxml.FXML;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet4Controller;
import ca.mcgill.ecse.biketourplus.javafx.fxml.ViewUtils;
import ca.mcgill.ecse.biketourplus.controller.TOGuide;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class page_GuidesController {
	@FXML
	private TableView<TOGuide> guideTable;
	@FXML
	private TableColumn<TOGuide,String> guideEmailTableColumn;
	@FXML
	private TableColumn<TOGuide,String> guideNameTableColumn;
	@FXML
	private TableColumn<TOGuide,String> guideContactTableColumn;
	@FXML
	private TextField registerGuideEmailTextField;
	@FXML
	private TextField registerGuideNameTextField;
	@FXML
	private TextField registerGuideContactTextField;
	@FXML
	private TextField registerGuidePasswordTextField;
	@FXML
	private Button registerGuideButton;
	@FXML
	private TextField updateGuideNameTextField;
	@FXML
	private TextField updateGuideContactTextField;
	@FXML
	private TextField updateGuidePasswordTextField;
	@FXML
	private ComboBox<TOGuide> updateGuideEmailComboBox;
	@FXML
	private Button updateGuideButton;
	@FXML
	private Button updateCancelGuideButton;
	@FXML
	private ComboBox<TOGuide> deleteGuideEmailComboBox;
	@FXML
	private Button deleteGuideButton;
	@FXML
	private Button updateGuideSelectButton;
	
	@FXML
	public void initialize() {
		guideEmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("Email")); // setup table columns
		guideNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		guideContactTableColumn.setCellValueFactory(new PropertyValueFactory<>("EmergencyContact"));
		
		// set refreshable elements
		guideTable.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> guideTable.setItems(ViewUtils.getGuide()));
		
		updateGuideEmailComboBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
			updateGuideEmailComboBox.setItems(ViewUtils.getGuide());
			updateGuideEmailComboBox.setValue(null);
		});
		
		deleteGuideEmailComboBox.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
			deleteGuideEmailComboBox.setItems(ViewUtils.getGuide());
			deleteGuideEmailComboBox.setValue(null);
		});
		
		BikeTourPlusFxmlView.getInstance().registerRefreshEvent(guideTable, updateGuideEmailComboBox, deleteGuideEmailComboBox);
//		BikeTourPlusFxmlView.getInstance().refresh();
	}

	// Event Listener on Button[#registerGuideButton].onAction
	@FXML
	public void registerGuideButtonClicked(ActionEvent event) {
		String email = registerGuideEmailTextField.getText();
		String name = registerGuideNameTextField.getText();
		String contact = registerGuideContactTextField.getText();
		String password = registerGuidePasswordTextField.getText();
		
		// if guide was successfully registered
		if (ViewUtils.callController(BikeTourPlusFeatureSet4Controller.registerGuide(email, password, name, contact))) {
			registerGuideEmailTextField.clear(); // clear the textfields
			registerGuideNameTextField.clear();
			registerGuideContactTextField.clear();
			registerGuidePasswordTextField.clear();
			BikeTourPlusFxmlView.getInstance().refresh();
		}
	}
	
	// Event Listener on Button[#updateGuideButton].onAction
	@FXML
	public void updateGuideButtonClicked(ActionEvent event) {
		try {
			String email = updateGuideEmailComboBox.getValue().getEmail();
			String name = updateGuideNameTextField.getText();
			String contact = updateGuideContactTextField.getText();
			String password = updateGuidePasswordTextField.getText();
			
			// if guide was successfully updated
			if (ViewUtils.callController(BikeTourPlusFeatureSet4Controller.updateGuide(email, password, name, contact))) {
				updateGuideEmailComboBox.setValue(null); // clear the textfields
				updateGuideNameTextField.clear();
				updateGuideContactTextField.clear();
				updateGuidePasswordTextField.clear();
				BikeTourPlusFxmlView.getInstance().refresh();
			}
		} catch (Exception e) { // email might not be selected in combobox
			ViewUtils.showError("Select an email first");
		}
	}
	
	// Event Listener on Button[#updateCancelGuideButton].onAction
	@FXML
	public void updateCancelGuideButtonClicked(ActionEvent event) {
		updateGuideEmailComboBox.setValue(null); // clear the textfields
		updateGuideNameTextField.clear();
		updateGuideContactTextField.clear();
		updateGuidePasswordTextField.clear();
	}
	
	// Event Listener on Button[#deleteGuideButton].onAction
	@FXML
	public void deleteGuideButtonClicked(ActionEvent event) {
		try {
			String email = deleteGuideEmailComboBox.getValue().getEmail();
			BikeTourPlusFeatureSet4Controller.deleteGuide(email);
			deleteGuideEmailComboBox.setValue(null);
			BikeTourPlusFxmlView.getInstance().refresh();
		} catch (Exception e) { // email might not be selected in combobox
			ViewUtils.showError("Select an email first");
		}
	}
	
	@FXML
	public void updateGuideSelectButtonClicked(ActionEvent event) {
		try {
			updateGuideNameTextField.setText(updateGuideEmailComboBox.getValue().getName());
			updateGuideContactTextField.setText(updateGuideEmailComboBox.getValue().getEmergencyContact());
			updateGuidePasswordTextField.setText(updateGuideEmailComboBox.getValue().getPassword());
		} catch (Exception e) {
			ViewUtils.showError("Select an email first");
		}
	}
	
}
