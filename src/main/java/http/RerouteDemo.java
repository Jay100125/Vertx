package http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class RerouteDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(RerouteDemo.class.getName());
  }

  public void start()
  {
    Router router = Router.router(vertx);

    router.get("/jay").handler(ctx -> ctx.response().end("Hello JAY!"));
    router.get("/some").handler(ctx ->
      {
        ctx.response().setChunked(true);
        ctx.response().write("Hello World!");

        ctx.reroute("/jay");
      }
    );


    vertx.createHttpServer().requestHandler(router).listen(8080).onComplete(result -> {
      if(result.succeeded()) {

        System.out.println(Thread.currentThread().getName() + " HTTP server started on port 8080");
      } else {
        System.err.println("Failed to start HTTP server: " + result.cause());
      }
    });
  }
}
