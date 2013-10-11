package address_book;

/**
 * Interface to an address book iterator.
 * 
 * @author junyangxin
 *
 */
public interface AddressBookIterator {

  public boolean hasNext();

  public Contact getNext();
}
