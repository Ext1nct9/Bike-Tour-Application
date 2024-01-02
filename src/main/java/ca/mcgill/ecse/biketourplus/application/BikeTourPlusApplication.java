package ca.mcgill.ecse.biketourplus.application;

import java.sql.Date;
import java.time.LocalDate;
import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
import javafx.application.Application;

public class BikeTourPlusApplication {

  private static BikeTourPlus btp;
  
  public static final boolean DARK_MODE = false;

  private static Date currentDate;

  
  public static void main(String[] args) {
    // Start UI
    Application.launch(BikeTourPlusFxmlView.class, args);
  }
  
  public static BikeTourPlus getBikeTourPlus() {
    if (btp == null) {
      // these attributes are default, you should set them later with the setters
      btp = BikeTourPlusPersistence.load();
    }
    return btp;
  }
  
  public static Date getCurrentDate() {
    if (currentDate == null) {
      return Date.valueOf(LocalDate.now());
    }
    return currentDate;
  }

  public static void setCurrentDate(Date date) {
    currentDate = date;
  }
}

