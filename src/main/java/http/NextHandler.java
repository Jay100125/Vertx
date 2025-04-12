package http;

import cluster.Person;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NextHandler extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(NextHandler.class);

  private final JsonObject res = new JsonObject();

  public void start()
  {

    Router router = Router.router(vertx);

    router.get("/json").respond(ctx -> Future.succeededFuture(new JsonObject().put("Hello", "World!")));

    router.route("/class").respond(ctx -> Future.succeededFuture(new Person()));
    router.route().handler(routingContext -> {
      res.clear();
      routingContext.response().setStatusCode(200).setChunked(true).write(res.put("Hello", "World!").encode());

    });



    router.route().handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response.write("Shōgun\n");
      LOG.info("Just checking for second time");

      // Call the next matching route after a 5 second delay
      routingContext.vertx().setTimer(5000, tid -> routingContext.next());
    });


    router.route().handler(ctx -> {

      HttpServerResponse response = ctx.response();
      response.write("Game of Thrones\n");
      LOG.info("Just checking for Third time");

      // Now end the response
      ctx.response().end();
    });



    vertx.createHttpServer().requestHandler(router).listen(8080).onComplete(result -> {
      if(result.succeeded()) {

        System.out.println(Thread.currentThread().getName() + " HTTP server started on port 8080");
      } else {
        System.err.println("Failed to start HTTP server: " + result.cause());
      }

    });


  }
}
