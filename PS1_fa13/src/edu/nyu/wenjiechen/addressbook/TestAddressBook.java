package edu.nyu.wenjiechen.addressbook;

import java.util.List;

public class TestAddressBook {
  private static void sop(Object x) {
    System.out.println(x);
  }

  public static void main(String[] args) {
    AddressBook ab = AddressBook.getInstance();
    Contact c1 = Contact.newContact(Name.newName("lin", "li"));
    sop(c1);
    ab.addContact(c1);
    sop(ab);
    Contact c2 = new Contact.Builder(Name.newName("shuang", "li"))
        .email(Email.newEmail("sz@n.com"))
        .phone(PhoneNumber.newPhoneNumber(171, 111, 222))
        .address(PostalAddress.newAddress("30 neport pkwy"))
        .note(Note.newNote("hello")).build();
    sop(c2);
    ab.addContact(c2);
    sop(ab);

    Contact c3 = new Contact.Builder(Name.newName("peiran", "zhou"))
        .email(Email.newEmail("pz@n.com"))
        .phone(PhoneNumber.newPhoneNumber(912, 100, 222))
        .address(
            new PostalAddress.Builder().street1("8 street nyu")
                .city("new york").country("USA").state("NY").zip("10002")
                .build()).note(Note.newNote("girlfriend of shuang")).build();
    sop(c3);
    ab.addContact(c3);
    sop(ab);

    Contact c4 = new Contact.Builder(Name.newName("huang", "he"))
        .email(Email.newEmail("hh@dd.com"))
        .phone(PhoneNumber.newPhoneNumber(917, 200, 2022))
        .address(
            new PostalAddress.Builder().street1("8 street nyu")
                .city("new york").country("USA").state("NY").zip("10002")
                .build()).note(Note.newNote("nyu classmate")).build();
    sop(c4);
    ab.addContact(c4);
    sop(ab);

    sop("============search==========");
    List<Contact> cs = ab.Search("8 street");
    sop("search for: 8 street");
    for (Contact c : cs) {
      sop(c);
    }
    sop("search for: phone 9121000222");
    cs = ab.Search(PhoneNumber.newPhoneNumber(912, 100, 222));
    for (Contact c : cs) {
      sop(c);
    }

    sop("============save to file==========");
    ab.saveToFile("addressBookOutput.txt");
    ab.removeContact(c1);
    ab.removeContact(c2);
    ab.removeContact(c3);
    ab.removeContact(Name.newName("huang", "he"));
    sop(ab);

    sop("============read from file==========");
    ab.readFromFile("addressBookOutput.txt");
    sop(ab);
    ab.saveToFile("addressBookFromRead.txt");
  }
}
