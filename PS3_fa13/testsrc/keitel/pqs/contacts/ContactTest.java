package keitel.pqs.contacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ContactTest {
  Contact c;

  @Before
  public void setup() {
    c = Contact.createWithName("pei", "zhou");
  }

  @Test
  public void testGetName() {
    Assert.assertEquals("pei", c.getName().getFirstName());
    Assert.assertEquals("zhou", c.getName().getLastName());
  }

  @Test
  public void testSetName() {
    ContactName n = new ContactName("shuang", "li");
    c.setName(n);
    Assert.assertEquals("shuang", c.getName().getFirstName());
    Assert.assertEquals("li", c.getName().getLastName());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNullName() {
    c.setName(null);
  }

  @Test
  public void testGetAndSetEmailAddress() {
    c.setEmailAddress("hao123@ss.com");
    Assert.assertEquals("hao123@ss.com", c.getEmailAddress());
  }

  @Test
  public void testGetAndSetNote() {
    c.setNote("it's a reminder");
    Assert.assertEquals("it's a reminder", c.getNote());
  }

  @Test
  public void testSetAndGetPhoneNumber() throws Exception {
    PhoneNumber p = PhoneNumber.createNew("6467125518");
    c.setPhoneNumber(p);
    Assert.assertEquals("6467125518", c.getPhoneNumber().asString());
  }

  @Test
  public void testSetAndGetNullPhoneNumber() throws Exception {
    c.setPhoneNumber(null);
    Assert.assertNull(c.getPhoneNumber());
  }

  @Test
  public void testSetAndGetPostalAddress() {
    PostalAddress p = new PostalAddress("newport", "parkway", "Jersery City",
        "NJ", "USA", "07310");
    c.setPostalAddress(p);
    Assert.assertEquals(p, c.getPostalAddress());
  }

  @Test
  public void testSetAndGetNullPostalAddress() {
    c.setPostalAddress(null);
    Assert.assertNull(c.getPostalAddress());
  }

  @Test
  public void testCreateWithNameString() {
    c = Contact.createWithName("pei");
    Assert.assertEquals("pei", c.getName().getFirstName());
    Assert.assertEquals("", c.getName().getLastName());
  }

  @Test
  public void testCreateWithNameStringString() {
    Assert.assertEquals("pei", c.getName().getFirstName());
    Assert.assertEquals("zhou", c.getName().getLastName());
  }

  @Test(expected = Exception.class)
  public void testCreateWithNull() {
    c = Contact.createWithName(null);
  }

  @Test
  public void testToXmlElement() throws Exception {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .newDocument();
    c.setEmailAddress("hao123@ss.com");
    PhoneNumber p = PhoneNumber.createNew("6467125518");
    c.setPhoneNumber(p);
    c.setNote("it's a reminder");
    PostalAddress pa = new PostalAddress("newport", "parkway", "Jersery City",
        "NJ", "USA", "07310");
    c.setPostalAddress(pa);

    Element e = c.toXmlElement(doc);
    Element cn = (Element) e.getElementsByTagName("ContactName").item(0);
    Assert.assertEquals(cn.getAttribute("FirstName"), "pei");
    Assert.assertEquals(cn.getAttribute("LastName"), "zhou");
    Element posa = (Element) e.getElementsByTagName(PostalAddress.XML_NAME)
        .item(0);
    Assert.assertEquals(posa.getAttribute("AddressLine1"), "newport");
    Assert.assertEquals(posa.getAttribute("AddressLine2"), "parkway");
    Assert.assertEquals(posa.getAttribute("City"), "Jersery City");
    Assert.assertEquals(posa.getAttribute("State"), "NJ");
    Assert.assertEquals(posa.getAttribute("Country"), "USA");
    Assert.assertEquals(posa.getAttribute("PostalCode"), "07310");

    Element phonenum = (Element) e.getElementsByTagName(PhoneNumber.XML_NAME)
        .item(0);
    PhoneNumber pn = PhoneNumber.fromXml(phonenum);
    Assert.assertEquals(pn.asString(), "6467125518");
    String email = e.getElementsByTagName("Email").item(0).getTextContent();
    Assert.assertEquals("hao123@ss.com", email);
    String note = e.getElementsByTagName("Note").item(0).getTextContent();
    Assert.assertEquals("it's a reminder", note);
  }

  @Test
  public void testFromXml() throws Exception {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .newDocument();
    c.setEmailAddress("hao123@ss.com");
    PhoneNumber p = PhoneNumber.createNew("6467125518");
    c.setPhoneNumber(p);
    c.setNote("it's a reminder");
    PostalAddress pa = new PostalAddress("newport", "parkway", "Jersery City",
        "NJ", "USA", "07310");
    c.setPostalAddress(pa);
    Element e = c.toXmlElement(doc);
    Contact com = Contact.fromXml(e);

    Assert.assertEquals("pei", com.getName().getFirstName());
    Assert.assertEquals("zhou", com.getName().getLastName());
    Assert.assertEquals("hao123@ss.com", com.getEmailAddress());
    Assert.assertEquals("6467125518", com.getPhoneNumber().asString());
    Assert.assertEquals("it's a reminder", com.getNote());
    Assert.assertEquals(pa, com.getPostalAddress());
  }

  @Test
  public void testFirstNameLastNameComparator() {
    Contact c1 = Contact.createWithName("pei", "zhou");
    Contact c2 = Contact.createWithName("ross", "rao");
    Contact c3 = Contact.createWithName("joy", "smith");
    List<Contact> cs = new ArrayList<Contact>();
    cs.add(c1);
    cs.add(c2);
    cs.add(c3);
    Collections.sort(cs, Contact.SORT_BY_FIRST_NAME);
    Assert.assertEquals("joy", cs.get(0).getName().getFirstName());
    Assert.assertEquals("pei", cs.get(1).getName().getFirstName());
    Assert.assertEquals("ross", cs.get(2).getName().getFirstName());
  }

  @Test
  public void testLastNameLastNameComparator() {
    Contact c1 = Contact.createWithName("pei", "zhou");
    Contact c2 = Contact.createWithName("ross", "rao");
    Contact c3 = Contact.createWithName("joy", "smith");
    List<Contact> cs = new ArrayList<Contact>();
    cs.add(c1);
    cs.add(c2);
    cs.add(c3);
    Collections.sort(cs, Contact.SORT_BY_LAST_NAME);
    Assert.assertEquals("rao", cs.get(0).getName().getLastName());
    Assert.assertEquals("smith", cs.get(1).getName().getLastName());
    Assert.assertEquals("zhou", cs.get(2).getName().getLastName());
  }

}
