package practice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerDemo {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    DeploymentOptions options = new DeploymentOptions()
      .setWorker(true)
      .setWorkerPoolSize(2)
      .setInstances(12);

    vertx.deployVerticle(Worker.class.getName(), options);
  }

  public static class Worker extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(Worker.class);
    @Override
    public void start() {
      LOG.info("Starting worker...");
    }
  }
}
