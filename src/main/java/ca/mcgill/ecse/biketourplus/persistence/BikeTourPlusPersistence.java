package ca.mcgill.ecse.biketourplus.persistence;

import java.sql.Date;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Manager;


/**
 * This class was heavily inspired by Tutorial 07 (most of the code was copied and modified to fit
 * the BikeTourPlus project): https://github.com/F2022-ECSE223/ecse223-tutorials/wiki/Tutorial-07
 */
public class BikeTourPlusPersistence {

  private static String filename = "btp.data";
  private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.biketourplus");

  public static void setFilename(String filename) {
    BikeTourPlusPersistence.filename = filename;
  }
  
  /**
   * @author William Zhang
   * 
   *         This method saves the data of the BikeTourPlus application.
   */
  public static void save() {
    save(BikeTourPlusApplication.getBikeTourPlus());
  }

  /**
   * @author William Zhang
   * 
   *         This method saves the data of the BikeTourPlus application in the file.
   * 
   * @param btp - the BikeTourPlus we want to save
   */
  public static void save(BikeTourPlus btp) {
    serializer.serialize(btp, filename);
  }

  /**
   * @author William Zhang
   * 
   *         This method loads the data of the BikeTourPlus application.
   * 
   * @return - the BikeTourPlus that was previously saved, or a new one if there wasn't one
   *         previously saved
   */
  public static BikeTourPlus load() {
    var btp = (BikeTourPlus) serializer.deserialize(filename);
    // model cannot be loaded - create empty BTP
    if (btp == null) {
      btp = new BikeTourPlus(new Date(0L), 0, 0);
    } else {
      btp.reinitialize();
    }
    return btp;
  }
}
