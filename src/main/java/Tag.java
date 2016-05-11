import java.util.List;
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

	public void save() {
		try(Connection con = DB.sql2o.open()) {
			String sql = "INSERT INTO tags (type) VALUES (:type)";
			this.id = (int) con.createQuery(sql, true)
				.addParameter("type", this.type)
				.executeUpdate()
				.getKey();
		}
	}

	public static List<Tag> all() {
		String sql = "SELECT * FROM tags";
		try(Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(Tag.class);
		}
	}


}
