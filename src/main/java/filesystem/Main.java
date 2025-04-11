package filesystem;

import io.vertx.core.Context;
import io.vertx.core.Vertx;

import java.io.ObjectInputFilter;

public class Main {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new FileDemo()).onFailure(result -> {
      System.out.println("Result " + result.getMessage());
    });

  }
}
