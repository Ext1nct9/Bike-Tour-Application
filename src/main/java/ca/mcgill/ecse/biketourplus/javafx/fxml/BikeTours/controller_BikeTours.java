package ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTours;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import ca.mcgill.ecse.biketourplus.controller.TOBikeTour;
import ca.mcgill.ecse.biketourplus.controller.TOGuide;
import ca.mcgill.ecse.biketourplus.controller.TOParticipant;
import ca.mcgill.ecse.biketourplus.controller.TOParticipantCost;
import ca.mcgill.ecse.biketourplus.controller.TOParticipantsInTour;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import ca.mcgill.ecse.biketourplus.javafx.fxml.ViewUtils;
import ca.mcgill.ecse.biketourplus.model.User;
import javafx.event.ActionEvent;

import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;

import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ChoiceBox;

public class controller_BikeTours {
  @FXML
  private ChoiceBox<TOBikeTour> tourChoice;
  @FXML
  private Button selectButton;

  @FXML
  private Label guideMail;

  @FXML
  private Label guidePrice;
  @FXML
  private ListView<TOParticipantsInTour> partList;
  @FXML
  private Label partName;
  @FXML
  private Label emergencyContact;
  @FXML
  private Label partCost;
  @FXML
  private Label partStatus;
  @FXML
  private Label partCode;
  @FXML
  private Label partDiscount;
  @FXML
  private Label itemCost;

  @FXML
  Label guideName;


  @FXML
  public void initialize() {
    tourChoice.addEventHandler(BikeTourPlusFxmlView.REFRESH_EVENT, e -> {
      tourChoice.setItems(ViewUtils.getTour());
      tourChoice.setValue(null);
    });
    BikeTourPlusFxmlView.getInstance().registerRefreshEvent(tourChoice);

  }



  // Event Listener on Button[#selectButton].onAction
  @FXML
  public void buttonSelected(ActionEvent event) {
    if (tourChoice.getValue() == null) {
      ViewUtils.showError("Please select a tour.\n");
    }
    
    else {
    int nmbrWeeks = (tourChoice.getValue().getEndWeek() - tourChoice.getValue().getStartWeek()) + 1;
    guideMail.setText(tourChoice.getValue().getGuideEmail());
    guideName.setText(tourChoice.getValue().getGuideName());
    guidePrice.setText(Integer.toString(tourChoice.getValue().getTotalCostForGuide() * nmbrWeeks));



    partList.setItems(ViewUtils.getParticipants(tourChoice.getValue().getId()));

    }
  }

  @FXML
  public void partList(MouseEvent event) {

    partName.setText(partList.getSelectionModel().getSelectedItem().getParticipantUsername());
    emergencyContact.setText(partList.getSelectionModel().getSelectedItem().getEmergencyContact());

    String email = partList.getSelectionModel().getSelectedItem().getParticipantEmail();

    TOBikeTour test = BikeTourPlusFeatureSet1Controller.getBikeTour(tourChoice.getValue().getId());


    for (TOParticipantCost whatever : test.getParticipantCosts()) {
      if (whatever.getParticipantEmail().equals(email)) {
        partCost.setText(Integer.toString(whatever.getTotalCostForBikingTour()));
        itemCost.setText(Integer.toString(whatever.getTotalCostForBookableItems()));
        break;
      }
    }

    partStatus.setText(partList.getSelectionModel().getSelectedItem().getStatus());
    partCode.setText(partList.getSelectionModel().getSelectedItem().getAuthCode());
    partDiscount
        .setText(Integer.toString(partList.getSelectionModel().getSelectedItem().getDiscount()));



  }



}
