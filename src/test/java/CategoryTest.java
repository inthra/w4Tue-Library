import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
// import java.util.Arrays;

public class CategoryTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void CategoryTest_instantiatesCorrectly_true() {
    Category testCategory = new Category("Fiction");
    assertTrue(testCategory instanceof Category);
  }

  @Test
  public void getName_returnsName_string() {
    Category testCategory = new Category("Fiction");
    assertEquals("Fiction", testCategory.getName());
  }

  @Test
  public void all_emptyAtFirst_true() {
    assertEquals(0, Category.all().size());
  }

  @Test
  public void all_returnsAllCategories_list() {
    Category testCategory = new Category("Fiction");
    testCategory.save();
    assertTrue(testCategory.equals(Category.all().get(0)));
  }

  @Test
  public void equals_returnsTrueIfNamesAreSame_true() {
    Category firstCategory = new Category("Fiction");
    Category secondCategory = new Category("Fiction");
    assertTrue(firstCategory.equals(secondCategory));
  }

  @Test
  public void save_assignIdtoObject() {
    Category testCategory = new Category("Fiction");
    testCategory.save();
    Category savedCategory = Category.all().get(0);
    assertEquals(savedCategory, testCategory);
  }

  @Test
  public void find_findCategoryInDatabase_true() {
    Category testCategory = new Category("Fiction");
    testCategory.save();
    Category savedCategory = Category.find(testCategory.getId());
    assertTrue(testCategory.equals(savedCategory));
  }

  @Test
  public void update_updateCategoryName_true() {
    Category testCategory = new Category("Fiction");
    testCategory.save();
    testCategory.update("Non-Fiction");
    assertEquals("Non-Fiction", Category.find(testCategory.getId()).getName());
  }

  @Test
  public void delete_deleteCategory_true() {
    Category testCategory = new Category("Fiction");
    testCategory.save();
    testCategory.delete();
    assertEquals(0, Category.all().size());
  }
}
