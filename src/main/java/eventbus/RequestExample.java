package eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestExample extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(RequestExample.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    var eventBus = vertx.eventBus();
    final String message = "Hello World!";
    LOG.info(message + " on " + Thread.currentThread().getName());
    eventBus.request("my.request.address", message, reply -> {
      LOG.info("Received reply: " + reply.result().body().toString() + " On Thread " + Thread.currentThread().getName());
    });
  }
}
