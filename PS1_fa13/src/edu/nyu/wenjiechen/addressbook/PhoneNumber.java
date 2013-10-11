package edu.nyu.wenjiechen.addressbook;

/**
 * phone number of a contact. It contains three fields: area code, prefix, line
 * number
 * 
 * @author Wenjie Chen
 * 
 */
public class PhoneNumber {
  private final short areaCode;
  private final short prefix;
  private final short lineNumber;

  /**
   * help to create a PhoneNumber instance
   * 
   * @author Wenjie Chen
   * 
   */
  public static class Builder {
    // private String title = "";
    private short areaCode;
    private short prefix;
    private short lineNumber;

    /**
     * constructor initializes each field = 0
     */
    public Builder() {
      areaCode = 0;
      prefix = 0;
      lineNumber = 0;
      // title = "work";
    }

    /**
     * 
     * @param areaCode
     * @return Builder
     */
    public Builder areaCode(int areaCode) {
      this.areaCode = (short) areaCode;
      return this;
    }

    /**
     * 
     * @param prefix
     * @return Builder
     */
    public Builder prefix(int prefix) {
      this.prefix = (short) prefix;
      return this;
    }

    /**
     * 
     * @param lineNumber
     * @return Builder
     */
    public Builder lineNumber(int lineNumber) {
      this.lineNumber = (short) lineNumber;
      return this;
    }

    /*
     * public Builder title(String t) { title = t; return this; }
     */
    /**
     * 
     * @return an instance of PhoneNumber
     */
    public PhoneNumber build() {
      rangeCheck(areaCode, 999, "areaCode");
      rangeCheck(prefix, 999, "prefix");
      rangeCheck(lineNumber, 9999, "lineNumber");
      return new PhoneNumber(this);
    }

    /**
     * 
     * @param arg
     *          areaCode,prefix or lineNumber
     * @param max
     *          the max range
     * @param name
     *          the name of a checked argument
     * @throws IllegalArgumentException
     *           if an argument is illegal
     */
    private static void rangeCheck(int arg, int max, String name)
        throws IllegalArgumentException {
      if (arg < 0 || arg > max)
        throw new IllegalArgumentException(name + ": " + arg);
    }
  }

  /**
   * private constructor of PhoneNumber
   * 
   * @param builder
   */
  private PhoneNumber(Builder builder) {
    areaCode = builder.areaCode;
    prefix = builder.prefix;
    lineNumber = builder.lineNumber;
    // title = builder.title;
  }

  /**
   * @param t
   *          title
   * @param a
   *          area code
   * @param p
   *          prefix
   * @param l
   *          lineNumber
   * @return an instance of PhoneNumber
   */
  public static PhoneNumber newPhoneNumber(int a, int p, int l) {
    return new PhoneNumber.Builder().areaCode(a).prefix(p).lineNumber(l)
        .build();
  }

  /**
   * 
   * @return an instance of PhoneNumber, whose each filed = 0
   */
  public static PhoneNumber newEmptyNumber() {
    return new PhoneNumber.Builder().build();
  }

  /**
   * 
   * @return area code
   */
  public short getAreaCode() {
    return areaCode;
  }

  /**
   * 
   * @return prefix number
   */
  public short getPrefix() {
    return prefix;
  }

  /**
   * 
   * @return line number
   */
  public short getLineNumber() {
    return lineNumber;
  }

  /**
   * the phone number of a contact. The format is: (areaCode)prefix-lineNumber.
   * The representation is unspecified and subject to change.
   */
  @Override
  public String toString() {
    return String.format("Phone number: (%03d)%03d-%04d", areaCode, prefix,
        lineNumber);
  }

  /**
   * compare two phone number's areaCode, prefix and lineNumber fields
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PhoneNumber)) {
      return false;
    }
    PhoneNumber oth = (PhoneNumber) o;
    return oth.areaCode == areaCode && oth.prefix == prefix
        && oth.lineNumber == lineNumber;
  }

  /**
   * computed by areaCode, prefix, lineNumber
   */
  @Override
  public int hashCode() {
    int res = 17;
    res = 31 * res + areaCode;
    res = 31 * res + prefix;
    res = 31 * res + lineNumber;
    return res;
  }
}