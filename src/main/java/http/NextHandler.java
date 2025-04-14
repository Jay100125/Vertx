package http;

import cluster.Person;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.CookieHandler;

public class NextHandler extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(NextHandler.class);

  private final JsonObject res = new JsonObject();

  public void start()
  {

    Router router = Router.router(vertx);

    router.get("/json").respond(ctx -> Future.succeededFuture(new JsonObject().put("Hello", "World!")));

//    router.route().order(0).respond(ctx -> Future.succeededFuture(new Person()));
    router.route().order(0).handler(routingContext -> {
      res.clear();
      routingContext.response().setStatusCode(200).setChunked(true).write(res.put("Hello", "World!").encode());
      routingContext.vertx().setTimer(1000, id -> routingContext.next());

    });

    router.route().order(2).handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response.write("Shōgun\n");
      LOG.info("Just checking for second time");


      // Now end the response
      routingContext.response().end();
    });


    router.route().order(1).handler(ctx -> {

      HttpServerResponse response = ctx.response();
      response.write("Game of Thrones\n");
      LOG.info("Just checking for Third time");

      // Call the next matching route after a 5 second delay
      ctx.vertx().setTimer(5000, tid -> ctx.next());

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
