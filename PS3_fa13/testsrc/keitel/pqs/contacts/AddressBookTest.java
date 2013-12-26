package keitel.pqs.contacts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class AddressBookTest {
  AddressBook ab;
  Contact c1;
  Contact c2;
  Contact c3;

  @Before
  public void setup() throws Exception {
    c1 = Contact.createWithName("pei", "zhou");
    c1.setEmailAddress("hao123@ss.com");
    PhoneNumber p = PhoneNumber.createNew("6467125518");
    c1.setPhoneNumber(p);
    c1.setNote("it's a reminder");
    PostalAddress pa = new PostalAddress("newport", "parkway", "Jersery City",
        "NJ", "USA", "07310");
    c1.setPostalAddress(pa);

    c2 = Contact.createWithName("ross", "rao");
    c2.setEmailAddress("hao@nyu.edu");
    p = PhoneNumber.createNew("9127755518");
    c2.setPhoneNumber(p);
    c2.setNote("new york university");
    pa = new PostalAddress("river", "drive", "Jersery City", "NJ", "USA",
        "07310");
    c2.setPostalAddress(pa);

    c3 = Contact.createWithName("joy", "smith");
    c3.setEmailAddress("ssy@nyu.edu");
    p = PhoneNumber.createNew("9121151626");
    c3.setPhoneNumber(p);
    c3.setNote("new york university");
    pa = new PostalAddress("river", "south", "new york", "NY", "USA", "11204");
    c3.setPostalAddress(pa);

    ab = AddressBook.createEmpty();
    ab.add(c1);
    ab.add(c2);
    ab.add(c3);

  }

  @Test
  public void testEmptyAddressBook() {
    ab = AddressBook.createEmpty();
    List<Contact> cs = ab.getAllContacts();
    Assert.assertTrue(cs.isEmpty());
  }

  /*
   * BUG HERE! The author use Set to want to get the uniqueness for every
   * Contact. But he didn't override the equals() and hashcode() methods of
   * Contact, and the action of Set or Map rely on those two methods, which
   * leads an AddressBook can have two same Contact
   */
  @Test
  public void testAddCheckDuplicate() throws ParserConfigurationException {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .newDocument();
    Element e1 = c1.toXmlElement(doc);
    Contact c1Copy = Contact.fromXml(e1);
    ab.add(c1Copy);
    List<Contact> cs = ab.getAllContacts();
    Assert.assertEquals(3, cs.size());
  }

  @Test
  public void testAdd() {
    List<Contact> cs = ab.getAllContacts();
    Assert.assertTrue(cs.contains(c1));
    Assert.assertTrue(cs.contains(c2));
    Assert.assertTrue(cs.contains(c3));
  }

  @Test
  public void testRemove() {
    ab.remove(c1);
    List<Contact> cs = ab.getAllContacts();
    Assert.assertFalse(cs.contains(c1));
  }

  @Test
  public void testSearchByString() {
    List<Contact> cs = ab.search("Jersery");
    Assert.assertTrue(cs.contains(c2));
    Assert.assertTrue(cs.contains(c1));
    Assert.assertFalse(cs.contains(c3));
  }

  @Test
  public void testSearchByNameFilter() {
    List<Contact> cs = ab.search("zhou", SearchFilters.Name);
    Assert.assertTrue(cs.contains(c1));
    Assert.assertFalse(cs.contains(c2));
    Assert.assertFalse(cs.contains(c3));
  }

  @Test
  public void testSearchByPostalAddressFilter() {
    List<Contact> cs = ab.search("river", SearchFilters.PostalAddress);
    Assert.assertFalse(cs.contains(c1));
    Assert.assertTrue(cs.contains(c2));
    Assert.assertTrue(cs.contains(c3));
  }

  @Test
  public void testSearchByEmailFilter() {
    List<Contact> cs = ab.search("hao", SearchFilters.EmailAddress);
    Assert.assertTrue(cs.contains(c1));
    Assert.assertTrue(cs.contains(c2));
    Assert.assertFalse(cs.contains(c3));
  }

  @Test
  public void testSearchByNoteFilter() {
    List<Contact> cs = ab.search("new york university", SearchFilters.Note);
    Assert.assertFalse(cs.contains(c1));
    Assert.assertTrue(cs.contains(c2));
    Assert.assertTrue(cs.contains(c3));
  }

  @Test
  public void testSearchByPhoneNumberFilter() {
    List<Contact> cs = ab.search("5518", SearchFilters.PhoneNumber);
    Assert.assertTrue(cs.contains(c1));
    Assert.assertTrue(cs.contains(c2));
    Assert.assertFalse(cs.contains(c3));
  }

  @Test
  public void testSearchByAnyFieldFilter() {
    List<Contact> cs = ab.search("USA", SearchFilters.AnyField);
    Assert.assertTrue(cs.contains(c1));
    Assert.assertTrue(cs.contains(c2));
    Assert.assertTrue(cs.contains(c3));
  }

  @Test
  public void testSize() {
    Assert.assertEquals(3, ab.size());
  }

  @Test
  public void testSaveToFile() throws FileNotFoundException,
      ParserConfigurationException, TransformerFactoryConfigurationError,
      TransformerException {
    String filePath = "AddressBook.xml";
    ab.save(filePath);
  }

  @Test(expected = FileNotFoundException.class)
  public void testSaveToFileFileNotFound() throws FileNotFoundException,
      ParserConfigurationException, TransformerFactoryConfigurationError,
      TransformerException {
    String filePath = "noDir/AddressBook.xml";
    ab.save(filePath);
  }

  @Test
  public void testSaveToOutputStream() throws ParserConfigurationException,
      TransformerFactoryConfigurationError, TransformerException, IOException {
    OutputStream os = new FileOutputStream(new File("AddressBook.xml"));
    try {
      ab.save(os);
    } finally {
      os.close();
    }
  }

  @Test
  public void testLoadFromFile() throws FileNotFoundException, SAXException,
      IOException, ParserConfigurationException {
    String filepath = "AddressBook.xml";
    AddressBook.load(filepath);
  }

  @Test(expected = FileNotFoundException.class)
  public void testLoadFromFileThrowException() throws FileNotFoundException,
      SAXException, IOException, ParserConfigurationException {
    String filepath = "noDir/AddressBook.xml";
    AddressBook.load(filepath);
  }

  @Test
  public void testLoadFromInputStream() throws FileNotFoundException,
      SAXException, IOException, ParserConfigurationException {
    InputStream is = new FileInputStream(new File("AddressBook.xml"));
    AddressBook.load(is);
  }

  @Test
  public void testToString() {
    Assert.assertEquals("[AddressBook: 3 entries]", ab.toString());
  }

  @After
  public void tearDown() {

  }

}
