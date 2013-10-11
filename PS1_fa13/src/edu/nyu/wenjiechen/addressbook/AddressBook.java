package edu.nyu.wenjiechen.addressbook;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Address Book is a singlton class. Use getInstance() to use its object.
 * 
 * @author Wenjie Chen
 * 
 */
public class AddressBook {
  private static final AddressBook addressBook = new AddressBook();
  private static Set<Contact> contacts = new HashSet<Contact>();

  private AddressBook() {
  }

  /**
   * 
   * @return instance of AddressBook
   */
  public static AddressBook getInstance() {
    return addressBook;
  }

  /**
   * 
   * @param contact
   *          a new contact
   */
  public void addContact(Contact contact) {
    contacts.add(contact);
  }

  /**
   * 
   * @param contact
   *          you want to remove from address book
   * @return true if success, otherwise false
   */
  public boolean removeContact(Contact contact) {
    return contacts.remove(contact);
  }

  /**
   * 
   * @param name
   *          person's name you want to remove from address book
   * @return true if success, otherwise false
   */
  public boolean removeContact(Name name) {
    for (Contact c : contacts) {
      if (c.getName().equals(name)) {
        return contacts.remove(c);
      }
    }
    return false;
  }

  /**
   * 
   * @param s
   *          content is used find the contact in address book
   * @return A list contains all found persons' contact information
   */
  public List<Contact> Search(String s) {
    List<Contact> res = new LinkedList<Contact>();
    for (Contact c : contacts) {
      if (c.toString().contains(s)) {
        res.add(c);
      }
    }
    return res;
  }

  /**
   * 
   * @param p
   *          phone number use to find contact
   * @return A list contains all found persons' contact information
   */
  public List<Contact> Search(PhoneNumber p) {
    List<Contact> res = new LinkedList<Contact>();
    for (Contact c : contacts) {
      if (c.getPhoneNumber() != null && c.getPhoneNumber().equals(p)) {
        res.add(c);
      }
    }
    return res;
  }

  /**
   * 
   * @param filePath
   *          output content of address book to this file
   */
  public void saveToFile(String filePath) {
    FileWriter fw = null;
    StringBuilder content = new StringBuilder();
    try {
      fw = new FileWriter(filePath, false);
      for (Contact c : contacts) {
        content.append(c.toString() + "\n");
      }
      fw.write(content.toString());
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      if (fw != null) {
        try {
          fw.close();
        } catch (IOException e) {
          throw new RuntimeException("failed to close file!");
        }
      }
    }
  }

  /**
   * 
   * @param filePath
   *          from this file recreate address book
   */
  public void readFromFile(String filePath) {
    BufferedReader br = null;
    Contact contact = null;
    String line = "";
    String firstName = "";
    String lastName = "";
    String phone = "";
    String email = "";
    String street = "";
    String city = "";
    String state = "";
    String zip = "";
    String country = "";
    String note = "";
    boolean newInstance = false;
    try {
      br = new BufferedReader(new FileReader(filePath));
      while ((line = br.readLine()) != null) {
        if (line.startsWith("Contact:")) {
          if (newInstance == true) {
            contact = parser(firstName, lastName, phone, email, street, city,
                state, zip, country, note);
            addressBook.addContact(contact);
            firstName = "";
            lastName = "";
            phone = "";
            email = "";
            street = "";
            city = "";
            state = "";
            zip = "";
            country = "";
            note = "";
          }
          newInstance = true;
        } else if (line.startsWith("Name:")) {
          String[] items = line.split(":");
          String[] name = items[1].split(",");
          lastName = name[0];
          firstName = name[1];
        } else if (line.startsWith("Phone number:")) {
          String[] items = line.split(":");
          if (items.length == 2) {
            phone = items[1];
          }
        } else if (line.startsWith("Work Email:")) {
          String[] items = line.split(":");
          if (items.length == 2) {
            email = items[1];
          }
        } else if (line.startsWith("  Street:")) {
          String[] items = line.split(":");
          if (items.length == 2) {
            street = items[1];
          }
        } else if (line.startsWith("  City:")) {
          String[] items = line.split(":");
          if (items.length == 2) {
            city = items[1];
          }
        } else if (line.startsWith("  State:")) {
          String[] items = line.split(":");
          if (items.length == 2) {
            state = items[1];
          }
        } else if (line.startsWith("  Zip:")) {
          String[] items = line.split(":");
          if (items.length == 2) {
            zip = items[1];
          }
        } else if (line.startsWith("  Country:")) {
          String[] items = line.split(":");
          if (items.length == 2) {
            country = items[1];
          }
        } else if (line.startsWith("Note:")) {
          String[] items = line.split(":");
          if (items.length == 2) {
            note = items[1];
          }
        }
      }
      // last contact
      if (newInstance == true) {
        contact = parser(firstName, lastName, phone, email, street, city,
            state, zip, country, note);
        addressBook.addContact(contact);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (Exception e) {
          e.printStackTrace(System.out);
        }
      }
    }
  }

  private Contact parser(String fn, String ln, String p, String e,
      String street, String city, String state, String zip, String country,
      String note) {
    PhoneNumber phone = null;
    if (p.length() > 5) {
      String areaCode = p.substring(2, 5);
      String prefix = p.substring(6, 9);
      String lineNumber = p.substring(10, 14);
      phone = new PhoneNumber.Builder().areaCode(Integer.parseInt(areaCode))
          .prefix(Integer.parseInt(prefix))
          .lineNumber(Integer.parseInt(lineNumber)).build();
    } else {
      phone = PhoneNumber.newEmptyNumber();
    }
    return new Contact.Builder(Name.newName(fn, ln))
        .phone(phone)
        .email(Email.newEmail(e))
        .note(Note.newNote(note))
        .address(
            new PostalAddress.Builder().street1(street).city(city).state(state)
                .zip(zip).country(country).build()).build();
  }

  /**
   * output how many contacts the address book has
   */
  @Override
  public String toString() {
    return String.format("address book has %d contacts.", contacts.size());
  }
}
