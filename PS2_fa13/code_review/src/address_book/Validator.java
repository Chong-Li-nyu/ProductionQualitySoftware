package address_book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Package private class for performing validation.
 * 
 * @author junyangxin (junyang.xin@nyu.edu)
 *
 */
class Validator {

  /**
   * Check for null argument.
   * 
   * @param arg An argument object.
   * @param argName The name of the argument.
   * @throws IllegalArgumentException If the argument is null.
   */
  static void checkNullArg(Object arg, String argName) {
    if (arg == null) {
      throw new IllegalArgumentException(argName + " can't be null");
    }
  }

  /**
   * Check for null or empty argument.
   * 
   * @param arg An argument object.
   * @param argName The name of the argument.
   * @throws IllegalArgumentException If the argument is null or empty.
   */
  static void checkNullOrEmptyArg(Object arg, String argName) {
    if (arg == null) {
      throw new IllegalArgumentException(argName + " can't be null or empty");
    }
  }

  /**
   * Validate a phone number's formate. A valid phone number should only
   * contain digits, dash (-), plus sign(+), hash sign(#), asterisk (*)
   * or spaces. 
   * 
   * @throws IllegalArgumentException If a number is null or invalid.
   */
  static void checkPhoneNumberFormate(String number) {
    if (number == null) {
      throw new IllegalArgumentException("Phone number can't be null");
    } else {
      Pattern p = Pattern.compile("^[0-9-+#*\\s]*$");
      Matcher m = p.matcher(number);
      if (!m.matches()) {
        throw new IllegalArgumentException("Invalid phone number");
      }
    }
  }
}
