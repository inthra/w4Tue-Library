import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class CategoryTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void CategoryTest_instantiatesCorrectly_true() {
    Category testCategory = new Category("Fiction");
    assertTrue(testCategory instanceof Category);
  }

}
