package timer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OneShotTimer extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(OneShotTimer.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    LOG.info("Starting OneShotTimer");
    LOG.info("Deploying the Verticle");
    vertx.deployVerticle(new OneShotTimer());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.setTimer(1000, res -> {
      LOG.info("OneShotTimer ");
      vertx.fileSystem().createFile("./src/Jay.txt", result -> {
        if (result.succeeded()) {
          LOG.info("File created successfully.");
        } else {
          LOG.error("Failed to create file", result.cause());
        }
      });
    });

    // Offload blocking task
    vertx.executeBlocking(promise -> {
      for (int i = 0; i < Integer.MAX_VALUE; i++) {
        for (int j = 0; j < Integer.MAX_VALUE; j++) {
          LOG.info("Thread in the while loop: " + Thread.currentThread().getName());
          LOG.info("Start method");
        }
      }
      promise.complete();
    }, res -> {
      // Done with blocking task
      LOG.info("Blocking task done.");
    });

    LOG.info("OneShotTimer started.");

    startPromise.complete();
  }


  @Override
  public void stop() {
    LOG.info("Undeploying the verticle");
  }
}
