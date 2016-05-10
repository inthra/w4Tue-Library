import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
// import java.util.Arrays;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void AuthorTest_instantiatesCorrectly_true() {
    Author testAuthor = new Author("James", "Joyce");
    assertTrue(testAuthor instanceof Author);
  }

  @Test
  public void getFirstName_returnsFirstName_string() {
    Author testAuthor = new Author("James", "Joyce");
    assertEquals("James", testAuthor.getFirstName());
  }

  @Test
  public void getLastName_returnsLastName_string() {
    Author testAuthor = new Author("James", "Joyce");
    assertEquals("Joyce", testAuthor.getLastName());
  }


  @Test
  public void all_emptyAtFirst_true() {
    assertEquals(0, Author.all().size());
  }

  @Test
  public void all_returnsAllCategories_list() {
    Author testAuthor = new Author("James", "Joyce");
    testAuthor.save();
    assertTrue(testAuthor.equals(Author.all().get(0)));
  }

  @Test
  public void equals_returnsTrueIfNamesAreSame_true() {
    Author firstAuthor = new Author("James", "Joyce");
    Author secondAuthor = new Author("James", "Joyce");
    assertTrue(firstAuthor.equals(secondAuthor));
  }

  @Test
  public void save_assignIdtoObject() {
    Author testAuthor = new Author("James", "Joyce");
    testAuthor.save();
    Author savedAuthor = Author.all().get(0);
    assertEquals(savedAuthor, testAuthor);
  }

  @Test
  public void find_findAuthorInDatabase_true() {
    Author testAuthor = new Author("James", "Joyce");
    testAuthor.save();
    Author savedAuthor = Author.find(testAuthor.getId());
    assertTrue(testAuthor.equals(savedAuthor));
  }

  @Test
  public void update_updateAuthorName_true() {
    Author testAuthor = new Author("James", "Joyce");
    testAuthor.save();
    testAuthor.update("Jack", "Kerouac");
    assertEquals("Jack", Author.find(testAuthor.getId()).getFirstName());
    assertEquals("Kerouac", Author.find(testAuthor.getId()).getLastName());
  }

  @Test
  public void delete_deleteAuthor_true() {
    Author testAuthor = new Author("James", "Joyce");
    testAuthor.save();
    testAuthor.delete();
    assertEquals(0, Author.all().size());
  }
}
