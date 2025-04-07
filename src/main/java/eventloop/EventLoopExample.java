package eventloop;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EventLoopExample extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventLoopExample.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx(
      new VertxOptions().setMaxEventLoopExecuteTime(500)
        .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
        .setBlockedThreadCheckInterval(1)
        .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
    );
    vertx.deployVerticle(new EventLoopExample());
  }

  public void start(final Promise<Void> startPromise) throws Exception {
    LOGGER.info("EventLoopExample started");
    LOGGER.debug("start {}", getClass().getName()); // Fixed format string
    startPromise.complete();
    // Uncomment to test event loop blocking warning
    // Thread.sleep(10000);
  }
}
