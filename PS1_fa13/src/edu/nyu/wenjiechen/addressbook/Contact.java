package edu.nyu.wenjiechen.addressbook;

/**
 * a entry of AddressBook. It includes name, address, phone, email and note, but
 * only name is necessary.
 * 
 * @author Wenjie Chen
 * 
 */
public class Contact {
  private final Name name;
  private final PostalAddress address;
  private final PhoneNumber phone;
  private final Email email;
  private final Note note;

  /**
   * help to build Contact instance
   * 
   * @author Wenjie NYU
   * 
   */
  public static class Builder {
    private Name name;
    private PostalAddress address;
    private PhoneNumber phone;
    private Email email;
    private Note note;

    public Builder(Name name) {
      this.name = name;
    }

    /**
     * 
     * @param address
     * @return Builder
     */
    public Builder address(PostalAddress address) {
      this.address = address;
      return this;
    }

    /**
     * 
     * @param phone
     * @return Builder
     */
    public Builder phone(PhoneNumber phone) {
      this.phone = phone;
      return this;
    }

    /**
     * 
     * @param email
     * @return Builder
     */
    public Builder email(Email email) {
      this.email = email;
      return this;
    }

    /**
     * 
     * @param note
     * @return Builder
     */
    public Builder note(Note note) {
      this.note = note;
      return this;
    }

    /**
     * create a instance of Contact
     * 
     * @return Contact
     */
    public Contact build() {
      return new Contact(this);
    }
  }

  private Contact(Builder builder) {
    name = builder.name;
    address = builder.address;
    phone = builder.phone;
    email = builder.email;
    note = builder.note;
  }

  /**
   * only use a name to create a Contact instance
   * 
   * @param name
   * @return Contact
   */
  public static Contact newContact(Name name) {
    return new Contact.Builder(name).build();
  }

  /**
   * 
   * @return Name
   */
  public Name getName() {
    return name;
  }

  /**
   * 
   * @return PostalAddress
   */
  public PostalAddress getAddress() {
    return address;
  }

  /**
   * 
   * @return PhoneNumber
   */
  public PhoneNumber getPhoneNumber() {
    return phone;
  }

  /**
   * 
   * @return Email
   */
  public Email getEmail() {
    return email;
  }

  /**
   * 
   * @return Note
   */
  public Note getNote() {
    return note;
  }

  /**
   * the format is: Name: he, huang Phone number: (917)200-2022 Work Email:
   * hh@dd.com Work Address: Street:8 street nyu City: new york State: NY Zip:
   * 10002 Country: USA Note: nyu classmate
   */
  @Override
  public String toString() {
    String p = "Phone number: ";
    String e = "Work Email:";
    String a = "Work Address:";
    String n = "Note:";
    if (phone != null) {
      p = phone.toString();
    }
    if (email != null) {
      e = email.toString();
    }
    if (address != null) {
      a = address.toString();
    }
    if (note != null) {
      n = note.toString();
    }
    return String.format("Contact:\n%s\n%s\n%s\n%s\n%s\n", name, p, e, a, n);
  }

  /**
   * only compare name, phone, email and address
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Contact)) {
      return false;
    }
    Contact oth = (Contact) o;
    return oth.name.equals(name) && oth.phone.equals(phone)
        && oth.email.equals(email) && oth.address.equals(address);
  }

  /**
   * only name, phone, email and address are used to compute hashCode
   */
  @Override
  public int hashCode() {
    int res = 17;
    res = 31 * res + name.hashCode();
    if (phone != null) {
      res = 31 * res + phone.hashCode();
    }
    if (email != null) {
      res = 31 * res + email.hashCode();
    }
    if (address != null) {
      res = 31 * res + address.hashCode();
    }
    return res;
  }
}
