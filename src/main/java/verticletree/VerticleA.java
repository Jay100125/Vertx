package verticletree;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleA extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(VerticleA.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.info("Starting VerticleA " + getClass().getSimpleName());

    long timerID = vertx.setPeriodic(1000, id -> {
      LOG.info("And every second this is printed");
    });

    LOG.info("First this is printed");
    startPromise.complete();

  }

  public void stop(Promise<Void> stopPromise) throws Exception {
    LOG.info("Stopping VerticleA " + getClass().getSimpleName());
    stopPromise.complete();
  }
}
