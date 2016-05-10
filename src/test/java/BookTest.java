import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void BookTest_instantiatesCorrectly_true() {
    Book testBook = new Book("Little Prince", 1);
    assertTrue(testBook instanceof Book);
  }

  @Test
  public void getTitle_returnsName_string() {
    Book testBook = new Book("Little Prince", 1);
    assertEquals("Little Prince", testBook.getTitle());
  }

  @Test
  public void all_emptyAtFirst_true() {
    assertEquals(0, Book.all().size());
  }

  @Test
  public void all_returnsAllCategories_list() {
    Book testBook = new Book("Little Prince", 1);
    testBook.save();
    assertTrue(testBook.equals(Book.all().get(0)));
  }

  @Test
  public void equals_returnsTrueIfTitlesAreSame_true() {
    Book firstBook = new Book("Little Prince", 1);
    Book secondBook = new Book("Little Prince", 1);
    assertTrue(firstBook.equals(secondBook));
  }

  @Test
  public void save_assignIdtoObject() {
    Book testBook = new Book("Little Prince", 1);
    testBook.save();
    Book savedBook = Book.all().get(0);
    assertEquals(savedBook, testBook);
  }

  @Test
  public void find_findBookInDatabase_true() {
    Book testBook = new Book("Little Prince", 1);
    testBook.save();
    Book savedBook = Book.find(testBook.getId());
    assertTrue(testBook.equals(savedBook));
  }

  @Test
  public void update_updateBookTitle_true() {
    Book testBook = new Book("Little Prince", 1);
    testBook.save();
    testBook.update("Non Little Prince");
    assertEquals("Non Little Prince", Book.find(testBook.getId()).getTitle());
  }

  @Test
  public void delete_deleteAllAuthorsAndBooksAssociations() {
    Book myBook = new Book("Little Prince", 1);
    myBook.save();
    Author myAuthor = new Author("Antoine de", "Saint-Exupery");
    myAuthor.save();
    myBook.addAuthor(myAuthor);
    myBook.delete();
    assertEquals(0, myAuthor.getBooks().size());
  }

  @Test
  public void addAuthor_addsAuthorToBook_true() {
    Book myBook = new Book("Little Prince", 1);
    myBook.save();
    Author myAuthor = new Author("Antoine de", "Saint-Exupery");
    myAuthor.save();
    myBook.addAuthor(myAuthor);
    Author savedAuthor = myBook.getAuthors().get(0);
    assertTrue(myAuthor.equals(savedAuthor));
  }

  @Test
  public void getAuthors_returnsAllAuthors_List() {
    Book myBook = new Book("Little Prince", 1);
    myBook.save();
    Author myAuthor = new Author("Antoine de", "Saint-Exupery");
    myAuthor.save();
    myBook.addAuthor(myAuthor);
    List savedAuthors = myBook.getAuthors();
    assertEquals(1, savedAuthors.size());
  }
}
