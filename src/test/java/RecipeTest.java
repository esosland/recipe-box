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

  @Test
  public void all_listEmptyAtFirst_true() {
  	assertTrue(Recipe.all().size() == 0);
  }


  @Test
  public void save_savesRecipeToDatabase_true() {
    Recipe testRecipe = new Recipe("food", "list of great ingredients", "cook me");
    testRecipe.save();
    String expected = Recipe.all().get(0).getName();
    assertEquals(expected, "food");
  }

  @Test
  public void delete_deletesRecipeFromDatabase_true() {
    Recipe testRecipe = new Recipe("food", "list of great ingredients", "cook me");
    testRecipe.save();
    testRecipe.delete();
    assertTrue(Recipe.all().isEmpty());
  }

  @Test
  public void find_findsRecipeInDatabase_Recipe() {
    Recipe testRecipe = new Recipe("food", "list of great ingredients", "cook me");
    testRecipe.save();
    Recipe savedRecipe = Recipe.find(testRecipe.getId());
    assertTrue(savedRecipe.equals(testRecipe));
  }

  @Test
  public void update_updatesRecipe_true() {
    Recipe testRecipe = new Recipe("food", "list of great ingredients", "cook me");
    testRecipe.save();
    testRecipe.update("Chocolate Chip cookies", "cookie stuff", "make cookies");
    assertEquals("Chocolate Chip cookies", Recipe.find(testRecipe.getId()).getName());
  }

}
