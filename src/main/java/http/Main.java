package http;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Main {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(NextHandler.class.getName(), new DeploymentOptions().setInstances(1));
  }
}
