import org.junit.*;
import static org.junit.Assert.*;

public class RecipeTest {

  @Test
  public void save_savesRecipeToDatabase_true() {
    Recipe testRecipe = new Recipe("food", "list of great ingredients", "cook me");
    testRecipe.save();
    String expected = Recipe.all().get(0).getName();
    assertEquals(expected, "food");
  }

  // @Test
  // public void function_testdescription_expected() {
  //   Tamagotchi myPet = new Tamagotchi("lil dragon");
  //   assertEquals("lil dragon", myPet.getName());
  //}
}
