package http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleHttpServer extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(SimpleHttpServer.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(SimpleHttpServer.class.getName(), new DeploymentOptions().setInstances(5));
  }

  @Override
  public void start() throws InterruptedException {
//    HttpServer server = vertx.createHttpServer();
//
//    server.requestHandler(request -> {
//      // This handler gets called for each request that arrives on the server
//      HttpServerResponse response = request.response();
//
//      System.out.println("recived bby 1");
//
//      // Write to the response and end it
//      response.end("Hello World!");
//    });
//
//    server.listen(8080, result -> {
//      if (result.succeeded()) {
//        System.out.println("HTTP server started on port 8080");
//      } else {
//        System.err.println("Failed to start HTTP server: " + result.cause());
//      }
//    });

    Router router = Router.router(vertx);

    router.route("/:id").handler(routingContext -> {
      String id = routingContext.request().getParam("id");
      LOG.info("Received request for id {}", id);
      if(id.equals("1"))
      {
        LOG.info("id:  "+id);
        try {
          Thread.sleep(30000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      LOG.info("recived bby 2");

      routingContext.response().end("Hello World " + Thread.currentThread().getName());

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
