package address_book;

/**
 * The class that represents a contact. The instances of this class
 * is immutable.
 * 
 * @author junyangxin (junyang.xin@nyu.edu)
 *
 */
public class Contact {
  private String name;
  private Address address;
  private String number;
  private String email;
  private String note;

  /**
   * The Builder class for creating a Contact instance.
   * 
   * @author junyangxin (junyang.xin@nyu.edu)
   *
   */
  public static class Builder {
    // Required parameter name. A contact should at least have a name.
    private String name;
    
    // Optional parameters - initialized to default values
    private Address address = new Address.Builder().build();
    private String number = "";
    private String email = "";
    private String note = "";
    
    /**
     * Constructs a contact builder with a valid contact name.
     * 
     * @param name A valid (non-null and non-empty) contact name.
     * @throws IllegalArgumentException If the name is null or empty.
     * @see IllegalArgumentException
     */
    public Builder(String name) {
      Validator.checkNullOrEmptyArg(name, "Name");
      this.name = name;
    }
   
    /**
     * Assign an address to the contact.
     * 
     * @param address An address instance representing a contact's address.
     * @throws IllegalArgumentException If the address is null.
     * @see IllegalArgumentException
     */
    public Builder address(Address address) {
      Validator.checkNullArg(address, "Address");
      this.address = address;
      return this;
    }

    /**
     * Assign a number to the contact.
     * 
     * @param number An string representing a contact's number.
     * @throws IllegalArgumentException If the number is null.
     * @see IllegalArgumentException
     */
    public Builder number(String number) {
      Validator.checkPhoneNumberFormate(number);
      this.number = number;
      return this;
    }

    /**
     * Assign an email to the contact.
     * 
     * @param email An string representing a contact's email.
     * @throws IllegalArgumentException If the email is null.
     * @see IllegalArgumentException
     */
    public Builder email(String email) {
      Validator.checkNullArg(email, "Email");
      this.email = email;
      return this;
    }
    
    /**
     * Assign a note to the contact.
     * 
     * @param email An string representing a note.
     * @throws IllegalArgumentException If the note is null.
     * @see IllegalArgumentException
     */
    public Builder note(String note) {
      Validator.checkNullArg(note, "Note");
      this.note = note;
      return this;
    }
    
    /**
     * Method to build a contact with the information held by this builder.
     * 
     * @return A Contact instance.
     */
    public Contact build() {
      return new Contact(this);
    }
  }

  /**
   * Private constructor to prevent direct instantiation of Contact.
   * 
   * @param builder A Contract.Builder instance.
   */
  private Contact(Builder builder) {
    name = builder.name;
    address = builder.address;
    number = builder.number;
    email = builder.email;
    note = builder.note;
  }

  /**
   * Get the name of contact.
   * 
   * @return A contact name string.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the address of contact.
   * 
   * @return A Address instance representing the contact address.
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Get the number of contact.
   * 
   * @return A string representing the contact number.
   */
  public String getNumber() {
    return number;
  }

  /**
   * Get the email of contact.
   * 
   * @return A string representing the contact email.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Get the note information.
   * 
   * @return A string representing the note.
   */
  public String getNote() {
    return note;
  }

  /**
   * String representation of a contact.
   * 
   * @return The string representation of a contact.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Contact [Name: ");
    sb.append(name);
    sb.append(", address: ");
    sb.append(address);
    sb.append(", number: ");
    sb.append(number);
    sb.append(", email: ");
    sb.append(email);
    sb.append(", note: ");
    sb.append(note);
    sb.append("]");
    return sb.toString();
  }

  /**
   * Two contacts are equal if all their properties are identical.
   * 
   * @param o An object to compare to.
   * @return True if the two contacts are equal; false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Contact)) {
      return false;
    }
    Contact otherContact = (Contact) o;
    if (this == otherContact) {
      return true;
    }
    return name.equals(otherContact.name) &&
        address.equals(otherContact.address) &&
        number.equals(otherContact.number) &&
        email.equals(otherContact.email) &&
        note.equals(otherContact.note);
  }

  /**
   * A properly implemented hash function for contact.
   * 
   * @return The hash code of this contact.
   */
  @Override
  public int hashCode() {
    int result = 17;
    result = result * 31 + name.hashCode();
    result = result * 31 + address.hashCode();
    result = result * 31 + number.hashCode();
    result = result * 31 + email.hashCode();
    result = result * 31 + note.hashCode();
    return result;
  }
}
