package address_book;

/**
 * The class that represents a postal address. The instances of this class
 * 
 * @author junyangxin (junyang.xin@nyu.edu)
 *
 */
public class Address {
  private String street;
  private String city;
  private String state;
  private String zipCode;

  /**
   * The builder class for creating an Address instance.
   * 
   * @author junyangxin (jx372@nyu.edu)
   *
   */
  public static class Builder {
    // Optional parameters - initialized to default values
    private String street = "";
    private String city = "";
    private String state = "";
    private String zipCode = "";

    /**
     * Assign a street to the address.
     * 
     * @param address An address instance representing a street.
     * @throws IllegalArgumentException If the street is null.
     * @see IllegalArgumentException
     */
    public Builder street(String street) {
      Validator.checkNullArg(street, "Street");
      this.street = street;
      return this;
    }

    /**
     * Assign a city to the address.
     * 
     * @param address An address instance representing a city.
     * @throws IllegalArgumentException If the city is null.
     * @see IllegalArgumentException
     */
    public Builder city(String city) {
      Validator.checkNullArg(city, "City");
      this.city = city;
      return this;
    }
    
    /**
     * Assign a state to the address.
     * 
     * @param address An address instance representing a state.
     * @throws IllegalArgumentException If the state is null.
     * @see IllegalArgumentException
     */ 
    public Builder state(String state) {
      Validator.checkNullArg(state, "State");
      this.state = state;
      return this;
    }
    
    /**
     * Assign a zipCode to the address.
     * 
     * @param address An address instance representing a zip code.
     * @throws IllegalArgumentException If the zip code is null.
     * @see IllegalArgumentException
     */
    public Builder zipCode(String zipCode) {
      Validator.checkNullArg(zipCode, "Email");
      this.zipCode = zipCode;
      return this;
    }

    /**
     * Method to build an address with the information held by this builder.
     * 
     * @return An Address instance.
     */
    public Address build() {
      return new Address(this);
    }
  }

  /**
   * Private constructor to prevent direct instantiation of Address.
   * 
   * @param builder An Address.Builder instance.
   */
  private Address(Builder builder) {
    street = builder.street;
    city = builder.city;
    state = builder.state;
    zipCode = builder.zipCode;
  }

  /**
   * Get the street of this address.
   * 
   * @return A string representing the street of this address.
   */
  public String getStreet() {
    return street;
  }

  /**
   * Get the city of this address.
   * 
   * @return A string representing the city of this address.
   */    
  public String getCity() {
    return city;
  }
  
  /**
   * Get the state of this address.
   * 
   * @return A string representing the state of this address.
   */    
  public String getState() {
    return state;
  }
  
  /**
   * Get the zip code of this address.
   * 
   * @return A string representing the zip code of this address.
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * String representation of an address.
   * 
   * @return The string representation of an address.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Address [street: ");
    sb.append(street);
    sb.append(", city: ");
    sb.append(city);
    sb.append(", state: ");
    sb.append(state);
    sb.append(", zip code: ");
    sb.append(zipCode);
    sb.append("]");
    return sb.toString();
  }

  /**
   * Two addresses are equal if all their properties are identical.
   * 
   * @param o An object to compare to.
   * @return True if the two addresses are equal; false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Address)) {
      return false;
    }
    Address otherAddress = (Address) o;
    if (this == otherAddress) {
      return true;
    }
    return street.equals(otherAddress.street) &&
        city.equals(otherAddress.city) &&
        state.equals(otherAddress.state) &&
        zipCode.equals(otherAddress.zipCode);
  }

  /**
   * A properly implemented hash function for address.
   * 
   * @return The hash code of this address.
   */
  @Override
  public int hashCode() {
    int result = 17;
    result = result * 31 + street.hashCode();
    result = result * 31 + city.hashCode();
    result = result * 31 + state.hashCode();
    result = result * 31 + zipCode.hashCode();
    return result;
  }

  /**
   * Performs an exact match between the query string and the properties of
   * this address.
   * 
   * @param term A query string.
   * @return True if an exact match is found; false otherwise.
   */
  boolean exactMatch(String term) {
    return street.equals(term) || city.equals(term) || state.equals(term) ||
        zipCode.equals(term);
  }

  /**
   * Performs a partial match between the query string and the properties of
   * this address.
   * 
   * @param term A query string.
   * @return True if a partial match is found; false otherwise.
   */
  boolean partialMatch(String term) {
    return street.indexOf(term) != -1 ||
        city.indexOf(term) != -1 ||
        state.indexOf(term) != -1||
        zipCode.indexOf(term) != -1;
  }
}
