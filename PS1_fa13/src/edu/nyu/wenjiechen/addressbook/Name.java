package edu.nyu.wenjiechen.addressbook;

/**
 * The Name of a contact in a AddressBook, which is case-insensitive, namely
 * "Abb, CC" equals "abb, cc".
 * 
 * @author Wenjie Chen
 */
public class Name {
  private final String firstName;
  private final String lastName;

  /**
   * help to create a Name instance.
   * 
   * @author Wenjie Chen
   * 
   */
  public static class Builder {
    private String firstName = "";
    private String lastName = "";

    /**
     * constructor of Class Name.Builder
     */
    public Builder() {
    }

    /**
     * 
     * @param firstName
     *          set the firstName of a contact
     * @return Builder
     */
    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    /**
     * 
     * @param lastName
     *          set the lastName of a contact
     * @return Name.Builder
     */
    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    /**
     * 
     * @return an instance of a person's name
     */
    public Name build() {
      return new Name(this);
    }
  }

  /**
   * constructor of Name
   * 
   * @param Builder
   *          create a Name object by the builder.
   */
  private Name(Builder builder) {
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
  }

  /**
   * the static factory of Class Name creates a new Object while being invoked.
   * 
   * @param firstName
   *          first name of a contact
   * @param lastName
   *          last name of a contact
   * @return Name
   */
  public static Name newName(String firstName, String lastName) {
    return new Name.Builder().firstName(firstName).lastName(lastName).build();
  }

  /**
   * create an instance of a empty name.
   * 
   * @return
   */
  public static Name newEmptyName() {
    return new Name.Builder().build();
  }

  /**
   * 
   * @return first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * 
   * @return last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * return the full name of a contact. The format is: Last Name, First Name.
   * The representation is unspecified and subject to change.
   */
  @Override
  public String toString() {
    return String.format("Name: %s, %s", lastName, firstName);
  }

  /**
   * The strings of names are case-insensitive. It means "Shuang Zhou" equals
   * "shuang zhou".
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Name)) {
      return false;
    }
    Name otherName = (Name) o;
    return otherName.firstName.equalsIgnoreCase(firstName)
        && otherName.lastName.equalsIgnoreCase(lastName);
  }

  /**
   * This function is case-insensitive. "Shuang Zhou" and "shuang zhou" have the
   * same hashCode.
   */
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + firstName.toLowerCase().hashCode();
    result = 31 * result + lastName.toLowerCase().hashCode();
    return result;
  }
}