package edu.nyu.wenjiechen.addressbook;

/**
 * Email address of a contact
 * 
 * @author Wenjie NYU
 * 
 */
public class Email {
  private final String title;
  private final String email;

  /**
   * private constructor of Email class
   * 
   * @param t
   *          title
   * @param e
   *          email
   */
  private Email(String t, String e) {
    if (t.equals("") || t == null) {
      title = "Work";
    } else {
      title = t;
    }
    email = e;
    formatCheck();
  }

  /**
   * create a new Email instance, title is "work"
   * 
   * @param e
   *          email
   * @return Email
   */
  public static Email newEmail(String e) {
    return new Email("", e);
  }

  /**
   * create a new Email instance
   * 
   * @param e
   *          email address
   * @param t
   *          title
   * @return Email
   */
  public static Email newEmail(String e, String t) {
    return new Email(t, e);
  }

  /**
   * create a new Empty Email instance
   * 
   * @return Email
   */
  public static Email newEmptyEmail() {
    return new Email("", "");
  }

  /**
   * check if the format of email is valid
   */
  private void formatCheck() {
    if (!email.equals("") && !email.contains("@")) {
      throw new IllegalArgumentException("email: " + email
          + " doesn't contain \"@\"");
    }
    String[] e = email.split("@");
    if (!email.equals("") && (e.length != 2 || e[0].equals(""))) {
      throw new IllegalArgumentException("email address format error:" + email);
    }
  }

  /**
   * get email address
   * 
   * @return String
   */
  public String getEmailAddress() {
    return email;
  }

  /**
   * get title of email
   * 
   * @return title of email
   */
  public String getEmailTitle() {
    return title;
  }

  /**
   * email of a contact. format likes, work email: wenting@gmail.com. The
   * representation is unspecified and subject to change.
   */
  @Override
  public String toString() {
    return String.format("%s Email: %s", title, email);
  }

  /**
   * email address is case insensitive and title doesn't impact the results of
   * equals
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Email)) {
      return false;
    }
    Email oth = (Email) o;
    return oth.email.equalsIgnoreCase(email);
  }

  /**
   * email address is case insensitive and title doesn't impact the results of
   * hashCode
   */
  @Override
  public int hashCode() {
    int res = 17;
    res = 31 * res + email.toLowerCase().hashCode();
    return res;
  }
}
