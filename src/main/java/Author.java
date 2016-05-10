import org.sql2o.*;
// import java.util.Arrays;
import java.util.List;

public class Author {
  private int id;
  private String first_name;
  private String last_name;

  public Author(String first_name, String last_name) {
    this.first_name = first_name;
    this.last_name = last_name;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return first_name;
  }

  public String getLastName() {
    return last_name;
  }

  public static List<Author> all() {
    String sql = "SELECT * FROM authors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }

  @Override
  public boolean equals(Object otherAuthor) {
    if (!(otherAuthor instanceof Author)) {
      return false;
    } else {
      Author newAuthor = (Author) otherAuthor;
      return this.getFirstName().equals(newAuthor.getFirstName()) && this.getLastName().equals(newAuthor.getLastName());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors (first_name, last_name) VALUES (:first_name, :last_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("first_name", this.first_name)
        .addParameter("last_name", this.last_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Author find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM authors WHERE id=:id";
      Author newAuthor = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Author.class);
      return newAuthor;
    }
  }

  public void update(String first_name, String last_name) {
    if (!(first_name.equals(""))) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE authors SET (first_name, last_name) = (:update_first_name, :update_last_name) WHERE id=:id";
        con.createQuery(sql)
          .addParameter("update_first_name", first_name)
          .addParameter("update_last_name", last_name)
          .addParameter("id", this.getId())
          .executeUpdate();
      }
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM authors WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

}
