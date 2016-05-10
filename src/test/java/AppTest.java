import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
  goTo("http://localhost:4567/");
  assertThat(pageSource()).contains("Library");
  }

  @Test
  public void categoriesIsDisplayedTest() {
  Category testCategory = new Category("Cartoon");
  testCategory.save();
  goTo("http://localhost:4567/");
  assertThat(pageSource()).contains("Cartoon");
  }

  @Test
  public void patronSignInIsDisplayedTest() {
  Patron testPatron = new Patron("Perry", "Smith");
  testPatron.save();
  goTo("http://localhost:4567/patron");
  fill("#first_name").with("Perry");
  fill("#last_name").with("Smith");
  submit(".btn");
  assertThat(pageSource()).contains("Perry Smith");
  }

}
