package ca.mcgill.ecse.biketourplus.controller;

import java.sql.Date;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;

public class BikeTourPlusGUIExtraController {
  
  private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();  // class variable: reference BikeTourPlus model

  /**
   * Constructor: prevent instantiation of this class
   */
  private BikeTourPlusGUIExtraController() {
      // Private constructor to prevent instantiation
  }
  
  /**
   * Getter: start date
   * 
   * @return startDate
   */
  public static Date getStartDate() {
    return btp.getStartDate();
  }
  
  /**
   * Getter: number of weeks
   * 
   * @return numWeeks
   */
  public static int getNumWeeks() {
    return btp.getNrWeeks();
  }
  
  /**
   * Getter: weekly price for guide
   * 
   * @return guidePrice
   */
  public static int getGuidePrice() {
    return btp.getPriceOfGuidePerWeek();
  }
  
  /**
   * Checker: tours already created
   */
  public static boolean toursAlreadyCreated() {
    return (btp.getBikeTours().size() == 0) ? false: true;
  }
  
}
