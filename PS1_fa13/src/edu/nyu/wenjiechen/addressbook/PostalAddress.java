package edu.nyu.wenjiechen.addressbook;

/**
 * Represent home address, work address or other kind of addresses of a contact.
 * zip code is represented by a "String" not a "int" type.
 * 
 * @author Wenjie Chen
 * 
 */
public class PostalAddress {
  private final String title;
  private final String street1;
  private final String street2;
  private final String city;
  private final String state;
  private final String zip;
  private final String country;

  /**
   * help to create a PostalAddress instance
   * 
   * @author Wenjie Chen
   * 
   */
  public static class Builder {
    private String title = "";
    private String street1 = "";
    private String street2 = "";
    private String city = "";
    private String state = "";
    private String zip = "";
    private String country = "";

    /**
     * Builder's constructor initializes the title of address "work". That is
     * the undefined address of a contact is work address.
     */
    public Builder() {
      title = "Work";
    }

    /**
     * 
     * @param title
     *          indicates this is a home address or work address or other
     *          address.
     * @return Builder
     */
    public Builder title(String title) {
      if (title.equals(""))
        this.title = "Work";
      else {
        this.title = title;
      }
      return this;
    }

    /**
     * 
     * @param street
     *          main street address
     * @return Builder
     */
    public Builder street1(String street) {
      street1 = street;
      return this;
    }

    /**
     * 
     * @param street
     *          optional street address
     * @return Builder
     */
    public Builder street2(String street) {
      street2 = street;
      return this;
    }

    /**
     * 
     * @param city
     * @return Builder
     */
    public Builder city(String city) {
      this.city = city;
      return this;
    }

    /**
     * 
     * @param state
     * @return Builder
     */
    public Builder state(String state) {
      this.state = state;
      return this;
    }

    /**
     * 
     * @param zip
     *          is a String. Must be careful!
     * @return Builder
     */
    public Builder zip(String zip) {
      this.zip = zip;
      return this;
    }

    /**
     * 
     * @param country
     * @return Builder
     */
    public Builder country(String country) {
      this.country = country;
      return this;
    }

    /**
     * 
     * @return an instance of PostalAddress.
     */
    public PostalAddress build() {
      return new PostalAddress(this);
    }
  }

  /**
   * constructor of PostalAddress
   * 
   * @param builder
   *          a inner static class of PostalAddress to help to create an
   *          instance of PostalAddress.
   */
  private PostalAddress(Builder builder) {
    title = builder.title;
    street1 = builder.street1;
    street2 = builder.street2;
    city = builder.city;
    state = builder.state;
    zip = builder.zip;
    country = builder.country;
  }

  /**
   * 
   * @param title
   *          might "work", "home" or others
   * @param street1
   *          main address
   * @param street2
   *          optinal address
   * @param city
   * @param state
   * @param zip
   * @param country
   * @return create an instance of PostalAddress
   */
  public static PostalAddress newAddress(String street1) {
    return new PostalAddress.Builder().street1(street1).build();
  }

  // public static PostalAddress newAddress(String title, String street1,
  // String street2, String city, String state, String zip, String country) {
  // return new PostalAddress.Builder().title(title).street1(street1)
  // .street2(street2).city(city).state(state).zip(zip).country(country)
  // .build();
  // }
  //
  /**
   * 
   * @return empty PostalAddress
   */
  public static PostalAddress newEmptyAddress() {
    return new PostalAddress.Builder().build();
  }

  /**
   * 
   * @return title of address
   */
  public String getTitle() {
    return title;
  }

  /**
   * 
   * @return main address
   */
  public String getMainStreet() {
    return street1;
  }

  /**
   * 
   * @return optional street address
   */
  public String getOptionalStreet() {
    return street2;
  }

  /**
   * 
   * @return city name
   */
  public String getCity() {
    return city;
  }

  /**
   * 
   * @return state's name
   */
  public String getState() {
    return state;
  }

  /**
   * 
   * @return zip code. It's a String
   */
  public String getZip() {
    return zip;
  }

  /**
   * 
   * @return Country's name
   */
  public String getCountry() {
    return country;
  }

  /**
   * return a address of a contact. The format is: title Address: street1
   * street2 city state zip country The representation is unspecified and
   * subject to change.
   */
  @Override
  public String toString() {
    return String
        .format(
            "%s Address:\n  Street:%s %s\n  City: %s\n  State: %s\n  Zip: %s\n  Country: %s",
            title, street1, street2, city, state, zip, country);
    // return String.format("%s address:\n%s %s\n%s\n%s %s\n%s\n", title,
    // street1,
    // street2, city, state, zip, country);

  }

  /**
   * The strings of address are case-insensitive.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PostalAddress)) {
      return false;
    }
    PostalAddress other = (PostalAddress) o;
    return other.street1.equalsIgnoreCase(street1)
        && other.street2.equalsIgnoreCase(street2)
        && other.city.equalsIgnoreCase(city)
        && other.state.equalsIgnoreCase(state)
        && other.country.equalsIgnoreCase(country)
        && other.title.equalsIgnoreCase(title)
        && other.zip.equalsIgnoreCase(zip);
  }

  /**
   * This function is case-insensitive.
   */
  @Override
  public int hashCode() {
    int res = 17;
    res = 31 * res + title.toLowerCase().hashCode();
    res = 31 * res + street1.toLowerCase().hashCode();
    res = 31 * res + street2.toLowerCase().hashCode();
    res = 31 * res + city.toLowerCase().hashCode();
    res = 31 * res + state.toLowerCase().hashCode();
    res = 31 * res + zip.toLowerCase().hashCode();
    res = 31 * res + country.toLowerCase().hashCode();
    return res;
  }
}
