package ca.mcgill.ecse.biketourplus.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.AbstractDriver;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 * Serializes model elements to/from JSON.
 * 
 * This class was directly taken from:
 * https://github.com/F2022-ECSE223/ca.mcgill.ecse.btms/blob/5425826574c9b4934c9d5fa14f3890c0deac5769/src/main/java/ca/mcgill/ecse/btms/persistence/JsonSerializer.java
 */
public class JsonSerializer {

  private XStream xStream;

  static final int INDENT_WIDTH = 2;

  /**
   * @author William Zhang
   * 
   *         Constructs a JsonSerializer instance that can serialize domain model classes in the
   *         given package.
   */
  public JsonSerializer(String packageName) {
    this(packageName, new JettisonMappedXmlDriver());
  }

  /**
   * @author William Zhang
   * 
   *         Constructs a JsonSerializer and serializes the given driver.
   */
  private JsonSerializer(String packageName, AbstractDriver serializeDriver) {
    xStream = new XStream(serializeDriver);
    xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    // XStream blocks (de)serialization of all types for security reasons, except allowed types that
    // match this pattern
    xStream.allowTypesByWildcard(new String[] {packageName + ".**"});
    setupXStreamAliases(packageName);
  }

  /**
   * @author William Zhang
   * 
   *         Serializes the given object to the filename.
   */
  public void serialize(Object object, String filename) {
    try {
      var json = formatJsonString(xStream.toXML(object)) + "\n";
      Files.write(Paths.get(filename), json.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Could not save data to file '" + filename + "'.");
    }
  }

  /**
   * @author William Zhang
   * 
   *         Deserializes and returns the object stored in the given file.
   */
  public Object deserialize(String filename) {
    try {
      return xStream.fromXML(new File(filename));
    } catch (XStreamException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * @author William Zhang
   * 
   *         Sets up the XStream aliases for model classes to make the output JSON more
   *         human-friendly.
   */
  private void setupXStreamAliases(String packageName) {
    var modelPkg = packageName + ".model";
    try {
      ClassPath.from(getClass().getClassLoader()).getAllClasses().stream()
          // filter classes to only include those in the model package
          .filter(clsInfo -> clsInfo.getPackageName().equals(modelPkg))
          // establish 2-way mapping in XStream between model type name <-> its model class
          .forEach(clsInfo -> xStream.alias(clsInfo.getSimpleName(), clsInfo.load()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @author William Zhang
   * 
   *         Formats the input JSON string to make it easier for humans to read. Based on
   *         stackoverflow.com/a/46519130.<br>
   */
  private static String formatJsonString(final String json) {
    final char[] chars = json.toCharArray();
    final String newline = "\n"; // use this on all platforms

    String result = "";
    boolean beginQuotes = false;

    for (int i = 0, indent = 0; i < chars.length; i++) {
      char c = chars[i];
      if (c == '\"') {
        result += c;
        beginQuotes = !beginQuotes;
        continue; // skip the rest of the for loop body and continue execution at the next iteration
      }
      if (!beginQuotes) {
        result += switch (c) {
          case '{', '[' -> c + newline + String.format("%" + (indent += INDENT_WIDTH) + "s", "");
          case '}', ']' -> newline
              + ((indent -= INDENT_WIDTH) > 0 ? String.format("%" + indent + "s", "") : "") + c;
          case ':' -> c + " ";
          case ',' -> c + newline + (indent > 0 ? String.format("%" + indent + "s", "") : "");
          default -> "";
        };
        if (Character.isWhitespace(c) || "{}[]:,".contains(Character.toString(c))) {
          continue;
        }
      }
      result += c + (c == '\\' ? "" + chars[++i] : "");
    }

    return result;
  }

}
