import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Book {
  private int id;
  private String title;
  private int category_id;

  public Book(String title, int category_id) {
    this.title = title;
    this.category_id = category_id;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public int getCategoryId() {
    return category_id;
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
      String sql = "INSERT INTO books (title, category_id) VALUES (:title, :category_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("category_id", this.category_id)
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
      String deleteQuery = "DELETE FROM books WHERE id=:id";
      con.createQuery(deleteQuery)
        .addParameter("id", this.getId())
        .executeUpdate();

      String joinDeleteQuery = "DELETE FROM authors_books WHERE book_id=:book_id";
      con.createQuery(joinDeleteQuery)
        .addParameter("book_id", this.getId())
        .executeUpdate();
    }
  }

  public void addAuthor(Author author) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors_books (book_id, author_id) VALUES (:book_id, :author_id)";
      con.createQuery(sql)
        .addParameter("book_id", this.getId())
        .addParameter("author_id", author.getId())
        .executeUpdate();
    }
  }

  public List<Author> getAuthors() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT author_id FROM authors_books WHERE book_id = :book_id";
      List<Integer> authorIds = con.createQuery(joinQuery)
        .addParameter("book_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Author> authors = new ArrayList<Author>();

      for (Integer authorId : authorIds) {
        String authorQuery = "SELECT * FROM authors WHERE id = :authorId";
        Author author = con.createQuery(authorQuery)
          .addParameter("authorId", authorId)
          .executeAndFetchFirst(Author.class);
        authors.add(author);
      }
      return authors;
    }
  }


}
