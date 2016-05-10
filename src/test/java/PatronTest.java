import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
// import java.util.Arrays;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void PatronTest_instantiatesCorrectly_true() {
    Patron testPatron = new Patron("Perry", "Smith");
    assertTrue(testPatron instanceof Patron);
  }

  @Test
  public void getFirstName_returnsFirstName_string() {
    Patron testPatron = new Patron("Perry", "Smith");
    assertEquals("Perry", testPatron.getFirstName());
  }

  @Test
  public void getLastName_returnsLastName_string() {
    Patron testPatron = new Patron("Perry", "Smith");
    assertEquals("Smith", testPatron.getLastName());
  }


  @Test
  public void all_emptyAtFirst_true() {
    assertEquals(0, Patron.all().size());
  }

  @Test
  public void all_returnsAllCategories_list() {
    Patron testPatron = new Patron("Perry", "Smith");
    testPatron.save();
    assertTrue(testPatron.equals(Patron.all().get(0)));
  }

  @Test
  public void equals_returnsTrueIfNamesAreSame_true() {
    Patron firstPatron = new Patron("Perry", "Smith");
    Patron secondPatron = new Patron("Perry", "Smith");
    assertTrue(firstPatron.equals(secondPatron));
  }

  @Test
  public void save_assignIdtoObject() {
    Patron testPatron = new Patron("Perry", "Smith");
    testPatron.save();
    Patron savedPatron = Patron.all().get(0);
    assertEquals(savedPatron, testPatron);
  }

  @Test
  public void find_findPatronInDatabase_true() {
    Patron testPatron = new Patron("Perry", "Smith");
    testPatron.save();
    Patron savedPatron = Patron.find(testPatron.getId());
    assertTrue(testPatron.equals(savedPatron));
  }

  @Test
  public void update_updatePatronName_true() {
    Patron testPatron = new Patron("Perry", "Smith");
    testPatron.save();
    testPatron.update("Mike", "Smooth");
    assertEquals("Mike", Patron.find(testPatron.getId()).getFirstName());
    assertEquals("Smooth", Patron.find(testPatron.getId()).getLastName());
  }

  @Test
  public void delete_deletePatron_true() {
    Patron testPatron = new Patron("Perry", "Smith");
    testPatron.save();
    testPatron.delete();
    assertEquals(0, Patron.all().size());
  }
}
