package ca.mcgill.ecse.biketourplus.javafx.fxml;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class controller_MainPage {

  @FXML
  private Text txt_tabHeader; // header of current tab

  @FXML
  private AnchorPane ap_canvas; // canvas where current tab is displayed
  
  @FXML
  private ImageView img_bkground;

  public void initialize() {
    // bkground
    img_bkground.fitHeightProperty().bind(ap_canvas.heightProperty());
    img_bkground.fitWidthProperty().bind(ap_canvas.widthProperty());
    
    // Load control panel
    loadTab("ControlPanel/page_ControlPanel.fxml");
  }

  public void openControlPanel(MouseEvent e) {
    switchTab("Control Panel");
  }

  public void openBikeTours(MouseEvent e) {
    switchTab("Bike Tours");
  }

  public void openParticipants(MouseEvent e) {
    switchTab("Participants");
  }

  public void openGuides(MouseEvent e) {
    switchTab("Guides");
  }

  public void openGear(MouseEvent e) {
    switchTab("Gear");
  }

  public void openCombos(MouseEvent e) {
    switchTab("Combos");
  }
  
  /**
   * Close window
   */
  public void exit() {
    Stage stage = (Stage) ap_canvas.getScene().getWindow();
    stage.close();
  }

  /**
   * Helper method: load page
   * 
   * @param path - full path to target page
   */
  private void loadTab(String path) {
    
    // Try to load page
    Node newPage = null;
    try {
      newPage = FXMLLoader.load(getClass().getResource(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    // Prepare new page
    if(newPage != null) {
      newPage.setId("currentTab");
      newPage.setStyle("-fx-background-color: transparent;");
      AnchorPane.setBottomAnchor(newPage, 0.);
      AnchorPane.setLeftAnchor(newPage, 0.);
      AnchorPane.setRightAnchor(newPage, 0.);
      AnchorPane.setTopAnchor(newPage, 0.);
      ap_canvas.getChildren().add(newPage);
      BikeTourPlusFxmlView.getInstance().refresh();
    }
    
  }

  /**
   * Helper method: switch tabs
   * 
   * @param targetTab
   */
  private void switchTab(String targetTab) {
      
      // Return if already on targetTab
      if(targetTab.equals(txt_tabHeader.getText())) return;
      
      // Unload current tab
      ap_canvas.getChildren().remove(ap_canvas.lookup("#currentTab"));
      
      // Load target tab
      switch(targetTab) {
        case "Control Panel":
          loadTab("ControlPanel/page_ControlPanel.fxml");
          break;
        case "Bike Tours":
          loadTab("BikeTours/page_BikeTours.fxml");
          break;
        case "Participants":
          loadTab("Participants/page_Participants.fxml");
          break;
        case "Guides":
          loadTab("Guides/page_Guides.fxml");
          break;
        case "Gear":
          loadTab("Gear/page_Gear.fxml");
          break;
        case "Combos":
          loadTab("combos/ComboPage.fxml");
          break;
      }
      
      // Update tab header
      txt_tabHeader.setText(targetTab);
      
    }

}
