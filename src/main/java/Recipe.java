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


}
