package practice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteBlock extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(ExecuteBlock.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ExecuteBlock());
  }

  @Override
  public void start() {
    LOG.info("Starting executeBlocking demo...");

    for (int i = 1; i <= 5; i++) {
      int taskId = i;

      vertx.<String>executeBlocking(promise -> {
        LOG.info("Blocking task {} started on thread {}", taskId, Thread.currentThread().getName());
        try {
          Thread.sleep(10000); // Simulate blocking work
        } catch (InterruptedException e) {
          promise.fail(e);
        }
        LOG.info("Blocking task {} finished", taskId);
        promise.complete("Task " + taskId + " done");

      }, false,result -> {  // Change to true to preserve order
        if (result.succeeded()) {
          LOG.info("Result received: {}", result.result());
        } else {
          LOG.error("Failed: {}", result.cause().getMessage());
        }
      });
    }
  }
}
