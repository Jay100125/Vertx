package executeblocking;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerExecutorDemo extends AbstractVerticle {
  public static final Logger LOG = LoggerFactory.getLogger(WorkerExecutorDemo.class);
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(WorkerExecutorDemo.class.getName(), new DeploymentOptions().setInstances(3));
    vertx.deployVerticle(demo1.class, new DeploymentOptions().setInstances(8)).onFailure(res ->{
      LOG.info("Verticle deployment failed "+ res.getMessage());
    });
  }

  public void start() {
    LOG.info("Starting WorkerExecutorDemo");
    WorkerExecutor executor = vertx.createSharedWorkerExecutor("my-pool");
    executor.executeBlocking(future -> {
        LOG.info("Executing blocking worker executor");
    }).onComplete(res ->
    {
      LOG.info("Executed blocking worker executor");
    });
  }

 public static class demo1 extends AbstractVerticle {
    public void start() {
      LOG.info("Starting Hello");
    }
  }
}
