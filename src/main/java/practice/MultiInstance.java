package practice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiInstance {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    Vertx vertx2 = Vertx.vertx();

    vertx.deployVerticle(new Demo1());
    vertx2.deployVerticle(new Demo2());
  }

  static class Demo1 extends AbstractVerticle {
    public static final Logger LOG = LoggerFactory.getLogger(Demo1.class);
    public void start() {
      LOG.info("Hello World");


      vertx.eventBus().send("Hello", "World");
    }
  }

  static class Demo2 extends AbstractVerticle {
    public static final Logger LOG = LoggerFactory.getLogger(Demo2.class);
    public void start() {
      LOG.info("Hello World");

      vertx.eventBus().consumer("Hello", msg -> {
        LOG.info("Hello JAy");
      });
    }
  }
}
