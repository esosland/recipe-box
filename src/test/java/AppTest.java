
import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

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
    assertThat(pageSource()).contains("Recipe Box!");
  }

  @Test
  public void recipesAreListedTest() {
    Recipe testRecipe = new Recipe("Burrito", "burrito stuff", "wrap it up and eat it.");
    testRecipe.save();
    goTo("http://localhost:4567/recipes");
    assertThat(pageSource()).contains("Burrito");
  }

  @Test
  public void tagIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Tags"));
    fill("#tag").with("Desserts");
    submit(".btn");
    assertThat(pageSource()).contains("Desserts");
  }

  @Test
  public void recipeIsAddedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Recipes"));
    fill("#name").with("Rice");
    fill("#ingredients").with("Uncooked rice and water");
    fill("#directions").with("turn on rice cooker. ???. Profit.");
    submit(".btn");
    assertThat(pageSource()).contains("Rice");
  }
}
