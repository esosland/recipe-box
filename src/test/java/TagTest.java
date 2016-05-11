import org.junit.*;
import static org.junit.Assert.*;

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
  public void delete_deletesTagFromDatabase_true() {
    Tag testTag = new Tag("Dessert");
    testTag.save();
    testTag.delete();
    assertTrue(Tag.all().isEmpty());
  }

}
