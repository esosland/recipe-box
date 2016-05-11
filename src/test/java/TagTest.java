import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class TagTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Tag_instantiatesCorrectly_WithType() {
  	Tag testTag = new Tag("Dessert");
  	assertTrue(testTag instanceof Tag);
  	assertTrue(testTag.getType() == "Dessert");
  }

  @Test
  public void all_listEmptyAtFirst_true() {
  	assertTrue(Tag.all().size() == 0);
  }


  @Test
  public void save_savesTagToDatabase_true() {
    Tag testTag = new Tag("Dessert");
    testTag.save();
    String expected = Tag.all().get(0).getType();
    assertEquals(expected, "Dessert");
  }

  @Test
  public void find_findsTagInDatabase_Tag() {
    Tag testTag = new Tag("dessert");
    testTag.save();
    Tag savedTag = Tag.find(testTag.getId());
    assertTrue(savedTag.equals(testTag));
  }

  @Test
  public void delete_deletesTagFromDatabase_true() {
    Tag testTag = new Tag("Dessert");
    testTag.save();
    testTag.delete();
    assertTrue(Tag.all().isEmpty());
  }

  @Test
  public void update_updatesTag_true() {
    Tag testTag = new Tag("dessert");
    testTag.save();
    testTag.update("Ice Cream");
    assertEquals("Ice Cream", Tag.find(testTag.getId()).getType());
  }

  @Test
  public void addRecipe_addsRecipeToTag_true() {
    Tag myTag = new Tag("Mexican");
    myTag.save();
    Recipe myRecipe = new Recipe("Enchiladas", "enchilada stuff", "make enchiladas");
    myRecipe.save();
    myTag.addRecipe(myRecipe);
    Recipe savedRecipe = myTag.getRecipes().get(0);
    assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void getRecipes_returnsAllRecipes_List() {
    Tag myTag = new Tag("Mexican");
    myTag.save();
    Recipe myRecipe = new Recipe("Enchiladas", "enchilada stuff", "make enchiladas");
    myRecipe.save();
    myTag.addRecipe(myRecipe);
    List savedRecipes = myTag.getRecipes();
    assertEquals(1, savedRecipes.size());
  }



}
