import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      String deleteBooksQuery = "DELETE FROM books *;";
      String deleteAuthorsQuery = "DELETE FROM authors *;";
      String deletePatronsQuery = "DELETE FROM patrons *;";
      String deletePatronsBooksQuery = "DELETE FROM patrons_books *;";
      String deleteAuthorsBooksQuery = "DELETE FROM authors_books *;";
      con.createQuery(deleteCategoriesQuery).executeUpdate();
      con.createQuery(deleteBooksQuery).executeUpdate();
      con.createQuery(deleteAuthorsQuery).executeUpdate();
      con.createQuery(deletePatronsQuery).executeUpdate();
      con.createQuery(deletePatronsBooksQuery).executeUpdate();
      con.createQuery(deleteAuthorsBooksQuery).executeUpdate();
    }
  }

}
