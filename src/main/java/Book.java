import org.sql2o.*;
// import java.util.Arrays;
import java.util.List;

public class Book {
  private int id;
  private String title;

  public Book(String title) {
    this.title = title;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public static List<Book> all() {
    String sql = "SELECT * FROM books";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  @Override
  public boolean equals(Object otherBook) {
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books (title) VALUES (:title)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .executeUpdate()
        .getKey();
    }
  }

  public static Book find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books WHERE id=:id";
      Book newBook = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
      return newBook;
    }
  }

  public void update(String title) {
    if (!(title.equals(""))) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE books SET title = :update_title WHERE id=:id";
        con.createQuery(sql)
          .addParameter("update_title", title)
          .addParameter("id", this.getId())
          .executeUpdate();
      }
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM books WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

}
