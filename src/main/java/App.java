import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;
import java.util.List;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/recipes.vtl");
      List<Recipe> recipes = Recipe.all();
      model.put("recipes", recipes);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/recipes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/recipes.vtl");
      String nameInput = request.queryParams("name");
      String ingredientsInput = request.queryParams("ingredients");
      String directionsInput = request.queryParams("directions");
      Recipe newRecipe = new Recipe(nameInput, ingredientsInput, directionsInput);
      newRecipe.save();
      List<Recipe> recipes = Recipe.all();
      model.put("recipes", recipes);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/tags", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/tags.vtl");
      List<Tag> tags = Tag.all();
      model.put("tags", tags);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tags", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/tags.vtl");
      String tagInput = request.queryParams("tag");
      Tag newTag = new Tag(tagInput);
      newTag.save();
      List<Tag> tags = Tag.all();
      model.put("tags", tags);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());



  }
}
