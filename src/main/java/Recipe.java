import java.util.List;
import org.sql2o.*;

public class Recipe {
	private String name;
	private int id;
	private String ingredients;
	private String directions;

	public Recipe(String name, String ingredients, String directions) {
		this.name = name;
		this.ingredients = ingredients;
		this.directions = directions;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String getIngredients() {
		return ingredients;
	}

	public String getDirections() {
		return directions;
	}

	public void save() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO recipes (name, ingredients, directions) VALUES (:name, :ingredients, :directions)";
			this.id = (int) con.createQuery(sql, true)
				.addParameter("name", this.name)
				.addParameter("ingredients", this.ingredients)
				.addParameter("directions", this.directions)
				.executeUpdate()
				.getKey();
		}
	}

	public void delete() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "DELETE FROM recipes WHERE id=:id";
			con.createQuery(sql)
				.addParameter("id", id)
				.executeUpdate();
		}
	}

	public static List<Recipe> all() {
		String sql = "SELECT * FROM recipes";
		try(Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(Recipe.class);
		}
	}

	public static Recipe find(int id) {
		try(Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM recipes WHERE id=:id";
			Recipe recipe = con.createQuery(sql)
				.addParameter("id", id)
				.executeAndFetchFirst(Recipe.class);
				return recipe;
		}
	}

	@Override
	public boolean equals(Object otherRecipe) {
		if (!(otherRecipe instanceof Recipe)) {
			return false;
		} else {
			Recipe newRecipe = (Recipe) otherRecipe;
			return this.getName().equals(newRecipe.getName()) &&
			this.getId() == newRecipe.getId() &&
			this.getIngredients().equals(newRecipe.getIngredients()) &&
			this.getDirections().equals(newRecipe.getDirections());
		}
	}

	public void update(String newName, String newIngredients, String newDirections) {
		try(Connection con = DB.sql2o.open()) {
			String updateName = "UPDATE recipes SET name = :newName WHERE id = :id";
			String updateIngredients = "UPDATE recipes SET ingredients = :newIngredients WHERE id = :id";
			String updateDirections = "UPDATE recipes SET directions = :newDirections WHERE id = :id";

			con.createQuery(updateName)
				.addParameter("newName", newName)
				.addParameter("id", this.id)
				.executeUpdate();

			con.createQuery(updateIngredients)
				.addParameter("newIngredients", newIngredients)
				.addParameter("id", this.id)
				.executeUpdate();

			con.createQuery(updateDirections)
				.addParameter("newDirections", newDirections)
				.addParameter("id", this.id)
				.executeUpdate();
		}
	}


}
