package http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

public class SimpleHttpServer extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new SimpleHttpServer());
  }

  @Override
  public void start() throws InterruptedException {
    HttpServer server = vertx.createHttpServer();

    server.requestHandler(request -> {
      // This handler gets called for each request that arrives on the server
      HttpServerResponse response = request.response();
      response.putHeader("content-type", "text/plain");

      // Write to the response and end it
      response.end("Hello World!");
    });

    server.listen(8080, result -> {
      if (result.succeeded()) {
        System.out.println("HTTP server started on port 8080");
      } else {
        System.err.println("Failed to start HTTP server: " + result.cause());
      }
    });
  }
}
