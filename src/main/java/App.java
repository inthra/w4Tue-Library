import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("categories", Category.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/categories/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Category category = Category.find(Integer.parseInt(request.params(":id")));
      model.put("category", category);
      model.put("template", "templates/category.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/patron", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/patron.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/patron", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String first_name = request.queryParams("first_name");
      String last_name = request.queryParams("last_name");
      Patron patron = Patron.findName(first_name, last_name);
      String url = String.format("templates/patron/%d.vtl", patron.getId());
      model.put("template", url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // get("/patron/:id", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   Patron patron = Patron.find(Integer.parseInt(request.params(":id")));
    //   model.put("patron", patron);
    //
    // })

  }
}
