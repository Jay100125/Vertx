package http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimplePostServer extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(SimplePostServer.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(SimplePostServer.class.getName(), new DeploymentOptions().setInstances(2));
  }


  public void start() throws Exception {
    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());


    router.route(HttpMethod.POST, "/api/messages").handler(this::handlePostMessage);

    router.route(HttpMethod.POST, "/api/users").handler(this::handleCreateUser);

    // Create the HTTP server and tell it to use our router to handle requests
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080, result -> {
        if (result.succeeded()) {
          System.out.println("Server is now listening on port 8080");
        } else {
          System.out.println("Failed to start server: " + result.cause());
        }
      });
  }

  private void handlePostMessage(RoutingContext context) {
    JsonObject body = context.getBodyAsJson();

    String message = body.getString("message");
    if(body == null)
    {
      context.response().setStatusCode(400).end(new JsonObject().put("status", "bad request").put("Error", "Something gone wrong").encodePrettily());
      return;
    }

    JsonObject response = new JsonObject().put("Success", true).put("Message" , "Message received " + message).put("timestamp", System.currentTimeMillis()).put("Current Thread", Thread.currentThread().getName());

    try
    {

      Thread.sleep(10000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    context.response().putHeader("content-type", "application/json").end(response.encode());
  }


  private void handleCreateUser(RoutingContext ctx) {
    // Get the request body as a JsonObject
    JsonObject requestBody = ctx.getBodyAsJson();

    if (requestBody == null) {
      ctx.response()
        .setStatusCode(400)
        .putHeader("Content-Type", "application/json")
        .end(new JsonObject()
          .put("success", false)
          .put("error", "Body must be valid JSON")
          .encode());
      return;
    }

    // Extract user data
    String name = requestBody.getString("name");
    String email = requestBody.getString("email");

    if (name == null || email == null) {
      ctx.response()
        .setStatusCode(400)
        .putHeader("Content-Type", "application/json")
        .end(new JsonObject()
          .put("success", false)
          .put("error", "Fields 'name' and 'email' are required")
          .encode());
      return;
    }


    // Create response with user data and ID
    JsonObject response = new JsonObject()
      .put("success", true)
      .put("message", "User created successfully")
      .put("user", new JsonObject()
        .put("id", "usr_" + System.currentTimeMillis())
        .put("name", name)
        .put("email", email));

    // Send response
    ctx.response()
      .putHeader("Content-Type", "application/json")
      .end(response.encode());
  }
}

