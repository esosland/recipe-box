import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Tag {
	private String type;
	private int id;

	public Tag(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

  public int getId() {
		return id;
	}

	public void save() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO tags (type) VALUES (:type)";
			this.id = (int) con.createQuery(sql, true)
				.addParameter("type", this.type)
				.executeUpdate()
				.getKey();
		}
	}

  public void delete() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "DELETE FROM tags WHERE id=:id";
			con.createQuery(sql)
				.addParameter("id", id)
				.executeUpdate();
		}
	}

	public static List<Tag> all() {
		String sql = "SELECT * FROM tags";
		try(Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(Tag.class);
		}
	}

  public static Tag find(int id) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM tags WHERE id=:id";
			Tag tag = con.createQuery(sql)
				.addParameter("id", id)
				.executeAndFetchFirst(Tag.class);
				return tag;
		}
	}

  @Override
  public boolean equals(Object otherTag) {
    if (!(otherTag instanceof Tag)) {
      return false;
    } else {
      Tag newTag = (Tag) otherTag;
      return this.getType().equals(newTag.getType()) &&
      this.getId() == newTag.getId();
    }
  }

  public void update(String newType) {
		try(Connection con = DB.sql2o.open()) {
			String updateType = "UPDATE tags SET type = :newType WHERE id = :id";

      con.createQuery(updateType)
        .addParameter("newType", newType)
        .addParameter("id", this.id)
        .executeUpdate();
      }
    }

    public void addRecipe(Recipe recipe) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO recipes_tags (tag_id, recipe_id) VALUES (:tag_id, :recipe_id)";
        con.createQuery(sql)
          .addParameter("tag_id", this.getId())
          .addParameter("recipe_id", recipe.getId())
          .executeUpdate();
      }
    }


    public List<Recipe> getRecipes() {
  try(Connection con = DB.sql2o.open()){
    String joinQuery = "SELECT recipe_id FROM recipes_tags WHERE tag_id = :tag_id";
    List<Integer> recipeIds = con.createQuery(joinQuery)
      .addParameter("tag_id", this.getId())
      .executeAndFetch(Integer.class);

    List<Recipe> recipes = new ArrayList<Recipe>();

    for (Integer recipeId : recipeIds) {
      String recipeQuery = "SELECT * FROM recipes WHERE id = :recipeId";
      Recipe recipe = con.createQuery(recipeQuery)
        .addParameter("recipeId", recipeId)
        .executeAndFetchFirst(Recipe.class);
      recipes.add(recipe);
    }
    return recipes;
  }
}

}
