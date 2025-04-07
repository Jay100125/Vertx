package eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseExample extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(ResponseExample.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    var eventBus = vertx.eventBus();
    eventBus.consumer("my.request.address", message ->{
      LOG.info("Received message " + message.body().toString() + " On Thread " + Thread.currentThread().getName());
      message.reply("Hello World");
      LOG.info("Message send successfully");
    });
  }
}
