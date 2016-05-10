import org.sql2o.*;
// import java.util.Arrays;
import java.util.List;

public class Patron {
  private int id;
  private String first_name;
  private String last_name;

  public Patron(String first_name, String last_name) {
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

  public static List<Patron> all() {
    String sql = "SELECT * FROM patrons";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patron.class);
    }
  }

  @Override
  public boolean equals(Object otherPatron) {
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getFirstName().equals(newPatron.getFirstName()) && this.getLastName().equals(newPatron.getLastName());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons (first_name, last_name) VALUES (:first_name, :last_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("first_name", this.first_name)
        .addParameter("last_name", this.last_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Patron find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patrons WHERE id=:id";
      Patron newPatron = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Patron.class);
      return newPatron;
    }
  }

  public void update(String first_name, String last_name) {
    if (!(first_name.equals(""))) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE patrons SET (first_name, last_name) = (:update_first_name, :update_last_name) WHERE id=:id";
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
      String sql = "DELETE FROM patrons WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

}
