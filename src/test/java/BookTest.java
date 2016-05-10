import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
// import java.util.Arrays;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void BookTest_instantiatesCorrectly_true() {
    Book testBook = new Book("Little Prince");
    assertTrue(testBook instanceof Book);
  }

  @Test
  public void getTitle_returnsName_string() {
    Book testBook = new Book("Little Prince");
    assertEquals("Little Prince", testBook.getTitle());
  }

  @Test
  public void all_emptyAtFirst_true() {
    assertEquals(0, Book.all().size());
  }

  @Test
  public void all_returnsAllCategories_list() {
    Book testBook = new Book("Little Prince");
    testBook.save();
    assertTrue(testBook.equals(Book.all().get(0)));
  }

  @Test
  public void equals_returnsTrueIfTitlesAreSame_true() {
    Book firstBook = new Book("Little Prince");
    Book secondBook = new Book("Little Prince");
    assertTrue(firstBook.equals(secondBook));
  }

  @Test
  public void save_assignIdtoObject() {
    Book testBook = new Book("Little Prince");
    testBook.save();
    Book savedBook = Book.all().get(0);
    assertEquals(savedBook, testBook);
  }

  @Test
  public void find_findBookInDatabase_true() {
    Book testBook = new Book("Little Prince");
    testBook.save();
    Book savedBook = Book.find(testBook.getId());
    assertTrue(testBook.equals(savedBook));
  }

  @Test
  public void update_updateBookTitle_true() {
    Book testBook = new Book("Little Prince");
    testBook.save();
    testBook.update("Non Little Prince");
    assertEquals("Non Little Prince", Book.find(testBook.getId()).getTitle());
  }

  @Test
  public void delete_deleteBook_true() {
    Book testBook = new Book("Little Prince");
    testBook.save();
    testBook.delete();
    assertEquals(0, Book.all().size());
  }
}
