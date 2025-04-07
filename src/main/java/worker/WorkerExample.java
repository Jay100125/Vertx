package worker;

import com.example.starter.MainVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerExample extends AbstractVerticle {

  private static final Logger LOGGER  = LoggerFactory.getLogger(WorkerExample.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerExample());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();

    vertx.executeBlocking(event -> {
        LOGGER.debug("Hello World " + Thread.currentThread().getName());
      try {
        Thread.sleep(5000);
        event.complete();
      } catch (InterruptedException e) {
        event.fail(e);
      }
    }, result -> {
      if(result.succeeded()) {
        LOGGER.debug("Success " + Thread.currentThread().getName());
      }
      else
      {
        System.out.println("Failure");
      }
      }
    );
  }
}
