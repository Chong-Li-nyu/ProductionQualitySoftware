package edu.nyu.wenjiechen.addressbook;

/**
 * note of a contact
 * 
 * @author Wenjie Chen
 * 
 */
public class Note {
  private final String note;

  private Note(String n) {
    note = n;
  }

  /**
   * static factory to create a Note
   * 
   * @param n
   *          content of note
   * @return Note instance
   */
  public static Note newNote(String n) {
    if (n != null) {
      return new Note(n);
    } else {
      throw new NullPointerException();
    }
  }

  /**
   * 
   * @return content of note
   */
  public String getNote() {
    return note;
  }

  /**
   * format is: Note:
   */
  @Override
  public String toString() {
    return String.format("Note: %s", note);
  }

  /**
   * case insensetive
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Note)) {
      return false;
    }
    Note oth = (Note) o;
    return oth.note.equalsIgnoreCase(note);
  }

  /**
   * case insensetive
   */
  @Override
  public int hashCode() {
    return note.toLowerCase().hashCode();
  }
}
