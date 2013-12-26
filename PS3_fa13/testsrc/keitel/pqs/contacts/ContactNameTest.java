package keitel.pqs.contacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ContactNameTest {
  private ContactName c;

  @Before
  public void setup() {
    c = new ContactName("pei", "zhou");
  }

  @Test
  public void testFirstName() {
    Assert.assertEquals("pei", c.getFirstName());
  }

  @Test
  public void testLastName() {
    Assert.assertEquals("zhou", c.getLastName());
  }

  @Test
  public void testSetFirstName() {
    c.setFirstName("peiran");
    Assert.assertEquals("peiran", c.getFirstName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNullFirstName() {
    c.setFirstName(null);
  }

  @Test
  public void testSetLastName() {
    c.setLastName("wu");
    Assert.assertEquals("wu", c.getLastName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFirstName() {
    c = new ContactName(null, "");
  }

  @Test
  public void testToString() {
    Assert.assertEquals("pei zhou", c.toString());
  }

  // didn't test fromXml(), toXmlElement()

  @Test
  public void testToXmlElement() throws ParserConfigurationException {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .newDocument();
    Element e = c.toXmlElement(doc);
    Assert.assertTrue(e.getAttribute("FirstName").equals("pei"));
    Assert.assertTrue(e.getAttribute("LastName").equals("zhou"));
  }

  @Test
  public void testFromXml() throws ParserConfigurationException {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .newDocument();
    Element e = doc.createElement("ContactName");
    e.setAttribute("FirstName", "pei");
    e.setAttribute("LastName", "zhou");
    ContactName.fromXml(e);
    Assert.assertTrue(c.getFirstName().equals("pei"));
    Assert.assertTrue(c.getLastName().equals("zhou"));
  }

  @Test
  public void testSortByFirstName() {
    List<ContactName> names = new ArrayList<ContactName>();
    names.add(new ContactName("hao", "li"));
    names.add(new ContactName("hao", "wang"));
    names.add(new ContactName("zhang", "jun"));
    names.add(new ContactName("cai", "niu"));
    Collections.sort(names, ContactName.SORT_BY_FIRST_NAME);
    Assert.assertTrue(names.get(0).getFirstName().equals("cai"));
    Assert.assertTrue(names.get(1).getFirstName().equals("hao")
        && names.get(1).getLastName().equals("li"));
    Assert.assertTrue(names.get(2).getFirstName().equals("hao")
        && names.get(2).getLastName().equals("wang"));
    Assert.assertTrue(names.get(3).getFirstName().equals("zhang"));
  }

  @Test
  public void testSortByLastName() {
    List<ContactName> names = new ArrayList<ContactName>();
    names.add(new ContactName("hao", "li"));
    names.add(new ContactName("jun", "li"));
    names.add(new ContactName("zhang", "jun"));
    names.add(new ContactName("cai", "niu"));
    Collections.sort(names, ContactName.SORT_BY_LAST_NAME);
    Assert.assertTrue(names.get(0).getLastName().equals("jun"));
    Assert.assertTrue(names.get(1).getLastName().equals("li")
        && names.get(1).getFirstName().equals("hao"));
    Assert.assertTrue(names.get(2).getLastName().equals("li")
        && names.get(2).getFirstName().equals("jun"));
    Assert.assertTrue(names.get(3).getLastName().equals("niu"));
  }
}
