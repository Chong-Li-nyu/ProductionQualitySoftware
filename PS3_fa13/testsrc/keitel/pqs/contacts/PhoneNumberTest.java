package keitel.pqs.contacts;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PhoneNumberTest {
  PhoneNumber p;

  @Before
  public void setup() {
    p = PhoneNumber.tryCreateNew("(646)712-5518");
  }

  @Test
  public void testAsString() {
    Assert.assertEquals("6467125518", p.asString());
  }

  @Test
  public void testToString() {
    Assert.assertEquals("Country Code: 1 National Number: 6467125518",
        p.toString());
  }

  @Test(expected = Exception.class)
  public void testCrateNewThrowException() throws Exception {
    PhoneNumber com = PhoneNumber.createNew("adfbb");
  }

  @Test
  public void testCreateNew() throws Exception {
    p = PhoneNumber.createNew("(646)712-5518");
    Assert.assertEquals("6467125518", p.asString()); 
  }

  @Test
  public void testCreatNewNull() {
    p = PhoneNumber.tryCreateNew("");
    Assert.assertNull(p);
  }

  @Test
  public void testToXmlElement() throws ParserConfigurationException {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .newDocument();
    Element e = p.toXmlElement(doc);
    Assert.assertTrue(e.getAttribute("FormattedString").equals("6467125518"));
  }

  @Test
  public void testFromXml() throws ParserConfigurationException {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .newDocument();
    Element e = doc.createElement("PhoneNumber");
    e.setAttribute("FormattedString", "6467125518");
    p = PhoneNumber.fromXml(e);
    Assert.assertTrue(p.asString().equals("6467125518"));
  }

}
