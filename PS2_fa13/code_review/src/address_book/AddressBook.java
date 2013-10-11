package address_book;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents an address book. An address book allows operations
 * such as adding a contact, removing a contact, performing exact or partial
 * search for contacts and retrieving the information of the contacts, saving
 * the address book to a file and create an address book from a file.
 * 
 * @author junyangxin (junyang.xin@nyu.edu)
 *
 */
public class AddressBook {
  private static AddressBook instance = null;
  private ArrayList<Contact> addressBook;

  /**
   * Initializes an empty address book.
   */
  private AddressBook() {
    addressBook = new ArrayList<Contact>();
  }

  /**
   * Get an address book instance. If the instance hasn't existed, create a
   * new empty one; otherwise returns the existing address book instance.
   */
  public static AddressBook getInstance() {
    if (instance == null) {
      instance = new AddressBook();
    }
    return instance;
  }

  /**
   * Get an address book instance. If the instance hasn't existed, create a
   * new empty one; otherwise returns the existing address book instance.
   */
  public static AddressBook createEmptyAddressBook() {
    if (instance == null) {
      instance = new AddressBook();
      return instance;
    } else {
      throw new IllegalStateException("An address book already exists");
    }
  }

  /**
   * Create an AddressBook instance from data saved in a json file.
   * 
   * @param filePath A string representing the data file path.
   * @return An AddressBook instance created from the data in file.
   * @throws IllegalStateException  If an address book instance has already
   *                                existed.
   * @see IllegalStateException
   * @throws FileNotFoundException  If the file does not exist, is a directory
   *                                rather than a regular file, or for some
   *                                other reason cannot be opened for reading.
   * @see FileNotFoundException
   * @throws JsonSyntaxException If json is not a valid representation for an
   *                             object of type AddressBook.
   * @see com.google.gson.JsonSyntaxException
   */
  public static AddressBook createAddressBookFromFile(String filePath)
      throws FileNotFoundException {
    if (instance == null) {
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      Gson gson = new Gson();
      instance = gson.fromJson(br, AddressBook.class);
      return instance;
    } else {
      throw new IllegalStateException("An address book already exists");
    }
  }

  /**
   * Save the address book data into a json file.
   * 
   * @param filePath A string representing the path to the saved file.
   * @throws IOException If the named file exists but is a directory rather
   *                     than a regular file, does not exist but cannot be
   *                     created, or cannot be opened for any other reason
   *                     rather than a regular file, or for some other reason
   *                     cannot be opened for reading.
   * @see IOException
   */
  public void dumpToFile(String filePath) throws IOException {
    FileWriter writer = new FileWriter(filePath);
    Gson gson = new Gson();
    writer.write(gson.toJson(instance));
    writer.flush();
    writer.close();
  }


  /**
   * Add a contact to the address book.
   * 
   * @param contact A contact to add.
   * @return True if the add operation succeeded; otherwise false.
   * @throws IllegalArgumentException If the contact parameter is null.
   */
  public boolean addContact(Contact contact) {
    Validator.checkNullArg(contact, "Contact");
    return addressBook.add(contact);
  }

  /**
   * Remove a contact from the address book.
   * 
   * @return true if the remove operation succeeded; otherwise false.
   */
  public boolean removeContact(Contact contact) {
    return addressBook.remove(contact);
  }

  @Override
  public String toString() {
    return "Address book: " + addressBook;
  }

  /**
   * Search for contacts with properties that exactly match the query string.
   * 
   * @param term A query string.
   * @return A potentially empty array list containing all matched contacts.
   */
  public ArrayList<Contact> searchExactMatch(String term) {
    ArrayList<Contact> result = new ArrayList<Contact>();
    for (Contact contact: addressBook) {
      if (contact.getName().equals(term) ||
          contact.getNumber().equals(term) ||
          contact.getAddress().exactMatch(term) ||
          contact.getEmail().equals(term) ||
          contact.getNote().equals(term)) {
        result.add(contact);
      }
    }
    return result;
  }

  /**
   * Search for contacts with properties that partially match the query string.
   * 
   * @param term A query string.
   * @return A potentially empty array list containing all matched contacts.
   */
  public ArrayList<Contact> searchPartialMatch(String term) {
    ArrayList<Contact> result = new ArrayList<Contact>();
    for (Contact contact: addressBook) {
      if (contact.getName().indexOf(term) != -1 ||
          contact.getNumber().indexOf(term) != -1||
          contact.getAddress().partialMatch(term) ||
          contact.getEmail().indexOf(term) != -1||
          contact.getNote().indexOf(term) != -1) {
        result.add(contact);
      }
    }
    return result;
  }


  /**
   * Get the number of contacts in this address book.
   * 
   * @return the number of contacts in this address book.
   */
  public int numberOfContacts() {
    return addressBook.size();
  }

  /**
   * Return an iterator of this address book.
   * 
   * @return An AddressBookIterator instance that can iterate through this
   *         address book.
   */
  public AddressBookIterator iterator() {
    AddressBookIterator iter = new AwesomeIterator();
    return iter;
  }

  /**
   * My awesome iterator to iterate through the address book. This along with
   * not exposing the addressbook array list make sure that this is the only
   * way to iterate through the address book.
   * 
   * @author junyangxin (junyang.xin@nyu.edu)
   *
   */
  private class AwesomeIterator implements AddressBookIterator {
    private Iterator<Contact> iter = addressBook.iterator();
    
    @Override
    public boolean hasNext() {
      if (iter.hasNext()) {
        return true;
      } else {
        return false;
      }
    }

    @Override
    public Contact getNext() {
      if (iter.hasNext()) {
        return iter.next();
      } else {
        return null;
      }
    }
  }
}
