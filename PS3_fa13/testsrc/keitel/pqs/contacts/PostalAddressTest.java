package keitel.pqs.contacts;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PostalAddressTest {
  PostalAddress p;

  @Before
  public void setup() {
    p = new PostalAddress("newport", "parkway", "Jersery City", "NJ", "USA",
        "07310");
  }

  @Test
  public void testConstructor() {
    p = new PostalAddress();
    Assert.assertEquals("", p.getAddressLine1());
    Assert.assertEquals("", p.getAddressLine2());
    Assert.assertEquals("", p.getCity());
    Assert.assertEquals("", p.getCountry());
    Assert.assertEquals("", p.getPostalCode());
    Assert.assertEquals("", p.getState());
  }

  @Test
  public void testGetAddressline1() {
    Assert.assertEquals("newport", p.getAddressLine1());
  }

  @Test 
  public void testSetAddressline1() {
    p.setAddressLine1("new york city");
    Assert.assertEquals("new york city", p.getAddressLine1());
  }

  @Test
  public void testSetNullAddressline1() {
    p.setAddressLine1(null);
    Assert.assertEquals("", p.getAddressLine1());
  }

  @Test
  public void testSetNullAddressline2() {
    p.setAddressLine2(null);
    Assert.assertEquals("", p.getAddressLine2());
  }

  @Test
  public void testGetCity() {
    Assert.assertEquals("Jersery City", p.getCity());
  }

  @Test
  public void testSetCity() {
    p.setCity("new york");
    Assert.assertEquals("new york", p.getCity());
  }

  @Test
  public void testSetNullCity() {
    p.setCity(null);
    Assert.assertEquals("", p.getCity());
  }

  @Test
  public void testGetState() {
    Assert.assertEquals("NJ", p.getState());
  }

  @Test
  public void testSetState() {
    p.setState("NY");
    Assert.assertEquals("NY", p.getState());
  }

  @Test
  public void testSetNullState() {
    p.setState(null);
    Assert.assertEquals("", p.getState());
  }

  @Test
  public void testGetCountry() {
    Assert.assertEquals("USA", p.getCountry());
  }

  @Test
  public void testSetCountry() {
    p.setCountry("China");
    Assert.assertEquals("China", p.getCountry());
  }

  @Test
  public void testSetNullCountry() {
    p.setCountry(null);
    Assert.assertEquals("", p.getCountry());
  }

  @Test
  public void testGetPostalCode() {
    Assert.assertEquals("07310", p.getPostalCode());
  }

  @Test
  public void testSetPostalCode() {
    p.setPostalCode("510000");
    Assert.assertEquals("510000", p.getPostalCode());
  }

  @Test
  public void testSetNullPostalCode() {
    p.setPostalCode(null);
    Assert.assertEquals("", p.getPostalCode());
  }

  @Test
  public void testEqualsTheSameObject() {
    PostalAddress oth = p;
    Assert.assertEquals(true, p.equals(oth));
  }

  @Test
  public void testEqualsDifferentObject() {
    PostalAddress oth = new PostalAddress("newport", "parkway", "Jersery City",
        "NJ", "USA", "07310");
    Assert.assertEquals(true, p.equals(oth));
  }

  @Test
  public void testNotEqual() {
    PostalAddress oth = new PostalAddress("newport", "parkway", "Jersery City",
        "NY", "USA", "07310");
    Assert.assertEquals(false, p.equals(oth));
  }

  @Test
  public void testNotEqualOnDifferentObject() {
    Object obj = new Object();
    Assert.assertEquals(false, p.equals(obj));
  }

  @Test
  public void testHashcodeEquals() {
    PostalAddress oth = new PostalAddress("newport", "parkway", "Jersery City",
        "NJ", "USA", "07310");
    int hashcode = oth.hashCode();
    Assert.assertEquals(hashcode, p.hashCode());
  }

  @Test
  public void testHashcodeNotEquals() {
    PostalAddress oth = new PostalAddress("newport", "parkway", "Jersery City",
        "NY", "USA", "07310");
    int hashcode = oth.hashCode();
    Assert.assertFalse(p.hashCode() == hashcode);
  }

  @Test
  public void testToString() {
    String s = "newport\nparkway\nJersery City,NJ 07310\nUSA\n";
    Assert.assertEquals(s, p.toString());
  }

  @Test
  public void testToXmlElement() throws ParserConfigurationException {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .newDocument();
    Element e = p.toXmlElement(doc);
    Assert.assertTrue(e.getAttribute("AddressLine1").equals("newport"));
    Assert.assertTrue(e.getAttribute("AddressLine2").equals("parkway"));
    Assert.assertTrue(e.getAttribute("City").equals("Jersery City"));
    Assert.assertTrue(e.getAttribute("State").equals("NJ"));
    Assert.assertTrue(e.getAttribute("Country").equals("USA"));
    Assert.assertTrue(e.getAttribute("PostalCode").equals("07310"));
  }

  @Test
  public void testFromXml() throws ParserConfigurationException {
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .newDocument();
    Element e = doc.createElement("PostalAddress");
    e.setAttribute("AddressLine1", "newport");
    e.setAttribute("AddressLine2", "parkway");
    e.setAttribute("City", "Jersery City");
    e.setAttribute("State", "NJ");
    e.setAttribute("Country", "USA");
    e.setAttribute("PostalCode", "07310");
    PostalAddress test = PostalAddress.fromXml(e);
    Assert.assertTrue(test.getAddressLine1().equals("newport"));
    Assert.assertTrue(test.getAddressLine2().equals("parkway"));
    Assert.assertTrue(test.getCity().equals("Jersery City"));
    Assert.assertTrue(test.getState().equals("NJ"));
    Assert.assertTrue(test.getCountry().equals("USA"));
    Assert.assertTrue(test.getPostalCode().equals("07310"));
  }

  @Test
  public void testFromXmlNullElement() throws ParserConfigurationException {
    PostalAddress test = PostalAddress.fromXml(null);
    Assert.assertNull(test);
  }

  @Test
  public void testCopyConstructor() {
    PostalAddress c = new PostalAddress(p);
    Assert.assertEquals(c, p);
  }
}
