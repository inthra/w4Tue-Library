import org.sql2o.*;
import java.util.Arrays;
import java.util.List;

public class Category {
  private int id;
  private String name;

  public Category(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public static List<Category> all() {
    String sql = "SELECT * FROM categories";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }

  @Override
  public boolean equals(Object otherCategory) {
    if (!(otherCategory instanceof Category)) {
      return false;
    } else {
      Category newCategory = (Category) otherCategory;
      return this.getName().equals(newCategory.getName());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Category find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM categories WHERE id=:id";
      Category newCategory = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Category.class);
      return newCategory;
    }
  }

  public void update(String name) {
    if (!(name.equals(""))) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE categories SET name = :update_name WHERE id=:id";
        con.createQuery(sql)
          .addParameter("update_name", name)
          .addParameter("id", this.getId())
          .executeUpdate();
      }
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM categories WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

}
