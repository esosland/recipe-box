import org.junit.*;
import static org.junit.Assert.*;

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_instantiatesCorrectly_WithNameIngredientsDirections() {
  	Recipe testRecipe = new Recipe("Chocolate Chip cookies", "cookie stuff", "make cookies");
  	assertTrue(testRecipe instanceof Recipe);
  	assertTrue(testRecipe.getName() == "Chocolate Chip cookies");
  }
  //
  // @Test
  // public void all_listEmptyAtFirst_true() {
  // 	assertTrue(Recipe.all().size() == 0);
  // }


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
