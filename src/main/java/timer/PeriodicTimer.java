package timer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeriodicTimer extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(PeriodicTimer.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new PeriodicTimer());
  }

  public void start() {

    LOG.info("PeriodicTimer started");

    vertx.setPeriodic(1000, l -> {
      vertx.executeBlocking(future -> {
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        LOG.info("THis is how it works");
      }, false);

    });
  }
}
